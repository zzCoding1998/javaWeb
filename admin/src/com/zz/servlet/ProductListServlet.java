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
import com.zz.service.IProductListService;
import com.zz.service.impl.GetCategoryServiceImpl;
import com.zz.service.impl.ProductListServiceImpl;
import com.zz.vo.PageInfo;

/**
 * 
 * 后台显示所有商品列表
 * @author zzCoding
 *
 */
public class ProductListServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//从视图得到分页参数
		String currentPageStr = request.getParameter("currentPage");
		int currentPage;
		if(currentPageStr != null) {
			currentPage= Integer.parseInt(currentPageStr);
		}else{
			currentPage=1;
		}
		int countPerPage = 12;
		//放入商品信息
		IProductListService productListService = new ProductListServiceImpl();
		PageInfo<Product> pageInfo = null;
		try {
			pageInfo = productListService.getPageInfo(currentPage,countPerPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("pageInfo", pageInfo);
		//放入商品类别信息
		IGetCategoryService getCategoryService = new GetCategoryServiceImpl();
		List<Category> categoryList = getCategoryService.getCategory();
		request.setAttribute("categoryList", categoryList);
		request.getRequestDispatcher("admin/product/list.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}