package com.zz.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.zz.dao.IGetProductByPidDao;
import com.zz.entity.Product;
import com.zz.utils.DataSourceUtils;

public class GetProductByPidDaoImpl implements IGetProductByPidDao {

	@Override
	public Product getProductByPid(String pid) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where pid =?";
		return runner.query(sql, new BeanHandler<Product>(Product.class),pid);
	}

}
