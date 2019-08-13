package com.zz.entity;

public class OrderItem {

	private String itemid;
	private int count;
	private double subtotal;
	private String oid;
	private Product product;
	
	
	public OrderItem() {
		super();
	}
	public OrderItem(String itemid, int count, double subtotal, String oid, Product product) {
		super();
		this.itemid = itemid;
		this.count = count;
		this.subtotal = subtotal;
		this.oid = oid;
		this.product = product;
	}
	
	
	public String getItemid() {
		return itemid;
	}
	public void setItemid(String itemid) {
		this.itemid = itemid;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	
	
	@Override
	public String toString() {
		return "OrderItem [itemid=" + itemid + ", count=" + count + ", subtotal=" + subtotal + ", oid=" + oid
				+ ", product=" + product + "]";
	}
}
