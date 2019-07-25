package com.zz.test;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.junit.Test;

import com.zz.utils.MailUtils;

public class TestEmail {
	
	@Test
	public void testEmail() throws AddressException, MessagingException, IOException {
		MailUtils.sendMail("2889717155@qq.com", "测试邮件", "这是一封测试邮件");
	}

}
