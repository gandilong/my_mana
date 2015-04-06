/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thang.tools.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.thang.tools.util.JsonUtils;

/**
 * 用于参数传递
 * @author gandilong
 */
public class ActionValues extends HashMap<String,Object>{
    
    private static final long serialVersionUID=1L;
    
    private boolean page=false;//默认不分页
    private boolean sort=false;//默认不排序
    
    public final static String SORT="sort";//排序字段
    public final static String ORDER="order";//排序升降属性:asc|desc
    
    public final static String PAGE_NOW="pageNow";//当前页号
    public final static String PAGE_NUM="pageNum";//总页数
    public final static String PAGE_SIZE="pageSize";//每页记录数
    public final static String TOTAL="total";//总记录数
    		
    
    public ActionValues(){
    	super();
    }
    
    /**
     * 高定容量
     * @param size
     */
    public ActionValues(int size){
    	super(size);
    }
    
    public ActionValues(Map<String,Object> values){
    	super();
        this.putAll(values);
    }
    
   	public ActionValues(HttpServletRequest request){
        super(sizeParams(request)+7);  		
   		String name=null;
   		Enumeration<String> paramNames=request.getParameterNames();
   		Enumeration<String> attrNames=request.getAttributeNames();
   		
   		while(paramNames.hasMoreElements()){
   			name=paramNames.nextElement();
   			if("user.FILTERED".equals(name)){//过滤无效参数
   				continue;
   			}
   			if(request.getParameterValues(name).length>1){
   				put(name, request.getParameterValues(name));
   			}else{
   				//过滤空值
   				if(null!=request.getParameter(name)&&!"".equals(request.getParameter(name).trim())){
   					put(name, request.getParameter(name));	
   				}
   			}
   		}
   		
   		while(attrNames.hasMoreElements()){
   			name=attrNames.nextElement();
   			if("user.FILTERED".equals(name)){//过滤无效参数
   				continue;
   			}
   			if(null!=request.getAttribute(name)&&!"".equals(String.valueOf(request.getAttribute(name)).trim())){
   				put(name,request.getAttribute(name));	
   			}
   		}
   		
   	}
    
    public void add(String key,Object obj){
    	this.put(key, obj);
    }
    
    /**
     * 获得字符串
     * @param key
     * @return
     */
    public String getStr(String key){
        if(null!=get(key)){
            return String.valueOf(get(key));
        }
        return null;
    }
    
    /**
     * 获得整型数字
     * @param key
     * @return
     */
    public int getInt(String key){
    	if(isNotEmpty(key)){
    		return Integer.parseInt(String.valueOf(get(key)));
    	}
    	return 0;
    }
    
    /**
     * 获取长整型数字值
     * @param key
     * @return
     */
    public long getLong(String key){
    	if(isNotEmpty(key)){
    		return Long.parseLong(String.valueOf(get(key)));
    	}
    	return 0;
    }
    
    /**
     * 获取单精度数字值
     * @param key
     * @return
     */
    public float getFloat(String key){
    	if(isNotEmpty(key)){
    		return Float.parseFloat(String.valueOf(get(key)));
    	}
    	return 0.0F;
    }
    
    /**
     * 获取双精度数字值 常用于金钱计算
     * @param key
     * @return
     */
    public double getDouble(String key){
    	if(isNotEmpty(key)){
    		return Double.parseDouble(String.valueOf(get(key)));
    	}
    	return 0.0;
    }
    
    /**
     * 获取大数字
     * @param key
     * @return
     */
    public BigDecimal getBigDecimal(String key){
    	if(isNotEmpty(key)){
    		return new BigDecimal(getStr(key));
    	}
    	return BigDecimal.ZERO;
    }
    
    public boolean isNotEmpty(String key){
        Object obj=get(key);
        if(null!=obj&&!"".equals(String.valueOf(obj).trim())){
             return true;
        }
        return false;
    }
    
    /**
     * 判断是否存在
     * @param key
     * @return
     */
    public boolean isNotNull(String key){
        if(null!=get(key)){
            return true;
        }
        return false;
    }

    /**
     * 过滤空字符串,把所有key对应的值为空的或""的，移除掉。
     */
    public void filterEmpty(){
    	Iterator<String> keys=keySet().iterator();
    	String key=null;
    	List<String> keys_str=new ArrayList<String>();
    	while(keys.hasNext()){
    		key=keys.next();
    		if(!isNotEmpty(key)){
    			keys_str.add(key);
    		}
    	}
    	
    	for(String k:keys_str){
    		remove(k);
    	}
    	
    }
    
    public void setTotal(long total){
         add(ActionValues.TOTAL,total);
         int pageSize=getInt(ActionValues.PAGE_SIZE)==-1?30:getInt(ActionValues.PAGE_SIZE);
         add(ActionValues.PAGE_NUM,(total/pageSize)+(total%pageSize==0?0:1));
    }
    
    /**
     * 判断是否分页 true分页   false不分页
     * @return
     */
    public boolean isPage(){
    	return page;
    }
    
    /**
     * 判断是否排序  true排序  false不排序
     * @return
     */
    public boolean isSort(){
    	return sort;
    }

    /**
     * 打开分页功能，如果开启分页，默认也进行排序
     */
	public ActionValues openPage() {
		page=true;
		return this;
	}
	
	/**
     * 打开分页功能，并设置当前页和每页数据条数
     */
	public ActionValues openPage(int pageNow,int pageSize) {
		page=true;
		add(PAGE_NOW, pageNow<=0?1:pageNow);
		add(PAGE_SIZE,pageSize<=0?30:pageSize);
		return this;
	}
	
	/**
     * 关闭分页功能
     */
	public void offPage(){
		page=false;
	}
	
	/**
	 * 关闭排序
	 */
	public void offSort(){
		sort=false;
	}
	
	/**
	 * 打开排序并按sort升序排序
	 * @param sort 排序字段
	 */
	public ActionValues ascBy(String sort){
		this.sort=true;
		this.put(ActionValues.SORT, sort);
		this.put(ActionValues.ORDER, "asc");
		return this;
	}
	
	/**
	 * 打开排序并按sort降序排序
	 * @param sort
	 */
	public ActionValues descBy(String sort){
		this.sort=true;
		this.put(ActionValues.SORT, sort);
		this.put(ActionValues.ORDER, "desc");
		return this;
	}
	
	/**
	 * 计算Enumeration长度
	 * @param params
	 * @return
	 */
	private static int sizeParams(HttpServletRequest request){
		Enumeration<String> paramNames=request.getParameterNames();
   		Enumeration<String> attrNames=request.getAttributeNames();
		int size=0;
		if(null!=paramNames){
			while(paramNames.hasMoreElements()){
				paramNames.nextElement();
				size++;
			}
		}
		if(null!=attrNames){
			while(attrNames.hasMoreElements()){
				attrNames.nextElement();
				size++;
			}
		}
		return size;
	}
	
	@Override
	public String toString() {
		return JsonUtils.toJsonStr(this);
	}
}
