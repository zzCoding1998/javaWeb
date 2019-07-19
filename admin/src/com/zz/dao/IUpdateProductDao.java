package com.zz.dao;

import java.sql.SQLException;

import com.zz.entity.Product;

public interface IUpdateProductDao {

	void updateProduct(Product product) throws SQLException;

}
