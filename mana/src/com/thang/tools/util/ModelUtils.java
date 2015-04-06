package com.thang.tools.util;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.thang.tools.mate.entity.Column;
import com.thang.tools.mate.entity.Id;
import com.thang.tools.mate.entity.Table;

public class ModelUtils {

	/**
	 * 得到指定实体类的对映表名
	 * @param model
	 * @return
	 */
	public static String getTableName(Class<?> model){
		if(model.isAnnotationPresent(Table.class)){
			Table table=model.getAnnotation(Table.class);
			return table.value();
		}
		return null;
	}
	
	/**
	 * 得到实体类主键
	 * @param model
	 * @return
	 */
	public static String getPrimaryKey(Class<?> model){
		Field[] fields=model.getDeclaredFields();
		boolean hasID=false;//判断是否有名叫"id"的字段
		for(Field field:fields){
			if("id".equalsIgnoreCase(field.getName())){
				hasID=true;
			}
			if(field.isAnnotationPresent(Id.class)){
				return field.getName();
			}
		}
		if(hasID){//如果没有 @id注解 但有名叫“id”的字段，可以返回"id"
			return "id";
		}
		return null;
	}
	
	/**
	 * 以字符串数组的形式返回指定类的字段名称
	 * @param model
	 * @return
	 */
	public static String[] getFieldNames(Class<?> model){
		Field[] fields=model.getDeclaredFields();
		List<String> fieldNames=null;
		if(null!=fields&&fields.length>0){
		    fieldNames=new ArrayList<String>(fields.length);
		    for(Field field:fields){
		    	if("serialVersionUID".equals(field.getName())){
		    		continue;
		    	}
		    	fieldNames.add(field.getName());
		    }
		    
		}
		return fieldNames.toArray(new String[fieldNames.size()]);
	}
	
	/**
	 * 得到指定字段对象
	 * @param model
	 * @param fieldName
	 * @return
	 */
	public static Field getField(Class<?> model,String fieldName){
		Field[] fields=model.getDeclaredFields();
		if(null!=fields&&fields.length>0){
		    for(Field field:fields){
		    	if(fieldName.equals(field.getName())){
		    		return field;
		    	}
		    }
		}
		return null;
	}
	/**
	 * 得到所有列
	 * @param model
	 * @return
	 */
	public static String[] getColumnNames(Class<?> model){
		Field[] fields=model.getDeclaredFields();
		List<String> columnNames=null;
		if(null!=fields&&fields.length>0){
			columnNames=new ArrayList<String>(fields.length);
		    for(Field field:fields){
		    	if("serialVersionUID".equals(field.getName())){
		    		continue;
		    	}
		    	if(field.isAnnotationPresent(Column.class)){
		    		columnNames.add(field.getAnnotation(Column.class).value());
		    	}else{
		    		columnNames.add(field.getName());
		    	}
		    }
		    
		}
		return columnNames.toArray(new String[columnNames.size()]);
	}
	
	/**
	 * 获取指定类的指定字段的数据库字段名称
	 * @param model
	 * @param fieldName
	 * @return
	 */
	public static String getColumn(Class<?> model,String fieldName){
		try{
		    Field field=model.getField(fieldName);
		    if(field.isAnnotationPresent(Column.class)){
    		    return field.getAnnotation(Column.class).value();
    	    }
		}catch(Exception e){
			e.printStackTrace();
		}
		return fieldName;
	}
	
	/**
	 * 获取指定对象指定字段的值
	 * @param obj
	 * @param fieldName
	 * @return
	 */
	public static Object getFieldValue(Object obj,String fieldName){
		try{
		  if(null!=obj){
			  return obj.getClass().getMethod("get"+StrUtils.upperFirst(fieldName)).invoke(obj,new Object[]{});
		  }
		}catch(Exception e){
			e.printStackTrace();
		}
		  return null;
	}
	
	/**
	 * 得到指定类的父类 的泛型类型
	 * @return
	 */
	public static Class<?> getParamType(Class<?> cls){
		Type type=cls.getGenericSuperclass();
		ParameterizedType ptype=(ParameterizedType)type;
		Type[] types=ptype.getActualTypeArguments();
		return types[0].getClass();
	}
}
