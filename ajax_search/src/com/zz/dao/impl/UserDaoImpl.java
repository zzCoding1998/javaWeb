package com.zz.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.zz.dao.IUserDao;

public class UserDaoImpl implements IUserDao {
	
	QueryRunner runner = new QueryRunner(new ComboPooledDataSource());

	@Override
	public int selectUsername(String username) throws SQLException {
		String sql ="select count(*) from user where username=?";
		Long count =  (Long) runner.query(sql, new ScalarHandler(),username);
		return count.intValue();
	}

}
