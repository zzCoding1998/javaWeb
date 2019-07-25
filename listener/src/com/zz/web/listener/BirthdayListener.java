package com.zz.web.listener;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.mail.MessagingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.zz.entity.User;
import com.zz.service.IBirthdayService;
import com.zz.service.impl.BirthdayServiceImpl;
import com.zz.utils.MailUtils;

public class BirthdayListener implements ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		IBirthdayService birthdayService = new BirthdayServiceImpl();
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				SimpleDateFormat dataFormat = new SimpleDateFormat("MM-dd");
				String currentDate = dataFormat.format(new Date());
				List<User> userList = null;
				try {
					userList = birthdayService.getusers(currentDate);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				if(userList!=null) {
					for(User user:userList) {
						try {
							MailUtils.sendMail(user.getEmail(), "生日祝福", user.getName()+"先生/女士, 祝您生日快乐！");
							System.out.println("已发送生日祝福给..." + user.getName());
						} catch (MessagingException | IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}, new Date(), 1000*10);
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

}
