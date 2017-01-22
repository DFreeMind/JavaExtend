package com.weduoo.javaExtend.synchronize_lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * reentrantLock使用
 * @author iBook
 *
 */
public class ReetrantLock_lock {
	static ReentrantLock lock = new ReentrantLock();
	static ReentrantLock lock2 = new ReentrantLock();
	public static void main(String[] args) {
		new Thread(){
			public void run(){
				lock.lock();
				try {
					System.out.println(this.getName()+"线程获得锁");
					Thread.sleep(5000);
				}catch(Exception e){}finally{
					System.out.println(this.getName()+"线程释放锁");
					lock.unlock();
				}
			}
		}.start();
		new Thread(){
			public void run(){
				//只有使用同一个锁才会锁住线程,若是这里使用lock2,那么lock1与lock2就不会互相影响
				//可以看到打印的信息如下:
//				Thread-0线程获得锁
//				Thread-1线程获得锁
//				Thread-1线程释放锁
//				Thread-0线程释放锁
				lock2.lock();
				try {
					System.out.println(this.getName()+"线程获得锁");
				}finally{
					System.out.println(this.getName()+"线程释放锁");
					lock2.unlock();
				}
			}
		}.start();
	}
}
