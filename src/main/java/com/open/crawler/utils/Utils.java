package com.open.crawler.utils;

import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

	public static String rootPath = "";
	private static DecimalFormat decimalFormat = new DecimalFormat("0.00");
	private static DecimalFormat decimalFormat4 = new DecimalFormat("0.0000");

	/**
	 * 把  Date String 2015-10-08 18:11:16.301  转换成  Long 20151008181116301
	 * @param timestamp
	 * @return
	 */
	public static Long GetDateStringToLong(String timestamp) {
		return Long.parseLong(Pattern.compile("[^0-9]").matcher(timestamp.toString()).replaceAll("").trim());
	}
	/**
	 * 字符串转换成时间
	 * @param date
	 * @return
	 */
	public static Timestamp StringToTimestamp(String date) {
		 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		 Timestamp time = null;
	     try {
	    	 time = new Timestamp(df.parse(date).getTime());
	     } 
	     catch (Exception e) {
	    	 e.printStackTrace();
	     } finally {
	    	 return time;
	     }
	}
	
	/**
	 * 合法身份证号返回 true
	 * 不合法返回 false
	 * @param id
	 * @return
	 */
	public static boolean IsIdCard(String id) {
		/** 
		 * 身份证号合法性校验  * 该程序实现的功能： 
		 * 1.判断输入身份证号是否准确，如不正确，重新输入，直到输入正确为止；  * 2.判断身份证的合法性； 
		 * 3.根据有效身份证信息，得出主人的出生年月日以及判断性别  */
		final int[] weight = new int []{7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2};//乘以的系数
		final int[] checkDigit = new int[]{1,0,'X',9,8,7,6,5,4,3,2};//对应的校验码

		int[] arr = new int[18];
		int sum = 0;
		int flag = 0;
		
		if (id.matches("^\\d{17}[0-9X]$") == false) return false;
		
		for (int i = 0;i < 18;i++) {
			arr[i] = Integer.parseInt(id.substring(i, i+1));//使用IdentityCard.substring(i, i+1)方法可取出号码的每一位
			if (i != 17) {
				sum += weight[i] * arr[i];
			}
		}
		
		flag = sum % 11;//得出余数
		
		if(checkDigit[flag]==arr[arr.length-1]){
//			System.out.println("您的身份证是合法的！");
//			System.out.println("你的出生年月日为："+id.substring(6,14));//可以根据身份证号知道出生年月日
//			System.out.println("你的性别为："+(arr[16]%2==0?"女":"男"));//可以根据身份证号判断男女
			return true;
		} else {
//			System.out.println("您的身份证是不合法的！");
			return false;
		}
	}
	
	/**
	 * 合法手机号返回 true
	 * 不合法返回 false
	 * @param mobiles
	 * @return
	 */
	public static boolean IsMobile(String mobiles){
		String regExp = "^((13[0-9])|(15[^4,\\D])|(18[0,3,5-9]))\\d{8}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}  
	
	/**
	 * 将字节b转换成图片
	 * @param b
	 * @param pathName
	 * @return
	 */
	public static Image Bytes2image(byte[] b, String pathName) {
		Image im = null;
		try {
			FileOutputStream fout = new FileOutputStream(pathName);
		    //将字节写入文件
		    fout.write(b);
		    fout.close();
		} catch (Exception e) {
            e.printStackTrace();  
        } finally {
        	return im; 
        }
	}

	/**
	 * 去除异常中的单引号
	 *
	 * @param e
	 * @return
	 */
	public static String getErrorMsg(Throwable e) {
		Object err = null;
		if (e.getCause() != null) {
			if (e.getCause().getCause() != null) {
				err = e.getCause().getCause().getMessage();
			} else {
				err = e.getCause().getMessage();
			}
		} else {
			err = e.getMessage();
		}
		String msg = err != null ? StringHelper.replaceBlank(err.toString()
				.replaceAll("'", StringUtils.EMPTY).replaceAll("\"", StringUtils.EMPTY)) : "错误描述空";
		return msg;
	}

	/**
	 * yyyy-MM-dd字符串转换成日期
	 *
	 * @param str
	 * @return
	 */
	public static Date changeStr2Date(String str) {
		try {
			return DateTimeUtils.parseDate(str,DateTimeUtils.DATE_PATTEN1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 *
	 * @param obj
	 * @return
	 */
	public static Double transObj(Object obj) {
		if (obj == null) {
			return null;
		} else {
			return Double.parseDouble(String.valueOf(obj));
		}

	}

	public static String transObjToStr(Object obj) {
		if ((obj == null) || "null".equals(obj)) {
			return StringUtils.EMPTY;
		} else {
			return String.valueOf(obj);
		}

	}

	public static String transDoubleToStrWithCheck(double obj) {
		if (obj == -1) {
			return "/";
		} else {
			return String.valueOf(NumberUtils.leftDouble(obj, 4));
		}

	}

	/**
	 * 字符串分割匹配
	 * @param target
	 * @param key
	 * @param split
	 * @return
	 */
	public static String stringMatching(String target, String key, String split) {
		try {
			if (StringUtils.isNotBlank(target) && StringUtils.isNotBlank(key) &&
					StringUtils.isNotBlank(split) && target.contains(key)) {
				for (String office : target.split(split)) {
					if (office.contains(key)) {
						return office;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static Double transObjectToDouble(Object o) {
		if (o == null) {
			return null;
		}
		try {
			return Double.parseDouble(o.toString());
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 分割list 每个分组 num个cutList
	 *
	 * @param list
	 * @param num
	 * @return
	 */
	public static <T> java.util.List<java.util.List<T>> cutList(java.util.List<T> list, int num) {
		java.util.List<java.util.List<T>> newList = new ArrayList<java.util.List<T>>();
		int size = list.size();
		int count = (int) Math.floor(size / num); // 去小数
		int a = size % num; // 取余---以num为分割线，每num个组成一个新的List
		if (a == 0) {
			// 完美分割则直接分段提取
			for (int i = 0; i < count; i++) {
				java.util.List<T> cut = list.subList(i * num, num * (i + 1));
				newList.add(cut);
			}
		} else {
			// 不能完美分割，先提取足够num的，不够的再一次提取
			for (int j = 0; j < count; j++) {
				java.util.List<T> cut = list.subList(j * num, num * (j + 1));
				newList.add(cut);
			}
			java.util.List<T> cut1 = list.subList(count * num, (count * num) + a);
			newList.add(cut1);
		}
		return newList;
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
			BigDecimal b1 = new BigDecimal(d.toString());
			return b1.setScale(n, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		return 0.00;
	}

	/**
	 * 4舍5入 保留 2位 小数
	 *
	 * @param d
	 * @return
	 */
	public static String leftTwoDecimal(Object d) {
		if (d == null) {
			return "0.00";
		}
		BigDecimal b1 = new BigDecimal(d.toString());
		return b1.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
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
			String d_str = String.valueOf(d);
			BigDecimal b1 = new BigDecimal(d_str);
			b1 = b1.setScale(4, BigDecimal.ROUND_HALF_UP);
			return b1.setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
		}
	}

	/**
	 * 第4位 4舍5入
	 *
	 * @param d
	 * @return
	 */
	public static String leftFourDecimal(Double d) {
		if (d == null) {
			return "0.0000";
		}
		String d_str = String.valueOf(d);
		BigDecimal b1 = new BigDecimal(d_str);
		return b1.setScale(4, BigDecimal.ROUND_HALF_UP).toString();
	}

	public static boolean checkEmail(String email) {
		String reg = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		return email.matches(reg);
	}

	// 验证身份证是否合法，只校验数字和末尾的x
	public static boolean checkIDChar(String x) {
		boolean b = true;
		String reg = "^\\d{17}(\\d|X|x)";
		return x.matches(reg);
	}

	public static boolean checkRight(long o) {
		boolean g = true;
		if ((o % Math.pow(10, 2)) == 0) {
			g = false;
		} else if ((o % Math.pow(10, 4)) == 0) {
			g = false;
		} else if (((o % Math.pow(10, 4)) / Math.pow(10, 2)) == 0) {
			g = false;
		} else if (((o % Math.pow(10, 2)) / 10) > 3) {
			g = false;
		} else if ((((o % Math.pow(10, 2)) / 10) == 3) && ((o % Math.pow(10, 1)) > 1)) {
			g = false;
		}
		return g;
	}

	public static String getWebRoot() {
		String webRoot = null;
		String classNameUrl = "/com/dotech/pms/core/Utils.class";
		String temp = null;
		try {
			java.net.URL classUrl = Utils.class.getResource(classNameUrl);
			temp = classUrl.getPath();
			temp = URLDecoder.decode(temp);
			webRoot = temp.substring(0, temp.indexOf("WEB-INF"));
		} catch (Exception e) {
			webRoot = "";
		}
		return webRoot;
	}

	/**
	 * 删除文件
	 *
	 * @param filePathAndName
	 *            String 文件路径及名称 如c:/uu.txt
	 * @param filePathAndName
	 *            String
	 * @return boolean
	 */
	public static boolean delFile(String filePathAndName) {
		File myDelFile = new java.io.File(filePathAndName);
		if (!myDelFile.exists()) {
			return true;
		}
		return myDelFile.delete();
	}

	/**
	 * 根据路径创建一系列的目录
	 *
	 * @param path
	 */
	public static void mkDirectory(String path) {
		File file;
		try {
			String lPath = path.replace("\\", File.separator);
			file = new File(lPath);
			if (!file.exists()) {
				file.mkdirs();
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {
			file = null;
		}
	}

	/**
	 * 读取BufferedReader
	 *
	 * @param bin
	 * @return
	 * @throws IOException
	 */
	public static String bufferedReaderToString(BufferedReader bin) throws IOException {
		StringBuilder result = new StringBuilder();
		String str;
		while ((str = bin.readLine()) != null) {
			result.append(str);
		}
		return result.toString();
	}
}