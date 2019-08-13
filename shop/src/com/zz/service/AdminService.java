package com.zz.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.zz.dao.AdminDao;
import com.zz.dao.ProductDao;
import com.zz.entity.Category;
import com.zz.entity.Condition;
import com.zz.entity.OrderItem;
import com.zz.entity.Orders;
import com.zz.entity.PageBean;
import com.zz.entity.Product;
import com.zz.utils.CommonsUtils;

public class AdminService {
	
	AdminDao adminDao = new AdminDao();
	
	//根据condition查询商品分页信息
	public PageBean<Product> getProductByCondition(Condition condition, int page, int countPerPage) throws SQLException {
		//封装pageBean
		PageBean<Product> pageBean = new PageBean<Product>();
		//设置当前页
		pageBean.setPage(page);
		//设置每页显示信息条数
		pageBean.setCount(countPerPage);
		//设置最大条数
		pageBean.setTotalCount(adminDao.getTotalCount(condition));
		//设置最大页数
		int totalPage = (int) Math.ceil(1.0*pageBean.getTotalCount()/pageBean.getCount());
		pageBean.setTotalPage(totalPage);
		//获取当前页商品信息列表
		int index = (page-1)*countPerPage;
		pageBean.setDataList(adminDao.getProductByCondition(condition,index,countPerPage));
		return pageBean;
	}
	
	//查询商品分类信息
	public List<Category> getCategory() {
		try {
			return adminDao.getCategory();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	//添加商品
	public void addProduct(Product product) {
		try {
			adminDao.addProduct(product);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//获得商品列表
	public List<Product> getProductList() {
		List<Product> productList;
		try {
			productList = adminDao.getProductList();
			return productList;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	//在业务层封装pageBean信息，返回给web层
	public PageBean<Product> getpageBean(int page,int countPerPage) throws Exception {
		PageBean<Product> pageBean = new PageBean<Product>();
		//设置当前页数
		pageBean.setPage(page);
		//设置当前页面条数
		pageBean.setCount(countPerPage);
		//设置总条数
		int totalCount = adminDao.getTotalCount();
		pageBean.setTotalCount(totalCount);
		//设置总页数
		int totalPage = (int) Math.ceil(1.0*totalCount/countPerPage);
		pageBean.setTotalPage(totalPage);
		//获取商品信息
		int index = (page-1)*countPerPage;
		pageBean.setDataList(adminDao.getDataList(index,countPerPage));
		return pageBean;
	}
	
	//删除商品
	public int delProduct(String pid) {
		int result = 0;
		try {
			result = adminDao.delProduct(pid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//根据商品pid查询商品
	public Product getProductByPid(String pid) {
		try {
			return adminDao.getProductByPid(pid);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	//修改商品信息
	public void updateProduct(Product product) {
		try {
			adminDao.updateProduct(product);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//更新商品分类
	public void updateCategory(String cname, String newCname) {
		try {
			adminDao.updateCategory(cname,newCname);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//添加商品分类
	public void categoryAdd(String cname) {
		try {
			String cid = CommonsUtils.getUUID();
			Category category = new Category(cid,cname);
			adminDao.categoryAdd(category);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//删除分类
	public void delCategory(String cid) {
		try {
			adminDao.delCategory(cid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//获取订单列表
	public List<Orders> getOrderList() {
		List<Orders> orderList = null;
		try {
			orderList = adminDao.getOrderList();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orderList;
	}

	//根据oid查询订单详情信息
	public List<OrderItem> getOrderInfoByOid(String oid) {
		//1.根据oid查出所有的订单项,并放入orders对象
		List<OrderItem> orderItemList = null;
		List<Map<String, Object>> mapList = null;
		try {
			mapList = adminDao.getOrderItemListByOid(oid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//2.解析mapList，将数据填充到orderItemList
		if(mapList!=null) {
			orderItemList = new ArrayList<OrderItem>();
			for(Map<String, Object> map : mapList) {
				OrderItem orderItem = new OrderItem();
				orderItem.setCount((int) map.get("count"));
				orderItem.setSubtotal((double) map.get("subtotal"));
				orderItem.setProduct(new Product());
				orderItem.getProduct().setPid((String) map.get("pid"));
				orderItemList.add(orderItem);
			}
		}
		//3.遍历orderItemList，填充product信息
		ProductDao productDao = new ProductDao();
		if(orderItemList!=null) {
			for(OrderItem orderItem : orderItemList) {
				try {
					Product product = productDao.getProductInfoByPid(orderItem.getProduct().getPid());
					orderItem.setProduct(product);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return orderItemList;
	}
}
