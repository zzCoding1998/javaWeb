package com.zz.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 完成cookie记录上次登录时间案例
 * 记录时长为24小时
 * @author zzCoding
 *
 */

public class LastAccessServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//设置浏览器解码方式为UTF-8
		response.setContentType("text/html;Charset=UTF-8");
		//生成当前时间字符串
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentDate = simpleDateFormat.format(date);
		
		//创建cookie对象
		Cookie cookie = new Cookie("lastAccess",currentDate);
		//cookie对象持久化
		cookie.setMaxAge(60*60*24);
		//设置cookie携带路径
		cookie.setPath("/cookie_session/lastAccess");
		//将cookie对象放入response对象中，携带到客户端
		response.addCookie(cookie);
		
		//获取request中cookie参数，得到上次登陆时间，并显示在页面
		Cookie[] cookies = request.getCookies();
		int flag = 0;	//标志是否找到上次登录时间
		if(cookies==null) {
			response.getWriter().write("您是第一次访问，欢迎您！");
		}else {
			for(Cookie cook : cookies) {
				if(cook.getName().equals("lastAccess")) {
					response.getWriter().write("您上次访问时间为："+cook.getValue());
					flag=1;
				}
			}
			//如果没有找到上次访问的时间，则认为是第一次登录
			if(flag==0) {
				response.getWriter().write("您是第一次访问，欢迎您！");
			}
		}
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}