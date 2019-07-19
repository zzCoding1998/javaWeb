package com.zz.dao;

import java.sql.SQLException;
import java.util.List;

import com.zz.entity.Product;

public interface IProductListDao {
	
	List<Product> getProductList() throws SQLException;
	
	int getTotalCount() throws SQLException;
	
	List<Product> getCurrentList(int index, int countPerPage) throws SQLException;
	
}
