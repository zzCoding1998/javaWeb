package com.zz.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.zz.dao.IProductListDao;
import com.zz.dao.impl.ProductListDaoImpl;
import com.zz.entity.Product;
import com.zz.service.IProductListService;

public class ProductListServiceImpl implements IProductListService {

	@Override
	public List<Product> getProductList() {
		IProductListDao productListDao = new ProductListDaoImpl();
		List<Product> productList;
		try {
			productList = productListDao.getProductList();
			return productList;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
