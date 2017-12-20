package com.xgd.resources.entity;

import java.util.Date;
import java.util.List;

public class SchedulingServer {
	private Integer id;
	private String serverName;//服务器名称
	private String ipAndPort;//服务器ip和端口数值
	private Date setUpTime;//服务器录入时间
	private Date lastEditTime;//服务器上次修改时间
	private Integer cycleTime;//服务器状态检测周期
	private Date lastCheckTime;//服务器状态上次检测时间
	private Integer serverState;//服务器状态,0表示正常,1表示一个模块损坏，2表示2个模块坏掉，依次类推
	private List<SchedulingModule> modules;//服务器子模块
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getLastEditTime() {
		return lastEditTime;
	}
	public void setLastEditTime(Date lastEditTime) {
		this.lastEditTime = lastEditTime;
	}
	public List<SchedulingModule> getModules() {
		return modules;
	}
	public void setModules(List<SchedulingModule> modules) {
		this.modules = modules;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public String getIpAndPort() {
		return ipAndPort;
	}
	public void setIpAndPort(String ipAndPort) {
		this.ipAndPort = ipAndPort;
	}
	public Date getSetUpTime() {
		return setUpTime;
	}
	public void setSetUpTime(Date setUpTime) {
		this.setUpTime = setUpTime;
	}
	public Integer getCycleTime() {
		return cycleTime;
	}
	public void setCycleTime(Integer cycleTime) {
		this.cycleTime = cycleTime;
	}
	public Date getLastCheckTime() {
		return lastCheckTime;
	}
	public void setLastCheckTime(Date lastCheckTime) {
		this.lastCheckTime = lastCheckTime;
	}
	public Integer getServerState() {
		return serverState;
	}
	public void setServerState(Integer serverState) {
		this.serverState = serverState;
	}
	
}
