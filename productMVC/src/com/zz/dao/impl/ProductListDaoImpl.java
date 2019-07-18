package com.zz.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.zz.dao.IProductListDao;
import com.zz.entity.Product;

public class ProductListDaoImpl implements IProductListDao {
	
	QueryRunner runner = new QueryRunner(new ComboPooledDataSource());

	@Override
	//查询所有的商品
	public List<Product> getProductList() {
		String sql="select * from product";
		try {
			return runner.query(sql, new BeanListHandler<Product>(Product.class));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
