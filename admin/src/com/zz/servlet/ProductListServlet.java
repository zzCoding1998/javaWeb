package com.zz.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zz.entity.Product;
import com.zz.service.IProductListService;
import com.zz.service.impl.ProductListServiceImpl;

/**
 * 
 * 后台显示所有商品列表
 * @author zzCoding
 *
 */
public class ProductListServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		IProductListService productListService = new ProductListServiceImpl();
		List<Product> productList = productListService.getProductList();
		request.setAttribute("productList", productList);
		request.getRequestDispatcher("admin/product/list.jsp").forward(request, response);;
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}