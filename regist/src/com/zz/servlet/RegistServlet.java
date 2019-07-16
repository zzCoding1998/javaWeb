package com.zz.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.zz.entity.User;

public class RegistServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = new User();
		try {
			//解决请求参数为中文的问题
			request.setCharacterEncoding("UTF-8");
			//1.封装表单数据到实体
			BeanUtils.populate(user, request.getParameterMap());
			//2.完全实体，填写uid
			user.setUid(UUID.randomUUID().toString());
			//3.模拟业务层调用，将数据存储到数据库
			int result = regist(user);
			//4.显示结果
			response.setContentType("text/html;Charset=UTF-8");
			if(result>0) {
				response.sendRedirect(request.getContextPath() + "/login.jsp");
			}else {
				response.getWriter().write("注册失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int regist(User user) throws SQLException {
		QueryRunner runner = new QueryRunner(new ComboPooledDataSource());
		String sql = "insert into user values(?,?,?,?,?,?,?,?,?,?)";
		Object[] param = {user.getUid(),user.getUsername(),user.getPassword(),user.getName(),user.getEmail()
				,user.getTelephone(),user.getBirthday(),user.getSex(),user.getState(),user.getCode()};
		return runner.update(sql,param);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}