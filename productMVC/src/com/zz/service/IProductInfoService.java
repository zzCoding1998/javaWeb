package com.zz.service;

import com.zz.entity.Product;

public interface IProductInfoService {

	//根据商品id获取商品信息
	Product getProductInfoById(String pid);

}
