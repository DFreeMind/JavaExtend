package com.weduoo.javaExtend.synchronize_lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;


/**
 * 使用ReentrantReadWriteLock方式实现读写分离
 * 使用读写锁，可以实现读写分离锁定，读操作并发进行，写操作锁定单个线程
 * 如果有一个线程已经占用了读锁，则此时其他线程如果要申请写锁，则申请写锁的线程会一直等待释放读锁。
 * 如果有一个线程已经占用读锁，其他线程还是申请读锁，则可以并发进行
 * 如果有一个线程已经占用了写锁，则此时其他线程如果申请写锁或者读锁，则申请的线程会一直等待释放写锁。
 *
 */
public class ReadWrite_reentrant {
	private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	public static void main(String[] args) {
		final ReadWrite_reentrant rdr =  new ReadWrite_reentrant();
		new Thread(){
			public void run(){
				rdr.read(Thread.currentThread());
				rdr.write(Thread.currentThread());
			}
		}.start();
		new Thread(){
			public void run(){
				rdr.read(Thread.currentThread());
				rdr.write(Thread.currentThread());
			}
		}.start();
	}
	public void read(Thread thread){
		//获取读锁,此锁可以并发执行
		rwl.readLock().lock();
		long start = System.currentTimeMillis();
		try {
			while(System.currentTimeMillis() - start <= 1000){
				System.out.println(thread.getName() + " 正在读取操作");
			}
		} catch (Exception e) {}finally{
			//执行完成释放锁
			rwl.readLock().unlock();
		}
	}
	public void write(Thread thread){
		//获取写锁,只能有一个线程进行操作
		rwl.writeLock().lock();
		long start = System.currentTimeMillis();
		try{
			while(System.currentTimeMillis() - start <= 1){
				System.out.println(thread.getName() + " 正在进行写操作");
			}
		}catch(Exception e){}finally{
			rwl.writeLock().unlock();
		}
	}
}
