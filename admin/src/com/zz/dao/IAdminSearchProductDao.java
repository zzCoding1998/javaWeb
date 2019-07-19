package com.zz.dao;

import java.sql.SQLException;
import java.util.List;

import com.zz.entity.Product;
import com.zz.vo.Condition;

public interface IAdminSearchProductDao {

	List<Product> getProductByCondition(Condition condition, int index, int countPerPage) throws SQLException;

	int getTotalCount(Condition condition) throws SQLException;

}
