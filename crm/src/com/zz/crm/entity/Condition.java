package com.zz.crm.entity;

import java.io.Serializable;

/**
 * 对应客户管理条件对象
 * @author zzCoding
 *
 * 2019年8月28日
 */

public class Condition implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String cust_name;
	private String cust_source;
	private String cust_industry;
	private String cust_level;
	
	
	
	public Condition() {
		super();
	}
	public Condition(String cust_name, String cust_source, String cust_industry, String cust_level, Integer page,
			Integer start, Integer number) {
		super();
		this.cust_name = cust_name;
		this.cust_source = cust_source;
		this.cust_industry = cust_industry;
		this.cust_level = cust_level;
		this.page = page;
		this.start = start;
		this.number = number;
	}


	//当前页数
	private Integer page;
	//起始索引
	private Integer start;
	//每页有多少条
	private Integer number;

	public String getCust_name() {
		return cust_name;
	}

	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}

	public String getCust_source() {
		return cust_source;
	}

	public void setCust_source(String cust_source) {
		this.cust_source = cust_source;
	}

	public String getCust_industry() {
		return cust_industry;
	}

	public void setCust_industry(String cust_industry) {
		this.cust_industry = cust_industry;
	}

	public String getCust_level() {
		return cust_level;
	}

	public void setCust_level(String cust_level) {
		this.cust_level = cust_level;
	}

	public Integer getPage() {
		return page;
	}
	
	public void setPage(Integer page) {
		this.page = page;
	}

	
	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}
	
	
	@Override
	public String toString() {
		return "Condition [cust_name=" + cust_name + ", cust_source=" + cust_source + ", cust_industry=" + cust_industry
				+ ", cust_level=" + cust_level + ", page=" + page + ", start=" + start + ", number=" + number + "]";
	}
}
