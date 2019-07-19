package com.zz.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 封装分页javaBean
 * @author zzCoding
 *
 * 2019年7月20日
 */
public class PageInfo<T> {

	//当前页
	private int currentPage;
	//每页条数
	private int currentCount;
	//总页数
	private int totalPage;
	//总条数
	private int totalCount;
	//当前页面商品信息列表
	List<T> currentList = new ArrayList<T>();
	
	
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getCurrentCount() {
		return currentCount;
	}
	public void setCurrentCount(int currentCount) {
		this.currentCount = currentCount;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public List<T> getCurrentList() {
		return currentList;
	}
	public void setCurrentList(List<T> currentList) {
		this.currentList = currentList;
	}
}
