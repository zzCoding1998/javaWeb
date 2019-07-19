package com.zz.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;

import com.zz.dao.IAddProductDao;
import com.zz.entity.Product;
import com.zz.utils.DataSourceUtils;

public class ProductDaoImpl implements IAddProductDao {

	@Override
	public void addProduct(Product product) throws SQLException {

		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "insert into product values(?,?,?,?,?,?,?,?,?,?)";
		Object[] param = {product.getPid(),product.getPname(),product.getMarket_price(),product.getShop_price(),
				product.getPimage(),product.getPdate(),product.getIs_hot(),product.getPdesc(),product.getPflag(),
				product.getCid()};
		runner.update(sql, param);
	}

}
