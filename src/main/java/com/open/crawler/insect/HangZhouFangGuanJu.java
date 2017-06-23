package com.open.crawler.insect;

import com.open.crawler.utils.Utils;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Wusg
 * Email wushangang@jianbaolife.com/1210460667@qq.com
 * On 2017/6/23 0023.
 */
public class HangZhouFangGuanJu extends InsectBase {

    private static String url = "http://www.hzfc.gov.cn";
    private static String dataUrl = "http://www.hzfc.gov.cn/scxx";
    private static String imgSrc = null;
    private static String imgSrcTag = ".php";

    public static void main(String[] args) {
        strat();
    }

    public static void strat() {
        try {
            httpGet = new HttpGet(url);
            httpResp = getHttpClient().execute(httpGet);

            httpGet = new HttpGet(dataUrl);
            httpResp = getHttpClient().execute(httpGet);

            document = Jsoup.parse(EntityUtils.toString(httpResp.getEntity()));
            elements = document.select("img[src]");

            for (Element element : elements) {
                imgSrc = element.attr("src");
                if (imgSrc.contains(imgSrcTag)) {
                    System.out.println(imgSrc);
//                    getData(dataUrl + "/" + imgSrc);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void getData(String imgUrl) {
        try {
            String realPath = "G:\\img\\";
            httpGet = new HttpGet(imgUrl);
            httpResp = getHttpClient().execute(httpGet);
            byte[] b = EntityUtils.toByteArray(httpResp.getEntity());
            Utils.Bytes2image(b, realPath + imgUrl +
                    (new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date()) +
                    ".jpg");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
