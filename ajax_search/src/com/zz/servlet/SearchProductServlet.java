package com.zz.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.zz.entity.Product;
import com.zz.service.ISearchProductService;
import com.zz.service.impl.SearchProductServiceImpl;

import jdk.nashorn.internal.parser.JSONParser;

/**
 * 完成站内搜索案例
 * @author zzCoding
 *
 * 2019年7月25日
 */

public class SearchProductServlet extends HttpServlet {
	
	ISearchProductService searchProductService = new SearchProductServiceImpl();

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html;charset=UTF-8");
		String info = request.getParameter("info");
		List<Product> productList;
		try {
			productList = searchProductService.searchProduct(info);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		response.getWriter().write(JSON.toJSON(productList).toString());
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}