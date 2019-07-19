package com.zz.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.zz.dao.IProductListDao;
import com.zz.dao.impl.ProductListDaoImpl;
import com.zz.entity.Product;
import com.zz.service.IProductListService;
import com.zz.vo.PageInfo;

public class ProductListServiceImpl implements IProductListService {

	IProductListDao productListDao = new ProductListDaoImpl();
	@Override
	public List<Product> getProductList() {
		List<Product> productList;
		try {
			productList = productListDao.getProductList();
			return productList;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	//在业务层封装pageInfo信息，返回给web层
	public PageInfo<Product> getPageInfo(int currentPage,int countPerPage) throws Exception {
		PageInfo<Product> pageInfo = new PageInfo<Product>();
		//设置当前页数
		pageInfo.setCurrentPage(currentPage);
		//设置当前页面条数
		pageInfo.setCurrentCount(countPerPage);
		//设置总条数
		int totalCount = productListDao.getTotalCount();
		pageInfo.setTotalCount(totalCount);
		//设置总页数
		int totalPage = (int) Math.ceil(1.0*totalCount/countPerPage);
		pageInfo.setTotalPage(totalPage);
		//获取商品信息
		int index = (currentPage-1)*countPerPage;
		pageInfo.setCurrentList(productListDao.getCurrentList(index,countPerPage));
		return pageInfo;
	}

}
