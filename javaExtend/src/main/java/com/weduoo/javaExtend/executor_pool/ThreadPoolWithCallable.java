package com.weduoo.javaExtend.executor_pool;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadPoolWithCallable {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService pool = Executors.newFixedThreadPool(4); 
		for(int i = 0; i < 10; i++){
			Future<String> submit = pool.submit(new Callable<String>(){
				public String call() throws Exception {
					//System.out.println("a");
					System.out.println(Thread.currentThread().getName()+ "-->正在工作");
					Thread.sleep(5000);
					System.out.println(Thread.currentThread().getName()+ "-->工作结束");
					return "b--"+Thread.currentThread().getName();
				}			   
			   });
			//从Future中get结果，这个方法是会被阻塞的，一直要等到线程任务返回结果
			 System.out.println("获取到结果：" + submit.get());
		} 
			pool.shutdown();

	}
}
