package com.open.crawler.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by Li Gongrong on 2016/8/26.
 */
public class StringHelper {
    StringHelper() {

    }

    /**
     * 过滤特殊字符
     *
     * @param str
     * @return
     * @throws PatternSyntaxException
     */
    public static String stringFilter(String str) throws PatternSyntaxException {
        // 只允许字母和数字
        // String regEx = "[^a-zA-Z0-9]";
        // 清除掉所有特殊字符
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    /**
     * 去除字符串中 空格回车字符
     *
     * @param str
     * @return
     */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    /**
     * 字符串拼接
     *
     * @param strings
     */
    public static String concat(Object... strings) {
        StringBuilder stb = new StringBuilder("");
        for (Object child : strings) {
            stb.append(child);
        }
        return stb.toString();
    }

    /**
     *
     * @param stb
     *            要拼接的语句
     * @param key
     *            属性名称
     * @param value
     *            值
     * @param yinhao
     *            值是否加引号
     * @param concatFH
     *            是否连接符 逗号
     */
    public static void concat(StringBuilder stb, String key, Object value, boolean yinhao,
                              boolean concatFH) {
        stb.append("\"").append(key).append("\":");
        if (yinhao) {
            stb.append("\"");
        }
        stb.append(value);
        if (yinhao) {
            stb.append("\"");
        }
        if (concatFH) {
            stb.append(",");
        }
    }

    /**
     * 判断字符串数组是否包含某元素
     *
     * @param arr
     * @param targetValue
     * @return
     */
    public static boolean stringArrayContains(String[] arr, String targetValue) {
        for (String s : arr) {
            if (s.equals(targetValue)) {
                return true;
            }
        }
        return false;
    }

    public static void validateStringLen(StringBuilder stb, String strName, String value,
                                         int maxlength) {
        if (StringUtils.isBlank(value)) {
            stb.append(stb.length() > 0 ? "," : "").append(strName).append("不可为空");
        } else if (value.length() > maxlength) {
            stb.append(stb.length() > 0 ? "," : "").append(strName).append("最大长度为")
                    .append(maxlength);
        }
    }

    /**
     * 左补齐
     *
     * @param length
     * @param number
     * @return
     */
    public static String lpad(int length, int number) {
        String f = "%0" + length + "d";
        return String.format(f, number);
    }

    /**
     * 取str 后 i位
     *
     * @param str
     * @param i
     * @return
     */
    public static String getLastStr(String str, int i) {
        if (org.apache.commons.lang3.StringUtils.isBlank(str)) {
            return org.apache.commons.lang3.StringUtils.EMPTY;
        }
        int k = str.length();
        if (k >= i) {
            return str.substring(k - i);
        } else {
            return str;
        }
    }

    /**
     * 替换回车空格
     *
     * @param str
     * @return
     */
    public static String replaceNR(String str) {
        if (str == null) {
            return null;
        }
        return str.replaceAll("\n", "").replaceAll("\r", "").trim();
    }

    /**
     * 字符串 trim 判断null
     *
     * @param str
     * @return
     */
    public static String trimStr(String str) {
        if (str != null) {
            return str.trim();
        }
        return "";
    }

    /**
     * 过滤 非字母数字
     *
     * @param str
     * @return
     */
    public static String replaceUnAlphanumeric(String str) {
        if (str != null) {
            return str.replaceAll("[^a-z^A-Z^0-9]", "");
        }
        return null;
    }

    public static String relpaceNumWithStr(String objStr, String replaceTo) {
        if (org.apache.commons.lang3.StringUtils.isNotBlank(objStr)) {
            String pat = "\\d+";
            Pattern p = Pattern.compile(pat);
            Matcher m = p.matcher(objStr);
            return m.replaceAll(replaceTo);
        } else {
            return "";
        }
    }

    /**
     * 替换换行和回车
     *
     * @param myString
     * @return
     */
    public static String replaceKH(String myString) {
        if (myString == null) {
            return null;
        }
        Pattern CRLF = Pattern.compile("(\r\n|\r|\n|\n\r)");
        Matcher m = CRLF.matcher(myString);
        if (m.find()) {
            return m.replaceAll("");
        }
        return myString;
    }

    /**
     *
     * @param s
     *            被替换的字符串
     * @param rpNum
     *            非数字字符替换成的字符串
     * @return 替换后的结果
     */
    public static String replaceNoNumeric(String s, String rpNum) {
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(s);
        return m.replaceAll(rpNum);
    }

    public static String[] getStrNums(String s) {
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(s);
        // 替换与模式匹配的所有字符（即非数字的字符将被""替换）
        return m.replaceAll(" ").trim().split("\\s+");
    }

    public static String getStrNum(String s) {
        StringBuilder ss = new StringBuilder("");
        Pattern p = Pattern.compile("\\d");
        Matcher m = p.matcher(s);
        while (m.find()) {
            ss.append(m.group());
        }
        return ss.toString();
    }

    // 去全角符号,去半角符号;
    public static String removeSign(String s) {
        if (s == null) {
            return null;
        }
        return s.replaceAll("\\p{Punct}", "").replaceAll("\\pP", "");
    }

    /**
     * 字符串比较
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean strValueCompare(String a, String b) {
        String atrim= a == null ? StringUtils.EMPTY : a.trim();
        return atrim.equals(b == null ? StringUtils.EMPTY : b.trim());
    }

    public static boolean strValueCompare(Object a, Object b) {
        String atrim= a == null ? StringUtils.EMPTY : String.valueOf(a).trim();
        return atrim.equals(b == null ? StringUtils.EMPTY : String.valueOf(b).trim());
    }

    /**
     * 18位证件获取其15位 编号
     *
     * @param idNo
     * @return
     */
    public static String idNo18To15(String idNo) {
        if (StringUtils.isEmpty(idNo)) {
            return "";
        }
        String id = idNo.trim();
        if (id.length() == 18) {
            return id.substring(0, 6) + id.substring(8, 17);
        }
        return id;
    }

    /**
     *
     * @param str
     * @param maxLength
     * @return
     */
    public static String subLength(Object str, int maxLength) {
        if (str != null) {
            String trim = String.valueOf(str).trim();
            trim = trim.replaceAll(",", "");
            if (trim.length() <= maxLength) {
                return trim;
            } else {
                return trim.substring(0, maxLength);
            }
        } else {
            return StringUtils.EMPTY;
        }
    }

    /**
     * null 或者 "null" 字符串转换"" excel 导出使用
     *
     * @param str
     * @return
     */
    public static String getUnNullStr(Object str) {
        if ((str == null) || "null".equalsIgnoreCase(str.toString())) {
            return StringUtils.EMPTY;
        } else {
            return str.toString();
        }
    }

    /**
     * null 或者 "null" 字符串转换"" excel 导出使用
     *
     * @param str
     * @return
     */
    public static String getUnNullStr(String str) {
        if ((str == null) || "null".equalsIgnoreCase(str)) {
            return StringUtils.EMPTY;
        } else {
            return str;
        }
    }

    /**
     * 返回非null字符串
     *
     * @param obj
     * @return
     */
    public static String getUnNullString(Object obj) {
        if (obj != null && !"null".equalsIgnoreCase(obj.toString())) {
            return String.valueOf(obj);
        }
        return StringUtils.EMPTY;

    }

    public static String transObjToStr(Object obj) {
        if (obj == null || "null".equalsIgnoreCase(obj.toString())) {
            return StringUtils.EMPTY;
        } else {
            return String.valueOf(obj);
        }

    }

    public static boolean strValueCompareIgnoreCase(Object a, Object b) {
        String one = a == null ? StringUtils.EMPTY : String.valueOf(a).trim();
        String two = b == null ? StringUtils.EMPTY : String.valueOf(b).trim();
        return one.equalsIgnoreCase(two);
    }

    public static String hidePIN(Object obj) {
        if (obj == null || "null".equals(obj)) {
            return "";
        } else {
            String str = String.valueOf(obj).trim();
            int len = str.length();
            if (len == 0) {
                return "";
            } else if (len >= 6) {
                return str.substring(0, len - 6) + "******";
            } else {
                return StringUtils.rightPad(str.substring(0, len / 2), 6, "*");
            }
        }
    }

    /**
     * 隐藏inStart到inEnd的字符，用n位replaceWith代替
     *
     * @param source
     * @param inStart
     * @param inEnd
     * @param replaceWith
     * @param n
     * @return
     */
    public static String hideBits(String source, int inStart, int inEnd, String replaceWith,
                                  int n) {
        if (StringUtils.isEmpty(source)) {
            return source;
        }

        int start = inStart;
        int end = inEnd;
        if (inStart < 0) {
            start = 0;
        }
        if (inEnd > source.length() - 1) {
            end = source.length() - 1;
        }
        StringBuilder result = new StringBuilder();
        result.append(source.subSequence(0, start));
        for (int i = 0; i < n; i++) {
            result.append(replaceWith);
        }
        result.append(source.substring(end + 1, source.length()));
        return result.toString();
    }

    public static boolean checkStr(String objStr, int minLen, int maxLen) {
        int len = StringUtils.isBlank(objStr) ? 0 : objStr.length();
        return len >= minLen && len <= maxLen;
    }

    public static String checkStr(String objStr, int minLen, int maxLen, String errorMsg) {
        return checkStr(objStr, minLen, maxLen) ? "" : errorMsg;
    }

    public static int parseInt(String intStr, int defaultValue) {
        return StringUtils.isNotBlank(intStr) ? Integer.parseInt(intStr) : defaultValue;
    }

    /**
     *  判断断是否包含中文
     * @param str
     * @return
     */
    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }
 // 根据Unicode编码完美的判断中文汉字和符号
    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }
 
    // 完整的判断中文汉字和符号
    public static boolean isChinese(String strName) {
        char[] ch = strName.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }
}
