package com.open.crawler.utils;

import com.alibaba.fastjson.JSON;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpUtils {
    public static final String CLASS_NAME = "HttpUtils";
    public static Integer lastResponseCode = 0;

    HttpUtils() {

    }

    /**
     * 描述：sendUrl 发送URL的post请求 默认utf8
     *
     * @param urlStr
     * @param params
     * @return
     */
    public static String sendPost(String urlStr, String params) {
        return sendPost(urlStr, params, "utf-8");
    }

    public static String sendPost(String urlStr, String params, String code) {
        return sendPost(urlStr, params, "utf-8", "application/x-www-form-urlencoded");
    }

    /**
     * 描述：sendUrl 发送URL的post请求
     *
     * @param urlStr
     * @param params
     * @return
     */
    public static String sendPost(String urlStr, String params, String code, String contentType) {
        OutputStreamWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            URL realUrl = new URL(urlStr);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setUseCaches(false);
            conn.setRequestProperty("Content-Type", contentType);

            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            // 发送请求参数
            out.write(params);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), code));
            String line = "";
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                    out = null;
                }
                if (in != null) {
                    in.close();
                    in = null;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
        return result.toString();
    }

    /**
     * 描述：saveAs Save Internet resource to local, accept
     * FTP(anonymous)/HTTP/HTTPS protocol.
     *
     * @param sUrl
     * @param sPath
     * @return 0 success
     * @return -1 fail Example: HttpUtils.saveAs("https://.../image/banner.jpg",
     *         "/Users/.../Desktop/" );
     */
    public static int saveAs(String sUrl, String sPath) {
        try {
            URL url = new URL(sUrl);
            int lastChar;
            for (lastChar = sUrl.length() - 1; lastChar >= 0; lastChar--) {
                if (sUrl.charAt(lastChar) == '\\' || sUrl.charAt(lastChar) == '/') {
                    break;
                }
            }
            String fileName = sUrl.substring(lastChar + 1);
            File outFile = new File(sPath + fileName);
            OutputStream os = new FileOutputStream(outFile);
            InputStream is = url.openStream();
            byte[] buff = new byte[1024];
            while (true) {
                int readed = is.read(buff);
                if (readed == -1) {
                    break;
                }
                byte[] temp = new byte[readed];
                System.arraycopy(buff, 0, temp, 0, readed);
                os.write(temp);
            }
            is.close();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        return 0;
    }

    public static String reqShortUrl(String url) {
        return url;
    }

    public static String reqShortUrlFromGoogle(String url) {
        PrintWriter out = null;
        BufferedReader in = null;
        StringBuffer result = new StringBuffer();
        try {
            URL realUrl = new URL("https://www.googleapis.com/urlshortener/v1/url");
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new PrintWriter(conn.getOutputStream());
            out.print("{\"longUrl\":\"" + url + "\"}");
            out.flush();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                    out = null;
                }
                if (in != null) {
                    in.close();
                    in = null;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        Map<String, String> map = JSON.parseObject(result.toString(), Map.class);
        return map.get("id");
    }

    public static String reqShortUrlFromSina(String urlStr) {
        try {
            URL url = new URL(
                    "http://api.t.sina.com.cn/short_url/shorten.json?source=209678993&url_long="
                            + urlStr);
            InputStream in = url.openStream();
            BufferedReader bin = new BufferedReader(new InputStreamReader(in, "utf-8"));
            String result = Utils.bufferedReaderToString(bin);
            bin.close();
            if (result != null && result.startsWith("null")) {
                result = result.substring(4);
            }

            List<Map<String, String>> lm = JSON.parseObject(result.toString(), List.class);
            if (lm != null && !lm.isEmpty()) {
                return lm.get(0).get("url_short");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return urlStr;
    }

    public static String sendGet(String urlStr) {
        try {
            URL url = new URL(urlStr);
            InputStream in = url.openStream();
            BufferedReader bin = new BufferedReader(new InputStreamReader(in, "utf-8"));
            String result = Utils.bufferedReaderToString(bin);
            bin.close();
            if (result != null && result.startsWith("null")) {
                result = result.substring(4);
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return urlStr;
    }

    /**
     * 描述：sendGetWithTimeout 发送URL的get请求并携带超时限制
     *
     * @param url
     * @param timeout
     * @return
     */
    public static String sendGetWithTimeout(String url, Integer timeout) {
        BufferedReader in = null;
        StringBuffer result = new StringBuffer();
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            ((HttpURLConnection) conn).setRequestMethod("GET");

            lastResponseCode = ((HttpURLConnection) conn).getResponseCode();

            conn.setReadTimeout(timeout.intValue());
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            String line = "";
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                    in = null;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return result.toString();
    }

    /**
     * 获取IP 所在城市
     *
     * @param ip
     * @return
     */
    public static String getIPCity(String ip) {
        boolean mark = true;
        String city = "";
        String result = "";

        result = sendPost("http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip=" + ip,
                "");
        Map<String, String> map = JSON.parseObject(result.toString(), Map.class);
        try {
            String ret = map.get("ret");
            if (!"1".equals(ret)) {
                mark = false;
            }
        } catch (Exception e) {
            mark = false;
        }

        if (mark) {
            city = map.get("city");
            if (!"".equals(city)) {
                return city;
            }
        }

        mark = true;
        result = sendPost("http://ip.taobao.com/service/getIpInfo.php",
                new StringBuilder("ip=").append(ip).toString());

        try {
            map = JSON.parseObject(result.toString(), Map.class);
            String code = map.get("code");
            if (!"0".equals(code)) {
                mark = false;
            }
        } catch (Exception e) {
            mark = false;
        }

        if (mark) {
            map = JSON.parseObject(map.get("data"), Map.class);
            city = map.get("city");
            if (!"".equals(city)) {
                return city;
            }
        }

        return "";

    }

    public static MallMessageSend post(String url, MallMessageRecv mallMessageRecv, int type,
            String identifyCode) {
        CloseableHttpClient httpClient = null;
        try {
            httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            String json = JSON.toJSONString(mallMessageRecv);
            StringEntity se = new StringEntity(json, "utf-8");
            if (type == 5) {
                httpPost.addHeader("sign", MD5.md5_32(StringHelper.concat(json, identifyCode)));
            }
            httpPost.setEntity(se);
            CloseableHttpResponse response = httpClient.execute(httpPost);
            String result = EntityUtils.toString(response.getEntity());
            httpClient.close();
            response.close();
            return JSON.parseObject(result, MallMessageSend.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

class MallMessageSend {
    private String key;
    private Header header;
    private Map<String,Object> body;
    public MallMessageSend(){
        header = new Header();
        body = new HashMap<>();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Map<String, Object> getBody() {
        return body;
    }

    public void setBody(Map<String, Object> body) {
        this.body = body;
    }
}

class Header {
    private Result ret_result;

    public Result getRet_result() {
        return ret_result;
    }

    public void setRet_result(Result ret_result) {
        this.ret_result = ret_result;
    }
}

class Result {
    private int ret_code;
    private String ret_msg;

    public int getRet_code() {
        return ret_code;
    }

    public void setRet_code(int ret_code) {
        this.ret_code = ret_code;
    }

    public String getRet_msg() {
        return ret_msg;
    }

    public void setRet_msg(String ret_msg) {
        this.ret_msg = ret_msg;
    }
}

class MallMessageRecv {
    private String key;
    private Map<String, Object> header;
    private Map<String, Object> body = new HashMap<>();

    public MallMessageRecv(){
        header = new HashMap<>();
        header.put("dev_model", "");
        header.put("dev_no", "");
        header.put("dev_plat", "");
        header.put("dev_ver", "");
        header.put("ip_addr", "");
        header.put("push_id", "");
        header.put("soft_ver", "");
        header.put("token_id", "");
        header.put("user_id", "");
        body = new HashMap<>();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Map<String, Object> getHeader() {
        return header;
    }

    public void setHeader(Map<String, Object> header) {
        this.header = header;
    }

    public Map<String, Object> getBody() {
        return body;
    }

    public void setBody(Map<String, Object> body) {
        this.body = body;
    }

}
