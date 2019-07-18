package com.zz.dao;

import com.zz.entity.Product;

public interface IProductInfoDao {

	//根据商品id获取商品信息
	Product getProductInfoById(String pid);

}
