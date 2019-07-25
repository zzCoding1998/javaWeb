package com.zz.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.zz.dao.ISearchProductDao;
import com.zz.entity.Product;

public class SearchProductDaoImpl implements ISearchProductDao {
	
	QueryRunner runner = new QueryRunner(new ComboPooledDataSource());

	@Override
	public List<Product> searchProduct(String info) throws SQLException {
		String sql = "select * from product where pname like ? limit 0,8";
		return runner.query(sql, new BeanListHandler<Product>(Product.class),"%"+info+"%");
	}

}
