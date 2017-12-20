package com.xgd.resources.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PageBean<T> implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Integer pageNum =1;//当前页数
	private Integer pageSize =0;//每页显示的最大数目
	private Integer pageTotal=1;//总页数
	private int totalRecords = 0; // 总记录数
	private List<T> pageInfo=new ArrayList<>();//每页显示的具体信息对象
	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getPageTotal() {
		return pageTotal;
	}
	public void setPageTotal(Integer pageTotal) {
		this.pageTotal = pageTotal;
	}
	public int getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
	public List<T> getPageInfo() {
		return pageInfo;
	}
	public void setPageInfo(List<T> pageInfo) {
		this.pageInfo = pageInfo;
	}

	
}
