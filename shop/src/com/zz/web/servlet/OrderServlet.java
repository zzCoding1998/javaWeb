package com.zz.web.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zz.entity.Cart;
import com.zz.entity.CartItem;
import com.zz.entity.OrderItem;
import com.zz.entity.Orders;
import com.zz.entity.Product;
import com.zz.entity.User;
import com.zz.service.OrderService;
import com.zz.utils.CommonsUtils;

@SuppressWarnings("serial")
public class OrderServlet extends BaseServlet {

	OrderService orderService = new OrderService();
	
	//提交订单
	public void submitOrder(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		HttpSession session = request.getSession();
		//1.需要先进行登录
		User user = (User) session.getAttribute("user");
		if(user==null) {
			response.sendRedirect(request.getContextPath()+"/login.jsp");
		}
		//2.从session得到购物车项，封装orderItemList对象
		Cart cart = (Cart) session.getAttribute("cart");
		List<OrderItem> orderItemList = new ArrayList<OrderItem>();
		String oid = CommonsUtils.getOrderId();
		for(CartItem cartItem:cart.getCartItems().values()) {
			OrderItem orderItem = new OrderItem();
			//订单项编号
			orderItem.setItemid(CommonsUtils.getUUID());
			//订单项包含商品的数量
			orderItem.setCount(cartItem.getNum());
			//订单项总价
			orderItem.setSubtotal(cartItem.getSubTotalPrice());
			//订单编号
			orderItem.setOid(oid);
			//订单商品
			orderItem.setProduct(cartItem.getProduct());
			//将订单项放入列表
			orderItemList.add(orderItem);
		}
		//3.获得相关信息，封装orders对象
		Orders orders = new Orders();
		//订单编号
		orders.setOid(oid);
		//订单生成日期
		SimpleDateFormat dataFormat = new SimpleDateFormat("yy-MM-dd hh:mm:ss");
		String ordertime = dataFormat.format(new Date());
		orders.setOrdertime(ordertime);
		//订单总价
		orders.setTotal(cart.getTotalPrice());
		//订单状态
		orders.setState(0);
		//订单项
		orders.setOrderItemList(orderItemList);
		//订单所属用户
		orders.setUser(user);
		//3.2剩余收货人信息不填充
		//4.传递数据，数据库操作
		boolean isSuccess = orderService.submitOrder(orders);
		//5.操作成功，将order数据放到session中,清空购物车
		if(isSuccess) {
			session.setAttribute("orders", orders);
			session.removeAttribute("cart");
		}
		//6.重定向
		response.sendRedirect(request.getContextPath()+"/order_info.jsp");
	}
	//确认订单
	public void orderConfirm(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
		//1.获取收货人信息
		String address = request.getParameter("address");
		if(address.equals("")) {
			request.setAttribute("addressInfo", "请填写收货地址！");
			request.getRequestDispatcher("/order_info.jsp").forward(request, response);
		}
		String name = request.getParameter("name");
		if(name.equals("")) {
			request.setAttribute("nameInfo", "请填写收件人姓名！");
			request.getRequestDispatcher("/order_info.jsp").forward(request, response);
		}
		String telephone = request.getParameter("telephone");
		if(telephone.equals("")) {
			request.setAttribute("telephoneInfo", "请填写联系方式！");
			request.getRequestDispatcher("/order_info.jsp").forward(request, response);
		}
		//2.封装到orders中
		HttpSession session = request.getSession();
		Orders orders = (Orders) session.getAttribute("orders");
		if(orders!=null) {
			orders.setAddress(address);
			orders.setName(name);
			orders.setTelephone(telephone);
		}
		//3.传递数据到业务层，完善订单信息
		boolean isSuccess = orderService.updateOrder(orders);
		//4.如果更新成功，将最新信息更新到session中
		if(isSuccess) {
			session.setAttribute("orders", orders);
		}
		//5.重定向到支付界面
		response.sendRedirect(request.getContextPath()+"/alipay/alipayInfo.jsp");
	}
	
	//支付回调页面
	public void orderSuccess(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		
		//1.拿到相应参数
		//商户订单号
		String out_trade_no = request.getParameter("out_trade_no");
		//支付宝交易号
		String trade_no = request.getParameter("trade_no");
		//付款金额
		String total_amount = request.getParameter("total_amount");
		//2.根据参数判断是否付款成功
		HttpSession session = request.getSession();
		Orders orders = (Orders) session.getAttribute("orders");
		//校对商品编号和交易金额
		//2.1付款成功
		if(out_trade_no.equals(orders.getOid()) && total_amount.equals(("0."+orders.getTotal()).substring(0, 4))) {
			//更改交易单状态
			boolean modifyOrderState = orderService.modifyOrderState(orders.getOid());
			if(modifyOrderState) {
				//清除session中的订单信息
				//session.removeAttribute("orders");
				//将相关的信息请求转发给成功页面
				request.setAttribute("payResult", "支付成功");
				request.setAttribute("out_trade_no", out_trade_no);
				request.setAttribute("trade_no", trade_no);
				request.setAttribute("total_amount", total_amount);
				request.getRequestDispatcher("paySuccess.jsp").forward(request, response);;
			}
		}
	}
	
	//我的订单
	public void getMyOrder(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		
		//1.判断当前用户是否登录
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if(user==null) {
			response.sendRedirect(request.getContextPath()+"/login.jsp");
			return;
		} 
		//2.从数据库中查询该用户的所有的orders
		List<Orders> ordersList = orderService.getAllOrdersByUid(user.getUid());
		//3.根据ordersList中的交易编号，填充每笔交易的orderItemList,得到完整的ordersList
		List<Map<String, Object>> mapList = null;
		OrderItem orderItem = null;
		Product product = null;
		List<OrderItem> orderItemList = null;
		if(ordersList!=null) {
			for(Orders orders : ordersList) {
				//处理ordertime格式
				orders.setOrdertime(orders.getOrdertime().substring(0, 19));
				orderItem = new OrderItem();
				orderItemList = new ArrayList<OrderItem>();
				product = new Product();
				orderItem.setProduct(product);
				//先得到orderitem的map列表
				mapList = orderService.getOrderitemMapListByOid(orders.getOid());
				//解析map列表，得到orderItemList
				//有数据o.count,o.subtotal,p.pid,p.pname,p.shop_price,p.pimage
				for(Map<String, Object> map : mapList) {
					orderItem.setCount((Integer) map.get("count"));
					orderItem.setSubtotal((Double) map.get("subtotal"));
					orderItem.getProduct().setPid((String) map.get("pid"));
					orderItem.getProduct().setPname((String) map.get("pname"));
					orderItem.getProduct().setShop_price((Double) map.get("shop_price"));
					orderItem.getProduct().setPimage((String) map.get("pimage"));
					orderItemList.add(orderItem);
				}
				//将orderItemList放入orders中
				orders.setOrderItemList(orderItemList);
			}
		}
		//4.请求转发
		request.setAttribute("ordersList", ordersList);
		request.getRequestDispatcher("/order_list.jsp").forward(request, response);
	}
}