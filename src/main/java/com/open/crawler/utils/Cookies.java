package com.open.crawler.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpResponse;
import org.apache.http.message.BasicHeaderElementIterator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class Cookies {
	/**
	 * 根据HttpResponse来缓存Cookies
	 * @param cookie
	 * @param httpResp
	 */
	public static void SetCookies(Map<String, String> cookie, HttpResponse httpResp) {
		HeaderElementIterator it = new BasicHeaderElementIterator(
				httpResp.headerIterator("Set-Cookie"));
		while (it.hasNext()) {
			HeaderElement elem = it.nextElement(); 
			cookie.put(elem.getName(), elem.getValue());
		}
	}
	/**
	 * 将Map中的Cookies转换成字符串
	 * @param cookie
	 * @return
	 */
	public static String getCookieToString(Map<String, String> cookie) {
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
	 * 将json转换成Map存放的Cookies
	 * @param json
	 * @return
	 */
	public static Map<String, String> JsonToCookies(String json) {
		Map<String, String> cookie = new HashMap<String, String>();;
		try {
			JSONObject jsonObject = JSONObject.parseObject(json);
			Iterator<String> iterator = (Iterator<String>) jsonObject.keySet().iterator();
	        String key = null;
	        String value = null;
	        while (iterator.hasNext()) {  
	            key = iterator.next(); 
	            value = jsonObject.getString(key); 
	            cookie.put(key, value); 
	        } 
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return cookie;
		}
	}
}
