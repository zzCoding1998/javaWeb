package com.zz.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.zz.dao.IGetCategoryDao;
import com.zz.dao.impl.GetCategoryDaoImpl;
import com.zz.entity.Category;
import com.zz.service.IGetCategoryService;

public class GetCategoryServiceImpl implements IGetCategoryService {
	
	IGetCategoryDao getCategoryDao = new GetCategoryDaoImpl();

	@Override
	public List<Category> getCategory() {
		try {
			return getCategoryDao.getCategory();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
