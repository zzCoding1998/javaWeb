package com.zz.service;

import java.sql.SQLException;

import com.zz.dao.UserDao;
import com.zz.entity.User;

public class UserService {
	
	UserDao userDao = new UserDao();

	//用户注册
	public boolean register(User user) throws SQLException {
		int row = userDao.register(user);
		if(row==1) {
			return true;
		}else {
			return false;
		}
	}

	//邮箱激活，修改状态码
	public boolean modifyState(String code) throws SQLException {
		boolean isActiveSuccess = false;
		int row = userDao.modifyState(code);
		if(row==1) {
			isActiveSuccess = true;
		}
		return isActiveSuccess;
	}
	
	//判断用户名是否存在
	public boolean isAccountExist(User user) throws SQLException {
		User queryUser = null;
		queryUser = userDao.queryUser(user);
		if(queryUser!=null) {
			return true;
		}else {
			return false;
		}
		
	}

	//用户登录验证
	public User login(User user) {
		
		UserDao userDao = new UserDao();
		try {
			user =  userDao.getUserByUsernameAndPassword(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

}
