package com.open.crawler.utils;

import java.io.File;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

import com.sun.mail.util.MailSSLSocketFactory;

import sun.misc.BASE64Encoder;

import javax.activation.*;

/**
 * 发送邮件工具类
 * 
 * @author wsg
 *
 */
public class SendEmail {

	private static final String mailHost = "smtp.qq.com"; // 设置邮件服务器主机名
	private static final String mailTransportProtocol = "smtp"; // 发送邮件协议名称
	private static final String mailSmtpPort = "465"; // 邮件服务器端口 QQ SMTP: 465/587
	private static final boolean mailDebug = false; // 开启debug调试
	private static final boolean mailSmtpAuth = true; // 发送服务器需要身份验证
	private static Properties props = null;
	private static Session session = null;

	static {
		init();
	}

	/**
	 * 初始化
	 */
	private static void init() {
		try {
			props = new Properties();
			props.setProperty("mail.host", mailHost); // 设置邮件服务器主机名
			props.setProperty("mail.transport.protocol", mailTransportProtocol); // 发送邮件协议名称
			props.setProperty("mail.debug", "\"" + mailDebug + "\""); // 开启debug调试
			props.setProperty("mail.smtp.auth", "\"" + mailSmtpAuth + "\""); // 发送服务器需要身份验证

			MailSSLSocketFactory sf = new MailSSLSocketFactory(); // SSL
			sf.setTrustAllHosts(true);
			props.put("mail.smtp.ssl.enable", "true");
			props.put("mail.smtp.ssl.socketFactory", sf);
			props.put("mail.smtp.port", mailSmtpPort);

			session = Session.getInstance(props);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发送邮件
	 * 
	 * @param subject
	 *            主题
	 * @param text
	 *            内容
	 * @param toAddress
	 *            多个用逗号隔开
	 */
	public static void sendEmail(String subject, String text, String toAddress) {
		sendEmail(subject, text, toAddress.split(","), null);
	}

	/**
	 * 发送邮件 带附件
	 * 
	 * @param subject
	 *            主题
	 * @param text
	 *            内容
	 * @param toAddress
	 *            多个用逗号隔开
	 * @param files
	 *            附件 多个用逗号隔开
	 */
	public static void sendEmail(String subject, String text, String toAddress, String files) {
		sendEmail(subject, text, toAddress.split(","), files.split(","));
	}

	/**
	 * 发送多人条邮件
	 * 
	 * @param subject
	 *            主题
	 * @param text
	 *            内容
	 * @param toAddress
	 *            收件地址
	 * @param files
	 *            邮件附件
	 */
	public static void sendEmail(String subject, String text, String[] toAddress, String[] files) {
		try {
			if (session == null) {
				init();
			}
			Message msg = new MimeMessage(session);
			msg.setSubject(subject);
			msg.setText(text);
			msg.setFrom(new InternetAddress("1210460667@qq.com"));
			msg.setHeader("charset", "UTF-8");
			// msg.setSentDate(new Date());
			// 附件
			if (files != null) {
				Multipart multipart = new MimeMultipart();
				for (String name : files) {
					File file = new File(name);
					MimeBodyPart fileBody = new MimeBodyPart();
					DataSource source = new FileDataSource(name);
					fileBody.setDataHandler(new DataHandler(source));
					BASE64Encoder enc = new BASE64Encoder();
					fileBody.setFileName("=?UTF-8?B?" + enc.encode(file.getName().getBytes()) + "?=");
					multipart.addBodyPart(fileBody);
				}
				MimeBodyPart contentPart = new MimeBodyPart();
				contentPart.setText(text);
				multipart.addBodyPart(contentPart);
				msg.setContent(multipart);
			}

			Transport transport = session.getTransport();
			transport.connect(mailHost, "1210460667@qq.com", "hxeuagjeshgdbabb");

			Address[] address = new Address[toAddress.length];
			for (int i = 0; i < toAddress.length; i++) {
				address[i] = new InternetAddress(toAddress[i]);
			}
			transport.sendMessage(msg, address);
			transport.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
