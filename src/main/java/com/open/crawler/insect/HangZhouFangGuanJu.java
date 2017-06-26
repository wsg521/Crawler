package com.open.crawler.insect;

import com.open.crawler.utils.Utils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.io.File;
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
    private static String imgSrcTag = ".php?";
    private static int sleepTime = 1000 * 60 * 50;

    public static void main(String[] args) {
        start();
    }

    public static void start() {
        try {
            int count = 1;
            while (true) {
                doTasks();
                System.out.println("第 " + count + " 次查询杭州市售房信息。");
                count++;
                Thread.sleep(sleepTime);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void doTasks() {
        try {
            httpGet = new HttpGet(url);
            httpResp = getHttpClient().execute(httpGet);

            httpGet = new HttpGet(dataUrl);
            httpResp = getHttpClient().execute(httpGet);

            document = Jsoup.parse(EntityUtils.toString(httpResp.getEntity()));
            elements = document.select("img[src]");

            String realPath = getRealPath();
            Utils.mkDirectory(realPath);

            for (Element element : elements) {
                imgSrc = element.attr("src");
                if (imgSrc.contains(imgSrcTag)) {
                    getData(dataUrl + "/" + imgSrc, realPath);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void getData(String imgUrl, String realPath) {
        try {
            httpGet = new HttpGet(imgUrl);
            httpResp = getHttpClient().execute(httpGet);
            byte[] b = EntityUtils.toByteArray(httpResp.getEntity());
            Utils.Bytes2image(b, realPath + File.separator +
                    getIMGPath(imgUrl) +
                    (new SimpleDateFormat("yyyyMMddHHmmss"))
                            .format(new Date()) + ".jpg");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getRealPath() {
        Date date = new Date();
        StringBuilder path =  new StringBuilder(Utils.rootPath);
        path.append("杭州售房信息")
            .append(File.separator)
            .append(DateFormatUtils.format(date, "yyyy_MM"))
            .append(File.separator)
            .append(DateFormatUtils.format(date, "dd"))
            .append(File.separator)
            .append(DateFormatUtils.format(date, "HH_mm_ss"));
        return path.toString();
    }

    private static String getIMGPath(String imgSrc) {

        if (imgSrc.contains("jrspfksxx")) {
            return "今日商品房可售信息";
        } else if (imgSrc.contains("xjspfpzysxx")) {
            return "新建商品房批准预售信息";
        } else if (imgSrc.contains("spfljcjxxfwlxsy")) {
            return "上月商品房累计成交信息";
        } else if (imgSrc.contains("spfljcjxxfwlxjr")) {
            return "今日房累计成交信息";
        } else if (imgSrc.contains("spfljcjxxqysy")) {
            return "上月区域商品房累计成交信息";
        } else if (imgSrc.contains("spfljcjxxqyjr")) {
            return "今日区域商品房累计成交信息";
        } else if (imgSrc.contains("esfljcjxxsy")) {
            return "上月二手房累计成交信息";
        } else if (imgSrc.contains("esfljcjxxjr")) {
            return "今日二手房累计成交信息";
        }

        return "img";
    }

}
