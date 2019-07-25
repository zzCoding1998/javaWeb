package com.zz.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.zz.dao.IBirthdayDao;
import com.zz.entity.User;

public class BirthdayDaoImpl implements IBirthdayDao {
	
	QueryRunner runner = new QueryRunner(new ComboPooledDataSource());

	@Override
	public List<User> getusers(String currentDate) throws SQLException {
		String sql = "select * from user where birthday like ?";
		return runner.query(sql, new BeanListHandler<User>(User.class), "%"+currentDate+"%");
	}

}
