package com.zz.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.zz.dao.IAdminSearchProductDao;
import com.zz.entity.Product;
import com.zz.utils.DataSourceUtils;
import com.zz.vo.Condition;

public class AdminSearchProductDaoImpl implements IAdminSearchProductDao {

	@Override
	public List<Product> getProductByCondition(Condition condition, int index, int countPerPage) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where 1=1";
		List<Object> param = new ArrayList<Object>();
		if(condition.getPname()!=null && condition.getPname()!="") {
			sql += " and pname like ?";
			param.add("%"+ condition.getPname() +"%");
		}
		if(condition.getIs_hot()!=null && condition.getIs_hot()!="") {
			sql += " and is_hot = ?";
			param.add(condition.getIs_hot());
		}
		if(condition.getCid()!=null && condition.getCid()!="") {
			sql += " and cid = ?";
			param.add(condition.getCid());
		}
		sql += " limit ?,?";
		param.add(index);
		param.add(countPerPage);
		return runner.query(sql, new BeanListHandler<Product>(Product.class), param.toArray());
	}

	@Override
	public int getTotalCount(Condition condition) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		List<Object> param = new ArrayList<Object>();
		String sql = "select count(*) from product where 1=1";
		if(condition.getPname()!=null && condition.getPname()!="") {
			sql += " and pname like ?";
			param.add("%"+ condition.getPname() +"%");
		}
		if(condition.getIs_hot()!=null && condition.getIs_hot()!="") {
			sql += " and is_hot = ?";
			param.add(condition.getIs_hot());
		}
		if(condition.getCid()!=null && condition.getCid()!="") {
			sql += " and cid = ?";
			param.add(condition.getCid());
		}
		Long countPerPage =  (Long) runner.query(sql, new ScalarHandler(), param.toArray());
		return countPerPage.intValue();
	}

}
