package com.open.crawler.utils;

/**
 * Created by Administrator on 2015/5/30.
 */
public class RedisUtil {
//    private static JedisPool pool;
//    private static Integer db=2;
//
//    static {
//        ResourceBundle bundle = ResourceBundle.getBundle("redis");
//        if (bundle == null) {
//            throw new IllegalArgumentException(
//                    "[redis.properties] is not found!");
//        }
//        JedisPoolConfig config = new JedisPoolConfig();
//        config.setMaxTotal(Integer.valueOf(bundle
//                .getString("redis.pool.maxActive")));
//        config.setMaxIdle(Integer.valueOf(bundle
//                .getString("redis.pool.maxIdle")));
//        config.setTestOnBorrow(Boolean.valueOf(bundle
//                .getString("redis.pool.testOnBorrow")));
//        config.setTestOnReturn(Boolean.valueOf(bundle
//                .getString("redis.pool.testOnReturn")));
//        pool = new JedisPool(config, bundle.getString("redis.ip"),
//                Integer.valueOf(bundle.getString("redis.port")), 0,
//                bundle.getString("redis.password"), db);
//    }
//
//    public static void setDb(Integer dbIndex)
//    {
//        db=dbIndex;
//    }
//
//    //设置数据
//    public static void set(String key, String value, Integer timeOut) {
//        Jedis jredis = pool.getResource();
//        if (timeOut > 0) {
//            jredis.expire(key, timeOut);
//        }
//        jredis.set(key, value);
//        pool.returnResourceObject(jredis);
//    }
//
//    public static String get(String key) {
//        Jedis jredis = pool.getResource();
//        String result = jredis.get(key);
//        pool.returnResourceObject(jredis);
//        return result;
//    }
//
//    public static void del(String key) {
//        Jedis jredis = pool.getResource();
//        Long result = jredis.del(key);
//        pool.returnResourceObject(jredis);
//    }
}
