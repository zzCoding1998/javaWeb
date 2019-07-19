package com.zz.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.zz.entity.Product;
import com.zz.service.IAddProductService;
import com.zz.service.impl.AddProductServiceImpl;

/**
 * 添加商品
 * @author zzCoding
 *
 */

public class AddProductServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		IAddProductService addProductService = new AddProductServiceImpl();
		Product product = new Product();
		request.setCharacterEncoding("UTF-8");
		try {
			//获取参数，封装
			BeanUtils.populate(product, request.getParameterMap());
			product.setPid(UUID.randomUUID().toString().replace("-", ""));
			product.setPimage(""); 
			SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd"); 
			String date = dateFormat.format(new Date());
			product.setPdate(date);
			//添加商品
			addProductService.addProduct(product);
			//重定向到信息查询页
			response.sendRedirect(request.getContextPath()+"/productList");
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}