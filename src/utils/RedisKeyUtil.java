package com.sinolife.activity.util.key;

/**
 * 活动-Redis常量管理配置
 * 命名规则：REDIS_KEY + 活动名 + 具体名称 + (其他)
 * 值定义规则：年月:系统名:缓存类型:活动名:具体名称:(其他)
 * e.g.:
 * public final static String REDIS_KEY_NEWYEARS2020_TEST = "202004:LEM:STRING:NEWYEAR2020:TEST";
 * 
 * @author renjia.wang001
 * 
 *
 */
public class RedisKeyUtil {

	/**
	 * 默认缓存有效期:1天
	 */
	public static final int CACHE_EXPIRE_TIME = 24*3600;
	/**
	 * 缓存有效期:3天
	 */
	public static final int CACHE_EXPIRE_TIME_3d = 3*24*3600;
	
	/**
	 * 缓存有效期:180天
	 */
	public static final int CACHE_EXPIRE_TIME_180 = 24*3600*180;
	
	/**
	 * 缓存有效期key
	 */
	/**
	 *  1秒
	 */
	public static final String CACHE_EXPIRE_TIME_1S = "expire_1s";
	/**
	 *  5分钟，300秒
	 */
	public static final String CACHE_EXPIRE_TIME_5M = "expire_5m";
	/**
	 *  10分钟，600秒
	 */
	public static final String CACHE_EXPIRE_TIME_10M = "expire_10m";
	/**
	 *  1小时，3600秒
	 */
	public static final String CACHE_EXPIRE_TIME_1H = "expire_1h";
	/**
	 *  1天，86400秒
	 */
	public static final String CACHE_EXPIRE_TIME_1D = "expire_1d";
	/**
	 *  3天，259200秒
	 */
	public static final String CACHE_EXPIRE_TIME_3D = "expire_3d";
	/**
	 *  7天，604800秒
	 */
	public static final String CACHE_EXPIRE_TIME_7D = "expire_7d";
	/**
	 *  30天，2592000秒
	 */
	public static final String CACHE_EXPIRE_TIME_30D = "expire_30d";
	/**
	 *  180天，15552000秒
	 */
	public static final String CACHE_EXPIRE_TIME_180D = "expire_180d";
	
	/**
	 * 下标
	 */
	public static final int CACHE_INDEX_0 = 0;
	public static final int CACHE_INDEX_100 = 100;
	public static final int CACHE_INDEX_100000 = 100000;
	
	/**
	 * 公共限制缓存key
	 * 使用规则：REDIS_KEY_PUB_LIMIT + 活动key + ":" + userId
	 * 最终值：LEM:STRING:PUB:LIMIT:wx_fwhActKey_004:20190000000000001234
	 */
	public static final String REDIS_KEY_PUB_LIMIT = "202004:LEM:STRING:PUB:LIMIT:";
	
	/******************** 2020压岁钱活动 ***************************/
	/**
	 * 2020压岁钱活动-用户未播种的植物信息(Hash)
	 */
	public static final String REDIS_KEY_NEWYEARS2020_SEEDINFO = "202004:LEM:HASH:NEWYEAR2020:SEEDINFO";
	
	/**
	 * 2020压岁钱活动-轮播详情缓存(List)
	 */
	public static final String REDIS_KEY_NEWYEARS2020_CAROUSEL_DETAIL = "202004:LEM:LIST:NEWYEAR2020:CAROUSEL:DETAIL";

	/**
	 * 2020压岁钱活动-用户头像昵称群名缓存(Hash)
	 */
	public static final String REDIS_KEY_NEWYEARS2020_USERINFO_BASE = "202004:LEM:HASH:NEWYEAR2020:USERINFO:BASE";
	
	/**
	 * 2020压岁钱活动-用户加保订单获得奖励缓存(Set)
	 */
	public static final String REDIS_KEY_NEWYEARS2020_SEND_REWARD = "202004:LEM:SET:NEWYEAR2020:SEND:REWARD:ORDERID";
	
	/**
	 * 2020压岁钱活动-区域激活排名计数缓存(String)
	 * 最终key值：LEM:STRING:NEWYEAR2020:AREARANK:INCY:区域ID
	 */
	public static final String REDIS_KEY_NEWYEARS2020_AREARANK_INCY = "202004:LEM:STRING:NEWYEAR2020:AREARANK:INCY";
	
	/**
	 * 2020压岁钱活动-区域激活用户排名(Hash)
	 * 最终key值：LEM:HASH:NEWYEAR2020:AREARANK:areaBlock_0~areaBlock_39
	 */
	public static final String REDIS_KEY_NEWYEARS2020_AREARANK = "202004:LEM:HASH:NEWYEAR2020:AREARANK";
	
	/**
	 * 2020压岁钱活动-每关用户游戏开始时间缓存(Hash)
	 * 最终key值：LEM:HASH:NEWYEAR2020:GAME:BEGIN:第几关
	 */
	public static final String REDIS_KEY_NEWYEARS2020_GAME_BEGIN = "202004:LEM:HASH:NEWYEAR2020:GAME:BEGIN";

	/**
	 * 2020压岁钱活动-每关用户游戏步数累计缓存(String)
	 * 最终key值：LEM:STRING:NEWYEAR2020:GAME:STEP:第几关:userId
	 */
	public static final String REDIS_KEY_NEWYEARS2020_GAME_STEP = "202004:LEM:STRING:NEWYEAR2020:GAME:STEP";

	/**
	 * 2020压岁钱活动-游戏星级对应步数信息缓存(String)
	 */
	public static final String REDIS_KEY_NEWYEARS2020_GAME_STAR_INFO = "202004:LEM:HASH:NEWYEAR2020:GAME:STAR:INFO";

	/**
	 * 2020压岁钱活动-100条区域路线缓存(Hash)
	 */
	public static final String REDIS_KEY_NEWYEARS2020_AREA_ROUTE = "202004:LEM:HASH:NEWYEAR2020:AREA:ROUTE";
	
	/**
	 * 2020压岁钱活动-不同开罐类型任务列表缓存(Hash)
	 */
	public static final String REDIS_KEY_NEWYEARS2020_TASK_LIST = "202004:LEM:HASH:NEWYEAR2020:TASK:LIST";
	
	/**
	 * 2020压岁钱活动-任务配置信息缓存(Hash)
	 */
	public static final String REDIS_KEY_NEWYEARS2020_TASK_INFO = "202004:LEM:HASH:NEWYEAR2020:TASK:INFO";
	
	/**
	 * 2020压岁钱活动-集市物品库存缓存(Hash)
	 */
	public static final String REDIS_KEY_NEWYEARS2020_GOODS_STOCK = "202004:LEM:HASH:NEWYEAR2020:GOODS:STOCK";
	
	/**
	 * 2020压岁钱活动-用户累计收割次数缓存(String)
	 */
	public static final String REDIS_KEY_NEWYEARS2020_REAP_INCY = "202004:LEM:STRING:NEWYEAR2020:REAP:INCY:";
	
	/**
	 * 2020压岁钱活动-区域激活第一名用户信息缓存(Hash)
	 */
	public static final String REDIS_KEY_NEWYEARS2020_AREARANK_FIRST_USERINFO = "202004:LEM:HASH:NEWYEAR2020:AREARANK:FIRST:USERINFO";
	
	/**
	 * 2020压岁钱活动-40个区域配置(Hash)
	 */
	public static final String REDIS_KEY_NEWYEARS2020_CONFIG_AREA = "202004:LEM:HASH:NEWYEAR2020:CONFIG:AREA";
	
	/**
	 * 2020压岁钱活动-每个地区激活用户排名(Hash)
	 * 最终key值：LEM:HASH:NEWYEAR2020:AREARANK:goodsId
	 */
	public static final String REDIS_KEY_NEWYEARS2020_EVERY_AREARANK = "202004:LEM:HASH:NEWYEAR2020:EVERY:AREARANK";
	
	/**
	 * 2020压岁钱活动-每个地区激活排名计数缓存(String)
	 */
	public static final String REDIS_KEY_NEWYEARS2020_EVERY_AREARANK_INCY = "202004:LEM:STRING:NEWYEAR2020:EVERY:AREARANK:INCY";
	
	/**
	 * 2020压岁钱活动-每个地区激活第一名用户信息缓存(Hash)
	 */
	public static final String REDIS_KEY_NEWYEARS2020_EVERY_AREARANK_FIRST_USERINFO = "202004:LEM:HASH:NEWYEAR2020:EVERY:AREARANK:FIRST:USERINFO";

	/**
	 * 2020压岁钱活动-获取地区对应区域信息缓存(Hash)
	 */
	public static final String REDIS_KEY_NEWYEARS2020_AREABLOCK_INDEX = "202004:LEM:HASH:NEWYEAR2020:AREABLOCK:INDEX";
	
	/**
	 * 2020压岁钱活动-引导流程信息缓存(Hash)
	 */
	public static final String REDIS_KEY_NEWYEARS2020_GUIDE_PROGRESS = "202004:LEM:HASH:NEWYEAR2020:GUIDE:PROGRESS";

	/**
	 * 2020压岁钱活动-最新激活大区域数据缓存
	 */
	public static final String REDIS_KEY_NEWYEARS2020_NEW_ACTIVE_AREA_BLOCK = "202004:LEM:HASH:NEWYEAR2020:NEW:ACTIVE:AREABLOCK";

	/**
	 * 2020压岁钱未签提醒-大富翁
	 */
	public static final String REDIS_KEY_NEWYEARS2020_PENSION_MSG_ALTER = "202004:LEM:STRING:NEWYEAR2020:PENSION:MSG:ALTER";
	
	/**
	 * 2020压岁钱未签提醒-存钱罐
	 */
	public static final String REDIS_KEY_NEWYEARS2020_SIGN_MSG_ALTER = "202004:LEM:STRING:NEWYEAR2020:SIGN:MSG:ALTER";
	
	/**
	 * 2020压岁钱活动-布谷鸟上方箭头显示次数缓存(Hash)
	 */
	public static final String REDIS_KEY_NEWYEARS2020_ARROWS_COUNT = "202004:LEM:HASH:NEWYEAR2020:ARROWS:COUNT:USERID";
	
	/**
	 * 2020压岁钱排行榜用户数据缓存(Hash)
	 */
	public static final String REDIS_KEY_NEWYEARS2020_USER_RANK_INFO = "202004:LEM:HASH:NEWYEAR2020:USER:RANK:INFO";
	
	
	/******************** 2020压岁钱预热活动 ***************************/
	/**
	 * 2020压岁钱预热活动-游戏开始时间缓存(Hash)
	 */
	public static final String REDIS_KEY_NEWYEARS2020_PREHEAT_GAME_BEGIN = "202004:LEM:HASH:NEWYEAR2020:PREHEAT:GAME:BEGIN";
	
	/**
	 * 2020压岁钱预热活动-游戏提交步数
	 */
	public static final String REDIS_KEY_NEWYEARS2020_PREHEAT_GAME_STEP = "202004:LEM:HASH:NEWYEAR2020:PREHEAT:GAME:STEP";

	/**
	 * 2020预热活动-用户获得某日挑战赛猪币奖励缓存(Set)
	 */
	public static final String REDIS_KEY_PREHEAT2020_SEND_REWARD = "202004:LEM:SET:NEWYEAR2020:PREHEAT:SEND:REWARD:OPERATEDATE:USERID";
	
	/**
	 * 2020预热活动-用户当前排行榜最好记录(Hash)
	 */
	public static final String REDIS_KEY_NEWYEARS2020_PREHEAT_RANK_THE_BEST_RECORD = "202004:LEM:HASH:NEWYEAR2020:PREHEAT:RANK:THEBEST:RECORD";
	
	/**
	 * 2020预热活动-当前排行榜所有信息记录(ZSet)
	 */
	public static final String REDIS_KEY_NEWYEARS2020_PREHEAT_RANK_ALL = "202004:LEM:ZSET:NEWYEAR2020:PREHEAT:RANK:ALL";
	
	/**
	 * 2020预热活动-当前排行榜群信息记录(ZSet)
	 */
	public static final String REDIS_KEY_NEWYEARS2020_PREHEAT_RANK_GROUP = "202004:LEM:ZSET:NEWYEAR2020:PREHEAT:RANK:GROUP";
	
	/**
	 * 每天缓存限制用户游戏次数(String)
	 * LEM:STRING:NEWYEAR2020:PREHEAT:GAME:COUNT:20191220:userId
	 */
	public static final String REDIS_KEY_NEWYEARS2020_PREHEAT_GAME_COUNT = "202004:LEM:STRING:NEWYEAR2020:PREHEAT:GAME:COUNT";
	
	/************************ 2020压岁钱拉新活动 ****************************/
	/**
	 * 2020老拉新活动-用户今日共同签到奖励缓存(Set)
	 */
	public static final String REDIS_KEY_PULLNEW2020_SEND_REWARD = "202004:LEM:SET:PULLNEW2020:SEND:REWARD:OPERATEDATE:BIZKEY";
	
	/**
	 * 2020老拉新活动-用户挑战失败缓存(Set)
	 */
	public static final String REDIS_KEY_PULLNEW2020_USER_FAILURE = "202004:LEM:SET:PULLNEW2020:USER:FAILURE";
	
	/**
	 * 2020老拉新活动-用户完成挑战缓存(Set)
	 */
	public static final String REDIS_KEY_PULLNEW2020_USER_ACHIEVE = "202004:LEM:SET:PULLNEW2020:USER:ACHIEVE";
	
	/**
	 * 2020老拉新活动-共同签到天数对应猪豆豆奖励信息缓存(String)
	 */
	public static final String REDIS_KEY_PULLNEW2020_REWARD_INFO = "202004:LEM:STRING:PULLNEW2020:GAME:STAR:INFO";
	
	
	
	/*******************诸葛亮游戏内部版*******************/
	/**
	 * 诸葛亮游戏内部版-房间信息缓存(Hash)
	 */
	public static final String REDIS_KEY_ZHUGELIANGGAME_ROOM_INFO = "202004:LEM:HASH:ZHUGELIANGGAME:ROOM:INFO";
	
	/**
	 * 诸葛亮游戏内部版-用户当前记录ID缓存(Hash)
	 */
	public static final String REDIS_KEY_ZHUGELIANGGAME_USER_DRAWID = "202004:LEM:HASH:ZHUGELIANGGAME:USER:DRAWID";
	
	/**
	 * 诸葛亮游戏内部版-某个房间某场待匹配用户缓存(Set)
	 * 最终key值：LEM:SET:ZHUGELIANGGAME:USER:NOMATCH:房间ID:场次
	 */
	public static final String REDIS_KEY_ZHUGELIANGGAME_USER_NOMATCH = "202004:LEM:SET:ZHUGELIANGGAME:USER:NOMATCH:";
	
	/**
	 * 诸葛亮游戏内部版-用户对手信息缓存(HASH)
	 */
	public static final String REDIS_KEY_ZHUGELIANGGAME_USER_MATCHINFO = "202004:LEM:HASH:ZHUGELIANGGAME:USER:MATCHINFO";
	
	/**
	 * 诸葛亮游戏内部版-用户坐标缓存(HASH)
	 */
	public static final String REDIS_KEY_ZHUGELIANGGAME_USER_SITE = "202004:LEM:HASH:ZHUGELIANGGAME:USER:SITE";
	
	/**
	 * 诸葛亮游戏内部版-房间剩余可进入人数缓存(HASH)
	 */
	public static final String REDIS_KEY_ZHUGELIANGGAME_ROOM_LEFT_UNUM = "202004:LEM:HASH:ZHUGELIANGGAME:ROOM:LEFT:UNUM";
	/*******************诸葛亮游戏内部版 end*******************/
	
	/************************ 2020压岁钱第一天活动 ****************************/
	
	/**
	 * 2020压岁钱第一天活动-用户获得水世界门票缓存(Set)
	 */
	public static final String REDIS_KEY_FIRSTDAY2020_USER_GET_TICKETS = "202004:LEM:SET:FIRSTDAY2020:USER:GET:TICKETS";
	
	/************************ 2020压岁钱直通40% ****************************/
	/**
	 * 2020压岁钱直通40%-用户累计存钱金额缓存(HASH)
	 */
	public static final String REDIS_KEY_DIRECT402020_USER_TOTALAMOUNT = "202004:LEM:HASH:DIRECT402020:USER:TOTALAMOUNT";
	
	
	/**
	 * 2020压岁钱是否解锁过海洋的缓存(Set)
	 */
	public static final String REDIS_KEY_UNLOCKSEA_USER_COLLECTION = "202004:LEM:SET:UNLOCKSEA:USER:COLLECTION";
	/**
	 * 2020压岁钱-第一个种下世界树的用户信息
	 */
	public static final String REDIS_KEY_FIRST_PLANT_WORLD_TREE = "202004:LEM:STRING:FIRST:PLANT:WORLD:TREE";
	
	/************************ 2020元旦 ****************************/
	/**
	 * 2020元旦活动存用户的贡献数值最多的(zset)
	 */
	public static final String REDIS_KEY_NEWYEARDAYS2020_THEMOST = "202004:LEM:ZSET:NEWYEAR2020:FISRT:DAY:FESTIVAL:THEMOST";
	
	/**
	 * 2020元旦活动存用户的贡献数值最近的(lsit)
	 */
	public static final String REDIS_KEY_NEWYEARDAYS2020_UPTODATE = "202004:LEM:LIST:NEWYEAR2020:FISRT:DAY:FESTIVAL:UPTODATE";
	
	/**
	 * 2020元旦活动-用户补发过猪豆豆缓存(Set)
	 */
	public static final String REDIS_KEY_NEWYEARS2020_USER_GET_REPLACEMENT = "202004:LEM:SET:NEWYEARS2020:USER:GET:REPLACEMENT";

	/**
	 * 2020元旦活动-用户头像昵称群名缓存(Hash)
	 */
	public static final String REDIS_KEY_NEWYEARS2020_USER_BASE_INFOMATION = "202004:LEM:HASH:NEWYEAR2020:BASE:INFOMATION";
	
	/**
	 * 2020元旦活动-用户是否种过2020缓存(Set)
	 */
	public static final String REDIS_KEY_NEWYEARS2020_USER_PLANTED_2020SEEDS = "202004:LEM:HASH:NEWYEARS2020:USER:PLANTED:2020SEEDS";

	/**
	 * 2020元旦活动-用户是否种过50000缓存(Set)
	 */
	public static final String REDIS_KEY_NEWYEARS2020_USER_PLANTED_50000SEEDS = "202004:LEM:HASH:NEWYEARS2020:USER:PLANTED:50000SEEDS";
	
	/**
	 * 2020元旦活动-群内种植2020排名缓存(Hash)
	 */
	public static final String REDIS_KEY_NEWYEARS2020_GROUP_PLANTED_2020SEEDS_RANK = "202004:LEM:HASH:NEWYEAR2020:GROUP:PLANTED:2020SEEDS:RANK:GROUPKEY";
	
	/**
	 * 2020万人PK-32强用户点赞数据
	 */
	public static final String REDIS_KEY_PKGAME_TEN_THOUSAND_LIKE = "202004:LEM:STRING:PKGAME:TEN:THOUSAND:LIKE";
	
	/**
	 * 2020万人PK-点过赞的用户
	 */
	public static final String REDIS_KEY_PKGAME_TEN_THOUSAND_DO_LIKE = "202004:LEM:STRING:PKGAME:TEN:THOUSAND:DO:LIKE";
	/**
	 * 2020万人PK-操作步数
	 */
	public static final String REDIS_KEY_PKGAME10000_STEP = "202004:LEM:STRING:PKGAME10000:STEP";
	public static final String REDIS_KEY_PKGAME10000_BEGIN_TIME = "202004:LEM:STRING:PKGAME10000:BEGINTIME";
	/**
	 * 2020万人PK-游戏开始时间缓存(Hash)
	 */
	public static final String REDIS_KEY_PKGAME10000_GAME_BEGIN = "202004:LEM:STRING:PKGAME10000:GAME:BEGIN";
	
	/**
	 * 2020万人PK-娱乐赛房间状态
	 */
	public static final String REDIS_KEY_PKGAME10000_GAME_ROOM_STATUS = "202004:LEM:STRING:PKGAME10000:ROOM:STATUS:";
	
	/**
	 * 2020万人PK-娱乐赛对手userId
	 */
	public static final String REDIS_KEY_PKGAME10000_GAME_RIV_USERID = "202004:LEM:STRING:PKGAME10000:RIV:USERID:";
	
	/**
	 * 2020万人PK-娱乐赛对手是否已摆放怪兽
	 */
	public static final String REDIS_KEY_PKGAME10000_GAME_RIV_HAS_PUT = "202004:LEM:STRING:PKGAME10000:RIV:HAS:PUT:";
	
	/**
	 * 2020万人PK-娱乐赛已摆放怪兽数据缓存
	 */
	public static final String REDIS_KEY_PKGAME10000_GAME_MATRIX_DATA = "202004:LEM:STRING:PKGAME10000:MATRIX:DATA:";
	
	/**
	 * 2020万人PK-娱乐赛玩家已点过的格子
	 */
	public static final String REDIS_KEY_PKGAME10000_GAME_CAIL_INDEX = "202004:LEM:LIST:PKGAME10000:CAIL:INDEX:";
	
	/**
	 * 2020万人PK-娱乐赛下一步轮到谁
	 */
	public static final String REDIS_KEY_PKGAME10000_GAME_NEXT_USER = "202004:LEM:STRING:PKGAME10000:NEXT:USER:";
	
	/**
	 * 2020万人PK-娱乐赛玩家是否胜利
	 */
	public static final String REDIS_KEY_PKGAME10000_GAME_HAS_WIN = "202004:LEM:STRING:PKGAME10000:HAS:WIN:";
	
	/**
	 * 2020万人PK-娱乐赛玩家正确击中怪兽的数据
	 */
	public static final String REDIS_KEY_PKGAME10000_GAME_CORRECT_NUM = "202004:LEM:LIST:PKGAME10000:CORRECT:NUM:";
	
	/************************ 用户基本信息  ****************************/
	
	public static final String REDIS_KEY_USER_HEADURL_BASE64 = "202004:LEM:STRING:USER:HEADURL:BASE64";
	
	/************************ 抢豆豆活动  ****************************/
	
	/**
	 * 抢豆豆活动-群内抢豆豆总数量(Hash)
	 */
	public static final String REDIS_KEY_NEWYEARS2020_PIGBEAN_GROUP_AMOUNT_ROBTYPE = "202004:LEM:HASH:NEWYEAR2020:PIGBEAN:GROUP:AMOUNT:ROBTYPE";
	
	/**
	 * 抢豆豆活动-群内开抢轮数(Hash)
	 */
	public static final String REDIS_KEY_NEWYEARS2020_PIGBEAN_GROUP_ROUNDS_ROBTYPE = "202004:LEM:HASH:NEWYEAR2020:PIGBEAN:GROUP:ROUNDS:ROBTYPE";
	
	/**
	 * 抢豆豆活动-群内储备未开抢的抢豆豆数量(Hash)
	 */
	public static final String REDIS_KEY_NEWYEARS2020_PIGBEAN_GROUP_STORE_ROBTYPE = "202004:LEM:HASH:NEWYEAR2020:PIGBEAN:GROUP:STORE:ROBTYPE";
	
	/**
	 * 抢豆豆活动-群内轮播(list)
	 */
	public static final String REDIS_KEY_NEWYEARS2020_PIGBEAN_GROUP_RANK_ROBTYPE = "202004:LEM:LIST:NEWYEAR2020:PIGBEAN:GROUP:RANK:ROBTYPE:GROUPKEY";
	
	/**
	 * 每个群一个缓存key，用户记录群的开抢详情
	 */
	public static final String REDIS_KEY_NEWYEARS2020_PIGBEAN_GROUP_RELATE_INFO = "202004:LEM:SET:NEWYEAR2020:PIGBEAN:GROUP:RELATE:INFO";
	
	/**
	 * 用户记录每个群进行到第几轮
	 */
	public static final String REDIS_KEY_NEWYEARS2020_PIGBEAN_GROUP_ROUNDS_RECORD = "202004:LEM:HASH:NEWYEAR2020:PIGBEAN:GROUP:ROUNDS:RECORD";
	
	/**
	 * 当天是否发送当天第条抢猪豆豆消息
	 */
	public static final String REDIS_KEY_NEWYEARS2020_PIGBEAN_GROUP_ISSEND_GRAB = "202004:LEM:HASH:NEWYEAR2020:PIGBEAN:GROUP:ISSEND:GRAB";
	
	/**
	 * 抢豆豆活动-线程内正在处理的群缓存(String)
	 */
	public static final String REDIS_KEY_NEWYEARS2020_PIGBEAN_THREAD_GROUPKEY = "202004:LEM:SET:NEWYEAR2020:PIGBEAN:THREAD:GROUPKEY";
	
	/**
	 * MQ微信消息发送
	 * 最终值：LEM:HASH:NEWYEAR2020:PIGBEAN:SEND:START:MSG:20200109
	 */
	public static final String REDIS_KEY_NEWYEARS2020_PIGBEAN_SEND_START_MSG = "202004:LEM:HASH:NEWYEAR2020:PIGBEAN:SEND:START:MSG:";

	/**
	 * 抢豆豆活动-弹窗计数
	 */
	public static final String REDIS_KEY_NEWYEARS2020_PIGBEAN_GRAB_POP_WINDOW = "202004:LEM:HASH:NEWYEAR2020:PIGBEAN:GRAB:POP:WINDOW";
	
	/**
	 * 抢豆豆活动-表示用户确实进入过游戏界面
	 */
	public static final String REDIS_KEY_NEWYEARS2020_PIGBEAN_GRAB_ENTER_GAME = "202004:LEM:HASH:NEWYEAR2020:PIGBEAN:GRAB:ENTER_GAME";
	
	/**
	 * 抢豆豆活动-MQ处理占位数据缓存
	 * 最终值：LEM:STRING:NEWYEAR2020:PIGBEAN:DRAWINFO:drawId
	 */
	public static final String REDIS_KEY_NEWYEARS2020_PIGBEAN_DRAW = "202004:LEM:STRING:NEWYEAR2020:PIGBEAN:DRAWINFO";
	
	/**
	 * 抢豆豆活动-限制用户抢豆豆流程中刷新操作
	 * 最终值：LEM:STRING:NEWYEAR2020:PIGBEAN:REFRESH:drawId
	 */
	public static final String REDIS_KEY_NEWYEARS2020_PIGBEAN_REFRESH = "202004:LEM:STRING:NEWYEAR2020:PIGBEAN:REFRESH";
		
	
	/*************************** 限时答题活动 *************************************/
	/**
	 * 限时答题活动-问卷序号缓存(String)
	 * LEM:STRING:LIMITTIMEANS:RESEARCHNO:问卷KEY
	 */
	public static final String REDIS_KEY_LIMITTIMEANS_RESEARCHNO = "202004:LEM:STRING:LIMITTIMEANS:RESEARCHNO:";
	
	/**
	 * 限时答题活动-总成绩scoreId缓存(HASH) userId:scoreId
	 * LEM:MAP:LIMITTIMEANS:SCOREIDS
	 */
	public static final String REDIS_KEY_LIMITTIMEANS_SCOREID = "202004:LEM:HASH:LIMITTIMEANS:SCOREIDS";
	
	/*************************** 元宵节答题活动 *************************************/
	/**
	 * 问卷ID
	 */
	public static final String REDIS_KEY_LARTERN_FESTIVAL_RESEARCHNO = "202004:LEM:STRING:LARTERN:FESTIVAL:RESEARCHNO";
	
	/**
	 * 用户上次未完成的题目
	 */
	public static final String REDIS_KEY_LARTERN_FESTIVAL_QUESTIONID = "202004:LEM:STRING:LARTERN:FESTIVAL:QUESTIONID:";
	
	/**
	 * 用户已成功答题
	 */
	public static final String REDIS_KEY_LARTERN_FESTIVAL_HAS_ANSWER_CORRECT = "202004:LEM:STRING:LARTERN:FESTIVAL:HAS:ANSWER:CORRECT:";
	
	/**
	 * 用户已答对题目数
	 */
	public static final String REDIS_KEY_LARTERN_FESTIVAL_ANSWER_CORRECT_NUM = "202004:LEM:STRING:LARTERN:FESTIVAL:ANSWER:CORRECT:NUM:";
	
	
	/******************** 2020压岁钱活动 迭代***************************/
	/**
	 * 用户春节期间是否获得过520种子缓存(Set)
	 */
	public static final String REDIS_KEY_NEWYEARS2020_USER_GOT_520SEEDS = "202004:LEM:HASH:NEWYEARS2020:USER:GOT:520SEEDS";
	
	/**
	 * 用户春节期间是否获得过1314种子缓存(Set)
	 */
	public static final String REDIS_KEY_NEWYEARS2020_USER_GOT_1314SEEDS = "202004:LEM:HASH:NEWYEARS2020:USER:GOT:1314SEEDS";
	
	/**
	 * 用户春节期间是否获得过3344种子缓存(Set)
	 */
	public static final String REDIS_KEY_NEWYEARS2020_USER_GOT_3344SEEDS = "202004:LEM:HASH:NEWYEARS2020:USER:GOT:3344SEEDS";
	
	/**
	 * 用户春节期间是否获得过9999种子缓存(Set)
	 */
	public static final String REDIS_KEY_NEWYEARS2020_USER_GOT_9999SEEDS = "202004:LEM:HASH:NEWYEARS2020:USER:GOT:9999SEEDS";
	
	/**
	 * 用户春节期间是否获得过50000种子缓存(Set)
	 */
	public static final String REDIS_KEY_NEWYEARS2020_USER_GOT_50000SEEDS = "202004:LEM:HASH:NEWYEARS2020:USER:GOT:50000SEEDS";
	
	/**
	 * 新型肺炎产品
	 */
	public static final String REDIS_KEY_NEWFEIYAN_JOININSNO = "202004:LEM:STRING:NEWFEIYAN:JOININSNO:";

	/**
	 * 是否生成过情人节农田
	 */
	public static final String REDIS_KEY_NEWYEARS2020_VALENTINESDAY_FARMLAND = "202004:LEM:SET:NEWYEARS2020:VALENTINESDAY_FARMLAND";
	
	/**
	 * 用户今日空庄园弹窗缓存(Set)
	 */
	public static final String REDIS_KEY_NEWYEARS2020_USER_EMPTY_MANOR_POP = "202004:LEM:SET:NEWYEARS2020:USER:EMPTY:MANOR:POP:OPERATEDATE";
	
	/**
	 * 用户今日生日弹窗缓存(Set)
	 */
	public static final String REDIS_KEY_NEWYEARS2020_USER_BIRTHDAY_POP = "202004:LEM:SET:NEWYEARS2020:USER:BIRTHDAY:POP:OPERATEDATE";
	
	/**
	 * 用户勾选关闭生日弹窗提醒缓存(Set)
	 */
	public static final String REDIS_KEY_NEWYEARS2020_USER_CLOSE_BIRTHDAY_POP = "202004:LEM:SET:NEWYEARS2020:USER:CLOSE:BIRTHDAY:POP";
	
	/**
	 * 用户是否种过3344缓存(Set)
	 */
	public static final String REDIS_KEY_NEWYEARS2020_USER_PLANTED_3344SEEDS = "202004:LEM:HASH:NEWYEARS2020:USER:PLANTED:3344SEEDS";
	
	/**
	 * 用户是否种过9999缓存(Set)
	 */
	public static final String REDIS_KEY_NEWYEARS2020_USER_PLANTED_9999SEEDS = "202004:LEM:HASH:NEWYEARS2020:USER:PLANTED:9999SEEDS";
	
	/**
	 * 用户今日设置目标弹窗缓存(Set)
	 */
	public static final String REDIS_KEY_NEWYEARS2020_USER_SET_TARGET_POP = "202004:LEM:SET:NEWYEARS2020:USER:SET:TARGET:POP:OPERATEDATE";
	
	/**
	 * 弹出设置目标弹窗次数计数缓存(Hash)
	 */
	public static final String REDIS_KEY_NEWYEARS2020_SHOW_SET_TARGET_POP_COUNT = "202004:LEM:HASH:NEWYEARS2020:SHOW:SET:TARGET:POP:COUNT";
	
	/**
	 * 用户近年来最好排名缓存(Hash)
	 */
	public static final String REDIS_KEY_NEWYEARS2020_USER_HISTORY_BEST_RANKNO = "202004:LEM:HASH:NEWYEARS2020:USER:HISTORY:BEST:RANKNO";
	
	/**
	 * 掉档标记缓存(Set)
	 */
	public static final String REDIS_KEY_NEWYEARS2020_USER_DEMOTION = "202004:LEM:SET:NEWYEARS2020:USER:DEMOTION";
	
	/**
	 * 排行榜各档位最后一名详情
	 */
	public static final String REDIS_KEY_NEWYEARS2020_RANK_LEVEL_LAST = "202004:LEM:HASH:NEWYEARS2020:RANK:LEVEL:LAST";
	
	/******************** 2020压岁钱活动 最后三天抢土豪***************************/
	/**
	 * 前5000名用户的user_id和名次(Hasp)
	 */
	public static final String REDIS_KEY_NEWYEARS2020_RANK_TOP_5000PEOPLE = "202004:LEM:HASH:NEWYEARS2020:RANK:TOP:5000PEOPLE";
	
	/**
	 * 前3000名用户的user_id和名次(Hasp) 活动结束后跑的排行榜前3000定值
	 */
	public static final String REDIS_KEY_NEWYEARS2020_RANK_SOLID_3000PEOPLE = "202004:LEM:HASH:NEWYEARS2020:RANK:SOLID:3000PEOPLE";

	/**
	 * 2020压岁钱排行榜已赠送奖励缓存（SET）
	 */
	public static final String REDIS_KEY_NEWYEARS2020_RANK_DRAW = "202004:LEM:SET:NEWYEARS2020:RANK:HAS_SEND_DRAW";

	/**
	 * 2020压岁钱排行榜弹窗勋章数和节省金额
	 */
	public static final String REDIS_KEY_NEWYEARS2020_PURPLE_MONEY = "202004:LEM:HASH:NEWYEARS2020:USER:PURPLE_MONEY";
	
	/**
	 * 2020压岁钱排行榜设置及时消息的缓存（SET）
	 */
	public static final String REDIS_KEY_NEWYEARS2020_SET_RANKNO_PROMPT_MSG = "202004:LEM:SET:NEWYEARS2020:SET:RANKNO:PROMPT:MSG";
	
	/**
	 * map类型，key存放保单号policyNo，value存放保单对应的F码
	 */
	public static final String POLICY_PROGRAMMER_CODE = "202004:LEM:HASH:POLICY:PROGRAMMERCODE";
	
	/********************天天签到300天挑战 战令***************************/
	
	/**
	 * 天天签到300天挑战 战令-不同开罐类型任务列表缓存(Hash)
	 */
	public static final String REDIS_KEY_SIGN300_WARORDER_TASK_LIST = "202004:LEM:HASH:SIGN300:WARORDER:TASK:LIST";
	
	/**
	 * 天天签到300天挑战 战令-任务配置信息缓存(Hash)
	 */
	public static final String REDIS_KEY_SIGN300_WARORDER_TASK_INFO = "202004:LEM:HASH:SIGN300:WARORDER:TASK:INFO";

	/**
	 * 天天签到300天挑战 及时消息-当天断签计数缓存(string)
	 */
	public static final String REDIS_KEY_SIGN300_CURR_NOT_SIGN = "202004:LEM:STRING:SIGN300:CURR_NOT_SIGN:";
	
	/**
	 * 天天签到300天挑战 战令-用户完成爱的GPS(Set)
	 */
	public static final String REDIS_KEY_SIGN300_WARORDER_TASK_ACHIEVE_LOVEGPS = "202004:LEM:SET:SIGN300:WARORDER:ACHIEVE:LOVEGPS";
	
	/**
	 * 天天签到300天挑战 战令-已修复过的全年签到补签金额数据(Set)
	 */
	public static final String REDIS_KEY_SIGN300_WARORDER_REPAIRED = "202004:LEM:SET:SIGN300:WARORDER:REPAIRED";
	

	/******************** 直播抢购植物大米  ***************************/
	/**
	 * 限时抢购植物送大米
	 */
	public static final String REDIS_KEY_NEWYEARS2020_BUY_SEND_RICE = "202004:LEM:SET:NEWYEARS2020:BUY:SEND:RICE";
	
	/**
	 * 用户是否送过大米
	 */
	public static final String REDIS_KEY_NEWYEARS2020_HAVE_SEND_RICE = "202004:LEM:STRING:NEWYEARS2020:HAVE:SEND:RICE:";
	
	
	/******************** 天天存签到  ***************************/
	
	/**
	 * 荣誉等级弹窗（暂时不能修正缓存key）
	 */
	public static final String REDIS_KEY_SIGN_HONOR_SHOW_POPUP = "LEM:STRING:SIGN:HONOR:SHOW:POPUP";
	
	
	/******************** 成就勋章  ***************************/
	
	/**
	 * 历史数据缓存
	 */
	public static final String REDIS_KEY_GROWN_BADGE_OLDDATA_REDIS = "202004:LEM:STRING:GROWN:BADGE:OLDDATA:REDIS";
	
	/**
	 * 小程序易课堂点击立即报名按钮（暂不可修改缓存key）
	 */
	public static final String REDIS_KEY_EASY_TEACH_IGN_UP_NUM = "LEM:LIST:EASY_TEACH_SIGN_UP_NUM";
	
	/**
	 * 小程序易课堂拉新活动 缓存邀请人
	 */
	public static final String REDIS_KEY_EASY_TEACH_INVITE_USER = "202004:LEM:SET:EASY:TEACH:INVITE:USER";
	
	/**
	 * 小程序易课堂拉新活动 自主用户
	 */
	public static final String REDIS_KEY_EASY_TEACH_ZZ_USER = "202004:LEM:SET:EASY:TEACH:ZZ:USER";
	
	/**
	 * 小程序易课堂拉新活动 缓存邀请人(机器人消息)
	 */
	public static final String REDIS_KEY_EASY_TEACH_ROBOT_INVITE_USER = "202004:LEM:SET:EASY:TEACH:ROBOT:INVITE:USER";
	
	/**
	 * 小程序易课堂拉新活动 小程序用户授权手机号
	 */
	public static final String REDIS_KEY_EASY_TEACH_USER_MOBILE = "202004:LEM:STRING:EASY_TEACH_USER_MOBILE";
	
	/**
	 * 小程序易课堂拉新活动 缓存任务key
	 */
	public static final String REDIS_KEY_EASY_TEACH_INVITE_USER_TASK_KEY = "202005:LEM:STRING:EASY:TEACH:INVITE:USER:TASK:KEY";
	
	/**
	 * 小程序易课堂往期回顾 关联直播  缓存任务key（发送开始报名机器人消息）
	 */
	public static final String REDIS_KEY_EASY_TEACH_LINKED_LIVE_TASK_KEY = "202005:LEM:STRING:EASY:TEACH:LINKED:LIVE:TASK:KEY";
	
	/**
	 * 小程序易课堂往期回顾 关联直播  缓存订阅用户(机器人消息)
	 */
	public static final String REDIS_KEY_EASY_TEACH_ROBOT_LINKED_LIVE_USER = "202005:LEM:SET:EASY:TEACH:ROBOT:LINKED:LIVE:USER";
	
	
	/******************** 活动领取奖品机器人提醒消息  ***************************/
	
	/**
	 * 贺岁活动奖品填写
	 * 还有奖品未填写地址
	 */
	public static final String REDIS_KEY_NEWYEARS2020_PRIZE_ROBOTNEWS = "202004:LEM:LIST:NEWYEARS2020:PRIZE:ROBOTNEWS";
	
	/**
	 * 挑战赛奖品填写
	 * 挑战成功10天奖品未填写地址
	 */
	public static final String REDIS_KEY_CHALLENGE_PRIZE_ROBOTNEWS = "202004:LEM:LIST:CHALLENGE:PRIZE:ROBOTNEWS";
	
	/**
	 * 小春芽提醒兑换
	 * 待领取+剩余≥15个、有孩子未兑换小春芽
	 */
	public static final String REDIS_KEY_XCY_EXCHANGE_ROBOTNEWS = "202004:LEM:LIST:XCY:EXCHANGE:ROBOTNEWS";
	
	/**
	 * 小春芽承保成功
	 * 承保成功的所有人
	 */
	public static final String REDIS_KEY_XCY_INSURANCE_ROBOTNEWS = "202004:LEM:LIST:XCY:INSURANCE:ROBOTNEWS";
	
	/**
	 * 存放企业微信externalUserId和unionId对应关系（HASH）
	 */
	public static final String REDIS_KEY_EXTERNAL_UNIONID = "202004:LEM:HASH:CORP:EXUSERID:UNIONID";
	
	/**
	 * 小程序易课堂订阅消息模版 list
	 */
	public static final String REDIS_KEY_XCX_EASY_SUB_TEMPLATE = "202004:LEM:LIST:XCX:EASY:SUB:TEMPLATE:";
	
	/**
	 * 小程序易课堂订阅消息模版详情string
	 */
	public static final String REDIS_KEY_XCX_EASY_SUB_TEMPLATE_CONTENT = "202004:LEM:LIST:XCX:EASY:SUB:TEMPLATE:CONTENT:";

	/**
	 * 月目标差额提醒(Hash)
	 */
	public static final String REDIS_KEY_MONTH_GOAL_MARGIN = "202004:LEM:HASH:ROBOTNEWS:MONTHGOALMARGIN";
	
	/**
	 * 月月开通大富翁提醒(Hash)
	 */
	public static final String REDIS_KEY_EVERY_MONTH_OPEN_DFW = "202004:LEM:HASH:ROBOTNEWS:EVERYMONTHOPENDFW";
	
	/**
	 * 投保人当天生日提醒(list)
	 */
	public static final String REDIS_KEY_APP_BIRHDAY_ON_TODAY = "202004:LEM:LIST:ROBOTNEWS:APPBIRHDAYONTODAY";
	
	/**
	 * 被保人当天生日提醒（List）
	 */
	public static final String REDIS_KEY_INS_BIRHDAY_ON_TODAY = "202004:LEM:LIST:ROBOTNEWS:INSBIRHDAYONTODAY";
	
	/**
	 * 与时间赛跑提醒（Hash）
	 */
	public static final String REDIS_KEY_TIME_RACE = "202005:LEM:HASH:ROBOTNEWS:TIMERACE";
	
	/**
	 * 与时间赛跑提醒终止开关（String）
	 */
	public static final String REDIS_KEY_TIME_RACE_SWITCH = "202005:LEM:STRING:ROBOTNEWS:TIMERACE:SWITCH";

	/**
	 * 日报表频率控制
	 */
	public static final String REDIS_KEY_DATA_REPORT_LOCK = "202004:LEM:STRING:DATE:REPORT:LOCK";
	
	/**
	 * 108/180挑战排行榜
	 */
	public static final String LEM_TTC_108_OR_108_CHANGE_SUCCESS_USER_DATA = "202004:LEM:SET:TTC:108:OR:108:CHANGE:SUCCESS:USER:DATA";
	public static final String LEM_TTC_108_180_CHANGE_USER_DATA_USERID = "202004:LEM:STRING:TTC:108:OR:180:CHANGE:SUCCESS:USER:DATA:USERID:";

	/**
	 * 2019年连续签到排行榜
	 */
	public static final String LEM_TTC_2019_ALL_YEAR_SIGN_USER_DATA_USERID = "202004:LEM:STRING:TTC:2019:ALL:YEAR:SIGN:USER:DATA:USERID:";
	public static final String LEM_TTC_2019_ALL_YEAR_SIGN_USER_DATA = "202004:LEM:SET:TTC:2019:ALL:YEAR:SIGN:USER:DATA";
	
	/********************天天签到300天挑战 正赛和报名***************************/
	public static final String RDS_ACTIVITY_USER_CHALLENGE_300_SUCCESSED_SET = "202004:LEM:SET:SIGN:CHALLENGE:300:SUCCESSED";
	public static final String RDS_USER_SIGNATURE_TYPE = "202004:LEM:ZSET:SIGN:300:CHALLENGE:USER:SIGNATURE:TYPE:";//缓存用户补签详情数据信息
	public static final String RDS_USER_SIGNATURE_TASK_ID = "202004:LEM:HASH:SIGN:300:CHALLENGE:USER:SIGNATURE:TASK:ID:";//缓存用户补签指定id数据信息
	public static final String RDS_USER_SIGNATURE_COUNT_TYPE = "202004:LEM:STRING:SIGN:300:CHALLENGE:USER:SIGNATURE:COUNT:TYPE:";//缓存用户补签数据计数信息
	public static final String RDS_SIGN_300_USER_OUT_SIGN = "202004:LEM:SET:SIGN:300:USER:OUT:SIGN";//用户断签弹窗
	public static final String RDS_SIGN_300_USER_REVIEW_SUCCESS = "202004:LEM:SET:SIGN:300:USER:REVIEW:SUCCESS";//用户补签审核通过弹窗

	/******************************** 记录开罐来源缓存 **************************************/
	/**
	 * 缓存弹窗次数
	 */
	public static String REDIS_KEY_OPENSOURCE_INC = "202004:LEM:HASH:OPEN_VIEW_SOURCE_INC";
	/**
	 * 抢土豪占位时间控制
	 */
	public static final String REIDS_KEY_GRAB_CHANCE_TIME = "202004:LEM:STRING:GRAB:CHANCE:TIME";
	
	
	/**
	 * 保存存钱罐邀请关系
	 */
	public static final String USER_RELATION_INVITE_GROW = "202004:LEM:SET:USER:RELATION:INVITE:GROW";
	
	/**
	 * 保存养老罐邀请关系
	 */
	public static final String USER_RELATION_INVITE_PENSION = "202004:LEM:SET:USER:RELATION:INVITE:PENSION";
	
	/**
	 * 防止存钱罐邀请关系丢失
	 */
	public static final String USER_RELATION_INVITE_GROW_REVIEW = "202004:LEM:SET:USER:RELATION:INVITE:GROW:REVIEW";
	
	/**
	 * 防止养老罐邀请关系丢失
	 */
	public static final String USER_RELATION_INVITE_PENSION_REVIEW = "202004:LEM:SET:USER:RELATION:INVITE:PENSION:REVIEW";
	
	/**
	 * 天天存弹窗签到天数排序
	 */
	public static final String LEM_SET_EVERY_DAY_SIGIN_RANK = "202004:LEM:SET:EVERY:DAY:SIGIN:RANK";
	
	/**
	 * 天天存弹窗签到天数单个用户
	 */
	public static final String LEM_SET_EVERY_DAY_SIGIN_RANK_USERID = "202004:LEM:SET:EVERY:DAY:SIGIN:RANK:USERID:";
	
	/**
	 * 天天存问候提醒
	 */
	public static final String LEM_STRING_EVERY_DAY_SIGIN_GOOD = "202004:LEM:STRING:EVERY:DAY:SIGIN:GOOD:";
	
	/**
	 * 天天存满贯次数更新
	 */
	public static final String LEM_STRING_EVERY_DAY_ALAM_UPDATE_VIEWID = "202005:LEM:STRING:EVERY:DAY:ALAM:UPDATE:VIEWID:";
	
	/**
	 * taskId的key前缀
	 */
	public static final String TASK_MONITOR_PRE="202004:LEM:STRING:TASK:";
	
	/**
	 * 更新过用户物品账号历史总获得数
	 */
	public static final String RDS_UPDATE_USER_GAIN_NUM = "202004:LEM:SET:UPDATE:USER:GAIN:NUM:";//用户补签审核通过弹窗
	
	/**
	 * 二维码链接缓存前缀
	 */
	public static final String RDS_QRCODE_URL_PRE="202004:LEM:STRING:QRCODE:";
	/**
	 * 新用户引导完成所有任务
	 */
	public static final String RDS_NEW_USER_GUIDE="202004:LEM:STRING:NEW:USER:GUIDE:";

	/**
	 * 自定义场景key
	 */
	public static final String LEM_LIST_VIEW_SCENE_LIST = "202004:LEM:LIST:VIEW:SCENE:LIST";
	
	/**
	 * 520活动猜拳匹配Id
	 */
	public static final String LEM_SET_520_ACT_GUESSING_GAME_ID = "202005:LEM:SET:520:ACT:GUESSING:GAME:ID";
	
	/**
	 * 520活动加保奖励轮播数据
	 */
	public static final String LEM_LIST_520_ACT_ADD_POS_DATA = "202005:LEM:LIST:520:ACT:ADD:POS:DATA";
	
	/**
	 * 520活动猜拳获胜轮播数据
	 */
	public static final String LEM_LIST_520_ACT_GAME_VICTOR_DATA = "202005:LEM:LIST:520:ACT:GAME:VICTOR:DATA";
	/**
	 * 520活动猜拳用户获胜发消息记录
	 */
	public static final String LEM_LIST_520_ACT_GAME_VICTOR_USERMSG = "202005:LEM:LIST:520:ACT:GAME:VICTOR:USERMSG";
	
	/**
	 * 2020-520活动
	 */
	public static final String LEM_2020520_ROB_RICE_COUNT_PREFIX = "202005:LEM:STRING:520:ACT:ROB_RICE_COUNT:";
	public static final String LEM_2020520_ROB_RICE_USER_PREFIX = "202005:LEM:SET:520:ACT:ROB_RICE_USER:";
	public static final String LEM_2020520_SLEEP_USER_MSG = "202005:LEM:SET:520:ACT:SLEEP_USER_MSG:";
	
	/**
	 * 罐友公益活动现有爱心总值
	 */
	public static final String LEM_ACT_WELFARE_LOVEVALUE_EXISTING = "202005:LEM:STRING:ACT:WELFARE:RULE_VALUE:";
	/**
	 * 罐友公益活动轮播图
	 */
	public static final String LEM_ACT_WELFARE_VICTOR_DATA = "202005:LEM:LIST:ACT:WELFARE:VICTOR:DATA:";
	
	/**
	 * 重要弹窗次数
	 */
	public static String REDIS_KEY_IMPORTANT_POPUP = "202005:LEM:HASH:IMPORTANT:POPUP";
	/**
	 * 用户旧等级缓存
	 */
	public static String REDIS_KEY_USER_OLD_LEVEL = "202005:LEM:HASH:USER_OLD_LEVEL";
	
	/**
	 * 2020儿童节加保轮播数据
	 */
	public static final String LEM_LIST_CHILDREN_DAY_ADD_POS_DATA = "202005:LEM:LIST:CHILDREN:DAY:ADD:POS:DATA";
	
	/**
	 * 2020儿童节游戏中奖轮播
	 */
	public static final String LEM_LIST_CHILDREN_DAY_GAME_DATA = "202005:LEM:LIST:CHILDREN:DAY:GAME:DATA";
	
	/**
	 * 儿童节活动用户中奖发消息记录
	 */
	public static final String LEM_LIST_CHILDREN_DAY_GAME_DRAW_USERMSG = "202005:LEM:LIST:CHILDREN:DAY:GAME:DRAW:USERMSG";
	
	/**
	 * add by zcl 
	 * 儿童节客户的用户Id,罐子Id,加保金额,得到猪币数,预计点亮紫色勋章,预计节省的金额
	 */
	public static final String LEM_LIST_CHILDREN_DAY_GAME_CLIENT_DATA_INFO = "202005:LEM:LIST:CHILDREN:DAY:GAME:CLIENT:DATA:INFO";
	
	/**
	 * add by zcl (大额加保另存)
	 * 儿童节客户的用户Id,罐子Id,加保金额,得到猪币数,预计点亮紫色勋章,预计节省的金额
	 */
	public static final String LEM_LIST_CHILDREN_DAY_GAME_LARGE_MONEY_POS = "202005:LEM:LIST:CHILDREN:DAY:GAME:LARGE:MONEY:POS";
	
	
}

