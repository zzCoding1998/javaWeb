package com.zz.service;

import java.sql.SQLException;
import java.util.List;

import com.zz.entity.Product;

public interface ISearchProductService {

	List<Product> searchProduct(String info) throws SQLException;

}
