package com.xgd.resources.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.xgd.resources.service.IpAndPortResourcesService;


public class IpAndPortResourcesJob implements Job{
	
	@Autowired
	private IpAndPortResourcesService ipAndPortResourcesService;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		//System.out.println("调度任务开始执行啦。。。。。。。。。。");
		ipAndPortResourcesService.updateResourcesStatusByTimeOut();
	}

}
