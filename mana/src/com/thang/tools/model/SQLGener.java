package com.thang.tools.model;

import com.thang.tools.model.ActionValues;
import com.thang.tools.util.ModelUtils;
import com.thang.tools.util.StrUtils;
/**
 * 用于生成增删改查SQL
 * @author gandilong
 *
 */
public class SQLGener {

	/**
	 * 得到查询一条语句的SQL
	 * @param model
	 * @return
	 */
	public static String GetSQL(Class<?> model){
		StringBuffer sber=new StringBuffer("select ");
		
		String[] fieldNames=ModelUtils.getFieldNames(model);
		String tableName=ModelUtils.getTableName(model);
		String id=ModelUtils.getPrimaryKey(model);
		
		sber.append(StrUtils.join(fieldNames, ","));
		sber.append(" from ").append(tableName).append(" where ").append(id).append("=:").append(id);
		return sber.toString();
	}
	
	/**
	 * 用于单表查询
	 * 得到查询多条记录的SQL语句
	 * @param model
	 * @param values
	 * @return
	 */
	public static String SelectSQL(Class<?> model,ActionValues values){
		StringBuffer sber=new StringBuffer("select ");
		
		String[] fieldNames=ModelUtils.getFieldNames(model);
		String tableName=ModelUtils.getTableName(model);
		
		sber.append(StrUtils.join(fieldNames, ","));
		
		sber.append(" from ").append(tableName).append(" where ").append(" 1=1 ");
		
		for(String fieldName:fieldNames){
			if(values.isNotEmpty(fieldName)){
				Class<?> type=ModelUtils.getField(model, fieldName).getType();
				if(String.class==type){
					sber.append(" and ").append(ModelUtils.getColumn(model, fieldName)).append("='").append(values.getStr(fieldName)).append("'");
				}else if(Integer.class==type){
					sber.append(" and ").append(ModelUtils.getColumn(model, fieldName)).append("=").append(values.getInt(fieldName));
				}else if(Long.class==type){
					sber.append(" and ").append(ModelUtils.getColumn(model, fieldName)).append("=").append(values.getLong(fieldName));
				}else if(Float.class==type){
					sber.append(" and ").append(ModelUtils.getColumn(model, fieldName)).append("=").append(values.getFloat(fieldName));
				}else if(Double.class==type){
					sber.append(" and ").append(ModelUtils.getColumn(model, fieldName)).append("=").append(values.getDouble(fieldName));
				}else{
					sber.append(" and ").append(values.get(fieldName));//如果是对象类型，则调用对象的toString()方法。
				}
			}
		}
		
		return sber.toString();
	}
	/**
	 * 生成插入SQL
	 * @param model
	 * @return
	 */
	public static String InsertSQL(Class<?> model){
		StringBuffer sber=new StringBuffer("insert into ");
		
		String[] fieldNames=ModelUtils.getFieldNames(model);
		String tableName=ModelUtils.getTableName(model);
		
		sber.append(tableName).append("(").append(StrUtils.join(fieldNames, ",")).append(")values(:").append(StrUtils.join(fieldNames, ",:")).append(")");
		
		return sber.toString();
		
	}
	
	/**
	 * 生成修改SQL
	 * @param model
	 * @param values
	 * @return
	 */
	public static String UpdateSQL(Class<?> model,ActionValues values){
		StringBuffer sber=new StringBuffer("update ");
		
		String[] fieldNames=ModelUtils.getFieldNames(model);
		String tableName=ModelUtils.getTableName(model);
		String id=ModelUtils.getPrimaryKey(model);
		
		sber.append(tableName).append(" set ");
		for(String fieldName:fieldNames){
			if(values.isNotEmpty(fieldName)&&!id.equalsIgnoreCase(fieldName)){
				sber.append(" ").append(fieldName).append("=:").append(fieldName).append(",");
			}
		}
		sber.delete(sber.length()-1, sber.length());
		sber.append(" where ").append(id).append("=:").append(id);
		
		return sber.toString();
		
	}
	
	/**
	 * 生成删除SQL
	 * @param model
	 * @return
	 */
	public static String DeleteSQL(Class<?> model){
        StringBuffer sber=new StringBuffer("delete from ");
		
		String tableName=ModelUtils.getTableName(model);
		String id=ModelUtils.getPrimaryKey(model);
		sber.append(tableName).append(" where ").append(id).append("=:").append(id);
		return sber.toString();
	}
}
