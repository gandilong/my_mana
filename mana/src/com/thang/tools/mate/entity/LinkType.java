package com.thang.tools.mate.entity;

public enum LinkType {

	lt(-2),//小于
	le(-1),//小于等于
	eq(0),//等于
	ge(2),//大于等于
	gt(1),//大于
	ne(3),//不等于
	like(4);
	
	private int type=0;
	
	private LinkType(int type){
		this.type=type;
	}
	
	@Override
	public String toString() {
		return String.valueOf(type);
	}
}
