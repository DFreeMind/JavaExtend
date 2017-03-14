package com.weduoo.javaExtend.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * 代理方式示例,代理List中的方法
 * @author iBook
 *
 */
public class ListProxyDemo {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) {
		final List list = new ArrayList();
		//Proxy类的newProxyInstance方法需要传入三个参数
		//第一个:被代理类的类加载器,此处是List类的类加载器
		//第二个:被代理类所实现的接口列表
		//第三个:方法调用处理器(句柄方法)
		Object proxy = Proxy.newProxyInstance(List.class.getClassLoader(), 
				list.getClass().getInterfaces(), new InvocationHandler() {
			//内部类的方法,当代理对象执行方法调用时就会调用此方法,此方法传入三个参数
			//第一个:代理对象,方法在此对象上被调用
			//第二个:Method实例对象,与在代理对象上调用的接口方法一致
			//第三个:参数数组,包含了在代理对象上调用对象时传入的参数值(可以为空)
			@Override
			public Object invoke(Object proxy, Method method, Object[] args)
					throws Throwable {
				System.err.println("调用代理方法");
				Object returnValue = method.invoke(list, args);
				//当调用的是代理的size方法时修改返回的值
				if(method.getName().equals("size")){
					//方法的返回类型要和原始方法的返回类型一直
					//若是类型不一致,返回的类型需要能转化成原始返回类型
					//如此处放回的是Integer类型,返回String类型就不可以
					return 100;
				}
				return returnValue;
			}
		});
		
		//将被代理的对象强制转换为实际要操作的对象
		List list2 = (List) proxy;
		boolean add = list2.add("aa");
		boolean add2 = list2.add("bb");
		System.err.println("aa add :"+add);
		System.err.println("bb add :"+add2);
		System.err.println("size:别代理后的"+ list2.size()+",被代理之前的"+list.size());
	}
}
