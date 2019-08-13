package com.zz.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.zz.entity.Product;
import com.zz.utils.DataSourceUtils;

public class ProductDao {
	
	QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());

	public List<Product> getHotProdcutList() throws SQLException {
		String sql = "select * from product where is_hot=? limit ?,?";
		return runner.query(sql, new BeanListHandler<Product>(Product.class), 1,0,9);
	}

	public List<Product> getNewProdcutList() throws SQLException {
		String sql = "select * from product order by pdate limit ?,?";
		return runner.query(sql, new BeanListHandler<Product>(Product.class), 0,9);
	}
	
	public int getTotalCountByCid(String cid) throws SQLException {
		String sql = "select count(*) from product where cid=?";
		Long count = (Long) runner.query(sql,new ScalarHandler(),cid);
		return count.intValue();
	}

	public List<Product> getDataList(int beginIndex, int count,String cid) throws SQLException {
		String sql = "select * from product where cid =? limit ?,?";
		return runner.query(sql, new BeanListHandler<Product>(Product.class), cid,beginIndex,count);
	}

	public Product getProductInfoByPid(String pid) throws SQLException {
		String sql = "select * from product where pid=?";
		return runner.query(sql, new BeanHandler<Product>(Product.class),pid);
	}

	public List<Product> getProductInfoBykeywords(String keywords) throws SQLException {
		String sql = "select * from product where pname like ? limit ?,?";
		return runner.query(sql, new BeanListHandler<Product>(Product.class), "%"+keywords+"%",0,8);
	}

	public int getTotalCountByKeywords(String keywords) throws SQLException {
		String sql = "select count(*) from product where pname like ?";
		Long totalCount = (Long) runner.query(sql, new ScalarHandler(), "%"+keywords+"%");
		return totalCount.intValue();
	}

	public List<Product> getDataListByKeywords(int beginIndex, int count, String keywords) throws SQLException {
		String sql = "select * from product where pname like ? limit ?,?";
		return runner.query(sql, new BeanListHandler<Product>(Product.class),"%"+keywords+"%",beginIndex,count);
	}

}
