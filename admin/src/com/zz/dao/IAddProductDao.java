package com.zz.dao;

import java.sql.SQLException;

import com.zz.entity.Product;

public interface IAddProductDao {

	void addProduct(Product product) throws SQLException;

}
