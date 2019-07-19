package com.zz.dao;

import java.sql.SQLException;

import com.zz.entity.Product;

public interface IGetProductByPidDao {

	Product getProductByPid(String pid) throws SQLException;

}
