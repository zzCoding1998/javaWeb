package com.zz.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

public class MailUtils {

	public static void sendMail(String email,String subject, String emailMsg)
			throws AddressException, MessagingException, IOException {
		
		//1.加载配置文件，获取登陆者信息
		InputStream resourceAsStream = MailUtils.class.getClassLoader().getResourceAsStream("mail.properties");
		Properties resourceProp = new Properties();
		resourceProp.load(resourceAsStream);
		String server = resourceProp.getProperty("server");		//服务器域名
		String account = resourceProp.getProperty("account");	//登录账号
		String sender = resourceProp.getProperty("sender");		//发送者
		String limitCode = resourceProp.getProperty("limitCode");	//授权码
		
		// 2.创建一个程序与邮件服务器会话对象 Session
		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", "SMTP");
		props.setProperty("mail.host", server);
		props.setProperty("mail.smtp.auth", "true");// 指定验证为true

		// 创建验证器
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(account, limitCode);
			}
		};

		Session session = Session.getInstance(props, auth);

		// 3.创建一个Message，它相当于是邮件内容
		Message message = new MimeMessage(session);

		message.setFrom(new InternetAddress(sender)); // 设置发送者

		message.setRecipient(RecipientType.TO, new InternetAddress(email)); // 设置发送方式与接收者

		message.setSubject(subject);
		// message.setText("这是一封激活邮件，请<a href='#'>点击</a>");

		message.setContent(emailMsg, "text/html;charset=utf-8");

		// 4.创建 Transport用于将邮件发送

		Transport.send(message);
	}
}
