package com.zz.service.impl;

import com.zz.dao.IProductInfoDao;
import com.zz.dao.impl.ProductInfoDaoImpl;
import com.zz.entity.Product;
import com.zz.service.IProductInfoService;

public class ProductInfoServiceImpl implements IProductInfoService {

	IProductInfoDao productInfoDao = new ProductInfoDaoImpl();
	
	@Override
	//根据商品id获取商品信息
	public Product getProductInfoById(String pid) {
		return productInfoDao.getProductInfoById(pid);
	}

}
