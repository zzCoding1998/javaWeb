package com.zz.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.zz.entity.User;
import com.zz.utils.DataSourceUtils;

public class UserDao {
	
	QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());

	//用户注册
	public int register(User user) throws SQLException {
		String sql = "insert into user() values(?,?,?,?,?,?,?,?,?,?)";
		Object[] params = {user.getUid(),user.getUsername(),user.getPassword(),user.getName(),user.getEmail(),
				user.getTelephone(),user.getBirthday(),user.getSex(),user.getState(),user.getCode()};
		return runner.update(sql, params);
	}

	//邮箱激活，修改状态码
	public int modifyState(String code) throws SQLException {
		String sql = "update user set state=1 where code=?";
		return runner.update(sql, code);
	}

	//根据用户名查询用户信息
	public User queryUser(User user) throws SQLException {
		String sql = "select * from user where username=?";
		return runner.query(sql, new BeanHandler<User>(User.class),user.getUsername());
	}

	//根据用户名和密码查询用户
	public User getUserByUsernameAndPassword(User user) throws SQLException {
		String sql ="select * from user where username=? and password=?";
		return runner.query(sql, new BeanHandler<User>(User.class), user.getUsername(),user.getPassword());
	}

}
