package com.zz.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.zz.dao.IBirthdayDao;
import com.zz.dao.impl.BirthdayDaoImpl;
import com.zz.entity.User;
import com.zz.service.IBirthdayService;

public class BirthdayServiceImpl implements IBirthdayService {
	
	IBirthdayDao birthdayDao = new BirthdayDaoImpl();

	@Override
	public List<User> getusers(String currentDate) throws SQLException {
		return birthdayDao.getusers(currentDate);
	}

}
