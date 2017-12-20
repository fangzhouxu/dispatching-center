package com.xgd.resources.controller;

import java.util.HashMap;



import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xgd.resources.service.UserService;

@Controller
@RequestMapping(value ="/user",method=RequestMethod.POST)
public class UserController extends BaseController{
	private  static final Logger loger = Logger.getLogger(UserController.class);  
	@Autowired
	private UserService userService;
	
	@RequestMapping("/login")
	@ResponseBody
	public HashMap<String, Object> login(@RequestParam("data")String userData){
		System.out.println(userData);
		Subject currentUser = SecurityUtils.getSubject();
		//UsernamePasswordToken token = new UsernamePasswordToken(loginName, loginPassword);
		HashMap<String, Object> result = this.getHashMap();
		loger.info("request:/user/login:  "+userData);
		result.put("result", 1);
		return result;
	}
}
