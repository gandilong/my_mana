/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thang.tools.model;

import java.util.HashMap;
import java.util.Iterator;

import com.thang.tools.util.JsonUtils;


/**
 *
 * @author gandilong
 */
public class ResultValues extends HashMap<String,Object>{
    
    private static final long serialVersionUID=1L;
    
    
    public void add(String key,Object obj){
    	this.put(key, obj);
    }
    /**
     * 获得字符串形式的值
     * @param key
     * @return
     */
    public String getStr(String key){
        if(null!=get(key)){
            return String.valueOf(get(key));
        }
        return null;
    }
    
    public int getInt(String key){
    	if(isNotEmpty(key)){
    		return Integer.parseInt(String.valueOf(get(key)));
    	}
    	return -1;
    }
    
    public long getLong(String key){
    	if(isNotEmpty(key)){
    		return Long.parseLong(String.valueOf(get(key)));
    	}
    	return -1;
    }
    
    /*public <T>T getBean(Class<T> cls){
    	T t=null;
    	try {
			t=cls.newInstance();
			BeanUtils.copyProperties(t, this);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
        
    	return t;
    }*/
    
    public boolean isNotEmpty(String key){
        Object obj=get(key);
        if(null!=obj&&!"".equals(String.valueOf(obj).trim())){
             return true;
        }
        return false;
    }
    
    /**
     * 用自己的属性和值生成一个实体对象实例，
     * @param t
     * @return
     
    public <T>T covertToBean(Class<T> t){
    	T obj=null;
    	try{
    	    obj=t.newInstance();
    	    Field[] fields=t.getDeclaredFields();
    	    for(Field field:fields){
    	    	BeanUtils.setProperty(obj, field.getName(),getStr(StrUtils.dropUnderline(field.getName())));	
    	    }
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return obj;
    }*/
    
    /**
     * 把所有Key去掉下划线,转成驼峰式
     */
    public void formatKey(){
        ResultValues result=null;
        if(size()>0){
            result=new ResultValues();
            Iterator<String> it=keySet().iterator();
            String key=null;
            while(it.hasNext()){
                key=it.next();
                result.put(convertToPropertyName(key),get(key));
            }
            clear();
            putAll(result);
        }
    }
    
    /**
     * 把所有Key转成小写
     */
    public void lowerCaseKey(){
        ResultValues result=null;
        if(size()>0){
            result=new ResultValues();
            Iterator<String> it=keySet().iterator();
            String key=null;
            while(it.hasNext()){
                key=it.next();
                result.put(key.toLowerCase(),get(key));
            }
            clear();
            putAll(result);
        }
    }
    
    /**
     * 去掉下划线
     * @param name
     * @return
     */
    public String convertToPropertyName(String name){
        name=name.toLowerCase();
        String[] str=name.split("\\_");
        int size=str.length;
        for(int i=0;i<size;i++){
            if(0==i){
                name=str[i];
            }else{
                name=name+upperFirstCase(str[i]);
            }
        }
        return name;
    }
    
    /**
     * 首字母转大写
     * @param str
     * @return
     */
    private String upperFirstCase(String str){
        if(null==str||"".equals(str)){
            return "";
        }
        return str.substring(0,1).toUpperCase()+str.substring(1);
    }
    
    @Override
	public String toString() {
		return JsonUtils.toJsonStr(this);
	}
    
}
