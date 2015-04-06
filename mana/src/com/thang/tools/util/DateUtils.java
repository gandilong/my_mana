package com.thang.tools.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {

	public final static String YYYY_MM="yyyy-MM"; 
	public final static String YYYY_MM_DD="yyyy-MM-dd"; 
	public final static String YYYY_MM_DD_HH_mm_ss="yyyy-MM-dd HH:mm:ss"; 
	public final static String YYYY_MM_DD_HH_mm_ss_SS="yyyy-MM-dd HH:mm:ss SS"; 
	public final static SimpleDateFormat sdf=new SimpleDateFormat();
	
	public final static Calendar car=Calendar.getInstance();
	
	static {//设置时区为本地默认时区
		sdf.setTimeZone(TimeZone.getDefault());
		car.setTimeZone(TimeZone.getDefault());
	}
	
	/**
	 * 得到当前时间对象
	 * @return
	 */
	public static Date now(){
		return new Date();
	}
	
	/**
	 * 昨天的日期对象
	 * @return
	 */
	public static Date lastDay(){
		return agoDay(1);
	}
	
	/**
	 * 获取前指定天的时间对象
	 * @param daynum
	 * @return
	 */
	public static Date agoDay(int daynum){
        car.clear();
        car.add(Calendar.DATE, daynum);
        long subTime=car.getTimeInMillis();
        car.setTime(now());
        long nowTime=car.getTimeInMillis();
        car.setTimeInMillis(nowTime-subTime);
		return car.getTime();
	}
	
	/**
	 * 获得指写天数后的日期对象
	 * @param daynum
	 * @return
	 */
	public static Date afterDay(int daynum){
		car.clear();
	    car.add(Calendar.DATE, daynum);
	    long subTime=car.getTimeInMillis();
	    car.setTime(now());
	    long nowTime=car.getTimeInMillis();
	    car.setTimeInMillis(nowTime+subTime);
	    return car.getTime();
	}
	
	/**
	 * 返回YYYY-MM 为格式的时间字符
	 * @return String
	 */
	public static String getSysmonth(){
		sdf.applyPattern(YYYY_MM);
		return sdf.format(now());
	}
	
	/**
	 * 返回YYYY-MM-DD 为格式的时间字符
	 * @return String
	 */
	public static String getSysdate(){
		sdf.applyPattern(YYYY_MM_DD);
		return sdf.format(now());
	}
	
	/**
	 * 返回YYYY-MM-DD HH:mm:ss 为格式的时间字符
	 * @return String
	 */
	public static String getSystime(){
		sdf.applyPattern(YYYY_MM_DD_HH_mm_ss);
		return sdf.format(now());
	}
	
	/**
	 * 定制格式化日期字符串
	 * @param date
	 * @param format
	 * @return
	 */
	public static String dateToStr(Date date,String format){
		sdf.applyPattern(format);
		return sdf.format(date);
	}
}
