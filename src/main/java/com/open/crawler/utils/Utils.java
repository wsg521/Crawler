package com.open.crawler.utils;

import java.awt.*;
import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
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
	
	public static void main(String[] args) {
		
//		Scanner sc = new Scanner(System.in);
//		String id;
//		
//		while (true) {
//			System.out.println("请输入身份证号码：");
//			id = sc.next();
//			IsIdCard(id);
//		}
		
		System.out.println(IsMobile("18337101865"));
		
	}
}


	
	/** 
	 * 身份证号合法性校验  * 该程序实现的功能： 
	 * 1.判断输入身份证号是否准确，如不正确，重新输入，直到输入正确为止；  * 2.判断身份证的合法性； 
	 * 3.根据有效身份证信息，得出主人的出生年月日以及判断性别  */
//	private static final int[] weight = new int []{7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2};//乘以的系数
//	private static final int[] checkDigit = new int[]{1,0,'X',9,8,7,6,5,4,3,2};//对应的校验码
//		
//	public static void main(String[] args) {
//		
//		Scanner sc = new Scanner(System.in);
//		String id;
//		int[] arr = new int[18];
//		int sum = 0;
//		int flag = 0;
//		System.out.println("请输入身份证号码：");
//		id = sc.next();
//		
//		while (id.matches("^\\d{17}[0-9X]$") == false) {
//			System.out.println("ERROR!输入有误！请重新输入");
//			id = sc.next();
//		}
//		
//		for (int i = 0;i < 18;i++) {
//			arr[i] = Integer.parseInt(id.substring(i, i+1));//使用IdentityCard.substring(i, i+1)方法可取出号码的每一位
//			if (i != 17) {
//				sum += weight[i] * arr[i];
//			}
//		}
//		
//		flag = sum % 11;//得出余数
//		
//		if(checkDigit[flag]==arr[arr.length-1]){
//			System.out.println("您的身份证是合法的！");
//			System.out.println("你的出生年月日为："+id.substring(6,14));//可以根据身份证号知道出生年月日
//			System.out.println("你的性别为："+(arr[16]%2==0?"女":"男"));//可以根据身份证号判断男女
//		} else {
//			System.out.println("您的身份证是不合法的！");
//		}
// 		
//	}
