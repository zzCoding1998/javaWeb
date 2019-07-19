package com.zz.dao;

import java.sql.SQLException;
import java.util.List;

import com.zz.entity.Category;

public interface IGetCategoryDao {

	List<Category> getCategory() throws SQLException;
	
}
