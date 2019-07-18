package com.zz.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.zz.entity.Product;

public class ProductListServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//从数据库中取出商品信息，请求转发到product_list.jsp
		try {
			QueryRunner runner = new QueryRunner(new ComboPooledDataSource());
			String sql = "select * from product";
			List<Product> productList = runner.query(sql, new BeanListHandler<Product>(Product.class));
			request.setAttribute("productList", productList);
			request.getRequestDispatcher("/product_list.jsp").forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}