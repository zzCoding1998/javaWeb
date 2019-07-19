package com.zz.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zz.entity.Category;
import com.zz.service.IGetCategoryService;
import com.zz.service.impl.GetCategoryServiceImpl;

/**
 * 查询所有的商品分类,并请求转发到add.jsp
 * @author Administrator
 *
 */
public class GetCategoryServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		IGetCategoryService getCategoryService = new GetCategoryServiceImpl();
		List<Category> categoryList = getCategoryService.getCategory();
		request.setAttribute("categoryList", categoryList);
		request.getRequestDispatcher("/admin/product/add.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}