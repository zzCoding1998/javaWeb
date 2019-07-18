package com.zz.dao;

import java.util.List;

import com.zz.entity.Product;

public interface IProductListDao {

	//查询所有的商品
	List<Product> getProductList();

}
