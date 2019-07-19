package com.zz.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.zz.entity.Product;
import com.zz.service.IUpdateProductService;
import com.zz.service.impl.UpdateProductServiceImpl;

/**
 * 将修改过的商品数据更新到数据库中
 * @author zzCoding
 *
 */

public class EditProductServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		IUpdateProductService updateProductService = new UpdateProductServiceImpl();
		Product product = new Product();
		request.setCharacterEncoding("UTF-8");
		
		try {
			BeanUtils.populate(product, request.getParameterMap());
			updateProductService.updateProduct(product);
			response.sendRedirect(request.getContextPath()+"/productList");
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}