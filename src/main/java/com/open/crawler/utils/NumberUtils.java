package com.open.crawler.utils;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Pattern;

public class NumberUtils {   
    private static DecimalFormat decimalFormat = new DecimalFormat("0.00");
    private static DecimalFormat decimalFormat4 = new DecimalFormat("0.0000");
    NumberUtils(){
        
    }
    public static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    public static Double getUnNullDouble(Object o) {
        if (o != null) {
            return Double.parseDouble(String.valueOf(o));
        }
        return 0.00;
    }

    public static int getUnNullInteger(Object o) {
        String num = String.valueOf(o);
        if (StringUtils.isNumeric(num)) {
            return Integer.parseInt(num);
        }
        return 0;
    }

    /**
     * 保留 2位 小数，舍弃后面
     *
     * @param s
     * @return
     */
    public static double leftTwoDouble(String s) {
        if (!org.apache.commons.lang3.StringUtils.isBlank(s)) {
            s = s.trim();
            BigDecimal b1 = new BigDecimal(s);
            return b1.setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
        }
        return 0;
    }

    /**
     * 保留 n位 小数，四舍五入
     *
     * @param d
     * @param n
     * @return
     */
    public static double leftDouble(Double d, int n) {
        if (d != null) {
            BigDecimal b1 = new BigDecimal(d);
            return b1.setScale(n, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        return 0.00;
    }

    /**
     * 第4位 4舍5入 然后 舍弃 第2位 之后的
     *
     * @param d
     * @return
     */
    public static Double getDouble4u2d(Object d) {
        if (d == null) {
            return 0.00;
        } else {
            // 不使用BigDecimal(double)是应为不精确;String 构造方法是完全可预知的：
            // 写入 new BigDecimal("0.1") 将创建一个 BigDecimal，它正好 等于预期的 0.1
            String doubleStr = String.valueOf(d);
            BigDecimal b1 = new BigDecimal(doubleStr);
            b1 = b1.setScale(4, BigDecimal.ROUND_HALF_UP);
            return b1.setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
        }
    }

    /**
     * 将正整数准换成4位，不足前面补0
     *
     * @param iValue
     * @return
     */
    public static String generate4BitsString(int iValue) {
        if (iValue < 10) {
            return "000" + iValue;
        } else if (iValue < 100) {
            return "00" + iValue;
        } else if (iValue < 1000) {
            return "0" + iValue;
        } else {
            return String.valueOf(iValue);
        }
    }

    public static String generateNBitsString(String value, int n) {
        StringBuilder result = new StringBuilder();
        int count = n;
        while (value.length() < count) {
            count--;
            result.append("0");
        }
        result.append(value);
        return result.toString();
    }

    public static Double transObj(Object obj) {
        if (obj == null) {
            return null;
        } else {
            return Double.parseDouble(String.valueOf(obj));
        }
    }
}
