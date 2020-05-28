package com.sinolife.lem.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sinolife.lem.base.dao.mapper.BaseInfoDao;
import com.sinolife.lem.wx.utils.Constants;
import com.sinolife.sf.config.RuntimeConfig;
import com.sinolife.util.CfgContants;
import com.sinolife.util.SerializeUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.ZParams;

/**
 * redis处理类方法集合
 * 
 * @author peng.duan
 * 
 */
@Component("redisClient")
public class RedisClient {

	private JedisPool jedisPool;// 非切片连接池

	private JedisSentinelPool jedisSentinelPool = null;
	
	private static final String OK = "OK";

	/**
	 * 日志
	 */
	private final static Logger logger = LoggerFactory.getLogger(RedisClient.class);

	@Autowired
	BaseInfoDao baseInfoDao;

	public JedisPool getJedisPool() {
		if (this.jedisPool == null) {
			synchronized (RedisClient.class) {
				if (jedisPool == null) {
					this.jedisPool = this.initialPool();
				}
			}
		}
		return this.jedisPool;
	}

	public Jedis getJedis() {
		String value = baseInfoDao
				.selectFunctionSwitch(Constants.FUNCTION_SWITCH + "_62");
		if (StringUtil.isNotNull(value) && Constants.SWITCH_ON.equals(value)) {
			return this.getJedisSentinelPool().getResource();
		} else {
			return this.getJedisPool().getResource();
		}
	}
 
	/**
	 * 初始化非切片池
	 */
	public JedisPool initialPool() {

		String redisIp = RuntimeConfig.get(CfgContants.redis_host_ip,
				String.class);
		String redisProt = RuntimeConfig.get(CfgContants.redis_host_port,
				String.class);
		String passWord = RuntimeConfig.get(CfgContants.redis_host_passWord,
				String.class);
		return this.initialPool(redisIp, redisProt, passWord);
	}

	/**
	 * 初始化非切片池
	 */
	public JedisPool initialPool(String redisIp, String redisProt,
			String passWord) {
		// 池基本配置
		JedisPoolConfig config = new JedisPoolConfig();

		config.setTestWhileIdle(true);
		// 控制一个pool最多有多少个状态为idle的jedis实例
		config.setMaxTotal(5000);
		// 最大能够保持空闲状态的对象数
		config.setMaxIdle(300);
		config.setMinIdle(10);
		config.setMaxWaitMillis(1000 * 10);
		// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的
		config.setTestOnBorrow(true);
		// 在还会给pool时，是否提前进行validate操作
		config.setTestOnReturn(false);
		// 多长空闲时间之后回收空闲连接
		config.setMinEvictableIdleTimeMillis(60000);
		jedisPool = new JedisPool(config, redisIp, Integer.valueOf(redisProt),
				10000, passWord);
		return jedisPool;
	}

	// 获取哨兵连接池
	public JedisSentinelPool getJedisSentinelPool() {
		if (this.jedisSentinelPool == null) {
			synchronized (JedisSentinelPool.class) {
				if (jedisSentinelPool == null) {
					this.jedisSentinelPool = this.initialSentinelPool();
				}
			}
		}
		return this.jedisSentinelPool;
	}

	/**
	 * 初始化Sentinel连接池
	 */
	public JedisSentinelPool initialSentinelPool() {
		Set<String> sentinelsSet = new HashSet<String>();
		String sentinelMaster = RuntimeConfig.get(
				CfgContants.redis_host_sentinel_master, String.class);
		String sentinelSlave = RuntimeConfig.get(
				CfgContants.redis_host_sentinel_slave, String.class);
		String sentinelSlave1 = RuntimeConfig.get(
				CfgContants.redis_host_sentinel_slave1, String.class);
		String passWord = RuntimeConfig.get(CfgContants.redis_host_passWord,
				String.class);
		// 连接地址以及端口号,有多个就一次增加
		sentinelsSet.add(sentinelMaster);
		sentinelsSet.add(sentinelSlave);
		sentinelsSet.add(sentinelSlave1);
		return this.initialSentinelPool(sentinelsSet, passWord);
	}

	/**
	 * 初始化连接池
	 */
	public JedisSentinelPool initialSentinelPool(Set<String> sentinelsSet,
			String passWord) {
		// 池基本配置
		JedisPoolConfig config = new JedisPoolConfig();
		config.setTestWhileIdle(true);
		// 控制一个pool最多有多少个状态为idle的jedis实例
		config.setMaxTotal(5000);
		// 最大能够保持空闲状态的对象数
		config.setMaxIdle(300);
		config.setMinIdle(10);
		config.setMaxWaitMillis(1000 * 10);
		// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的
		config.setTestOnBorrow(true);
		// 在还会给pool时，是否提前进行validate操作
		config.setTestOnReturn(false);
		// 多长空闲时间之后回收空闲连接
		config.setMinEvictableIdleTimeMillis(60000);
		String sentinelName = RuntimeConfig.get(
				CfgContants.redis_host_sentinel_name, String.class);
		jedisSentinelPool = new JedisSentinelPool(sentinelName, sentinelsSet,
				config, passWord);

		return jedisSentinelPool;
	}

	/**
	 * 释放连接池
	 * 
	 * @param jedis
	 */
	private void returnResource(Jedis jedis) {
		if (jedis != null) {
			jedis.close();
		}
	}
	
	/********************** list数据部分   **********************/
	/**
	 * 往list对象赋值
	 * 
	 * @param listName
	 * @param value
	 */
	public long setListByName(String listName, String value) {
		Jedis jedis = null;
		long size = 0;
		try {
			jedis = getJedis();
			size = jedis.lpush(listName, value);
		} catch (Exception e) {
			logger.error("RedisClient setListByName : ", e);
		} finally {
			this.returnResource(jedis);
		}
		return size;
	}
	
	/**
	 * 往list里赋值，超出固定长度后移除首个值
	 * @param listName
	 * @param value
	 * @param length
	 * @return
	 */
	public long pushToListAndRmMore(String listName, String value,long length){
		Jedis jedis = null;
		long size = 0;
		try {
			jedis = getJedis();
			size = jedis.lpush(listName, value);
			long listLength = jedis.llen(listName);
			while(listLength > length){
				jedis.rpop(listName);
				listLength = jedis.llen(listName);
			}
		} catch (Exception e) {
			logger.error("RedisClient pushToListAndRmMore listName = {}, value = {}, length = {} ", listName, value, length, e);
		} finally {
			this.returnResource(jedis);
		}
		return size;
	}

	/**
	 * 批量添加数据
	 * 
	 * @param listName
	 * @param redList
	 */
	public void setListBatchByName(String listName, List<String> redList) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			String[] a = new String[redList.size()];
			redList.toArray(a);
			jedis.lpush(listName, a);
		} catch (Exception e) {
			logger.error("RedisClient setListBatchByName listName = {}, redList = {}", listName, redList, e);
		} finally {
			this.returnResource(jedis);
		}
	}
	
	/**
	 * 获取对象的值
	 * 
	 * @param listName
	 * @return
	 */
	public String getListItem(String listName) {
		Jedis jedis = null;
		String item = null;
		try {
			jedis = getJedis();
			item = jedis.rpop(listName);
		} catch (Exception e) {
			logger.error("RedisClient getListItem listName = {} ", listName, e);
		} finally {
			this.returnResource(jedis);
		}
		return item;
	}
	
	/**
	 * 获取list集合所有数据
	 * @param listName
	 * @return
	 */
	public List<String> getAllListByName(String listName) {
		Jedis jedis = null;
		List<String> list = null;
		try {
			jedis = getJedis();
			list = jedis.lrange(listName, 0, -1);
		} catch (Exception e) {
			logger.error("RedisClient getAllListByName listName = {}", listName, e);
		} finally {
			this.returnResource(jedis);
		}
		return list;
	}

	/**
	 * 判断一个list是否存在
	 * @param listName
	 * @return
	 */
	public boolean isExistsListWithListName(String listName) {
		Jedis jedis = null;
		long i = 0;
		try {
			jedis = getJedis();
			i = jedis.llen(listName);
		} catch (Exception e) {
			logger.error("RedisClient isExistsListWithListName listName = {}", listName, e);
		} finally {
			this.returnResource(jedis);
		}
		if (i == 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 获取指定list大小
	 * @param listName
	 * @return
	 */
	public long getListCount(String listName) {
		Jedis jedis = null;
		long val = 0;
		try {
			jedis = getJedis();
			val = jedis.llen(listName);
		} catch (Exception e) {
			logger.error("RedisClient getListCount listName = {}", listName, e);
		} finally {
			this.returnResource(jedis);
		}
		return val;
	}

	/**
	 * 移除list中的所有value
	 * 
	 * @param key
	 * @param value
	 */
	public void delListItemByKey(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.lrem(key, 0, value);
		} catch (Exception e) {
			logger.error("RedisClient delListItemByKey key = {}, value = {} ", key, value, e);
		} finally {
			this.returnResource(jedis);
		}
	}
	
	/**
	 * 移除list中start到end范围之外的所有value
	 * @param key
	 * @param start
	 * @param end
	 */
	public void subListByKey(String key, long start, long end) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.ltrim(key, start, end);
		} catch (Exception e) {
			logger.error("RedisClient subListByKey key = {}, start = {}, end = {}", key, start, end, e);
		} finally {
			this.returnResource(jedis);
		}
	}
	
	/**
	 * 删除列表中前count个值为value的元素，返回实际删除的元素个数。根据count值的不同，该命令的执行方式会有所不同：  *
	 * 当count>0时， LREM会从列表左边开始删除。  * 当count<0时， LREM会从列表后边开始删除。  * 当count=0时，
	 * LREM删除所有值为value的元素。
	 * 
	 * @param key
	 * @param count
	 * @param value
	 * @return
	 */
	public Long lremList(String key, long count, String value) {
		Jedis jedis = null;
		Long val = null;
		try {
			jedis = getJedis();
			val = jedis.lrem(key, count, value);
		} catch (Exception e) {
			logger.error("RedisClient lremList key = {}, count = {}, value = {}", key, count, value, e);
		} finally {
			this.returnResource(jedis);
		}
		return val;
	}
	
	/**
	 * 随机获取set里的num个值
	 */
	public List<String> srandMemberByKey(String key, int num) {
		List<String> strList = new ArrayList<String>();
		Jedis jedis = null;
		try {
			jedis = getJedis();
			strList = jedis.srandmember(key, num);
		} catch (Exception e) {
			logger.error("RedisClient srandMemberByKey key = {}, num = {}", key, num, e);
		} finally {
			this.returnResource(jedis);
		}
		return strList;
	}
	
	
	/********************** hashMap数据部分  **********************/
	/**
	 * 设置一個hash
	 * 
	 * @param hashName
	 * @param key
	 * @param value
	 * @return
	 */
	public void setHashByName(String hashName, String key, String value) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.hset(hashName, key, value);
		} catch (Exception e) {
			logger.error("RedisClient setHashByName hashName = {}, key = {}, value = {}", hashName, key, value, e);
		} finally {
			this.returnResource(jedis);
		}
	}

	/**
	 * 设置一個hash
	 * 
	 * @param hashName
	 * @param key
	 * @param value
	 * @return
	 */
	public Long setHashNewByName(String hashName, String key, String value) {
		Jedis jedis = null;
		Long result = null;
		try {
			jedis = getJedis();
			result = jedis.hset(hashName, key, value);
		} catch (Exception e) {
			logger.error("RedisClient setHashNewByName hashName = {}, key = {}, value = {}", hashName, key, value, e);
		} finally {
			this.returnResource(jedis);
		}
		return result;
	}

	/**
	 * 判断key在指定的hashmap里是否存在
	 * 
	 * @param hashName
	 * @param key
	 * @return
	 */
	public boolean isExtisInData(String hashName, String key) {
		Jedis jedis = null;
		boolean isExtis = false;
		try {
			jedis = getJedis();
			isExtis = jedis.hexists(hashName, key);
		} catch (Exception e) {
			logger.error("RedisClient isExtisInData hashName = {}, key = {}", hashName, key, e);
		} finally {
			this.returnResource(jedis);
		}
		return isExtis;
	}

	/**
	 * 获取hash里面的某个值
	 * 
	 * @param hashName
	 * @param key
	 * @return
	 */
	public String getHashByName(String hashName, String key) {
		Jedis jedis = null;
		String val = null;
		try {
			jedis = getJedis();
			val = jedis.hget(hashName, key);

		} catch (Exception e) {
			logger.error("RedisClient getHashByName hashName = {}, key = {}", hashName, key, e);
		} finally {
			this.returnResource(jedis);
		}
		return val;
	}

	/**
	 * 获取hash里面的总数
	 * 
	 * @param hashName
	 * @param key
	 * @return
	 */
	public long getCountHashByName(String hashName) {
		Jedis jedis = null;
		long val = 0;
		try {
			jedis = getJedis();
			val = jedis.hlen(hashName);

		} catch (Exception e) {
			logger.error("RedisClient getCountHashByName hashName = {}", hashName, e);
		} finally {
			this.returnResource(jedis);
		}
		return val;
	}

	/**
	 * 获取指定hashMap对象的大小
	 * 
	 * @param hashMapName
	 * @return
	 */
	public long getHashMapCount(String hashMapName) {
		Jedis jedis = null;
		long val = 0;
		try {
			jedis = getJedis();
			val = jedis.hlen(hashMapName);

		} catch (Exception e) {
			logger.error("RedisClient getHashMapCount hashMapName = {}", hashMapName, e);
		} finally {
			this.returnResource(jedis);
		}
		return val;
	}
	

	/**
	 * 根据hashkey获取所有value
	 * 
	 * @param hastMapName
	 * @return
	 */
	public List<String> getHashAllVal(String hashMapName) {
		Jedis jedis = null;
		List<String> val = null;
		try {
			jedis = getJedis();
			val = jedis.hvals(hashMapName);
		} catch (Exception e) {
			logger.error("RedisClient getHashAllVal hashMapName = {}", hashMapName, e);
		} finally {
			this.returnResource(jedis);
		}
		return val;
	}

	/**
	 * 根据hashkey获取所有key
	 * 
	 * @param hastMapName
	 * @return
	 */
	public Set<String> getHashAllKey(String hashMapName) {
		Jedis jedis = null;
		Set<String> keys = null;
		try {
			jedis = getJedis();
			keys = jedis.hkeys(hashMapName);
		} catch (Exception e) {
			logger.error("RedisClient getHashAllKey hashMapName = {}", hashMapName, e);
		} finally {
			this.returnResource(jedis);
		}
		return keys;
	}
	
	public void hmset(String key, Map<String, String> map) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.hmset(key, map);
		} catch (Exception e) {
			logger.error("RedisClient hmset key = {}, map = {}", key, map, e);
		} finally {
			this.returnResource(jedis);
		}
	}
	
	/**
	 * 删除某个hash
	 * 
	 * @param key
	 * @param field
	 */
	public void hdelHashDataByField(String key, String field) {
		Jedis jedis = null;

		try {
			jedis = getJedis();
			jedis.hdel(key, field);
		} catch (Exception e) {
			logger.error("RedisClient hdelHashDataByField key = {}, field = {}", key, field, e);
		} finally {
			this.returnResource(jedis);
		}
	}
	
	/**
	 * 根据hashkey获取所有的字段和值
	 * @param keyName
	 * @return
	 */
	public Map<String, String> hgetAll(String keyName) {
		Jedis jedis = null;
		Map<String, String> map = null;
		try {
			jedis = getJedis();
			map = jedis.hgetAll(keyName);
		} catch (Exception e) {
			logger.error("RedisClient hgetAll keyName = {}", keyName, e);
		} finally {
			this.returnResource(jedis);
		}
		return map;
	}
	
	
	/********************** set数据部分  **********************/
	/**
	 * 判断某个值是否存在
	 * 
	 * @param setName
	 * @param key
	 * @return
	 */
	public boolean isExistsSetKey(String setName, String val) {
		Jedis jedis = null;
		boolean isExtis = false;
		try {
			jedis = getJedis();
			isExtis = jedis.sismember(setName, val);
		} catch (Exception e) {
			logger.error("RedisClient isExistsSetKey setName = {}, val = {}", setName, val, e);
		} finally {
			this.returnResource(jedis);
		}
		return isExtis;
	}

	/**
	 * 往一个set对象放值
	 * 
	 * @param setName
	 * @param key
	 */
	public void addSetItem(String setName, String val) {
		Jedis jedis = null;
		try {
			jedis = this.getJedis();
			jedis.sadd(setName, val);
		} catch (Exception e) {
			logger.error("RedisClient addSetItem setName = {}, val = {}", setName, val, e);
		} finally {
			this.returnResource(jedis);
		}
	}
	
	
	/**
	 * 往一个set对象放值
	 * @author wanghanwei.wb
	 * @param setName
	 * @param key
	 */
	public long addSetItemNew(String setName, String val) {
		Jedis jedis = null;
		long res = 0L;
		try {
			jedis = this.getJedis();
			res = jedis.sadd(setName, val);
		} catch (Exception e) {
			logger.error("RedisClient addSetItemNew setName = {}, val = {}", setName, val, e);
		} finally {
			this.returnResource(jedis);
		}
		return res;
	}

	/**
	 * 批量添加数据
	 * 
	 * @param listName
	 * @param redList
	 */
	public void batchAddSetItem(String setName, List<String> redList) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			for (String item : redList) {
				jedis.sadd(setName, item);
			}
		} catch (Exception e) {
			logger.error("RedisClient batchAddSetItem setName = {}, redList = {}", setName, redList, e);
		} finally {
			this.returnResource(jedis);
		}
	}

	/**
	 * 获取指定set里的所有值
	 * 
	 * @param key
	 * @return
	 */
	public Set<String> getSetAllValByKey(String key) {
		Jedis jedis = null;
		Set<String> val = null;
		try {
			jedis = getJedis();
			val = jedis.smembers(key);
		} catch (Exception e) {
			logger.error("RedisClient getSetAllValByKey key = {}", key, e);
		} finally {
			this.returnResource(jedis);
		}
		return val;
	}

	/**
	 * 获取指定set对象的大小
	 * 
	 * @param setName
	 * @return
	 */
	public long getSetCount(String setName) {
		Jedis jedis = null;
		long val = 0;
		try {
			jedis = getJedis();
			val = jedis.scard(setName);

		} catch (Exception e) {
			logger.error("RedisClient getSetCount setName = {}", setName, e);
		} finally {
			this.returnResource(jedis);
		}
		return val;
	}
	
	/**
	 * 获取set集合的差值(最大集合在第一个参数)
	 * @param allSetKey
	 * @param setKey
	 * @return
	 */
	public Set<String> getSdiffSetByKeys(String allSetKey, String setKey) {
		Jedis jedis = null;
		Set<String> val = new HashSet<String>();
		try {
			jedis = getJedis();
			val = jedis.sdiff(allSetKey, setKey);

		} catch (Exception e) {
			logger.error("RedisClient getSdiffSetByKeys allSetKey = {}, setKey = {}", allSetKey, setKey, e);
		} finally {
			this.returnResource(jedis);
		}
		return val;
	}
	
	/**
	 * Redis Zcard 命令用于计算集合中元素的数量。
	 * @author wanghanwei.wb
	 * @param setName
	 * @return
	 */
	public long zcard(String setName) {
		Jedis jedis = null;
		long val = 0;
		try {
			jedis = getJedis();
			val = jedis.zcard(setName);
		} catch (Exception e) {
			logger.error("RedisClient zcard setName = {}", setName, e);
		} finally {
			this.returnResource(jedis);
		}
		return val;
	}
	
	public double zincrby(String key, double score, String member) {
		Jedis jedis = null;
		double val = 0;
		try {
			jedis = getJedis();
			val = jedis.zincrby(key, score, member);
		} catch (Exception e) {
			logger.error("RedisClient zincrby key = {}, score = {}, member = {}", key, score, member, e);
		} finally {
			this.returnResource(jedis);
		}
		return val;
	}
	
	
	/**
	 * 倒序获取数据
	 * @author wanghanwei.wb
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public Set<String> zrevrange(String key, long start, long end) {
		Jedis jedis = null;
		Set<String> val = null;
		try {
			jedis = getJedis();
			val = jedis.zrevrange(key, start, end);
		} catch (Exception e) {
			logger.error("RedisClient zrevrange key = {}, start = {}, end = {}", key, start, end, e);
		} finally {
			this.returnResource(jedis);
		}
		return val;
	}
	

	/**
	 * 删除集合里的某个值
	 * 
	 * @param key
	 */
	public void removeValueByKey(String key, String value) {
		Jedis jedis = null;

		try {
			jedis = getJedis();
			jedis.srem(key, value);
		} catch (Exception e) {
			logger.error("RedisClient removeValueByKey key = {}, value = {}", key, value, e);
		} finally {
			this.returnResource(jedis);
		}
	}
	
	/**
	 * 移除集合中的一个或多个成员元素，不存在的成员元素会被忽略
	 * @param key
	 * @param members
	 * @return 返回被成功移除的元素数量，不包括被忽略的元素
	 */
	public Long sremSet(String key, String... members) {
		Jedis jedis = null;
		Long val = null;
		try {
			jedis = getJedis();
			val = jedis.srem(key, members);
		} catch (Exception e) {
			logger.error("RedisClient sremSet key = {}, members = {}", key, Arrays.asList(members), e);
		} finally {
			this.returnResource(jedis);
		}
		return val;
	}
	
	/**
	 * 将多个元素加入到key中，重复值忽略
	 * 
	 * @param key
	 * @param members
	 * @return
	 */
	public Long saddSet(String key, String... members) {
		Jedis jedis = null;
		Long val = null;
		try {
			jedis = getJedis();
			val = jedis.sadd(key, members);
		} catch (Exception e) {
			logger.error("RedisClient saddSet key = {}, members = {}", key, Arrays.asList(members), e);
		} finally {
			this.returnResource(jedis);
		}
		return val;
	}

	/**
	 * 随机从集合中取出1个值，并从集合中删除
	 * 
	 * @param key
	 * @return
	 */
	public String spopSet(String key) {
		Jedis jedis = null;
		String val = null;
		try {
			jedis = getJedis();
			val = jedis.spop(key);
		} catch (Exception e) {
			logger.error("RedisClient spopSet key = {}", key, e);
		} finally {
			this.returnResource(jedis);
		}
		return val;
	}

	/**
	 * 随机从集合中取出1个值，不会从集合中删除
	 * 
	 * @param key
	 * @return
	 */
	public String srandMemberSet(String key) {
		Jedis jedis = null;
		String val = null;
		try {
			jedis = getJedis();
			val = jedis.srandmember(key);
		} catch (Exception e) {
			logger.error("RedisClient srandMemberSet key = {}", key, e);
		} finally {
			this.returnResource(jedis);
		}
		return val;
	}
	
	/********************** String数据部分  **********************/
	public boolean setEx(String key, int seconds, String value) {
		Jedis jedis = getJedis();
		try {
			return OK.equals(jedis.setex(key, seconds, value));
		} finally {
			jedis.close();
		}
	}
	
	
	/**
	 * 判断string里面某个值是否存在
	 * 
	 * @param keyName
	 * @return
	 */
	public boolean isKeyExists(String keyName) {
		Jedis jedis = null;
		boolean isExtis = false;
		try {
			jedis = getJedis();
			isExtis = jedis.exists(keyName);
		} catch (Exception e) {
			logger.error("RedisClient isKeyExists keyName = {}", keyName, e);
		} finally {
			this.returnResource(jedis);
		}
		return isExtis;
	}

	/**
	 * 获取string里面Key的某个值
	 * 
	 * @param hashName
	 * @param key
	 * @return
	 */
	public String getValByKey(String key) {
		Jedis jedis = null;
		String val = null;
		try {
			jedis = getJedis();
			val = jedis.get(key);

		} catch (Exception e) {
			logger.error("RedisClient getValByKey key = {}", key, e);
		} finally {
			this.returnResource(jedis);
		}
		return val;
	}

	/**
	 * 设置string里面Key的某个值
	 * 
	 * @param hashName
	 * @param key
	 * @return
	 */
	public String setValByKey(String key, String val) {
		Jedis jedis = null;

		try {
			jedis = getJedis();
			val = jedis.set(key, val);

		} catch (Exception e) {
			logger.error("RedisClient setValByKey key = {}, val = {}", key, val, e);
		} finally {
			this.returnResource(jedis);
		}
		return val;
	}
	
	/**
	 * 删除某个key对象
	 * 
	 * @param key
	 */
	public void delDataByKey(String key) {
		Jedis jedis = null;

		try {
			jedis = getJedis();
			jedis.del(key);
		} catch (Exception e) {
			logger.error("RedisClient delDataByKey key = {}", key, e);
		} finally {
			this.returnResource(jedis);
		}
	}
	
	/**
	 * 往一个string对象放值 如果key 不存在，设置key 对应string 类型的值。如果key 已经存在，返 回0。
	 * 
	 * @param setName
	 * @param key
	 */
	public long setnx(String setName, String val) {
		Jedis jedis = null;
		try {
			jedis = this.getJedis();
			return jedis.setnx(setName, val);
		} catch (Exception e) {
			logger.error("RedisClient setnx setName = {}, val = {}", setName, val, e);
			return -1;
		} finally {
			this.returnResource(jedis);
		}

	}
	
	/**
	 * 设置字符串值并加上过期时间
	 * 当前redis中不存在key时，设值成功，反之失败
	 * 
	 * @param  key
	 * @param  value
	 * @param  expireTime	过期时间，单位为秒
	 * @return true：设置成功	false：设置失败，key已存在
	 * @author zhongzhibin.wb
	 */
	public boolean setnxex(String key, String value, long expireTime) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			return OK.equals(jedis.set(key, value, "nx", "ex", expireTime));
		} catch (Exception e) {
			logger.error("RedisClient setValByKey key = {}, value = {}, expireTime = {}", key, value, expireTime, e);
		} finally {
			returnResource(jedis);
		}
		return false;
	}
	
	
	/********************** object数据部分  **********************/
	/**
	 * 判断string里面某个Object是否存在
	 * 
	 * @param keyName
	 * @return
	 */
	public boolean isObjectExists(String keyName) {
		Jedis jedis = null;
		boolean isExtis = false;
		try {
			jedis = getJedis();
			isExtis = jedis.exists(keyName.getBytes());
		} catch (Exception e) {
			logger.error("RedisClient isObjectExists keyName = {}", keyName, e);
		} finally {
			this.returnResource(jedis);
		}
		return isExtis;
	}
	
	/**
	 * 给对应key放置一个object
	 * @param hashName
	 * @param key
	 * @return
	 */
	public byte[] setObjectValByKey(String key, Object value) {
		Jedis jedis = null;

		try {
			jedis = getJedis();
			jedis.set(key.getBytes(), SerializeUtils.serialize(value));
		} catch (Exception e) {
			logger.error("RedisClient setObjectValByKey key = {}, value = {}", key, value, e);
		} finally {
			this.returnResource(jedis);
		}
		return SerializeUtils.serialize(value);
	}
	
	/**
	 * 获取对应key放置一个object
	 * @param hashName
	 * @param key
	 * @return
	 */
	public Object getObjectValByKey(String key) {
		Jedis jedis = null;
		byte[] val = null;
		try {
			jedis = getJedis();
			val = jedis.get(key.getBytes());

		} catch (Exception e) {
			logger.error("RedisClient getObjectValByKey key = {}", key, e);
		} finally {
			this.returnResource(jedis);
		}
		return SerializeUtils.deSerialize(val);
	}
	
	/**
	 * 删除某个value为Object的key对象
	 * 
	 * @param key
	 */
	public void delOjectDataByKey(String key) {
		Jedis jedis = null;

		try {
			jedis = getJedis();
			jedis.del(key.getBytes());
		} catch (Exception e) {
			logger.error("RedisClient delOjectDataByKey key = {}", key, e);
		} finally {
			this.returnResource(jedis);
		}
	}

	/**
	 * 根据hashkey获取所有值(不能在涉及业务逻辑代码中调用此方法，模糊查询会过多占用redis资源)
	 * 
	 * *************禁用*************
	 * *************禁用*************
	 * *************禁用*************
	 * *************禁用*************
	 * *************禁用*************
	 * 
	 * @param hastMapName
	 * @return
	 */
	public Set<String> getKeysByName(String name) {
		Jedis jedis = null;
		Set<String> val = null;
		try {
			jedis = getJedis();
			val = jedis.keys(name);
		} catch (Exception e) {
			logger.error("RedisClient getKeysByName name = {}", name, e);
		} finally {
			this.returnResource(jedis);
		}
		return val;
	}

	public boolean batchDelByKeysName(String name) {
		boolean flag = false;
		name = name + "*";
		Set<String> keysName = this.getKeysByName(name);
		if (keysName != null) {
			Iterator<String> it = keysName.iterator();
			String key = it.next();
			while (StringUtils.isNotEmpty(key)) {
				this.delDataByKey(key);
				key = it.next();
			}
			flag = true;
		}
		return flag;
	}

	
	/********************** <T>集合部分  **********************/
	/**
	 * 设置 list
	 * 
	 * @param <T>
	 * @param key
	 * @param value
	 * @return
	 * @return
	 */
	public <T> void setList(String key, List<T> list) {
		Jedis jedis = null;
		try {
			jedis = this.getJedis();
			jedis.set(key.getBytes(), ObjectTranscoder.serialize(list));
		} catch (Exception e) {
			logger.error("RedisClient setList key = {}, list = {}", key, list, e);
		} finally {
			this.returnResource(jedis);
		}
	}

	/**
	 * 获取list
	 * 
	 * @param <T>
	 * @param key
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> getList(String key) {
		Jedis jedis = null;
		List<T> list = null;
		try {
			jedis = getJedis();
			if (jedis == null || !jedis.exists(key.getBytes())) {
				return null;
			}
			byte[] in = jedis.get(key.getBytes());
			list = (List<T>) ObjectTranscoder.deserialize(in);
		} finally {
			this.returnResource(jedis);
		}
		return list;
	}
	
	public long llen(String key) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			return jedis.llen(key);
		} catch (Exception e) {
			logger.error("RedisClient llen key = {}", key, e);
		} finally {
			this.returnResource(jedis);
		}
		return 0;
	}
	
	public long rpush(String key, String... strings) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			return jedis.rpush(key, strings);
		} catch (Exception e) {
			logger.error("RedisClient llpush key = {}, strings = {}", key, Arrays.asList(strings), e);
		} finally {
			this.returnResource(jedis);
		}
		return 0;
	} 
	
	public long llpush(String key, String... strings) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			return jedis.lpush(key, strings);
		} catch (Exception e) {
			logger.error("RedisClient llpush key = {}, strings = {}", key, Arrays.asList(strings), e);
		} finally {
			this.returnResource(jedis);
		}
		return 0;
	}
	
	public String lrpop(String key) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			return jedis.rpop(key);
		} catch (Exception e) {
			logger.error("RedisClient lrpop key = {}, strings = {}", key, e);
		} finally {
			this.returnResource(jedis);
		}
		return null;
	}
	
	/**
	 * 获取列表中的某一片段，将返回start、stop之间的所有元素（包含两端的元素），索引从0开始。 索引可以是负数，如：“-1”代表最后边的一个元素
	 * 
	 * @param <T>
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> lrangList(String key, int start, int end) {
		Jedis jedis = null;
		List<T> list = null;
		try {
			jedis = getJedis();
			list = (List<T>) jedis.lrange(key, start, end);
		} catch (Exception e) {
			logger.error("RedisClient lrangList key = {}, start = {}, end = {}", key, start, end, e);
		} finally {
			this.returnResource(jedis);
		}
		return list;
	}

	
	
	/********************** 有序集合数据  **********************/
	/**
	 * 添加一个有序的集合
	 * 
	 * @param key
	 * @param score
	 * @param member
	 * @return
	 */
	public void setZaddByName(String key, double score, String member) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.zadd(key, score, member);
		} catch (Exception e) {
			logger.error("RedisClient setZaddByName key = {}, score = {}, member = {}", key, score, member, e);
		} finally {
			this.returnResource(jedis);
		}
	}

	/**
	 * 获取有序集合的score值
	 * 
	 * @param key
	 * @param member
	 * @return
	 */
	public Double getZscore(String key, String member) {
		Jedis jedis = null;
		Double score = 0d;
		try {
			jedis = getJedis();
			score = jedis.zscore(key, member);
		} catch (Exception e) {
			logger.error("RedisClient getZscore key = {}, member = {}", key, member, e);
		} finally {
			this.returnResource(jedis);
		}
		return score;
	}

	/**
	 * 获取有序集合的排名
	 * 
	 * @param key
	 * @param member
	 * @return
	 */
	public Long getZrevrank(String key, String member) {
		Jedis jedis = null;
		Long rank = 0L;
		try {
			jedis = getJedis();
			rank = jedis.zrevrank(key, member);
		} catch (Exception e) {
			logger.error("RedisClient getZrevrank key = {}, member = {}", key, member, e);
		} finally {
			this.returnResource(jedis);
		}
		return rank;
	}

	public Set<String> zrevrange(String key, int start, int end) {
		Jedis jedis = null;
		Set<String> set = null;
		try {
			jedis = getJedis();
			set = jedis.zrevrange(key, start, end);
		} catch (Exception e) {
			logger.error("RedisClient zrevrange key = {}, start = {}, end = {}", key, start, end, e);
		} finally {
			this.returnResource(jedis);
		}
		return set;
	}

	public void zaddMembersByKey(String key, Map<String, Double> scoreMembers) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.zadd(key, scoreMembers);
		} catch (Exception e) {
			logger.error("RedisClient zaddMembersByKey key = {}, scoreMembers = {}", key, scoreMembers, e);
		} finally {
			this.returnResource(jedis);
		}
	}

	public Set<Tuple> zrevrangeWithScores(String key, int start, int end) {
		Jedis jedis = null;
		Set<Tuple> set = null;
		try {
			jedis = getJedis();
			set = jedis.zrevrangeWithScores(key, start, end);
		} catch (Exception e) {
			logger.error("RedisClient zrevrangeWithScores key = {}, start = {}, end = {} ", key, start, end, e);
		} finally {
			this.returnResource(jedis);
		}
		return set;
	}
	
	/**
	 * 有序集合添加成员
	 * 
	 * @param key
	 * @param score
	 * @param member
	 */
	public void zaddBySortedSet(String key, double score, String member) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.zadd(key, score, member);
		} catch (Exception e) {
			logger.error("RedisClient zaddBySortedSet key = {}, score = {}, member = {}", key, score, member, e);
		} finally {
			this.returnResource(jedis);
		}
	}

	/**
	 * 返回有序集中指定分数区间内的成员，分数从高到低排序
	 * 
	 * @param key
	 * @param max
	 * @param min
	 * @return
	 */
	public Set<String> zrevrangeByScore(String key, double max, double min) {
		Set<String> valSet = null;
		Jedis jedis = null;
		try {
			jedis = getJedis();
			valSet = jedis.zrevrangeByScore(key, max, min);
		} catch (Exception e) {
			logger.error("RedisClient zrevrangeByScore key = {}, max = {}, min = {}", key, max, min, e);
		} finally {
			this.returnResource(jedis);
		}
		return valSet;
	}

	/**
	 * 返回有序集中指定分数区间内的成员，分数从低到高排序
	 * 
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	public Set<String> zrangeByScore(String key, double min, double max) {
		Set<String> valSet = null;
		Jedis jedis = null;
		try {
			jedis = getJedis();
			valSet = jedis.zrangeByScore(key, min, max);
		} catch (Exception e) {
			logger.error("RedisClient zrangeByScore key = {}, min = {}, max = {}", key, min, max, e);
		} finally {
			this.returnResource(jedis);
		}
		return valSet;
	}
	
	/**
	 * 返回member在set的索引
	 * 
	 * @param  key
	 * @param  member
	 * @return -1 表示不存在
	 */
	public long zrankByMember(String key, String member) {
		Jedis jedis = null;
		long index = -1;
		try {
			jedis = getJedis();
			Long i = jedis.zrank(key, member);
			if (i != null) {
				index = i.longValue();
			}
		} catch (Exception e) {
			logger.error("RedisClient zrankByMember key = {}, member = {}", key, member, e);
		} finally {
			this.returnResource(jedis);
		}
		return index;
	}
	
	public Set<String> zrange(String key, long start, long end) {
		Jedis jedis = null;
		Set<String> set = null;
		try {
			jedis = getJedis();
			set = jedis.zrange(key, start, end);
		} catch (Exception e) {
			logger.error("RedisClient zrange key = {}, start = {}, end = {}", key, start, end, e);
		} finally {
			this.returnResource(jedis);
		}
		return set;
	}
	
	/**
	 * 移除有序集中的一个或多个成员，不存在的成员将被忽略
	 * @param key
	 * @param members
	 * @return
	 */
	public Long zremMemberByKey(String key, String... members) {
		Long val = null;
		Jedis jedis = null;
		try {
			jedis = getJedis();
			val = jedis.zrem(key, members);
		} catch (Exception e) {
			logger.error("RedisClient zremMemberByKey key = {}, members = {}", key, Arrays.asList(members), e);
		} finally {
			this.returnResource(jedis);
		}
		return val;
	}
	
	/**
	 * 移除有序集合中给定的排名区间的所有成员
	 * @param key
	 * @param members
	 * @return
	 */
	public Long zremAllMemberByKey(String key, double start, double end) {
		Long val = null;
		Jedis jedis = null;
		try {
			jedis = getJedis();
			val = jedis.zremrangeByScore(key, start, end);
		} catch (Exception e) {
			logger.error("RedisClient zremAllMemberByKey key = {}, start = {}, end = {}", key, start, end, e);
		} finally {
			this.returnResource(jedis);
		}
		return val;
	}

	/**
	 * 计算有序集的交集并存储在dstkey中
	 * @param dstkey
	 * @param sets
	 * @return
	 */
	public long zinterStore(String dstkey, String...sets){
		Long val = null;
		Jedis jedis = null;
		try {
			jedis = getJedis();
			val = jedis.zinterstore(dstkey, sets);
		} catch (Exception e) {
			logger.error("RedisClient zinterStore dstKey = {}, sets = {}", dstkey, Arrays.asList(sets), e);
		} finally {
			this.returnResource(jedis);
		}
		return val;
	}
	/**
	 * 计算有序集的交集并存储在dstkey中
	 * @param dstkey
	 * @param params
	 * @param sets
	 * @return
	 */
	public long zinterStore(String dstkey, ZParams params, String...sets){
		Long val = null;
		Jedis jedis = null;
		try {
			jedis = getJedis();
			val = jedis.zinterstore(dstkey, params, sets);
		} catch (Exception e) {
			logger.error("RedisClient zinterStore dstKey = {}, sets = {}", dstkey, Arrays.asList(sets), e);
		} finally {
			this.returnResource(jedis);
		}
		return val;
	}
	
	/**
	 * 计算有序集的并集并存储在dstkey中
	 * @param dstkey
	 * @param sets
	 * @return
	 */
	public long zunionStore(String dstkey, String...sets){
		Long val = null;
		Jedis jedis = null;
		try {
			jedis = getJedis();
			val = jedis.zunionstore(dstkey, sets);
		} catch (Exception e) {
			logger.error("RedisClient zunionStore dstkey = {}, sets = {}", dstkey, Arrays.asList(sets), e);
		} finally {
			this.returnResource(jedis);
		}
		return val;
	}
	/**
	 * 计算有序集的并集并存储在dstkey中
	 * @param dstkey
	 * @param sets
	 * @return
	 */
	public long zunionStore(String dstkey, ZParams params, String...sets){
		Long val = null;
		Jedis jedis = null;
		try {
			jedis = getJedis();
			val = jedis.zunionstore(dstkey, params, sets);
		} catch (Exception e) {
			logger.error("RedisClient zunionStore dstKey = {}, sets = {}", dstkey, Arrays.asList(sets), e);
		} finally {
			this.returnResource(jedis);
		}
		return val;
	}
	
	/**
	 * 移除有序集中给的分数区间内所有成员
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public long zremrangeByScore(String key, String start, String end){
		Long val = null;
		Jedis jedis = null;
		try {
			jedis = getJedis();
			val = jedis.zremrangeByScore(key, start, end);
		} catch (Exception e) {
			logger.error("RedisClient zremrangeByScore key = {}, start = {}, end = {}", key, start, end, e);
		} finally {
			this.returnResource(jedis);
		}
		return val;
	}
	
	/**
	 * 返回有序集中指定分数区间内的成员（带分数值），分数从低到高排序
	 * 
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) {
		Set<Tuple> set = null;
		Jedis jedis = null;
		try {
			jedis = getJedis();
			set = jedis.zrangeByScoreWithScores(key, min, max);
		} catch (Exception e) {
			logger.error("RedisClient zrangeByScoreWithScores key = {}, min = {}, max = {}", key, min, max, e);
		} finally {
			this.returnResource(jedis);
		}
		return set;
	}
	
	/**
	 * 移除元素
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public void zremRangeByRank(String key, long start, long end) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.zremrangeByRank(key, start, end);
		} catch (Exception e) {
			logger.error("RedisClient zremRangeByRank key = {}, score = {}, member = {}", key, start, end, e);
		} finally {
			this.returnResource(jedis);
		}
	}
	
	/********************** 为缓存数据加上增量部分  **********************/
	/**
	 * key字段值加上指定增量值 如果field不存在，value被初始化为 0 。
	 * 
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	public Long hincrBy(String key, String field, long value) {
		Jedis jedis = null;
		Long val = null;
		try {
			jedis = getJedis();
			val = jedis.hincrBy(key, field, value);
		} catch (Exception e) {
			logger.error("RedisClient hincrBy key = {}, field = {}, value = {}", key, field, value, e);
		} finally {
			this.returnResource(jedis);
		}
		return val;
	}
	
	public boolean hexists(String key, String field) {
		Jedis jedis = null;
		boolean val = false;
		try {
			jedis = getJedis();
			val = jedis.hexists(key, field);
		} catch (Exception e) {
			logger.error("RedisClient hincrBy key = {}, field = {}, value = {}", key, field, e);
		} finally {
			this.returnResource(jedis);
		}
		return val;
	}

	/**
	 * 给key字段累加值
	 * 
	 * @param key
	 * @param integer
	 * @return
	 */
	public Long incyByKey(String key, long integer) {
		Jedis jedis = null;
		Long val = null;
		try {
			jedis = getJedis();
			val = jedis.incrBy(key, integer);
		} catch (Exception e) {
			logger.error("RedisClient incyByKey key = {}, integer = {}", key, integer, e);
		} finally {
			this.returnResource(jedis);
		}
		return val;
	}
	
	/**
	 * 给key减少值
	 * 
	 * @param key
	 * @param integer
	 * @return
	 */
	public Long decrByKey(String key, long integer) {
		Jedis jedis = null;
		Long val = null;
		try {
			jedis = getJedis();
			val = jedis.decrBy(key, integer);
		} catch (Exception e) {
			logger.error("RedisClient decrByKey key = {}, integer = {}", key, integer, e);
		} finally {
			this.returnResource(jedis);
		}
		return val;
	}
	
	/**
	 * 初始化并累积值 （先判断key是否存在，不存在则先设置初始值再累积，否则直接累积）
	 * 
	 * @param key
	 * @param addNum
	 * @param initNum
	 * @return
	 */
	public long incyByKeyAndInit(String key, long addNum, long initNum) {
		Jedis jedis = null;
		Long val = null;
		try {
			jedis = getJedis();
			if (jedis.exists(key)) {
				val = jedis.incrBy(key, addNum);
			} else {
				val = jedis.incrBy(key, addNum + initNum);
			}
		} catch (Exception e) {
			logger.error("RedisClient incyByKeyAndInit key = {}, addNum = {}, initNum = {}", key, addNum, initNum, e);
		} finally {
			this.returnResource(jedis);
		}
		return val;
	}
	
	/**
	 * 获取剩余失效时间（秒）
	 * @param key
	 * @return
	 */
	public long getExpireTime(String key) {
		Jedis jedis = null;
		try {
			jedis = this.getJedis();
			return jedis.ttl(key);
		} catch (Exception e) {
			logger.error("RedisClient getExpireTime key = {}", key, e);
			return -1;
		} finally {
			this.returnResource(jedis);
		}
	}

	/********************** 有效期、控制并发及清理redis所有缓存数据部分  **********************/
	/**
	 * 设置一个key的生命周期
	 * 
	 * @param key
	 * @param time
	 *            秒
	 * @return
	 */
	public long setExpire(String key, int seconds) {
		Jedis jedis = null;
		try {
			jedis = this.getJedis();
			return jedis.expire(key, seconds);
		} catch (Exception e) {
			logger.error("RedisClient setExpire key = {}, seconds = {}", key, seconds, e);
			return -1;
		} finally {
			this.returnResource(jedis);
		}
	}

	/**
	 * 控制并发
	 */
	public Boolean watchKeys(String key, int num) {
		Jedis jedis = null;
		Boolean isSucc = false;
		try {
			jedis = getJedis();
	        jedis.watch(key);
	        String val = jedis.get(key);
	        int valint = Integer.valueOf(val);	        
	        if (valint <= num && valint>=1) {	        
	            Transaction tx = jedis.multi();// 开启事务
	            tx.incrBy(key, -1);	
	            List<Object> list = tx.exec();// 提交事务，如果此时key被改动了，则返回null	             
	            if (list == null ||list.size()==0) {
	            	isSucc = false;
	            } else {
	            	isSucc = true;
	            }	
	        }
	    }catch (Exception e) {
	    	logger.error("RedisClient watchKeys key = {}, num = {}", key, num, e);
		} finally {
			this.returnResource(jedis);
		}
		return isSucc;
	}
	
	/**
	 * 清空库中所有数据,供测试
	 */
	public void flushDB() {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.flushDB();
		} catch (Exception e) {
			logger.error("RedisClient flushDB : ", e);
		} finally {
			this.returnResource(jedis);
		}
	}
	
	/**
	 * 查询list索引区间内的值
	 * @param index
	 * @param length
	 * @param cacaheKey
	 * @return
	 */
	public List<String> queryCacheByKey(int index,int length,String cacaheKey) {
		Jedis jedis = null;
		List<String> lrange;
		try {
			jedis = getJedis();
			index  = index==0?0:index;
			length = length==0?-1:length;
			lrange = jedis.lrange(cacaheKey, index, length);
		}  finally {
			returnResource(jedis);
		}
		return lrange;
	}
}
