package com.zz.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;

import com.zz.dao.IUpdateProductDao;
import com.zz.entity.Product;
import com.zz.utils.DataSourceUtils;

public class UpdateProductDaoImpl implements IUpdateProductDao {

	@Override
	public void updateProduct(Product product) throws SQLException {
		
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "update product set pname=?,market_price=?,shop_price=?,cid=?,pdesc=?,is_hot=? where pid=?";
		Object[] param = {product.getPname(),product.getMarket_price(),product.getShop_price(),product.getCid(),
				product.getPdesc(),product.getIs_hot(),product.getPid()};
		runner.update(sql,param);
	}

}
