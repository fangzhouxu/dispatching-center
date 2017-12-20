package com.xgd.resources.service;

import java.util.ArrayList;
import java.util.List;

import com.xgd.resources.entity.PageBean;
import com.xgd.resources.entity.SchedulingModule;
import com.xgd.resources.entity.SchedulingServer;

public interface IpAndPortResourcesService {

	void updateResourcesStatusByTimeOut();

	void updateResourcesStatusByFinshed(SchedulingModule module);

	 void createServer(SchedulingServer server);

	 PageBean<SchedulingServer> getServerEntitiesByPage(Integer page, Integer rows, String dataStr);

	void updateServer(SchedulingServer server);

	void deleteOfBatch(String ids);

	SchedulingServer getSingleServerEntityById(Integer sid);

	void addModules(ArrayList<SchedulingModule> modules);

	List<SchedulingModule> getAllModulesDetails(Integer sid);

	void updateModuleState(SchedulingModule module);

	void editModule(SchedulingModule module);

	String checkValidation(SchedulingModule module);

	String getFreeIpAndPort(SchedulingModule module);

	void createModuleError(String error, String checkCode, String sipAndPort, String posip);

	int delectModule_Errors(String id);

	void updateServerStateByModule_id(String id);
	
}
