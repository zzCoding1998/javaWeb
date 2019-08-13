package com.zz.dao;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.zz.entity.Category;
import com.zz.entity.Condition;
import com.zz.entity.OrderItem;
import com.zz.entity.Orders;
import com.zz.entity.Product;
import com.zz.utils.DataSourceUtils;




public class AdminDao {

	SimpleDateFormat dataformat = new SimpleDateFormat("yy-MM-dd");
	
	//根据condition查询商品列表
	public List<Product> getProductByCondition(Condition condition, int index, int countPerPage) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where 1=1";
		List<Object> param = new ArrayList<Object>();
		if(condition.getPname()!=null && !"".equals(condition.getPname())) {
			sql += " and pname like ?";
			param.add("%"+ condition.getPname() +"%");
		}
		if(condition.getIs_hot()!=null && !"".equals(condition.getIs_hot())) {
			sql += " and is_hot = ?";
			param.add(condition.getIs_hot());
		}
		if(condition.getCid()!=null && !"".equals(condition.getCid())) {
			sql += " and cid = ?";
			param.add(condition.getCid());
		}
		sql += " order by pdate desc limit ?,?";
		param.add(index);
		param.add(countPerPage);
		return runner.query(sql, new BeanListHandler<Product>(Product.class), param.toArray());
	}

	//根据condition查询商品个数
	public int getTotalCount(Condition condition) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		List<Object> param = new ArrayList<Object>();
		String sql = "select count(*) from product where 1=1";
		if(condition.getPname()!=null && !"".equals(condition.getPname())) {
			sql += " and pname like ?";
			param.add("%"+ condition.getPname() +"%");
		}
		if(condition.getIs_hot()!=null && !"".equals(condition.getIs_hot())) {
			sql += " and is_hot = ?";
			param.add(condition.getIs_hot());
		}
		if(condition.getCid()!=null && !"".equals(condition.getCid())) {
			sql += " and cid = ?";
			param.add(condition.getCid());
		}
		Long countPerPage =  (Long) runner.query(sql, new ScalarHandler(), param.toArray());
		return countPerPage.intValue();
	}

	//查询所有商品分类
	public List<Category> getCategory() throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from category";
		return runner.query(sql, new BeanListHandler<Category>(Category.class));
	}
	
	//添加商品
	public void addProduct(Product product) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "insert into product values(?,?,?,?,?,?,?,?,?,?)";
		Object[] param = {product.getPid(),product.getPname(),product.getMarket_price(),product.getShop_price(),
				product.getPimage(),dataformat.format(product.getPdate()),product.getIs_hot(),product.getPdesc(),product.getPflag(),
				product.getCategory().getCid()};
		runner.update(sql, param);
	}
	
	//获得商品列表
	public List<Product> getProductList() throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product";
		return runner.query(sql, new BeanListHandler<Product>(Product.class));
	}

	//得到商品总数量
	public int getTotalCount() throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select count(*) from product";
		Long totalCount = (Long) runner.query(sql, new ScalarHandler());
		return totalCount.intValue();
	}

	//获得当前页面商品信息列表
	public List<Product> getDataList(int index, int countPerPage) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product order by pdate desc limit ?,?";
		return runner.query(sql, new BeanListHandler<Product>(Product.class), index,countPerPage);
	}
	
	//删除商品
	public int delProduct(String pid) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "delete from product where pid=?";
		return runner.update(sql,pid);
	}
	
	//根据商品pid查询商品
	public Product getProductByPid(String pid) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where pid =?";
		return runner.query(sql, new BeanHandler<Product>(Product.class),pid);
	}
	
	//更新商品信息
	public void updateProduct(Product product) throws SQLException {
		
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "update product set pname=?,market_price=?,shop_price=?,cid=?,pdesc=?,is_hot=?,pdate=?,pimage=? where pid=?";
		Object[] param = {product.getPname(),product.getMarket_price(),product.getShop_price(),product.getCategory().getCid(),
				product.getPdesc(),product.getIs_hot(),dataformat.format(new Date()),product.getPimage(),product.getPid()};
		runner.update(sql,param);
	}

	//编辑商品分类
	public void updateCategory(String cname, String newCname) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "update category set cname=? where cname=?";
		runner.update(sql, newCname,cname);
	}

	//添加商品分类
	public void categoryAdd(Category category) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "insert into category values(?,?)";
		runner.update(sql, category.getCid(),category.getCname());
	}

	//删除分类
	public void delCategory(String cid) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "delete from category where cid=?";
		runner.update(sql, cid);
	}

	//查找所有的订单
	public List<Orders> getOrderList() throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from orders ";
		return runner.query(sql, new BeanListHandler<Orders>(Orders.class));
	}

	//根据oid获取所有订单项
	public List<Map<String, Object>> getOrderItemListByOid(String oid) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from orderitem where oid=?";
		return runner.query(sql,new MapListHandler(), oid);
	}
}
