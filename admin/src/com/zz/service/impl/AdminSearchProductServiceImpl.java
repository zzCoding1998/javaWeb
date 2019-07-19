package com.zz.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.zz.dao.IAdminSearchProductDao;
import com.zz.dao.impl.AdminSearchProductDaoImpl;
import com.zz.entity.Product;
import com.zz.service.IAdminSearchProductService;
import com.zz.vo.Condition;
import com.zz.vo.PageInfo;

public class AdminSearchProductServiceImpl implements IAdminSearchProductService {

	IAdminSearchProductDao adminSearchProductDao = new AdminSearchProductDaoImpl();
	
	@Override
	public PageInfo<Product> getProductByCondition(Condition condition, int currentPage, int countPerPage) throws SQLException {
		//封装PageInfo
		PageInfo<Product> pageInfo = new PageInfo<Product>();
		//设置当前页
		pageInfo.setCurrentPage(currentPage);
		//设置每页显示信息条数
		pageInfo.setCurrentCount(countPerPage);
		//设置最大条数
		pageInfo.setTotalCount(adminSearchProductDao.getTotalCount(condition));
		//设置最大页数
		int totalPage = (int) Math.ceil(1.0*pageInfo.getTotalCount()/pageInfo.getCurrentCount());
		pageInfo.setTotalPage(totalPage);
		//获取当前页商品信息列表
		int index = (currentPage-1)*countPerPage;
		pageInfo.setCurrentList(adminSearchProductDao.getProductByCondition(condition,index,countPerPage));
		return pageInfo;
	}

}
