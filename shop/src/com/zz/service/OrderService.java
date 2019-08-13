package com.zz.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.zz.dao.OrderDao;
import com.zz.entity.OrderItem;
import com.zz.entity.Orders;
import com.zz.utils.DataSourceUtils;

public class OrderService {
	
	OrderDao orderDao = new OrderDao();

	//提交订单
	public boolean submitOrder(Orders orders) {
		//是否成功标志
		boolean flag = false;
		List<OrderItem> orderItemList = orders.getOrderItemList();
		//1.开启事务
		try {
			DataSourceUtils.startTransaction();
			//2.将订单信息写入数据库
			orderDao.addOrders(orders);
			//3.将订单项写入数据库
			for(OrderItem orderItem : orderItemList) {
				orderDao.addOrderItem(orderItem);
			}
			//4.提交事务，并关闭资源
			DataSourceUtils.commitAndRelease();
			//5.修改返回标志
			flag = true;
		} catch (SQLException e) {
			e.printStackTrace();
			//出错时事务回滚、提交、释放资源、返回
			try {
				DataSourceUtils.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			try {
				DataSourceUtils.commitAndRelease();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return flag;
		}
		return flag;
	}

	//更新订单地址、电话、收件人等信息
	public boolean updateOrder(Orders orders) {
		boolean flag = false;
		int result = 0;
		try {
			result = orderDao.updateOrder(orders);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(result==1) {
			flag=true;
		}
		return flag;
	}

	//更改订单状态
	public boolean modifyOrderState(String oid) {
		int result = 0;
		try {
			result = orderDao.modifyOrderState(oid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(result==1) {
			return true;
		}
		return false;
	}

	//根据uid查找所有的订单
	public List<Orders> getAllOrdersByUid(String uid) {
		List<Orders> ordersList = null;
		try {
			ordersList = orderDao.getAllOrdersByUid(uid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ordersList;
	}

	//根据oid得到orderitem的mapList集合
	public List<Map<String, Object>> getOrderitemMapListByOid(String oid) {
		List<Map<String, Object>> mapList = null;
		try {
			mapList  = orderDao.getOrderitemMapListByOid(oid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mapList;
	}
}
