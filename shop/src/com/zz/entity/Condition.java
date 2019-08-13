package com.zz.entity;

public class Condition {
	
	private String pname;
	private String is_hot;
	private String cid;
	
	public Condition() {
		super();
	}
	public Condition(String pname, String is_hot, String cid) {
		super();
		this.pname = pname;
		this.is_hot = is_hot;
		this.cid = cid;
	}


	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getIs_hot() {
		return is_hot;
	}
	public void setIs_hot(String is_hot) {
		this.is_hot = is_hot;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	
	@Override
	public String toString() {
		return "Condition [pname=" + pname + ", is_hot=" + is_hot + ", cid=" + cid + "]";
	}
}
