package com.zz.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.zz.dao.IProductInfoDao;
import com.zz.entity.Product;

public class ProductInfoDaoImpl implements IProductInfoDao {
	
	QueryRunner runner = new QueryRunner(new ComboPooledDataSource());

	@Override
	//根据商品id获取商品信息
	public Product getProductInfoById(String pid) {
		String sql = "select * from product where pid = ?";
		try {
			return runner.query(sql,new BeanHandler<Product>(Product.class),pid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
