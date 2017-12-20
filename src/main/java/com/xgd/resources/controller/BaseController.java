package com.xgd.resources.controller;

import java.util.HashMap;

import org.springframework.stereotype.Controller;


public class BaseController {
	public HashMap<String,Object> getHashMap(){
		return new HashMap<String,Object>();
	}
	
	/**
	 * 重定向至地址 url
	 * @param url 请求地址
	 * @return
	 */
	protected String redirectTo(String url) {
		StringBuilder rto = new StringBuilder("redirect:");
		rto.append(url);
		return rto.toString();
	}
}
