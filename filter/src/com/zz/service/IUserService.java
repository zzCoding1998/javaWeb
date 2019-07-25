package com.zz.service;

import java.sql.SQLException;

import com.zz.entity.User;

public interface IUserService {

	User login(User user) throws SQLException;

}
