package com.xgd.resources.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xgd.resources.entity.PageBean;
import com.xgd.resources.entity.SchedulingModule;
import com.xgd.resources.entity.SchedulingServer;
import com.xgd.resources.service.IpAndPortResourcesService;
import com.xgd.resources.service.SysLogService;
import com.xgd.resources.utils.DateConvertUtils;
import com.xgd.resources.utils.FastJsonUtils;

@Controller
@RequestMapping(value="/Resource", produces = "application/json;charset=UTF-8")
public class IpAndPortResourcesController extends BaseController{
	
	private static final Logger logger = Logger.getLogger(IpAndPortResourcesController.class);
	@Autowired
	private IpAndPortResourcesService ipAndPortResourcesService;

	@ResponseBody
	@RequestMapping(value="/getResource", method = RequestMethod.POST)
	public String handlerIpAndPortResource(HttpServletRequest request, @RequestBody String str) {
		long start = new Date().getTime();
		//System.out.println("请求的ip地址：" + );
		//System.out.println("请求json数据为:" + str);
		Map<String, Object> result = this.getHashMap();
		Map<String, String> requestParam = (Map<String, String>) FastJsonUtils.stringToMap(str);
		if (requestParam != null && requestParam.size() >= 3) {
			String checkCode = requestParam.get("checkCode");
			String sipAndPort = requestParam.get("sipAndPort");
			// 初步校验校验码合法性
			if (checkCode != null && checkCode.length() != 8|| checkCode.equals("00000000")) {
				System.out.println("请求校验码无效，请重试！");
			} else {
				SchedulingModule module = new SchedulingModule();
				module.setCheckCode(checkCode);
				SchedulingServer server = new SchedulingServer();
				server.setIpAndPort(sipAndPort);
				module.setServer(server);
				// 1.请求解析的Ip和端口
				if (requestParam.get("req").equals("01")) {
					// 根据checkCode查询，是否checkcode已存在，存在就直接返回空数据
					String ipAndPort = null;
					ipAndPort = ipAndPortResourcesService.checkValidation(module);
					if (ipAndPort != null) {
						System.out.println("发送的校验码已存在，无法请求空闲");
						return null;
					}
					ipAndPort = ipAndPortResourcesService.getFreeIpAndPort(module);
					logger.info("请求空闲Ip和端口耗时：" + (new Date().getTime() - start) + "ms");
					logger.info("请求空闲的Ip和端口是："+ipAndPort);
					if(ipAndPort!=null&&isNumeric(ipAndPort)){
						result.put("ipAndPort", ipAndPort);
						result.put("success", true);
						return FastJsonUtils.collectToString(result);
					}
					// 2.上报解析完成
				} else if (requestParam.get("req").equals("03")) {
					String error = requestParam.get("error");
					if(error!=null&&error.trim().isEmpty()){
						ipAndPortResourcesService.updateResourcesStatusByFinshed(module);
						logger.info("正常上报信息重置Ip端口状态后台耗时：" + (new Date().getTime() - start) + "ms");
					}else{
						//添加报错信息
						String posip = request.getRemoteAddr();
						logger.info("上报错误的ip是："+posip);
						ipAndPortResourcesService.createModuleError(error,checkCode,sipAndPort,posip);
					}
					return null;
				}
			}
		}
		result.put("data", "请求数据错误");
		result.put("success", false);
		return FastJsonUtils.collectToString(result);
	}


	@ResponseBody
	@RequestMapping(value="/createServer", method = RequestMethod.POST)
	public HashMap<String,Object> createServer( String data) {
			HashMap<String,Object> result = this.getHashMap();
		try{
			SchedulingServer server = FastJsonUtils.toBean(data, SchedulingServer.class);
			if(null==server.getId()){
				ipAndPortResourcesService.createServer(server);
			}else{
				ipAndPortResourcesService.updateServer(server);
			}
			result.put("success", true);
		}catch(Exception e){
			result.put("success", false);
			logger.info("添加或编辑服务器失败");
			e.printStackTrace();
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="/getServerData",method = RequestMethod.POST)
	public HashMap<String,Object> getAllServerByPage(@RequestParam(defaultValue= "1") Integer page, Integer rows,String dataStr){
		System.out.println("dataStr:"+dataStr);
		PageBean<SchedulingServer> serversPage = ipAndPortResourcesService.getServerEntitiesByPage(page,rows,dataStr);
		List<HashMap<String, Object>> jsonList = new ArrayList<>();
		HashMap<String, Object> jsonData = null;
		for (SchedulingServer schedulingServer : serversPage.getPageInfo()) {
			jsonData = this.getHashMap();
			jsonData.put("serverName", schedulingServer.getServerName());
			jsonData.put("ipAndPort", schedulingServer.getIpAndPort());
			//对日期进行字符串处理，返回给前端
			jsonData.put("lastEditTime", DateConvertUtils.getStringDate(schedulingServer.getLastEditTime()));
			jsonData.put("setUpTime", DateConvertUtils.getStringDate(schedulingServer.getSetUpTime()));
			jsonData.put("cycleTime", schedulingServer.getCycleTime());
			//对日期进行字符串处理，返回给前端
			jsonData.put("lastCheckTime", DateConvertUtils.getStringDate(schedulingServer.getLastCheckTime()));
			jsonData.put("serverState", schedulingServer.getServerState());
			jsonData.put("id", schedulingServer.getId());
			jsonList.add(jsonData);
		}
		HashMap<String, Object> result = this.getHashMap();
		result.put("rows", jsonList);
		result.put("total",serversPage.getTotalRecords());
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="/deleteServers", method = RequestMethod.POST)
	public String DeleteServersByIds(String ids){
		HashMap<String,Object> result =this.getHashMap();
		try{
			if(ids!=null&&!ids.trim().isEmpty()){
				ipAndPortResourcesService.deleteOfBatch(ids);
			}
			result.put("success", true);
		}catch(Exception e){
			result.put("success", false);
			e.printStackTrace();
		}
		return FastJsonUtils.toJSONString(result);
	}
	
	@ResponseBody
	@RequestMapping(value="/addModules",method=RequestMethod.POST)
	public String addModules(String modulesStr){
		HashMap<String, Object> result = this.getHashMap();
		try{
			//System.out.println("modulesStr: "+modulesStr);
			Map<String,Object> moduleMap =(Map<String,Object>) FastJsonUtils.stringToMap(modulesStr);
			//System.out.println(moduleMap); ;
			List<String> moduleNamelist = (List<String>) moduleMap.get("moduleName");
			List<String> ipAndPortlist = (List<String>) moduleMap.get("ipAndPort");
			String sid = (String) moduleMap.get("sid");
			Integer id = Integer.parseInt(sid);
			//根据id查询物理服务器
			SchedulingServer server =ipAndPortResourcesService.getSingleServerEntityById(id);
			ArrayList<SchedulingModule> modules = new ArrayList<>();
			if(moduleNamelist!=null&&(moduleNamelist.size()==6)&&ipAndPortlist!=null&&(moduleNamelist.size()==6)){
				for (int i = 0; i < 6; i++) {
					SchedulingModule module = new SchedulingModule();
					module.setModuleName(moduleNamelist.get(i));
					module.setIpAndPort(ipAndPortlist.get(i));
					module.setServer(server);
					modules.add(module);
				}
			}
			ipAndPortResourcesService.addModules(modules);
			result.put("success", true);
				
		}catch(Exception e){
			result.put("success", false);
			e.printStackTrace();
		}
	
		return FastJsonUtils.toJSONString(result);
	}
	
	@ResponseBody
	@RequestMapping(value="/getAllModules",method=RequestMethod.POST)
	public List<SchedulingModule> getAllModules(Integer sid){
		try{
			List<SchedulingModule> Modules = ipAndPortResourcesService.getAllModulesDetails(sid);
			//为了将sid数据再次传回页面
			return Modules;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/updateModuleState",method=RequestMethod.POST)
	public String updateModuleState(String jsonStr,String key){
		//System.out.println(jsonStr);
		HashMap<String,Object> result =this.getHashMap();
		if(jsonStr!=null&&jsonStr.contains("-")){
			try{
			SchedulingModule module = new SchedulingModule();
			String[] strings = jsonStr.split("-");
			String id = strings[0];
			Integer statePro = Integer.parseInt(strings[1]);
			Integer switchKey = Integer.parseInt(key);
			if(switchKey==0){
				//表示执行停用操作，同时之前状态值只可能是0或2
				if(statePro==0){
					module.setState(1);
				}else if(statePro==2){
					module.setState(3);
				}
				module.setSwitchKey(0);
			}else if(switchKey==1){
				//表示执行启用操作，同时之前的状态值只能是1或3
				if(statePro==1){
					module.setState(0);
				}else if(statePro==3){
					module.setState(0);
					module.setErrorTimes(0);
					//同时删除所有的报错记录,改变物理服务器的健康状态
					int row =ipAndPortResourcesService.delectModule_Errors(id);
					if(row >0){
						ipAndPortResourcesService.updateServerStateByModule_id(id);
					}
				}
				module.setSwitchKey(1);
			}
			module.setId(id);
			//System.out.println("服务器改变为：state="+module.getState()+",switchKey="+module.getSwitchKey());
			ipAndPortResourcesService.updateModuleState(module);
			result.put("success", true);
			}catch(Exception e){
				result.put("success", false);
				e.printStackTrace();
			}
		}else{
			result.put("success", false);
		}
		return FastJsonUtils.toJSONString(result);
	}
	
	@ResponseBody
	@RequestMapping(value="/editModule",method=RequestMethod.POST)
	public String editModule(String jsonData){
		HashMap<String,Object> result =this.getHashMap();
		try{
			SchedulingModule module = FastJsonUtils.toBean(jsonData, SchedulingModule.class);
			ipAndPortResourcesService.editModule(module);
			result.put("success", true);
		}catch(Exception e){
			result.put("success", false);
			logger.info("编辑模块失败");
			e.printStackTrace();
		}
		return FastJsonUtils.toJSONString(result);
	}
	
	private  boolean isNumeric(String str){
		   String subStr=str.replaceAll("-", "").replace(".", "");
		   Pattern pattern = Pattern.compile("[0-9]*"); 
		   Matcher isNum = pattern.matcher(subStr);
		   if( !isNum.matches() ){
		       return false; 
		   } 
		   return true; 
		}
}
