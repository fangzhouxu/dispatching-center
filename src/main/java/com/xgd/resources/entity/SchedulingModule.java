package com.xgd.resources.entity;

import java.util.Date;
import java.util.List;

public class SchedulingModule {
	private String id;//子模块id
	private String moduleName;//模块名称
	private String ipAndPort;//子模块ip和端口数值
	private Integer status;//子模块运行时状态，0表示空闲，1表示繁忙;
	private Date startTime;//子模块繁忙开始时间，默认不繁忙;
	private String checkCode;//子模块接收pos机的校验码;
	private Integer switchKey;//子模块启用开关键,1表示启用，0表示不启用
	private Integer state;//子模块健康状态，0表示正常，1表示异常
	private Integer errorTimes;//一段时间内，子模块的被pos机上报的无响应次数，为健康状态判断的依据，拟定报错5次以上，健康状态即为异常，就自动停用
	private SchedulingServer server;//子模块对应服务器
	private List<Module_Error> errors;//报错信息集合

	public String getId(){
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getIpAndPort() {
		return ipAndPort;
	}
	public void setIpAndPort(String ipAndPort) {
		this.ipAndPort = ipAndPort;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public String getCheckCode() {
		return checkCode;
	}
	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}
	public Integer getSwitchKey() {
		return switchKey;
	}
	public void setSwitchKey(Integer switchKey) {
		this.switchKey = switchKey;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public SchedulingServer getServer() {
		return server;
	}
	public void setServer(SchedulingServer server) {
		this.server = server;
	}
	public Integer getErrorTimes() {
		return errorTimes;
	}
	public void setErrorTimes(Integer errorTimes) {
		this.errorTimes = errorTimes;
	}
	public List<Module_Error> getErrors() {
		return errors;
	}
	public void setErrors(List<Module_Error> errors) {
		this.errors = errors;
	}
}
