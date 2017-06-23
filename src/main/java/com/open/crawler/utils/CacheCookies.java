package com.open.crawler.utils;

import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;

import java.util.HashMap;
import java.util.Map;

/**
 * Cookies缓存操作对象（没有对失效的的键进行销毁操作，生产模式时 需要进一步完善）
 * @author WSG
 *
 */
public class CacheCookies {
	//redis.--String store
	//Key - Cokie
	private static final Map<String, Map<String, String>> cookies = new HashMap<String, Map<String,String>>();
	
	/**
	 * 存放一个Cookies对象集
	 * @param key
	 * @param cookie
	 */
	public static void put(String key, Map<String, String> cookie) {
		cookies.put(key, cookie);
	}
	
	/**
	 * 获取一个Cookies对象集
	 * @param key
	 * @return
	 */
	public static Map<String, String> get(String key) {
		return cookies.get(key);
	}
	
	/**
	 * 获取一个cookies对象集的String对象
	 * @param key
	 * @return
	 */
	public static String getCookieString(String key) {
		Map<String, String> cookie =  get(key);
		StringBuffer cookieStringbuffer = new StringBuffer();
		for (String k : cookie.keySet()) {
			if (cookie.get(k) != null && !"".equals(cookie.get(k)))
				cookieStringbuffer.append(k).append("=").append(cookie.get(k)).append("; ");
			else
				cookieStringbuffer.append(k).append("; ");
		}
		return cookieStringbuffer.toString();
	}
	
	/**
	 * 获取一个Cookies对象集的CookieStore
	 * @param key
	 * @return
	 */
	public static CookieStore getCookieStore(String key) {
		Map<String, String> cookie =  get(key);
		CookieStore cookieStore = new BasicCookieStore();
		for (String k : cookie.keySet()) {
			BasicClientCookie basicCookie = new BasicClientCookie(k, cookie.get(k));
			cookieStore.addCookie(basicCookie);
		}
		return cookieStore;
	}
	
	/**
	 * 根据cookies键来维护cookie变化的值
	 * @param key session_token
	 * @param ck  cookie-name
	 * @param value cookie-value
	 */
	public static void maintainCookieByKey(String key, String ck, String value) {
		Map<String, String> cookie =  get(key);
		cookie.put(ck, value);
	}
	
	/**
	 * 删除一个Cookies对象集
	 * @param key
	 */
	public static void remove(String key) {
		cookies.remove(key);
	}
		
}
