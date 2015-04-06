package com.thang.tools.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.thang.tools.model.ActionValues;
import com.thang.tools.model.DBHandler;
import com.thang.tools.model.ResultValues;
import com.thang.tools.util.ConfigUtils;

/**
 * 封装了所有直接执行SQL的简易方法，不包括事物。
 * @author zyt
 *
 */
public class Dao {

	private static DataSource dataSource=null;
	private DBHandler dbHandler=null;
	private static Logger logger=Logger.getLogger(Dao.class);
	
	public Dao(){
		if(null==dataSource){
			logger.debug("自动装配数据源...");
			Dao.dataSource=ConfigUtils.getDataSource();
			dbHandler=new DBHandler(Dao.dataSource);
		}
	}
	
	/**
	 * 手动装栩数据源，但不会设置成默认数据源
	 * @param ds
	 */
	public Dao(DataSource ds){
		if(null==ds){
			logger.debug("手动装配数据源...");
			dbHandler=new DBHandler(Dao.dataSource);
		}
	}
	
	/**
	 * 手动装配数据源，可以设置成默认数据源
	 * @param ds
	 */
	public Dao(DataSource ds,boolean isDefault){
		if(null==ds){
			logger.debug("手动装配数据源...");
			if(isDefault){
				Dao.dataSource=ConfigUtils.getDataSource();
			}
			dbHandler=new DBHandler(Dao.dataSource);
		}
	}
	
	
	/**
	 * 最简单的执行方法，可以写inser,update,delete
	 * @param sql
	 * @return 1 表示成功
	 */
	public int execute(String sql){
		try{
			logger.debug(sql);
			return getDBHandler().update(sql);
		}catch(Exception e){
			logger.error("执行SQL出错！");
			logger.error(sql);
			logger.error(e.getMessage(),e.getCause());
		}
		return 0;
	}
	
	/**
	 * 可以进行insert,update,delete操作
	 * @param sql
	 * @param params
	 * @return 1 表示成功
	 */
	public int updateSQL(String sql,Map<String,Object> params){
		try{
			return getDBHandler().updateSQL(sql,params);
		}catch(Exception e){
			logger.error("update方法执行SQL出错！");
			logger.error(sql);
			logger.error(e.getMessage(),e.getCause());
		}
		return 0;
	}
	
	/**
	 * 可以进行insert,update,delete操作
	 * @param sql
	 * @param params
	 * @return 1 表示成功
	 */
	public int updateSQL(Connection conn,String sql,Map<String,Object> params){
		try{
			return getDBHandler().updateSQL(conn,sql,params);
		}catch(Exception e){
			logger.error("update方法执行SQL出错！");
			logger.error(sql);
			logger.error(e.getMessage(),e.getCause());
		}
		return 0;
	}
	
	/**
	 * 得到一条记录
	 * @param sql
	 * @param params
	 * @return
	 */
	public ResultValues getSQL(String sql,Map<String,Object> params){
		try{
			List<ResultValues> rs=getDBHandler().selectSQL(sql, params);
			if(null!=rs&&rs.size()>0){
				return rs.get(0);
			}
		}catch(Exception e){
			logger.error("get方法执行SQL出错！");
			logger.error(sql);
			logger.error(e.getMessage(),e.getCause());
		}
		return null;
	}
	
	/**
	 * 得到一条记录
	 * @param sql
	 * @param params
	 * @return
	 */
	public ResultValues getSQL(Connection conn,String sql,Map<String,Object> params){
		try{
			List<ResultValues> rs=getDBHandler().selectSQL(sql, params);
			if(null!=rs&&rs.size()>0){
				return rs.get(0);
			}
		}catch(Exception e){
			logger.error("get方法执行SQL出错！");
			logger.error(sql);
			logger.error(e.getMessage(),e.getCause());
		}
		return null;
	}
	
	/**
	 * 查询多条记录
	 * @param sql
	 * @param params
	 * @return
	 */
	public List<ResultValues> listSQL(String sql,ActionValues values){
		try{
			return getDBHandler().selectSQL(forPage(sql,values),values);
		}catch(Exception e){
			logger.error("list方法执行SQL出错！");
			logger.error(sql);
			logger.error(e.getMessage(),e.getCause());
		}
		return null;
	}
	
	/**
	 * 查询多条记录
	 * @param sql
	 * @param params
	 * @return
	 */
	public List<ResultValues> listSQL(Connection conn,String sql,ActionValues values){
		try{
			return getDBHandler().querySQL(conn,forPage(sql,values),values);
		}catch(Exception e){
			logger.error("list方法执行SQL出错！");
			logger.error(sql);
			logger.error(e.getMessage(),e.getCause());
		}
		return null;
	}
	
	/**
	 * 进行分页SQL处理
	 * @param sql
	 * @param values
	 * @return
	 */
	private String forPage(String sql,ActionValues values)throws Exception{
		StringBuilder sber=new StringBuilder(sql);
		String database=ConfigUtils.getConfig("database");
		int pageNow=1;
        int pageSize=30;
        if(0==values.getInt(ActionValues.PAGE_NOW)){
        	values.add(ActionValues.PAGE_NOW, pageNow);
        }else{
        	pageNow=values.getInt(ActionValues.PAGE_NOW);
        }
        
        if(0==values.getInt(ActionValues.PAGE_SIZE)){
        	values.add(ActionValues.PAGE_SIZE,pageSize);
        }else{
        	pageSize=values.getInt(ActionValues.PAGE_SIZE);
        }
		
		if("mysql".equalsIgnoreCase(database)){
			if(values.isSort()){
				sber.append(" order by ").append(values.getStr(ActionValues.SORT)).append(" ").append(values.getStr(ActionValues.ORDER)==null?"asc":values.getStr(ActionValues.ORDER)).append(" ");
			}
			if(values.isPage()){
				sber.append(" limit ").append((pageNow<=1?0:(pageNow-1))*pageSize).append(",").append(pageSize);
			}	
		}else if("mssql".equalsIgnoreCase(database)){
			if(values.isSort()){
				int fromIndex=sber.indexOf("from");
				if(-1==fromIndex){
					fromIndex=sber.indexOf("FROM");
				}
				if(-1==fromIndex){
					fromIndex=sber.indexOf("From");
				}
				if(-1==fromIndex){
					throw new Exception("不合法的SQL语句,没找到from！请用全小写的from或全大写的FROM");
				}
				sber.insert(fromIndex-1,"  ,row_number() over(order by "+values.getStr(ActionValues.SORT)+" "+(values.getStr(ActionValues.ORDER)==null?"asc":values.getStr(ActionValues.ORDER))+") rn  ");
			}
			if(values.isPage()){
				sber.insert(0, "select * from (  ").append(" ) m where  m.rn between "+((pageNow<=1?0:(pageNow-1))*pageSize)+" and "+((pageNow<=0?1:pageNow)*pageSize));
			}
		}else if("oracle".equalsIgnoreCase(database)){
			sber.insert(0,"select * from (select tp.*, rownum row_id from (");  
	        if(values.isSort()){
	        	if(values.isNotEmpty("sort")){
	            	sber.append(" order by ");
	            	sber.append(values.getStr(ActionValues.SORT)).append(" ").append(values.getStr(ActionValues.ORDER)==null?"asc":values.getStr(ActionValues.ORDER));
	            }
	        }
	        
	        sber.append(") tp where rownum<=").append(pageNow*pageSize);  
	        sber.append(") where row_id>").append((pageNow==1?0:pageNow-1)*pageSize); 
		}else{
			
		}
		
		return sber.toString();
	}
	
	/**
	 * 得到DBUtils操作类
	 * @return
	 */
	protected DBHandler getDBHandler(){
		if(null==dbHandler){
			if(null==Dao.dataSource){
				Dao.dataSource=ConfigUtils.getDataSource();
			}
			dbHandler=new DBHandler(dataSource);
		}
		return dbHandler;
	}
	
	/**
	 * 得到数据源
	 * @return
	 */
	public static DataSource getDataSource() {
		if(null==Dao.dataSource){
			Dao.dataSource=ConfigUtils.getDataSource();
		}
		return Dao.dataSource;
	}
	
}
