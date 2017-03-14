package com.weduoo.javaExtend.proxy;

public class Person implements PersonInterface {

	@Override
	public void love() {
		System.err.println("我喜欢科幻");
	}

	@Override
	public void doing() {
		System.err.println("我在看电影....");
	}

}
