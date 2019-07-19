package com.zz.service.impl;

import java.sql.SQLException;

import com.zz.dao.IAddProductDao;
import com.zz.dao.impl.ProductDaoImpl;
import com.zz.entity.Product;
import com.zz.service.IAddProductService;

public class AddProductServiceImpl implements IAddProductService {
	
	IAddProductDao addProductDao = new ProductDaoImpl();

	@Override
	public void addProduct(Product product) {
		
		try {
			addProductDao.addProduct(product);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
