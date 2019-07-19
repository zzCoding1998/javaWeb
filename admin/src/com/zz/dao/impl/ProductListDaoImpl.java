package com.zz.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.zz.dao.IProductListDao;
import com.zz.entity.Product;
import com.zz.utils.DataSourceUtils;

public class ProductListDaoImpl implements IProductListDao {

	@Override
	public List<Product> getProductList() throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product";
		return runner.query(sql, new BeanListHandler<Product>(Product.class));
	}

	public int getTotalCount() throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select count(*) from product";
		Long totalCount = (Long) runner.query(sql, new ScalarHandler());
		return totalCount.intValue();
	}

	@Override
	public List<Product> getCurrentList(int index, int countPerPage) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product limit ?,?";
		return runner.query(sql, new BeanListHandler<Product>(Product.class), index,countPerPage);
	}

}
