package com.zz.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.zz.dao.IUserDao;
import com.zz.entity.User;

public class UserDaoImpl implements IUserDao {
	
	QueryRunner runner = new QueryRunner(new ComboPooledDataSource());

	@Override
	public User findUser(String username, String password) throws SQLException {
		String sql = "select * from user where username=? and password=?";
		return runner.query(sql, new BeanHandler<User>(User.class), username,password);
	}

}
