package com.zz.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zz.service.IUserService;
import com.zz.service.impl.UserServiceImpl;

/**
 * ajax校验用户名是否存在
 * @author zzCoding
 *
 * 2019年7月25日
 */

public class CheckUsernameServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		IUserService userService = new UserServiceImpl();
		
		String username = request.getParameter("username");
		boolean isUseable;
		try {
			isUseable = userService.checkUsername(username);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		response.getWriter().write("{\"isUseable\":"+ isUseable +"}");
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}