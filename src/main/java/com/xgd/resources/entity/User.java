package com.xgd.resources.entity;

import java.util.Date;

public class User {
	private Integer id;
	private String username ;
	private String password;
	private Date setUpTime;
	private Date lastLoginTime;
	private String remark;
	private Integer lock;
	private Boolean isAdmin;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getSetUpTime() {
		return setUpTime;
	}
	public void setSetUpTime(Date setUpTime) {
		this.setUpTime = setUpTime;
	}
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getLock() {
		return lock;
	}
	public void setLock(Integer lock) {
		this.lock = lock;
	}
	public Boolean getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", setUpTime=" + setUpTime
				+ ", lastLoginTime=" + lastLoginTime + ", remark=" + remark + ", lock=" + lock + ", isAdmin=" + isAdmin
				+ "]";
	}
	
}
