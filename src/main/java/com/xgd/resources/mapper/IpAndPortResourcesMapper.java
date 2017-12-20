package com.xgd.resources.mapper;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xgd.resources.entity.Module_Error;
import com.xgd.resources.entity.SchedulingModule;
import com.xgd.resources.entity.SchedulingServer;

public interface IpAndPortResourcesMapper {

	String getFreeIpAndPortByOrder();

	int updateResourcesStatus(SchedulingModule module);

	int updateResourcesStatusByForce();

	void addServer(SchedulingServer server);

	List<SchedulingServer> queryServersByPage(Map<String, String> map);

	void updateServerById(SchedulingServer server);

	void deleteServerById(int id);

	SchedulingServer querySingleServerById(Integer id);

	void addModule(ArrayList<SchedulingModule> modules);

	List<SchedulingModule> QueryAllModules(Integer sid);

	int updateModuleState(SchedulingModule module);

	void editModuleById(SchedulingModule module);

	String getFreeIpAndPortByCheckCodeAndSipPort(SchedulingModule module);

	String getBusyIpAndPortByCheckCodeAndSip(SchedulingModule module);

	SchedulingModule querySingleModuleByCheckCodeAndSip(SchedulingModule module);

	void createModuleError(Module_Error err);

	Integer queryCycleTimeByIpAndPort(String sipAndPort);

	List<Module_Error> queryModuleErrorsByCycleTime(HashMap<String, Object> map);

	void updateModuleStateBySid(SchedulingModule module);

	Integer sumErrorStateOfModule(String sipAndPort);

	int deleteModule_ErrorsByModule_id(String id);

	int getServer_idByModule_id(String id);

	Integer queryAllServersCount();



}
