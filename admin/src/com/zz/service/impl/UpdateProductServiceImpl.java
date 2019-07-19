package com.zz.service.impl;

import java.sql.SQLException;

import com.zz.dao.IUpdateProductDao;
import com.zz.dao.impl.UpdateProductDaoImpl;
import com.zz.entity.Product;
import com.zz.service.IUpdateProductService;

public class UpdateProductServiceImpl implements IUpdateProductService {
	
	IUpdateProductDao updateProductDao = new UpdateProductDaoImpl();

	@Override
	public void updateProduct(Product product) {
		try {
			updateProductDao.updateProduct(product);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
