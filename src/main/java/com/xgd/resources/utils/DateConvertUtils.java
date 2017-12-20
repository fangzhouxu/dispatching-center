package com.xgd.resources.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期转换工具类
 * */
public class DateConvertUtils {


	public static Timestamp StringToDatestamp(String strTime){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try{
			date=df.parse(strTime);
		}catch(ParseException e){
			e.printStackTrace();
		}
		return dateToDatestamp(date);
	}
	
	public static Timestamp dateToDatestamp(Date date){
		long  longTime = date.getTime();
		return new Timestamp(longTime);
	}
	
	public static String getStringDate(Date date) {
		   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		   String dateString = formatter.format(date);
		   return dateString;
		}

}
