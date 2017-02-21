package com.weduoo.javaExtend.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.junit.Before;
import org.junit.Test;

public class ReflectDemo {

	public String className = null;
	
	//Class 代表JVM中加载好的一个特定的class类
	public Class personClass = null;
	
	/**
	 * 使用Class的forName方法反射出Person类
	 * @throws ClassNotFoundException
	 */
	@Before
	public void init() throws ClassNotFoundException{
		className = "com.weduoo.javaExtend.reflect.Person";
		personClass = Class.forName(className);
	}
	
	@Test
	public void getClassName(){
		System.err.println(personClass.getName());
	}
	/**
	 * newInstance方法会调用Person类的无参构造函数,如果没有无参构造则会报InstantiationException
	 * 错误。newInstance不会调用类默认的构造函数需要自己制定无参构造函数。
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
@Test
public void getNewInstance() throws 
			InstantiationException, IllegalAccessException{
	System.err.println(personClass.newInstance());
}
	/**
	 * 获取非默认空参构造方法
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void getPublicConstruct() 
			throws NoSuchMethodException, SecurityException, InstantiationException, 
			IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Constructor construct = personClass.getConstructor(Long.class,String.class);
		Object person = construct.newInstance(100L,"鸣人");
		System.err.println(person);
//		System.err.println(person.getId());
//		System.err.println(person.getName());
	}
}
