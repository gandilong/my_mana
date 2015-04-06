package com.thang.tools.util;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 
 * @author zyt
 * 2015年4月3日下午9:59:28
 */
public class JsonUtils {

	private static final Gson gson=new Gson();
	
	/**
	 * 把一个实体类对象转换成json字符串
	 * @param obj
	 * @return
	 */
	public static String toJsonStr(Object obj){
		return gson.toJson(obj);
	}
	
	/**
	 * 封装一个实体类字符串到一个Json对象中
	 * @param name
	 * @param obj
	 * @return
	 */
	public static String toJsonStr(String name,Object obj){
		return "{"+name+":"+toJsonStr(obj)+"}";
	}
	
	/**
	 * 解析json字符串到实体对象中
	 * @param json
	 * @param cls
	 * @return
	 */
	public static <T>T parseJson(String json,Class<T> cls){
		return gson.fromJson(json, cls);
	}
	
	/**
	 * 批量转换json字符串为list实体类
	 * @param json
	 * @param cls
	 * @return
	 */
	public static <T>List<T> parseJsons(String json,Class<T> cls){
		return gson.fromJson(json, new TypeToken<List<T>>() {}.getType());
	}
	
}
