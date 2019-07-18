package com.zz.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zz.entity.Product;
import com.zz.service.IProductInfoService;
import com.zz.service.impl.ProductInfoServiceImpl;

/**
 * 根据pid查询商品详细信息，并传递到product_info.jsp
 * 
 * @author zzCoding
 *
 */

public class ProductInfoServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		IProductInfoService productInfoService = new ProductInfoServiceImpl();
		//获取参数pid
		String pid = request.getParameter("pid");
		//传递给serveice层，并获取返回信息
		Product product =  productInfoService.getProductInfoById(pid);
		//请求转发到product_info.jsp
		request.setAttribute("product", product);
		request.getRequestDispatcher("/product_info.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}