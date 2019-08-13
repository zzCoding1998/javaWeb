package com.zz.entity;

/**
 * 用于存储购物车项的javaBean
 * @author zzCoding
 *
 * 2019年8月4日
 */
public class CartItem {

	private Product product;
	private int num;
	private double subTotalPrice;
	
	
	public CartItem() {
		super();
	}
	public CartItem(Product product, int num, double subTotalPrice) {
		super();
		this.product = product;
		this.num = num;
		this.subTotalPrice = subTotalPrice;
	}
	
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public double getSubTotalPrice() {
		return subTotalPrice;
	}
	public void setSubTotalPrice(double subTotalPrice) {
		this.subTotalPrice = subTotalPrice;
	}
	
	
	@Override
	public String toString() {
		return "CartItem [product=" + product + ", num=" + num + ", subTotalPrice=" + subTotalPrice + "]";
	}
}
