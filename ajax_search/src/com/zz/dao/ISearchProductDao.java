package com.zz.dao;

import java.sql.SQLException;
import java.util.List;

import com.zz.entity.Product;

public interface ISearchProductDao {

	List<Product> searchProduct(String info) throws SQLException;

}
