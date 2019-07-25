package com.zz.service;

import java.sql.SQLException;
import java.util.List;

import com.zz.entity.User;

public interface IBirthdayService {

	List<User> getusers(String currentDate) throws SQLException;

}
