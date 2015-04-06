package com.thang.test.util;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.thang.tools.util.JsonUtils;

public class JsonUtils_Test {

	@Test
	public void testToJsonStrObject() {
		Person p=new Person("good",25);
		System.out.println(JsonUtils.toJsonStr(p));
	}

	@Test
	public void testToJsonStrStringObject() {
		Person p=new Person("good",25);
		System.out.println(JsonUtils.toJsonStr("xixi", p));
	}
	
	@Test
	public void testParseJson(){
		Person p=new Person("good",25);
		Person k=JsonUtils.parseJson(JsonUtils.toJsonStr(p), Person.class);
		System.out.println(JsonUtils.toJsonStr(k));
	}
	
	@Test
	public void testParseJsons(){
		Person p=new Person("good",25);
		Person pa=new Person("goddod",252);
		Person pb=new Person("gossod",205);
		Person pc=new Person("goffod",12);
		List<Person> list=new ArrayList<Person>();
		list.add(p);
		list.add(pa);
		list.add(pb);
		list.add(pc);
		String json=JsonUtils.toJsonStr(list);
		List<Person> j=JsonUtils.parseJsons(json, Person.class);
		System.out.println(JsonUtils.toJsonStr(j));
	}
	
}

class Person{
	private int age;
	private String name;
	
	public Person(String name,int age){
		this.age=age;
		this.name=name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
