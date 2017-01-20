package com.weduoo.javaExtend.thread_test;

public class ImplementRunnableWay implements Runnable{

	String flag;
	public ImplementRunnableWay(String flag){
		this.flag = flag;
	}
	public void run() {
		// TODO Auto-generated method stub
		String name = Thread.currentThread().getName();
		System.out.println(name+"启动了"+flag);
	}
	public static void main(String[] args) {
		//通过Thread启动p线程
		ImplementRunnableWay p = new ImplementRunnableWay("a");
		new Thread(p).start();
	}
}
