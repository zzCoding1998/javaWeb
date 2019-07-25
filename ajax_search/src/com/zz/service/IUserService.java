package com.zz.service;

import java.sql.SQLException;

public interface IUserService {

	boolean checkUsername(String username) throws SQLException;

}
