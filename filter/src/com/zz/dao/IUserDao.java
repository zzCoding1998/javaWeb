package com.zz.dao;

import java.sql.SQLException;

import com.zz.entity.User;

public interface IUserDao {

	User findUser(String username, String password) throws SQLException;

}
