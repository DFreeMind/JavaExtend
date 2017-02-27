package com.weduoo.javaExtend.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
	 * getConstructor()方法的得参数类型需要与被反射的构造函数的类型一致。
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
		Person person = (Person)construct.newInstance(100L,"鸣人");
		System.err.println(person.getId());
		System.err.println(person.getName());
	}
	/**
	 * 获取私有构造函数
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void getPrivateConstructor() throws NoSuchMethodException, SecurityException, 
						InstantiationException, IllegalAccessException, 
						IllegalArgumentException, InvocationTargetException{
		Constructor con = personClass.getDeclaredConstructor(String.class);
		//设置是否允许访问，因为该构造器是private的，所以要手动设置允许访问，
		//不设置为true的时候会产生IllegalAccessException错误
		//如果构造器是public的就不需要这行了。
		con.setAccessible(true);
		Person person = (Person)con.newInstance("李洁");
		System.err.println(person.getName());
	}
	/**
	 * 获取private和public类型的字段
	 * 与获取构造函数类似getField()用于获取public类型的字段
	 * getDeclaredField既可以获取private又可以获取public类型的字段
	 * 对于获取private的字段越也需要设置setAccessible为true
	 * @throws Exception
	 * @throws SecurityException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void getPublicAndPrivateField() throws Exception, SecurityException{
		Constructor con = personClass.getConstructor(Long.class,String.class);
		Object obj = con.newInstance(100L,"鸣人");
		Field name = personClass.getField("name");
		//Field field = personClass.getDeclaredField("name");
		Field id = personClass.getDeclaredField("id");
		id.setAccessible(true);
		System.err.println(name.get(obj));
		System.err.println(id.get(obj));
		name.set(obj, "雏田");
		id.set(obj,10000L);
		System.err.println(name.get(obj));
		System.err.println(id.get(obj));
	}
	/**
	 * 与获取构造函数与字段类似,获取method也有两个方法
	 * getMethod()与getDeclaredMethod()方法,前一个之只能用于获取public类型方法
	 * 后一个既可以获取private与public,获取private时需要取消Java权限检查.
	 * 接着调用Method的invoke方法执行函数,后去函数执行的值.
	 * @throws Exception
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void getPrivateAndPublicMethod() throws Exception, IllegalAccessException{
		Object obj = personClass.newInstance();
		//获取public类型的方法
		Method toString = personClass.getMethod("toString");
		//使用invoke执行方法
		Object res1 = toString.invoke(obj);
		System.err.println(res1);
		
		//获取私private类型的方法,并取消Java权限检查
		Method getSomeThing = personClass.getDeclaredMethod("getSomeThing");
		getSomeThing.setAccessible(true);
		Object res2 = getSomeThing.invoke(obj);
		System.err.println(res2);
	}
	
	@Test
	public void otherMethod(){
		//获取当前类的类加载器对象
		//打印sun.misc.Launcher$AppClassLoader@7692ed85
		System.err.println(personClass.getClassLoader());
		
		//获取当前类实现的所有接口
		//打印interface java.io.Serializable
		Class[] interfaces = personClass.getInterfaces();
		for (Class class1 : interfaces) {
			System.err.println(class1);
		}
		
		//反射当前类的父类
		//打印class java.lang.Object
		System.err.println(personClass.getGenericSuperclass());
		
		//判断当前的Class对象表示是否是数组
		//但因false和true
		System.out.println(personClass.isArray());
		System.out.println(new String[3].getClass().isArray());
		
	}
	
	
	
	
	
	
	
	
}
