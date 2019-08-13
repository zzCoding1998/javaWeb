package com.zz.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

import com.zz.entity.User;
import com.zz.service.UserService;
import com.zz.utils.CommonsUtils;
import com.zz.utils.MailUtils;

/**
 * 用户模块
 * @author zzCoding
 *
 * 2019年8月4日
 */
@SuppressWarnings("serial")
public class UserServlet extends BaseServlet {

	//用户注册
	public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		UserService userService = new UserService();
		//获取注册数据，同时转换String类型为Date
		User user = new User();
		try {
			ConvertUtils.register(new Converter() {
				@Override
				public Object convert(@SuppressWarnings("rawtypes") Class arg0, Object arg1) {
					SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd");
					Date date = null;
					try {
						date = dataFormat.parse((String)arg1);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					return date;
				}
			}, Date.class);
			BeanUtils.populate(user, request.getParameterMap());
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		//判断验证码是否正确
		String checkCode = request.getParameter("checkCode");
		String checkCode_session = (String) request.getSession().getAttribute("checkCode");
		if(!checkCode.equals(checkCode_session)) {
			request.setAttribute("user", user);
			request.setAttribute("birthday", request.getParameter("birthday"));
			request.setAttribute("registerInfo", "验证码不正确！");
			request.getRequestDispatcher("/register.jsp").forward(request, response);
			return;
		}
		//判断用户是否存在
		boolean isAccountExist = false;
		try {
			isAccountExist = userService.isAccountExist(user);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		if(isAccountExist) {
			request.setAttribute("accountExist", "用户名已存在！");
			request.getRequestDispatcher("/register.jsp").forward(request, response);
			return;
		}
		//补充数据
		user.setUid(CommonsUtils.getUUID());
		user.setTelephone(null);
		user.setState(0);
		user.setCode(CommonsUtils.getUUID64());
		//传递数据
		boolean isRegisterSuccess = false;
		try {
			isRegisterSuccess = userService.register(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(isRegisterSuccess) {
			//注册成功，发送邮件，重定向到成功页面
			try {
				/* 在eclipse启动时没有问题，但部署到linux上后变成了127.0.0.1
				MailUtils.sendMail(user.getEmail(),"账户激活", "恭喜您！成功注册XXX网会员账号，点击链接激活账户：http://"
				+InetAddress.getLocalHost().toString().split("/")[1]+":8080"+request.getContextPath()+
				"/active?activeCode="+user.getCode());
				*/
				MailUtils.sendMail(user.getEmail(),"账户激活", "恭喜您！成功注册XXX网会员账号，点击链接激活账户：http://"
						+ this.getServletContext().getInitParameter("webHost")+":"
						+this.getServletContext().getInitParameter("webPort")
						+request.getContextPath()+"/user?method=active&activeCode="+user.getCode());
			} catch (MessagingException e) {
				e.printStackTrace();
			}
			request.setAttribute("email", user.getEmail());
			request.getRequestDispatcher("/registerSuccess.jsp").forward(request, response);
		}else {
			//注册失败，服务器报错，重定向到错误页面
			response.sendRedirect(request.getContextPath() + "/error.jsp");
		}
	}
	
	//用户是否存在
	public void isAccountExist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserService userService = new UserService();
		boolean isExist = false;
		String username = request.getParameter("username");
		User user = new User();
		user.setUsername(username);
		try {
			isExist = userService.isAccountExist(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(isExist) {
			response.getWriter().write("true");
		}else {
			response.getWriter().write("false");
		}
	}
	
	//用户邮箱激活
	public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		UserService userService = new UserService();
		String code = request.getParameter("activeCode");
		boolean isActiveSuccess = false;
		try {
			isActiveSuccess = userService.modifyState(code);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(isActiveSuccess) {
			response.sendRedirect(request.getContextPath()+"/activeSuccess.jsp");
		}else {
			response.sendRedirect(request.getContextPath()+"/activeFailure.jsp");
		}
	}
	
	//用户登录
	public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		UserService userService = new UserService();
		//0.验证码是否正确
		String checkImgCode = request.getParameter("checkImgCode");
		String checkCode = (String) request.getSession().getAttribute("checkCode");
		if(!checkImgCode.equals(checkCode)) {
			request.setAttribute("loginInfo", "验证码不正确");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			return;
		}
		//1.获取表单数据
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String[] options = request.getParameterValues("option");
		//2.封装成user对象
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		//3.传递数据，得到查询结果
		User loginUser = userService.login(user);
		//4.1.如果正确
		if(loginUser!=null) {
			//4.1.1放数据到session
			request.getSession().setAttribute("user", loginUser);
			//4.1.2判断是否自动登录，是否记住密码
			//定义标志
			boolean isAutoLogin = false;
			boolean isRemeberUsername = false;
			if(options!=null) {
				for(String option : options) {
					//自动登录
					if(option.equals("AutoLogin")) {
						String userInfo = loginUser.getUsername() + "@" + loginUser.getPassword();
						Cookie cookie = new Cookie("userInfo",URLEncoder.encode(userInfo, "UTF-8"));
						cookie.setMaxAge(60*60*24*3);
						cookie.setPath(request.getContextPath());
						response.addCookie(cookie);
						//更改标志
						isAutoLogin = true;
					}else if(option.equals("remeberUsername")) {
						//记住用户名
						Cookie cookie = new Cookie("username",URLEncoder.encode(loginUser.getUsername(), "UTF-8"));
						cookie.setMaxAge(60*60*24*3);
						cookie.setPath(request.getContextPath()+"/login.jsp");
						response.addCookie(cookie);
						//更改标志
						isRemeberUsername = true;
					}
				}
			}
			//如果没有选择自动登录,清除cookie
			if(!isAutoLogin) {
				Cookie newCookie = new Cookie("userInfo", "");
				newCookie.setPath(request.getContextPath());
				newCookie.setMaxAge(0);
				response.addCookie(newCookie);
			}
			//如果没有选择记住用户名，清除cookie
			if(!isRemeberUsername) {
				Cookie newCookie = new Cookie("username", "");
				newCookie.setPath(request.getContextPath()+"/login.jsp");
				newCookie.setMaxAge(0);
				response.addCookie(newCookie);
			}
			response.sendRedirect(request.getContextPath());
		}else {
			//4.2.用户名或密码不正确
			request.setAttribute("loginInfo", "用户名或密码不正确!");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
	}
	
	//登出
	public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//清除cookie
		Cookie cookie = new Cookie("userInfo","");
		cookie.setPath(request.getContextPath());
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		
		//清除session
		request.getSession().removeAttribute("user");
		response.sendRedirect(request.getContextPath()+"/login.jsp");
	}
}