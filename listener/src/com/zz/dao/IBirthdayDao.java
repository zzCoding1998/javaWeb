package com.zz.dao;

import java.sql.SQLException;
import java.util.List;

import com.zz.entity.User;

public interface IBirthdayDao {

	List<User> getusers(String currentDate) throws SQLException;

}
