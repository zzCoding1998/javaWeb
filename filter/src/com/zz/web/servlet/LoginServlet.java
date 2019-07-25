package com.zz.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.zz.entity.User;
import com.zz.service.IUserService;
import com.zz.service.impl.UserServiceImpl;

public class LoginServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		IUserService userService = new UserServiceImpl();
		User user = new User();
		User loginUser = null;
		
		//获取登录信息
		request.setCharacterEncoding("UTF-8");
		response.setContentType("UTF-8");
		try {
			BeanUtils.populate(user, request.getParameterMap());
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		//将user信息传递给service层，进行登录校验
		try {
			loginUser = userService.login(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//获取登录结果，跳转页面
		if(loginUser!=null) {
			if(request.getParameter("isAutoLogin")!=null) {
				Cookie cookie = new Cookie("userLoginInfo",loginUser.getUsername() + "@" +loginUser.getPassword());
				cookie.setMaxAge(60*60*24*3);
				cookie.setPath(request.getContextPath());
				response.addCookie(cookie);
			}
			request.getSession().setAttribute("user", loginUser);
			response.sendRedirect(request.getContextPath() + "/index.jsp");
		}else {
			request.setAttribute("loginInfo","用户名或密码不正确");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}