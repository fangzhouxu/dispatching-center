package com.xgd.resources.controller;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
@RequestMapping(value="/upload",method= RequestMethod.POST,produces="application/json;charset=UTF-8")
public class UploadFileController {
	
	@RequestMapping(value="/importExcelFile")
	@ResponseBody
	public String importExcel() throws Exception{
//		MultipartHttpServletRequest multipartRequest =(MultipartHttpServletRequest) request; 
//		MultipartFile file = multipartRequest.getFile("file1");
//		if(file.isEmpty()){
//			throw new Exception("文件不存在");
//		}
//		String filename = file.getOriginalFilename();
//		System.out.println(filename);
		return null;
	}
}
