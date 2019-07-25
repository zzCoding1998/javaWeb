package com.zz.dao;

import java.sql.SQLException;

public interface IUserDao {

	int selectUsername(String username) throws SQLException;

}
