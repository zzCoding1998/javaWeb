package com.zz.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;

import com.zz.dao.IDelProductDao;
import com.zz.utils.DataSourceUtils;

public class DelProductDaoImpl implements IDelProductDao {

	@Override
	public void delProduct(String pid) throws SQLException {

		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "delete from product where pid=?";
		runner.update(sql,pid);
		
	}

}
