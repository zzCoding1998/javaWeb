package com.zz.service;

import java.sql.SQLException;
import java.util.List;

import com.zz.dao.CategoryDao;
import com.zz.entity.Category;

public class CategoryService {
	
	CategoryDao categoryDao = new CategoryDao();

	//查询所有商品分类
	public List<Category> findAllCategory() {
		List<Category> categoryList = null;
		try {
			categoryList = categoryDao.findAllCategory();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return categoryList;
	}

	//根据cid获得商品分类名称
	public String getCnameByCid(String cid) {
		String cname = null;
		try {
			cname = categoryDao.getCnameByCid(cid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cname;
	}
}
