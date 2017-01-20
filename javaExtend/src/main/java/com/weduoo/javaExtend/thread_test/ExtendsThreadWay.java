package com.weduoo.javaExtend.thread_test;

public class ExtendsThreadWay extends Thread{
	String flag;
	public ExtendsThreadWay(String flag){
		this.flag=flag;
	}
	public void run(){
		//获得当前线程的名字
		String name = Thread.currentThread().getName();
		System.out.println(name+":我启动了_"+flag);
	}
	public static void main(String[] args) {
		//通过创建一个实例来启动一个线程来运行run方法
		ExtendsThreadWay etw = new ExtendsThreadWay("a");
		etw.start();
		ExtendsThreadWay etw2 = new ExtendsThreadWay("b");
		etw2.start();
	}
}
