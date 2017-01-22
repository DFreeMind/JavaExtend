package com.weduoo.javaExtend.synchronize_lock;
/**
 * 使用synchronized方式实现读写同步操作
 *一个线程又要读又要写，用synchronize来实现的话，读写操作都只能锁住后一个线程一个线程地进行
 */
public class ReadWrite_synchronized {
	public static void main(String[] args) {
		final ReadWrite_synchronized reds = new ReadWrite_synchronized();
		new Thread(){
			public void run(){
				reds.operation(Thread.currentThread());
			}
		}.start();
		new Thread(){
			public void run(){
				reds.operation(Thread.currentThread());
			}
		}.start();
	}
	public synchronized void operation(Thread thread){
		long start = System.currentTimeMillis();
		int i=0;
		while(System.currentTimeMillis() - start <=1){
			i++;
			if(i%4==0){
				System.out.println(thread.getName()+" 正在进行读操作");
			}else{
				System.out.println(thread.getName()+" 正在进行写操作");
			}
		}
	}
}
