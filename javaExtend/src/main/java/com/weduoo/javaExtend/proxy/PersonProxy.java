package com.weduoo.javaExtend.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class PersonProxy {
	public static void main(String[] args) {
		final Person person = new Person();
		Object proxy = Proxy.newProxyInstance(Person.class.getClassLoader(), 
				Person.class.getInterfaces(), new InvocationHandler() {
					@Override
					public Object invoke(Object proxy, Method method, Object[] args)
							throws Throwable {
						if(method.getName().equals("love")){
							//重写love方法的逻辑
							System.err.println("现在我只喜欢动漫");
						}else{
							//非love方法执行之前的逻辑
							Object value = method.invoke(person, args);
						}
						return null;
					}
				});
		PersonInterface p = (PersonInterface)proxy;
		person.love();
		p.love();
		person.doing();
		p.doing();
	}
}
