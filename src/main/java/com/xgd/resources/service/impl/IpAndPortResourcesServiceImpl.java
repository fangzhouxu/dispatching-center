package com.xgd.resources.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.xgd.resources.entity.Module_Error;
import com.xgd.resources.entity.PageBean;
import com.xgd.resources.entity.SchedulingModule;
import com.xgd.resources.entity.SchedulingServer;
import com.xgd.resources.mapper.IpAndPortResourcesMapper;
import com.xgd.resources.service.IpAndPortResourcesService;
import com.xgd.resources.utils.DateConvertUtils;
import com.xgd.resources.utils.FastJsonUtils;
import com.xgd.resources.utils.UUIDUtils;
@Transactional
@Service
public class IpAndPortResourcesServiceImpl implements IpAndPortResourcesService{
	
	@Autowired 
	private IpAndPortResourcesMapper resourcesMapper;
	
	/*
	 * pos10秒内没有上报消息，就将繁忙的状态重置空闲
	 * */
	@Override
	public void updateResourcesStatusByTimeOut(){
		int row =resourcesMapper.updateResourcesStatusByForce();
		if(row>=1){
			System.out.println("超时10秒无应答，执行强制空闲成功");
		}
	}
	

	@Override
	public void updateResourcesStatusByFinshed(SchedulingModule module) {
		//根据校验码查询繁忙状态ip和端口
		String ipAndPort = resourcesMapper.getBusyIpAndPortByCheckCodeAndSip(module);
		//设置空闲状态默认时间为2011-11-11 00:00:00。
		if(ipAndPort==null){
			//查不到说明已强制为空闲
			System.out.println("已经是空闲状态");
			return;
		}
		String strTime ="2011-11-11 00:00:00";
		Timestamp dateToDatestamp = DateConvertUtils.StringToDatestamp(strTime);
		module = new SchedulingModule();
		//重置状态设置为空闲
		module.setStatus(0);
		//重置检验码为空闲时检验码，以便服务器检验
		module.setCheckCode("00000000");
		//重置繁忙状态建立时间为空闲状态时间
		module.setStartTime(dateToDatestamp);
		module.setIpAndPort(ipAndPort);
		int row =resourcesMapper.updateResourcesStatus(module);
		//修改繁忙Ip的状态为空闲后，再重置校验码
		if(row==1){
			System.out.println("重置"+row+"条数据至空闲状态");
		}
	}


	/*
	 * 添加服务器信息*/
	@Override
	public void createServer(SchedulingServer server) {
		
		//完善服务器信息,设置服务器初始为未激活状态和创建时间
		server.setServerState(100);
		Timestamp datestamp = DateConvertUtils.dateToDatestamp(new Date());
		server.setSetUpTime(datestamp);
		server.setLastEditTime(datestamp);
		server.setLastCheckTime(datestamp);
		resourcesMapper.addServer(server);
	}

	/*查询所有物理服务器
	 * 
	 * */
	@Override
	public PageBean<SchedulingServer> getServerEntitiesByPage(Integer pageNo, Integer pageSize, String dataStr) {
		pageNo = pageNo==null?1:pageNo;
		pageSize = pageSize ==null?30:pageSize;
		PageHelper.startPage(pageNo, pageSize);
		Map<String, String> map = (Map<String, String>) FastJsonUtils.stringToMap(dataStr);
		List<SchedulingServer> serverList = resourcesMapper.queryServersByPage(map);
		Integer counts =resourcesMapper.queryAllServersCount();
		PageBean<SchedulingServer> pagebean = new PageBean<>();
		pagebean.setTotalRecords(counts);
		pagebean.setPageInfo(serverList);
		return pagebean;
	}


	@Override
	public void updateServer(SchedulingServer server) {
		server.setLastEditTime(DateConvertUtils.dateToDatestamp(new Date()));
		resourcesMapper.updateServerById(server);
		//若更新的是物理服务器的ip和端口，就同步到子模块中
		SchedulingModule module = new SchedulingModule();
		module.setServer(server);
		resourcesMapper.updateModuleStateBySid(module);
	}


	@Override
	public void deleteOfBatch(String ids) {
		
		String[] idList = ids.split("-");
		for (String id : idList) {
			resourcesMapper.deleteServerById(Integer.parseInt(id));
		}
	}


	@Override
	public SchedulingServer getSingleServerEntityById(Integer id) {
		return resourcesMapper.querySingleServerById(id);
	}


	@Override
	public void addModules(ArrayList<SchedulingModule> modules) {
		for (SchedulingModule schedulingModule : modules) {
			//子模块设置初始值
			String UUIDstr = UUIDUtils.getUUIDStr32();
			schedulingModule.setId(UUIDstr);
			schedulingModule.setCheckCode("00000000");
			//子模块健康状况0表示正常，1表示异常
			schedulingModule.setState(0);
			//子模块的繁忙状态，0表示空闲，1表示繁忙
			schedulingModule.setStatus(0);
			//设置子模块空闲状态的默认时间
			String strTime ="2011-11-11 00:00:00";
			Timestamp dateToDatestamp = DateConvertUtils.StringToDatestamp(strTime);
			schedulingModule.setStartTime(dateToDatestamp);
			//1表示启用，0表示停用
			schedulingModule.setSwitchKey(1);
			schedulingModule.setErrorTimes(0);
			//resourcesMapper.addModule(schedulingModule);
		}
		resourcesMapper.addModule(modules);
		SchedulingServer server = modules.get(0).getServer();
		//并将服务器状态设置成激活状态
		server.setServerState(0);
		resourcesMapper.updateServerById(server);
		
	}


	@Override
	public List<SchedulingModule> getAllModulesDetails(Integer sid) {
		
		return resourcesMapper.QueryAllModules(sid);
	}


	@Override
	public void updateModuleState(SchedulingModule module) {
		resourcesMapper.updateModuleState(module);
	}


	@Override
	public void editModule(SchedulingModule module) {
		resourcesMapper.editModuleById(module);
	}


	@Override
	public String checkValidation(SchedulingModule module) {
		return resourcesMapper.getBusyIpAndPortByCheckCodeAndSip(module);
	
	}
	

	@Override
	public String getFreeIpAndPort(SchedulingModule module) {
		module.setStatus(0);
		String strTime ="2011-11-11 00:00:00";
		Timestamp dateToDatestamp = DateConvertUtils.StringToDatestamp(strTime);
		module.setStartTime(dateToDatestamp);
		module.setSwitchKey(1);
		String ipAndPort = resourcesMapper.getFreeIpAndPortByCheckCodeAndSipPort(module);
		if(ipAndPort!=null){
			//将得到的ip和端口置为繁忙状态
			module.setStatus(1);
			module.setStartTime(DateConvertUtils.dateToDatestamp(new Date()));
			module.setIpAndPort(ipAndPort);
			int row =resourcesMapper.updateResourcesStatus(module);
			if(row==1){
				 //只有状态改变，才将端口返回前台
				 return ipAndPort;
			    }
			  return "更新状态失败";
		}
		return "无空闲的ip和端口";
	}



	@Override
	public void createModuleError(String error, String checkCode, String sipAndPort,String posip) {
		SchedulingModule module = new SchedulingModule();
		module.setCheckCode(checkCode);
		SchedulingServer server = new SchedulingServer();
		server.setIpAndPort(sipAndPort);
		module.setServer(server);
		//根据数据查询是否存在module数据，
		module =resourcesMapper.querySingleModuleByCheckCodeAndSip(module);
		if(module!=null){
			Module_Error err = new Module_Error();
			err.setErrorContent(error);
			err.setModule(module);
			Date date = new Date();
			Timestamp now = DateConvertUtils.dateToDatestamp(date);
			err.setSetUpTime(now);
			err.setPosip(posip);
			//插入报错信息,并查询之前的报错信息
			resourcesMapper.createModuleError(err);
			//查询服务器的状态检查周期
			Integer cycleTime =resourcesMapper.queryCycleTimeByIpAndPort(sipAndPort);
			//根据服务器的检测周期和目前时间查找这段时间内的物理服务器的子模块报错个数(当然包括刚添加的次数),假定超过的次数多于5次，即为异常
			Timestamp beginTime =new Timestamp(date.getTime()-cycleTime*60*1000);
			Timestamp endTime =DateConvertUtils.dateToDatestamp(new Date());
			HashMap<String, Object> map = new HashMap<>();
			map.put("beginTime", beginTime);
			map.put("endTime", endTime);
			map.put("module_id", module.getId());
			List<Module_Error> existErrors =resourcesMapper.queryModuleErrorsByCycleTime(map);
			module.setErrorTimes(existErrors.size());
			//添加报错次数
			resourcesMapper.updateModuleState(module);
			System.out.println("添加模块报错信息成功 ");
			if(existErrors!=null&&existErrors.size()>=5){
				//改变模块的状态
				//Integer state = module.getState();
				//只有正常在用的，才会上报健康状态，故将其置为2，异常在用
				//System.out.println("模块之前的健康状态state："+state);
					module.setState(2);
				 int row = resourcesMapper.updateModuleState(module);
				//最后再将服务器健康状态改变,根据物理服务器ip和端口查询子服务器的健康状态中的异常个数
				 if(row>0){
					 Integer serverState = resourcesMapper.sumErrorStateOfModule(sipAndPort);
					 System.out.println("服务器状态serverState："+serverState);
					 server.setServerState(serverState);
					 server.setLastCheckTime(date);
					 resourcesMapper.updateServerById(server);
				 }
			}
		}else{
			System.out.println("上传数据有误，或已置为空闲状态，无法设置报错信息");
		}
		
	}

	//模块启用时，删除之前所有的报错记录
	@Override
	public int delectModule_Errors(String id) {
		return resourcesMapper.deleteModule_ErrorsByModule_id(id);
	}


	@Override
	public void updateServerStateByModule_id(String id) {
		int server_id =resourcesMapper.getServer_idByModule_id(id);
		SchedulingServer server = resourcesMapper.querySingleServerById(server_id);
		Integer serverState = server.getServerState();
		serverState -=1;
		server.setServerState(serverState);
		resourcesMapper.updateServerById(server);
	}


}
