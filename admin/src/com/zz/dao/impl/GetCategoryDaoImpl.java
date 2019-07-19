package com.zz.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.zz.dao.IGetCategoryDao;
import com.zz.entity.Category;
import com.zz.utils.DataSourceUtils;

public class GetCategoryDaoImpl implements IGetCategoryDao{

	@Override
	public List<Category> getCategory() throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from category";
		return runner.query(sql, new BeanListHandler<Category>(Category.class));
	}

}
