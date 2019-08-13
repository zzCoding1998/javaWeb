package com.zz.entity;

import java.util.List;
/**
 * 用于分页的javaBean
 * @author zzCoding
 *
 * 2019年8月3日
 */
public class PageBean<T> {
	
	private int count;
	private int totalCount;
	private int page;
	private int totalPage;
	private List<T> dataList;
	
	
	public PageBean() {
		super();
	}
	public PageBean(int count, int totalCount, int page, int totalPage, List<T> dataList) {
		super();
		this.count = count;
		this.totalCount = totalCount;
		this.page = page;
		this.totalPage = totalPage;
		this.dataList = dataList;
	}
	
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public List<T> getDataList() {
		return dataList;
	}
	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}
	
	
	@Override
	public String toString() {
		return "PageBean [count=" + count + ", totalCount=" + totalCount + ", page=" + page + ", totalPage=" + totalPage
				+ ", dataList=" + dataList + "]";
	}
}
