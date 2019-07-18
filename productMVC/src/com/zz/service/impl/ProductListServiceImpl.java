package com.zz.service.impl;

import java.util.List;

import com.zz.dao.IProductListDao;
import com.zz.dao.impl.ProductListDaoImpl;
import com.zz.entity.Product;
import com.zz.service.IProductListService;

public class ProductListServiceImpl implements IProductListService{

	IProductListDao productListDao = new ProductListDaoImpl();
	
	@Override
	//查询所有的商品
	public List<Product> getProductList() {
		return productListDao.getProductList();
	}

}
