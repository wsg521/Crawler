package com.open.crawler.insect;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

/**
 * Created by Wusg
 * Email wushangang@jianbaolife.com/1210460667@qq.com
 * On 2017/6/23 0023.
 */
public class OppoWin extends InsectBase {

    private static String oppoNoOld = "160429092967159";
    private static String oppoNoNew = "170618125917031";
    private static String title = "吴山岗 购oppo手机获奖通知";
    private static String msg = "老用户购机获奖通知，请到oppo官网 http://www.oppo.com/cn " +
            "->首页->服务->中间名单 中查找，" +
            "购机订单号: 老单号: " + oppoNoOld + " 新单号: " + oppoNoNew;

    public static void start() {
        try {
            String url = "http://www.oppo.com/cn/service/list";
            httpGet = new HttpGet(url);

            int count = 1;

            while (true) {
                httpResp = getHttpClient().execute(httpGet);
                document = Jsoup.parse(EntityUtils.toString(httpResp.getEntity()));
                elements = document.select("p");

                for (Element element : elements) {
                    if (element.text().contains(oppoNoOld) ||
                            element.text().contains(oppoNoNew)) {
                        sendCallFileEmail(title, msg);
                        return;
                    }
                }

                if (document.toString().contains(oppoNoOld) ||
                        document.toString().contains(oppoNoNew)) {
                    sendCallFileEmail(title, msg);
                    return;
                }

                System.out.println("第 " + count + " 次查询oppo中奖通知，没查询到相应中奖数据。");
                if (count % 1440 == 0) {
                    throw new Exception("第 " + count + " 次查询oppo中奖通知，没查询到相应中奖数据。");
                }

                count++;
                Thread.sleep(60000);
            }
        } catch (Exception e) {
            sendErrorEmail("oppo获奖查询", e);
        }
    }

}
