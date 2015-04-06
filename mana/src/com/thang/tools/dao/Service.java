package com.thang.tools.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.thang.tools.mate.entity.Id;
import com.thang.tools.model.ActionValues;
import com.thang.tools.model.ResultValues;
import com.thang.tools.model.SQLGener;
import com.thang.tools.util.ModelUtils;
import com.thang.tools.util.UUIDUtils;

/**
 * 包含所有数据库操作
 * @author gandilong
 *
 */
public class Service{
	
	protected BaseDao dao=null;
	
	private static Logger logger=Logger.getLogger(Service.class);
	
	protected Service(){
	     if(null==dao){
	    	 dao=new BaseDao();
	     }
	}
	
	/**
	 * 得到一条记录 必须包含主键值
	 * @param modelClass
	 * @param values
	 * @return
	 */
	public ResultValues get(Class<?> modelClass,ActionValues values){
		String id=ModelUtils.getPrimaryKey(modelClass);
		if(values.isNotEmpty(id)){
			return dao.getSQL(SQLGener.GetSQL(modelClass),values);	
		}else{
			logger.warn("调用get(modelClass,values)方法必须包含主键值！");
		}
		return null;
	}
	
	/**
	 * 查询多条记录
	 * @param modelClass
	 * @param values
	 * @return
	 */
	public List<ResultValues> list(Class<?> modelClass,ActionValues values){
		return dao.listSQL(SQLGener.SelectSQL(modelClass, values),values);
	}
	
	
	public void insert(Class<?> modelClass,ActionValues values){
		dao.updateSQL(SQLGener.InsertSQL(modelClass),values);
	}
	
	/**
	 * 新增或修改
	 * @param modelClass
	 * @param values
	 */
	public void insertOrUpdate(Class<?> modelClass,ActionValues values){
		String id=ModelUtils.getPrimaryKey(modelClass);
		if(values.isNotEmpty(id)){
			dao.updateSQL(SQLGener.UpdateSQL(modelClass, values),values);
		}else{
			String idGenerType="auto";
			try{
			    modelClass.getField(id).getAnnotation(Id.class).value();
			}catch(Exception e){
				e.printStackTrace();
			}
			if(Id.AUTO.equals(idGenerType)){
				
			}else if(Id.UUID.equals(idGenerType)){
				values.add(id, UUIDUtils.getUUID());
			}
			dao.updateSQL(SQLGener.InsertSQL(modelClass),values);
		}
	}
	
	/**
	 * 删除一条记录，必须包含主键值
	 * @param modelClass
	 * @param values
	 */
	public void delete(Class<?> modelClass,ActionValues values){
		String id=ModelUtils.getPrimaryKey(modelClass);
		if(values.isNotEmpty(id)){
			dao.updateSQL(SQLGener.DeleteSQL(modelClass),values);	
		}else{
			logger.warn("调用delete(modelClass,values)方法必须包含主键值！");
		}
	}
	
	/**
	 * 获取数据库连接对角
	 */
	protected Connection getConnection(){
		try {
			return Dao.getDataSource().getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	//样例代码不能用于开发
	/*protected void beginTransaction(){
		try{
		    Connection conn=Dao.getDataSource().getConnection();
		    conn.setAutoCommit(false);
		    conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
		}catch(Exception e){
			e.printStackTrace();
		}
	}*/
	
}
