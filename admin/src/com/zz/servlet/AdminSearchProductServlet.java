package com.zz.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.zz.entity.Category;
import com.zz.entity.Product;
import com.zz.service.IAdminSearchProductService;
import com.zz.service.IGetCategoryService;
import com.zz.service.impl.AdminSearchProductServiceImpl;
import com.zz.service.impl.GetCategoryServiceImpl;
import com.zz.vo.Condition;
import com.zz.vo.PageInfo;

/**
 * 条件查询
 * @author zzCoding
 *
 */
public class AdminSearchProductServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		//从视图得到分页参数
		String currentPageStr = request.getParameter("currentPage");
		int currentPage;
		if(currentPageStr != null) {
			currentPage= Integer.parseInt(currentPageStr);
		}else{
			currentPage=1;
		}
		int countPerPage = 12;
		Condition condition = new Condition();
		try {
			//获取查询条件
			BeanUtils.populate(condition, request.getParameterMap());
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		//将数据传递给service层，获取返回数据,放到request
		IAdminSearchProductService adminSearchProductService = new AdminSearchProductServiceImpl();
		PageInfo<Product> pageInfo = null;
		try {
			pageInfo = adminSearchProductService.getProductByCondition(condition,currentPage,countPerPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("pageInfo", pageInfo);
		//放入商品类别信息
		IGetCategoryService getCategoryService = new GetCategoryServiceImpl();
		List<Category> categoryList = getCategoryService.getCategory();
		request.setAttribute("categoryList", categoryList);
		//将回显数据放到request
		request.setAttribute("condition", condition);
		//请求转发到jsp页面
		request.getRequestDispatcher("/admin/product/list.jsp").forward(request, response);;
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}