package com.zz.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.zz.entity.Category;
import com.zz.utils.DataSourceUtils;

public class CategoryDao {
	
	QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());

	public List<Category> findAllCategory() throws SQLException {
		String sql = "select * from category";
		return runner.query(sql, new BeanListHandler<Category>(Category.class));
	}

	public String getCnameByCid(String cid) throws SQLException {
		String sql = "select cname from category where cid=?";
		Category category = runner.query(sql, new BeanHandler<Category>(Category.class), cid);
		return category.getCname();
	}

}
