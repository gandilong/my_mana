package com.thang.tools.model;

public class ActionException extends Exception {

private static final long serialVersionUID = 6823746392929702137L;
	
	public ActionException(Class<?> cls,String message){
		super(cls.getName()+message);
	}
	
	public ActionException(Class<?> cls,Throwable cause){
		super(cls.getName()+cause.getMessage(),cause);
	}
	
}
