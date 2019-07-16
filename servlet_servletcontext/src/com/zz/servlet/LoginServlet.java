package com.zz.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.zz.entity.User;

public class LoginServlet extends HttpServlet {
	
	@Override
	//初始化访问人数为0
	public void init() throws ServletException {
		Integer count = 0;
		this.getServletContext().setAttribute("count", count);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = null;
		try {
			//1.获取表单提交参数
			String username = (String) request.getParameter("username");
			String password = request.getParameter("password");
			//2.创建QueryRunner对象，
			QueryRunner runner = new QueryRunner(new ComboPooledDataSource());
			//3.sql和参数列表
			String sql = "select * from tb_user where username=? and password=?";
			Object[] param = {username,password};
			//4.查询结果
			user = runner.query(sql, new BeanHandler<User>(User.class),param);
			//5.根据不同结果显示不同页面
			if(user==null) {
				response.getWriter().write("Username or Password is uncurrent!");
			}else {
				//每次访问时人数+1，并更新servletContext域对象
				Integer count = (Integer) this.getServletContext().getAttribute("count");
				count++;
				this.getServletContext().setAttribute("count", count);
				response.getWriter().write(user.getUsername() + " ,welcome to Servlet!Visitor:" + count);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}