package com.weduoo.javaExtend.synchronize_lock;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLock_tryLock {
	static ReentrantLock lock = new ReentrantLock();
	public static void main(String[] args) {
		new Thread(){
			public void run(){
				boolean tryLock = lock.tryLock();
				System.out.println(this.getName()+" "+tryLock);
				if(tryLock){
					try {
						System.out.println(this.getName()+" 获得锁");
						Thread.sleep(5000);
					}catch(Exception e){
						//TODO
					}finally{
						System.out.println(this.getName()+" 释放锁");
						lock.unlock();
					}
				}
			}
		}.start();
		new Thread(){
			public void run(){
				boolean tryLock = lock.tryLock();
				System.out.println(this.getName()+" "+tryLock);
				if(tryLock){
					try {
						System.out.println(this.getName()+" 获得锁");
					}finally{
						System.out.println(this.getName()+" 释放锁");
						lock.unlock();
					}
				}
			}
		}.start();
	}
}
