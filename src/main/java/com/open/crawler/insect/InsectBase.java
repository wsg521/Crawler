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

    private HttpClient httpClient = null;
    public HttpPost httpPost = null;
    public HttpGet httpGet = null;
    public HttpResponse httpResp = null;
    public Document document = null;
    public Elements elements = null;

    public void sendErrorEmail(String title, Exception e) {
        SendEmail.sendEmail(title, e.getMessage(), "1210460667@qq.com");
    }

    public void sendCallFileEmail(String title, String msg) {
        SendEmail.sendEmail(title, msg, "1210460667@qq.com");
    }

    public HttpClient getHttpClient() {
        return httpClient != null ? httpClient : SSL.creatNewHttpClient();
    }
}
