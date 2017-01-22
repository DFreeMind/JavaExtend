package com.weduoo.javaExtend.synchronize_lock;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLock_interruptiblyLock {
	private ReentrantLock lock = new ReentrantLock();
	public static void main(String[] args) {
		ReentrantLock_interruptiblyLock test = new ReentrantLock_interruptiblyLock();
		MyThread myThread1 = new MyThread(test);
		MyThread myThread2 = new MyThread(test);
		myThread1.start();
		myThread2.start();
		try {
			Thread.sleep(3000);
			//中断等待中的线程
			myThread2.interrupt();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void testInterruptibly(Thread thread) throws InterruptedException{
		//想要正确中断,就需要lock放到全局里面
		lock.lockInterruptibly();
		try{
			System.out.println(thread.getName() +" 获得锁" );
			//让第一个拿到锁的线程持续持有锁,其他的线程一直等待
			//然后中断其中等待的一个线程(myThread2)
			for( ; ; ){
				
			}
		}finally{
			System.out.println(thread.getName()+" 执行finally");
			lock.unlock();
			System.out.println(thread.getName() +" 释放锁" );
		}
	}
}
class MyThread extends Thread{
	private ReentrantLock_interruptiblyLock test= null;
	public MyThread(ReentrantLock_interruptiblyLock test){
		this.test = test;
	}
	public void run(){
		try {
			//传入当前线程
			test.testInterruptibly(Thread.currentThread());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}