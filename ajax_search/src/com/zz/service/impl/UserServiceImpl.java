package com.zz.service.impl;

import java.sql.SQLException;

import com.zz.dao.IUserDao;
import com.zz.dao.impl.UserDaoImpl;
import com.zz.service.IUserService;

public class UserServiceImpl implements IUserService {
	
	IUserDao userDao = new UserDaoImpl();

	@Override
	public boolean checkUsername(String username) throws SQLException {
		
		if(userDao.selectUsername(username)>0) {
			return false;
		}else {
			return true;
		}
		
	}

}
