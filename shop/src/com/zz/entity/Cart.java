package com.zz.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * 购物车：多个购物车项和总价组成
 * @author zzCoding
 *
 * 2019年8月4日
 */
public class Cart {

	//存储map形式，方便根据key删除购物车项
	private Map<String, CartItem> cartItems = new HashMap<String, CartItem>();
	private double totalPrice = 0;
	
	
	public Cart() {
		super();
	}
	public Cart(Map<String, CartItem> cartItems, double totalPrice) {
		super();
		this.cartItems = cartItems;
		this.totalPrice = totalPrice;
	}
	
	
	public Map<String, CartItem> getCartItems() {
		return cartItems;
	}
	public void setCartItems(Map<String, CartItem> cartItems) {
		this.cartItems = cartItems;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	
	@Override
	public String toString() {
		return "Cart [cartItems=" + cartItems + ", totalPrice=" + totalPrice + "]";
	}
}
