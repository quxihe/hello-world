package com.sinolife.lem.util;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;

/**
 * redis实现的分布式锁
 * 注意：
 *   该锁不可重入且不可嵌套使用
 * 
 * <pre> {@code
 * 使用范例：
 * 
 * String value = UUIDUtil.getUuid32();  // 此值唯一即可
 * try {
 *     boolean lockFlag = RedisDistributedLock.tryLock(key, value, 10);
 *     
 *     or
 *     
 *     if (!RedisDistributedLock.tryLock(key, value, 10)) {
 *         return;
 *     }
 *     
 *     业务逻辑
 * } finally {
 *     RedisDistributedLock.unlock(key, value, 10);
 * }
 * <pre>
 * 
 * 
 * 
 * @author zhongzhibin.wb
 *
 */
@Component
public class RedisDistributedLock {
	
	private static final String LOCK_SUCCESS = "OK";
	
	private static final int DEFAULT_EXPIRE_TIME = 15;
	
	private static final long spinForTimeoutThreshold = 1000L;
	
	// 避免不必要的unlock
	private static final ThreadLocal<Boolean> LOCK_FLAG = new ThreadLocal<Boolean>();
	
	/**
	 * 原子释放锁脚本 
	 */
	private static final String SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then "
			+ "return redis.call('del', KEYS[1]) else return 0 end";
	
	@Autowired
	private static RedisClient redisClient;
	
	@Autowired
	@SuppressWarnings("static-access")
	public RedisDistributedLock(RedisClient redisClient) {
		this.redisClient = redisClient;
	}
	
	/**
	 * 尝试获取锁，获得返回true，反之返回false
	 * 
	 * @param  jedis
	 * @param  key
	 * @param  value
	 * @param  expireTime	过期时间
	 * @return
	 */
	public static boolean tryLock(String key, String value, int expireTime) {
		if (expireTime <= 0) {
			expireTime = DEFAULT_EXPIRE_TIME;
		}
		
		Jedis jedis = redisClient.getJedis();
		String result = null;
		try {
			result = jedis.set(key, value, "nx", "ex", expireTime);
		} finally {
			jedis.close();
		}
		
		if (LOCK_SUCCESS.equals(result)) {
			LOCK_FLAG.set(true);
			return true;
		}
		
		LOCK_FLAG.set(false);
		return false;
	}
	
	/**
	 * 尝试在指定时间内获取锁，若获得锁返回ture，否则返回false
	 * 
	 * @param  jedis
	 * @param  key
	 * @param  value
	 * @param  expireTime	key过期时间
	 * @param  timeout		等待锁时间
	 * @param  unit			等待锁时间的单位
	 * @return
	 * @throws InterruptedException
	 */
	public static boolean tryLockWithTimeout(String key, String value, int expireTime, long timeout, TimeUnit unit) throws InterruptedException {
		if (tryLock(key, value, expireTime)) {
			return true;
		}
		
		long nanosTimeout = unit.toNanos(timeout);
		long lastTime = System.nanoTime();
		for (;;) {
			if (tryLock(key, value, expireTime)) {
				return true;
			}
			if (nanosTimeout <= 0) {
                return false;
			}
			if (nanosTimeout > spinForTimeoutThreshold) {
				Thread.sleep(100);
			}
			long now = System.nanoTime();
			nanosTimeout -= now - lastTime;
			lastTime = now;
		}
	}
	
	/**
	 * 释放锁
	 * 
	 * @param jedis
	 * @param key
	 * @param value  跟获取锁时设置的value一直才能释放锁
	 */
	public static void unlock(String key, String value) {
		if (!isHoldLock()) {
			return;
		}
		
		LOCK_FLAG.remove();
		
		Jedis jedis = redisClient.getJedis();
		try {
			jedis.eval(SCRIPT, Collections.singletonList(key), Collections.singletonList(value));
		} finally {
			jedis.close();
		}
	}
	
	private static boolean isHoldLock() {
		return LOCK_FLAG.get() == null ? false : LOCK_FLAG.get();
	}
}
