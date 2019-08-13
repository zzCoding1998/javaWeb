package com.zz.web.servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 提取servlet公共部分，利用反射执行相应方法
 * @author zzCoding
 *
 * 2019年8月4日
 */
@SuppressWarnings("serial")
public class BaseServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String method = req.getParameter("method");
			Class<? extends BaseServlet> clazz = this.getClass();
			Method methodBin = clazz.getMethod(method, HttpServletRequest.class,HttpServletResponse.class);
			methodBin.invoke(this, req,resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}