package com.xgd.resources.entity;

import java.util.Date;

public class Module_Error {
	private Integer id;
	private String errorContent;//上报的无响应内容
	private Date setUpTime;//上报信息创建时间
	private SchedulingModule module;//上报的无响应对应的模块
	private String posip;//上报的pos机ip
	public String getPosip() {
		return posip;
	}
	public void setPosip(String posip) {
		this.posip = posip;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getErrorContent() {
		return errorContent;
	}
	public void setErrorContent(String errorContent) {
		this.errorContent = errorContent;
	}
	public Date getSetUpTime() {
		return setUpTime;
	}
	public void setSetUpTime(Date setUpTime) {
		this.setUpTime = setUpTime;
	}
	public SchedulingModule getModule() {
		return module;
	}
	public void setModule(SchedulingModule module) {
		this.module = module;
	}
	
	
}
