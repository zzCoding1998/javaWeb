package com.zz.service;

import java.sql.SQLException;
import java.util.List;

import com.zz.dao.ProductDao;
import com.zz.entity.PageBean;
import com.zz.entity.Product;

public class ProductService {
	
	ProductDao productDao = new ProductDao();

	//查询热门商品
	public List<Product> getHotProductList() {
		try {
			return productDao.getHotProdcutList();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	//查询最新商品
	public List<Product> getNewProductList() {
		try {
			return productDao.getNewProdcutList();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	//根据cid封装分页信息
	public PageBean<Product> getPageBeanByCid(int currentPage, int count, String cid) {
		PageBean<Product> pageBean = new PageBean<Product>();
		//当前第几页
		pageBean.setPage(currentPage);
		//一共多少条
		int totalCount = 0;
		try {
			totalCount = productDao.getTotalCountByCid(cid);
			pageBean.setTotalCount(totalCount);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//一共有多少页
		int totalPage = (int) Math.ceil(totalCount*1.0/count);
		pageBean.setTotalPage(totalPage);
		//商品信息
		int beginIndex = (currentPage-1)*count;
		List<Product> dataList = null;
		try {
			dataList = productDao.getDataList(beginIndex,count,cid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		pageBean.setDataList(dataList);
		//当前页有多少条
		pageBean.setCount(dataList.size());
		//返回pageBean
		return pageBean;
	}

	//根据商品pid查询商品详细信息
	public Product getProductInfoByPid(String pid) {
		ProductDao productDao = new ProductDao();
		Product product = null;
		try {
			 product = productDao.getProductInfoByPid(pid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return product;
	}

	//根据关键字查询相关商品，最多8条
	public List<Product> getProductInfoByKeywords(String keywords) {
		ProductDao productDao = new ProductDao();
		List<Product> productList = null;
		try {
			productList = productDao.getProductInfoBykeywords(keywords);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return productList;
	}
	//根据关键字查询相关商品分页数据
	public PageBean<Product> getProductPageBeanBykeywords(int page, int count, String keywords) {
		ProductDao productDao = new ProductDao();
		PageBean<Product> pageBean = new PageBean<Product>();
		//1.当前第几页
		pageBean.setPage(page);
		//2.总共多少条
		int totalCount = 0;
		try {
			totalCount = productDao.getTotalCountByKeywords(keywords);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		pageBean.setTotalCount(totalCount);
		//3.一共多少页
		int totalPage = (int) Math.ceil(1.0*totalCount / count);
		pageBean.setTotalPage(totalPage);
		//4.商品信息
		int beginIndex = (page-1)*count;
		List<Product> dataList = null;
		try {
			dataList = productDao.getDataListByKeywords(beginIndex, count, keywords);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		pageBean.setDataList(dataList);
		//5.当前页面有多少条
		pageBean.setCount(dataList.size());
		return pageBean;
	}
}
