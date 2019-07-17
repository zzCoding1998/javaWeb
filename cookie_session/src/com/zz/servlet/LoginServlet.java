package com.zz.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.zz.entity.User;

public class LoginServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		User user = new User();
		User userInfo=null;
		try {
			//添加验证码校验功能
			request.setCharacterEncoding("UTF-8");
			String checkImg = request.getParameter("checkImg");
			String checkCode = (String) request.getSession().getAttribute("checkcode_session");
			if(!checkImg.equals(checkCode)) {
				request.setAttribute("loginInfo", "您的验证码不正确!");
				request.getRequestDispatcher("/login.jsp").forward(request, response);
				return;
			}
			//1.获取参数
			request.setCharacterEncoding("UTF-8");
			BeanUtils.populate(user, request.getParameterMap());
			//2.调用业务层，操作数据库，并拿到用户详细信息
			userInfo = login(user);
			//3.根据返回信息判断是否登陆成功
			if(userInfo!=null) {
				response.sendRedirect(request.getContextPath());
			}else {
				request.setAttribute("loginInfo", "用户名或密码不正确");
				request.getRequestDispatcher("/login.jsp").forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 模拟业务层，查询用户登录信息，并返回
	 */
	public User login(User user) throws SQLException {
		QueryRunner runner = new QueryRunner(new ComboPooledDataSource());
		String sql = "select * from user where username=? and password=?";
		return runner.query(sql, new BeanHandler<User>(User.class),user.getUsername(),user.getPassword());
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}