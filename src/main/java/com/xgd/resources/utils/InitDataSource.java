package com.xgd.resources.utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;

public class InitDataSource{
		private String driver;
		private String url;
		private String user;
		private String pwd;
		private Properties props;
		public void initParam(String paramFile)throws Exception{
			InputStream in = getInputStream(paramFile);
			try{
			props.load(in);
			driver = props.getProperty("jdbc.driverClassName");
			url = props.getProperty("jdbc.url");
			user = props.getProperty("jdbc.username");
			pwd = props.getProperty("jdbc.password");
			}catch(Exception e){
				System.out.println("读取配置文件时出错");
				e.printStackTrace();
			}
		}
		private InputStream getInputStream(String paramFile) {
			props= new Properties();
			InputStream in =InitDataSource.class.getClassLoader().getResourceAsStream(paramFile);
			return in;
		}
		public void insertData(String sql){
			try{
				Class.forName(driver);
				Connection conn = DriverManager.getConnection(url, user, pwd);
				Statement stmt = conn.createStatement();
				stmt.executeUpdate(sql);
			}catch(Exception e){
				System.out.println("添加数据失败");
				e.printStackTrace();
			}
		}
		//初始化读取配置文件到数据库。
		//用Replace保证tomcat启动时插入数据id不会更新，status设置为0，是表示初始状态为空闲，
		//时间戳timestamp取值范围1970-01-01 00:00:00到2037-12-31 00:00:00，这是百度出的官方取值，初始值取值2011-11-11 00:00:00，初始值千年一遇，具有纪念意义
		//检验码checkCode设置初始值：00000000
		public void initConf(String confFile) throws Exception{
			InputStream in = getInputStream(confFile);
			props.load(in);
			//读取ip和端口信息配置文件，并插入数据 到数据库
			String IpAndPort1 =props.getProperty("IpAndPort1");
			String IpAndPort2 =props.getProperty("IpAndPort2");
			String IpAndPort3 =props.getProperty("IpAndPort3");
			String IpAndPort4 =props.getProperty("IpAndPort4");
			String IpAndPort5 =props.getProperty("IpAndPort5");
			String IpAndPort6 =props.getProperty("IpAndPort6");
			String sql ="replace into t_ipresources (id,IpAndPort,status,startTime,checkCode)values"
					+ "(1,'"+IpAndPort1+"',0,'2011-11-11 00:00:00','00000000'),"
					+ "(2,'"+IpAndPort2+"',0,'2011-11-11 00:00:00','00000000'),"
					+ "(3,'"+IpAndPort3+"',0,'2011-11-11 00:00:00','00000000'),"
					+ "(4,'"+IpAndPort4+"',0,'2011-11-11 00:00:00','00000000'),"
					+ "(5,'"+IpAndPort5+"',0,'2011-11-11 00:00:00','00000000'),"
					+ "(6,'"+IpAndPort6+"',0,'2011-11-11 00:00:00','00000000')";
			insertData(sql);
		}
		public static void main(String[] args) throws Exception{
			InitDataSource init = new InitDataSource();
			init.initParam("src/main/resources/conf/jdbc.properties");
			//String sql="replace into t_ipresources (id,IpAndPort,status,startTime,checkCode)values(27,'172.17.1.201-8888',0,'1970-02-02 00:00:00','12345678')";
			//init.insertData(sql);
			init.initConf("src/main/resources/conf/IpResource.properties");
			System.out.println("添加成功");
		}
	}

