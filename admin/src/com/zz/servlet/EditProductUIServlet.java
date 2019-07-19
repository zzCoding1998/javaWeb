package com.zz.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zz.entity.Category;
import com.zz.entity.Product;
import com.zz.service.IGetCategoryService;
import com.zz.service.IGetProductByPidService;
import com.zz.service.impl.GetCategoryServiceImpl;
import com.zz.service.impl.GetProductByPidServiceImpl;

/**
 * 
 * 显示修改商品页面
 * @author zzCoding
 *
 */
public class EditProductUIServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		IGetProductByPidService getProductByPidService = new GetProductByPidServiceImpl();
		IGetCategoryService getCategoryService = new GetCategoryServiceImpl();
		
		String pid =request.getParameter("pid");
		Product product = getProductByPidService.getProductByPid(pid);
		request.setAttribute("product", product);
		
		List<Category> categoryList = getCategoryService.getCategory();
		request.setAttribute("categoryList", categoryList);
		
		request.getRequestDispatcher("/admin/product/edit.jsp").forward(request, response);;
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}