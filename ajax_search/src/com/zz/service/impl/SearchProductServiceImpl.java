package com.zz.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.zz.dao.ISearchProductDao;
import com.zz.dao.impl.SearchProductDaoImpl;
import com.zz.entity.Product;
import com.zz.service.ISearchProductService;

public class SearchProductServiceImpl implements ISearchProductService {
	
	ISearchProductDao searchProductDao = new SearchProductDaoImpl(); 

	@Override
	public List<Product> searchProduct(String info) throws SQLException {
		return searchProductDao.searchProduct(info);
	}

}
