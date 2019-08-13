package com.zz.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import com.zz.entity.OrderItem;
import com.zz.entity.Orders;
import com.zz.utils.DataSourceUtils;

public class OrderDao {

	QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
	
	//提交订单时将订单信息写入数据库
	public void addOrders(Orders orders) throws SQLException {
		String sql = "insert into orders(oid,ordertime,total,state,uid) values(?,?,?,?,?)";
		Object[] params = {orders.getOid(),orders.getOrdertime(),orders.getTotal(),orders.getState(),orders.getUser().getUid()};
		runner.update(DataSourceUtils.getConnection(), sql, params);
	}
	
	//提交订单时将订单项写入数据库
	public void addOrderItem(OrderItem orderItem) throws SQLException {
		String sql = "insert into orderitem(itemid,count,subtotal,pid,oid) values(?,?,?,?,?)";
		Object[] params = {orderItem.getItemid(),orderItem.getCount(),orderItem.getSubtotal(),orderItem.getProduct().getPid(),orderItem.getOid()};
		runner.update(DataSourceUtils.getConnection(), sql, params);
	}

	//更新订单地址、收件人、电话号码等信息
	public int updateOrder(Orders orders) throws SQLException {
		String sql = "update orders set address=?,name=?,telephone=? where oid=?";
		return runner.update(sql, orders.getAddress(),orders.getName(),orders.getTelephone(),orders.getOid());
	}

	//更改订单状态
	public int modifyOrderState(String oid) throws SQLException {
		String sql ="update orders set state=1 where oid=?";
		return runner.update(sql, oid);
	}

	//根据用户id查询所有订单
	public List<Orders> getAllOrdersByUid(String uid) throws SQLException {
		String sql = "select * from orders where uid=? order by ordertime desc";
		return runner.query(sql, new BeanListHandler<Orders>(Orders.class), uid);
	}

	//根据oid查询所有的订单项的mapList集合
	public List<Map<String, Object>> getOrderitemMapListByOid(String oid) throws SQLException {
		String sql = "select o.count,o.subtotal,p.pid,p.pname,p.shop_price,p.pimage from orderitem as o,product as p where o.pid=p.pid and o.oid=?";
		return runner.query(sql, new MapListHandler(), oid);
	}
	
	

}
