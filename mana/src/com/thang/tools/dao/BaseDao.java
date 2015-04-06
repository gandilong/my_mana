package com.thang.tools.dao;

import java.sql.Connection;
import java.util.List;

import org.apache.log4j.Logger;

import com.thang.tools.model.ActionValues;
import com.thang.tools.model.ResultValues;
import com.thang.tools.model.SQLParser;

/**
 * 增强了可以执行xml中的SQL功能
 * @author zyt
 *
 */
public class BaseDao extends Dao {

	private static Logger logger=Logger.getLogger(BaseDao.class);
	
	public BaseDao(){
		super();
	}
	
	/**
	 * 执行 新增，修改，删除 SQL
	 * @param namespace
	 * @param values
	 */
	public void updateXSQL(String namespace,ActionValues values){
		logger.debug("修改操作");
		updateSQL(SQLParser.getSQL(namespace, values), values);
	}
	
	/**
	 * 执行 新增，修改，删除 SQL
	 * @param conn
	 * @param namespace
	 * @param values
	 */
	public void updateXSQL(Connection conn,String namespace,ActionValues values){
		logger.debug("修改操作");
		updateSQL(conn,SQLParser.getSQL(namespace, values), values);
	}
	
	/**
	 * 执行 查询 SQL
	 * @param namesapce
	 * @param values
	 * @return
	 */
	public List<ResultValues> queryXSQL(String namesapce,ActionValues values){
		return listSQL(SQLParser.getSQL(namesapce, values), values);
	}
	
	
}
