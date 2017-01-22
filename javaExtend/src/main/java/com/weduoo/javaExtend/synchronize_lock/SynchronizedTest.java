package com.weduoo.javaExtend.synchronize_lock;

public class SynchronizedTest {
	public static void main(String[] args) {
		final SynchronizedTest sync1 = new SynchronizedTest();
new Thread("Thread-1"){
	public void run(){
		synchronized("naruto"){
			try {
				System.out.println(this.getName()+"开始工作");
				int i =1/0;   //如果发生异常，jvm会将锁释放
				Thread.sleep(5000);
				System.out.println(this.getName()+"结束工作");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}.start();
		new Thread("Thread-2"){
			public void run(){
				//Thread-1与Thread-2在"naruto"上具有同步锁,因此只有一个结束访问
				//另一个才可以访问资源
				synchronized("naruto"){
					System.out.println(this.getName()+"开始工作");
				}
			}
		}.start();
	}
}
