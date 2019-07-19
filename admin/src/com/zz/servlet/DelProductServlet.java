package com.zz.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zz.service.IDelProductService;
import com.zz.service.impl.DelProductServiceImpl;

/**
 * 根据pid删除商品
 * @author zzCoding
 *
 */

public class DelProductServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		IDelProductService delProductService = new DelProductServiceImpl();
		
		String pid = request.getParameter("pid");
		delProductService.delProduct(pid);
		response.sendRedirect(request.getContextPath() + "/productList");		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}