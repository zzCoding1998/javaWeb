package com.zz.service.impl;

import java.sql.SQLException;

import com.zz.dao.IUserDao;
import com.zz.dao.impl.UserDaoImpl;
import com.zz.entity.User;
import com.zz.service.IUserService;

public class UserServiceImpl implements IUserService {
	
	IUserDao userDao = new UserDaoImpl();

	@Override
	public User login(User user) throws SQLException {
		User loginUser = userDao.findUser(user.getUsername(),user.getPassword());
		return loginUser;
	}

}
