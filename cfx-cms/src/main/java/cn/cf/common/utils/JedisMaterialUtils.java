/**
 *
 */
package cn.cf.common.utils;

import cn.cf.json.JsonUtils;
import cn.cf.property.PropertyConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author bin
 */
public class JedisMaterialUtils {
	// Redis服务器IP
	private static String ADDR = PropertyConfig.getProperty("REDIS_IP");

	// Redis的端口号
	private static int PORT = Integer.valueOf(PropertyConfig.getProperty("REDIS_PORT"));

	// 访问密码
	private static String AUTH = PropertyConfig.getProperty("REDIS_PASSWORD");

	// 可用连接实例的最大数目，默认值为8；
	// 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
	private static int MAX_TOTAL = 10000;

	// 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
	private static int MAX_IDLE = 200;

	// 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
	private static long MAX_WAIT = 10000;

	private static int TIMEOUT = 10000;

	// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
	private static boolean TEST_ON_BORROW = true;

	private static JedisPool jedisPool = null;

	/**
	 * 初始化Redis连接池
	 */
	static {
		try {
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxTotal(MAX_TOTAL);
			config.setMaxIdle(MAX_IDLE);
			config.setMaxWaitMillis(MAX_WAIT);
			config.setTestOnBorrow(TEST_ON_BORROW);
			jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT, AUTH);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取Jedis实例
	 *
	 * @return
	 */
	public synchronized static Jedis getJedis() {
		try {
			if (jedisPool != null) {
				Jedis resource = jedisPool.getResource();
				return resource;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 释放jedis资源
	 *
	 * @param jedis
	 */
	@SuppressWarnings("deprecation")
	public static void returnResource(final Jedis jedis) {
		if (jedis != null) {
			jedisPool.returnResource(jedis);
		}
	}

	/**
	 * 向缓存中设置字符串内容
	 *
	 * @param key
	 *            key
	 * @param value
	 *            value
	 * @return
	 * @throws Exception
	 */
	public static boolean set(String key, String value) throws Exception {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.set(key, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			returnResource(jedis);
		}
	}

	/**
	 * 向缓存中设置对象
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean set(String key, Object value) {
		Jedis jedis = null;
		try {
			String objectJson = JsonUtils.convertToString(value);
			jedis = getJedis();
			jedis.set(key, objectJson);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			returnResource(jedis);
		}
	}

	/**
	 * 向缓存中设置时效性对象
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean set(String key, Object value, int seconds) {
		Jedis jedis = null;
		try {
			String objectJson = JsonUtils.convertToString(value);
			jedis = getJedis();
			jedis.set(key, objectJson);
			if (seconds != 0) {
				jedis.expire(key, seconds);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			returnResource(jedis);
		}
	}

	/**
	 * 删除缓存中得对象，根据key
	 *
	 * @param key
	 * @return
	 */
	public static boolean del(String key) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			if (jedis.exists(key)) {
				jedis.del(key);
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			returnResource(jedis);
		}
	}

	/**
	 * 删除缓存中的多个键值对
	 *
	 * @param key
	 * @return
	 */
	public static boolean mdel(String key) {
		Jedis jedis = null;
		boolean result = false;
		try {
			jedis = getJedis();
			Set<String> keys = jedis.keys(key + "*");
			for (String tkey : keys) {
				long temp = jedis.del(tkey);
				if (temp > 0) {
					result = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			returnResource(jedis);
		}

		return result;
	}

	/**
	 * 根据key 获取内容
	 *
	 * @param key
	 * @return
	 */
	public static Object get(String key) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			if (jedis.exists(key)) {
				Object value = jedis.get(key);
				return value;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			returnResource(jedis);
		}
	}

	/**
	 * 根据key 获取对象
	 *
	 * @param key
	 * @return
	 */
	public static <T> T get(String key, Class<T> clazz) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			if (jedis.exists(key)) {
				String value = jedis.get(key);
				if (null != value) {
					return JsonUtils.toBean(value, clazz);
				} else {
					return null;
				}
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			returnResource(jedis);
		}
	}

	/**
	 * 设置缓存
	 *
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @param cacheSeconds
	 *            超时时间，0为不超时
	 * @return
	 */
	public static String setInvalid(String key, String value, int cacheSeconds) {
		String result = null;
		Jedis jedis = null;
		try {
			jedis = getJedis();
			result = jedis.set(key, value);
			if (cacheSeconds != 0) {
				jedis.expire(key, cacheSeconds);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			returnResource(jedis);
		}
		return result;
	}

	/**
	 * 设置Map缓存
	 *
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @param cacheSeconds
	 *            超时时间，0为不超时
	 * @return
	 */
	public static String setMap(String key, Map<String, String> value, int cacheSeconds) {
		String result = null;
		Jedis jedis = null;
		try {
			jedis = getJedis();
			if (jedis.exists(key)) {
				jedis.del(key);
			}
			result = jedis.hmset(key, value);
			if (cacheSeconds != 0) {
				jedis.expire(key, cacheSeconds);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			returnResource(jedis);
		}
		return result;
	}

	/**
	 * 获取Map缓存
	 *
	 * @param key
	 *            键
	 * @return 值
	 */
	public static Map<String, String> getMap(String key) {
		Map<String, String> value = null;
		Jedis jedis = null;
		try {
			jedis = getJedis();
			if (jedis.exists(key)) {
				value = jedis.hgetAll(key);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			returnResource(jedis);
		}
		return value;
	}

	/**
	 * 缓存是否存在
	 *
	 * @param key
	 *            键
	 * @return
	 */
	public static boolean existsObject(String key) {
		boolean result = false;
		Jedis jedis = null;
		try {
			jedis = getJedis();
			result = jedis.exists(key);
		} catch (Exception e) {
		} finally {
			returnResource(jedis);
		}
		return result;
	}

	/**
	 * 设置过期时间
	 */
	public static void expire(String key, int seconds) {
		if (seconds <= 0) {
			return;
		}
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.expire(key, seconds);
		} catch (Exception e) {
		} finally {
			returnResource(jedis);
		}

	}

	public static boolean acquireLock(String lock) {
		// 1. 通过SETNX试图获取一个lock
		boolean success = false;
		Jedis jedis = null;
		try {
			jedis = getJedis();
			long acquired = jedis.setnx(lock, lock);
			// SETNX成功，则成功获取一个锁
			if (acquired == 1) {
				success = true;
			} else { // 未超时，则直接返回失败
				success = false;
			}
		} catch (Exception e) {
		} finally {
			returnResource(jedis);
		}
		return success;
	}

	// 释放锁
	public static void releaseLock(String lock) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			if (jedis.exists(lock)) {
				jedis.del(lock);
			}
		} catch (Exception e) {
		} finally {
			returnResource(jedis);
		}
	}

	public static void lock(String goodsPk) {
		boolean lockFlag = true;
		int num = 0;
		while (lockFlag) {// 循环等待拿锁
			lockFlag = JedisMaterialUtils.acquireLock(goodsPk);
			if (num == 0) {
				JedisMaterialUtils.expire(goodsPk, 60);
			}
			num++;
		}

	}

	/**
	 * 获取Set缓存
	 *
	 * @param key 键
	 * @return 值
	 */
	public static Set<String> getSet(String key) {
		Set<String> value = null;
		Jedis jedis = null;
		try {
			jedis = getJedis();
			if (jedis.exists(key)) {
				String str = jedis.get(key);
				String demosub = str.substring(1,str.length()-1);
				String demoArray[] = demosub.replace("\"","").split(",");
				value = new HashSet<>();
				for (int i = 0; i < demoArray.length; i++) {
					value.add(demoArray[i]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			returnResource(jedis);
		}
		return value;
	}

	public static void main(String[] args) {
		releaseLock("28f1bcb31f294c239f62cbb508982147");
	}
}
