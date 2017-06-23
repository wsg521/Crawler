package com.open.crawler.insect;

import com.open.crawler.utils.SSL;
import com.open.crawler.utils.SendEmail;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Created by Wusg
 * Email wushangang@jianbaolife.com/1210460667@qq.com
 * On 2017/6/23 0023.
 */
public class InsectBase {

    private static HttpClient httpClient = null;
    public static HttpPost httpPost = null;
    public static HttpGet httpGet = null;
    public static HttpResponse httpResp = null;
    public static Document document = null;
    public static Elements elements = null;

    public static void sendErrorEmail(String title, Exception e) {
        SendEmail.sendEmail(title, e.getMessage(), "1210460667@qq.com");
    }

    public static void sendCallFileEmail(String title, String msg) {
        SendEmail.sendEmail(title, msg, "1210460667@qq.com");
    }

    public static HttpClient getHttpClient() {
        return httpClient != null ? httpClient : SSL.creatHttpClient();
    }
}
