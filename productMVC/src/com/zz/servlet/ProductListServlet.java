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
 * 获取商品列表，并显示在页面
 * @author zzCoding
 *
 */
public class ProductListServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		IProductListService productListService = new ProductListServiceImpl();
		//调用业务层,接收商品列表数据
		List<Product> productList = productListService.getProductList();
		//将列表数据放入request域
		request.setAttribute("productList", productList);
		//重定向到product_list.jsp页面
		request.getRequestDispatcher("/product_list.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}