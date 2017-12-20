package com.xgd.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.Random;

import com.xgd.resources.entity.SchedulingServer;
import com.xgd.resources.utils.DateConvertUtils;

public class InsertDataTest {
	
	public static Connection getConn(){
		   Connection con = null;  //创建用于连接数据库的Connection对象    
	        try {    
	            Class.forName("com.mysql.jdbc.Driver");// 加载Mysql数据驱动    
	                
	            con = DriverManager.getConnection(    
	                    "jdbc:mysql://172.17.2.222:3306/ipresources", "root", "123456");// 创建数据连接    
	                
	        } catch (Exception e) {    
	            System.out.println("数据库连接失败" + e.getMessage());    
	        }    
	        return con; //返回所建立的数据库连接    
	    }    
	
	public static void main(String[] args) throws SQLException {
		SchedulingServer server = null;
		PreparedStatement stmt = null;
		Connection conn = null;
		for (int i = 0; i < 500; i++) {
			server = new SchedulingServer();
			if(i%2==0){
				server.setCycleTime(20);
			}else{
				server.setCycleTime(30);
			}
			if(i<200){
				server.setIpAndPort("172."+i+".22."+(i+1)+"-3"+(i/100)+i);
			}else{
				server.setIpAndPort(1+""+(i%100)+".2.33"+(i/100)+"."+(i-10)+"-100"+(i/100));
			}
			
			server.setServerName("测试服务器"+i);
			server.setLastEditTime(DateConvertUtils.dateToDatestamp(new Date()));
			server.setSetUpTime(DateConvertUtils.dateToDatestamp(new Date()));
			String sql = "INSERT INTO t_scheduling_server"
					+ "(serverName,ipAndPort,lastEditTime,cycleTime,setUpTime)values"
					+ "('"+server.getServerName()+"','"+server.getIpAndPort()+"','"+server.getLastEditTime()+"',"+server.getCycleTime()+",'"+server.getSetUpTime()+"')";
			try {
			conn = getConn();
				stmt = conn.prepareStatement(sql);
				stmt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
			      if(stmt!= null) 
			        stmt.close(); 		
			      if(conn!= null) 
			        conn.close(); 
			    }
	}
		System.out.println("导入成功！");
		
	}		
}
