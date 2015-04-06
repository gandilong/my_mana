package com.thang.tools.model;

import java.sql.Connection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.log4j.Logger;

/**
 * 对queryRunner进行封装以便能进行 :name,value 格式的应用。
 * @author zyt
 *
 */
public class DBHandler extends QueryRunner {

	private static Logger logger=Logger.getLogger(DBHandler.class);
	private ResultHandler resultHandler=new ResultHandler();
	
	public DBHandler(){
		super();
	}
	
	public DBHandler(boolean pmdKnownBroken){
		super(pmdKnownBroken);
	}
	
	public DBHandler(DataSource dataSource){
		super(dataSource);
	}
	
	public DBHandler(DataSource dataSource,boolean pmdKnownBroken){
		super(dataSource,pmdKnownBroken);
	}
	
	/**
	 * 执行一条记录
	 * @param sql
	 * @param params
	 * @return
	 */
	public int updateSQL(String sql,Map<String,Object> params){
		try{
		    return update(mapParams(sql,params));
		}catch(Exception e){
			logger.error("执行SQL出错！");
			logger.error(e.getMessage(), e.getCause());
		}
		return 0;
	}
	
	/**
	 * 指定Connection对象，可以加入事物
	 * @param conn
	 * @param sql
	 * @param params
	 * @return
	 */
	public int updateSQL(Connection conn,String sql,Map<String,Object> params){
		try{
			return update(conn,mapParams(sql,params));
		}catch(Exception e){
			logger.error("执行SQL出错！");
			logger.error(e.getMessage(), e.getCause());
		}
		return 0;
	}
	
	/**
	 * 查询多条记录
	 * @param sql
	 * @param params
	 * @return
	 */
	public List<ResultValues> selectSQL(String sql,Map<String,Object> params){
		try{
			return query(mapParams(sql,params), resultHandler);
		}catch(Exception e){
			logger.error("执行查询SQL出错！");
			logger.error(e.getMessage(), e.getCause());
		}
		return null;
	}
	
	/**
	 * 指定Connection对象，可以加入事物
	 * @param conn
	 * @param sql
	 * @param params
	 * @return
	 */
	public List<ResultValues> querySQL(Connection conn,String sql,Map<String,Object> params){
		try{
			return query(conn,mapParams(sql,params),resultHandler);
		}catch(Exception e){
			logger.error("执行SQL出错！");
			logger.error(e.getMessage(), e.getCause());
		}
		return null;
	}
	
	/**
	 * 设置参数  可以使用:name...,value...的形式
	 * @param sql
	 * @param params
	 * @return
	 */
	private String mapParams(String sql,Map<String,Object> params){
		try{
			if(null==params||0==params.size()){
				logger.debug("\n\t"+sql);
				return sql;
			}
			StringBuffer sber=new StringBuffer(sql);
			Set<String> keys=params.keySet();
			Iterator<String> it=keys.iterator();
			StringBuffer replace=new StringBuffer();
			String key=null;
			int len=0;
			int i=0;
			while(it.hasNext()){
				key=it.next();
				replace.append(":").append(key);//组合成:key形式
				len=replace.length();
				i=sber.indexOf(replace.toString());//锁定:key的位置
				
				while(-1!=i){
				    Object obj=params.get(key);
				
				    if(obj instanceof Integer || obj instanceof Long){
					    sber.replace(i, i+len,String.valueOf(obj));	
				    }else if(obj instanceof String){
					    sber.replace(i, i+len, "'"+String.valueOf(obj)+"'");
				    }else{
					    sber.replace(i, i+len, String.valueOf(obj));
				    }
				    i=sber.indexOf(replace.toString());//再次锁定:key的位置，以此来判断是否还有:key存在
				}
				replace.delete(0, len);//清除本次的:key形式值
			}
			logger.debug("\n\t"+sber.toString());
			return sber.toString();
		}catch(Exception e){
			logger.error("执行SQL出错！");
			logger.error(e.getMessage(), e.getCause());
		}
		return null;
	}
}
