package com.zz.service;

import java.util.List;

import com.zz.entity.Product;

public interface IProductListService {

	//查询所有的商品
	List<Product> getProductList();

}
