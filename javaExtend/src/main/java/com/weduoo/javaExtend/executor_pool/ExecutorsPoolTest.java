package com.weduoo.javaExtend.executor_pool;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ExecutorsPoolTest {
	public static void main(String[] args) throws 
					InterruptedException, ExecutionException {
		Future<?> submit = null;
		Random random = new Random();	
		
		//固定数量线程的线程池
		//ExecutorService exec = Executors.newFixedThreadPool(4);
		ScheduledExecutorService exec = Executors.newScheduledThreadPool(4);
		//用来记录各个线程返回的结果
		ArrayList<Future<?>> results = new ArrayList<Future<?>>();
		for(int i=1; i<= 10;i++){
			//返回Future类型,如果执行成功,Future的get方法返回值为null
			//submit = exec.submit(new TaskRunnable(i));
			//Callable有返回值
			//submit = exec.submit(new TaskCallable(i));
			//对于schedulerPool来说，调用submit提交任务时，跟普通pool效果一致
			// submit = exec.submit(new TaskCallable(i));
			//对于schedulerPool来说，调用schedule提交任务时，
			//则可按延迟，按间隔时长来调度线程的运行
			submit = exec.schedule(new TaskCallable(i), 
					random.nextInt(10), TimeUnit.SECONDS);
			//将返回结果放到List中,而不是直接调用Future的get方法,阻塞方法的执行
			results.add(submit);
		}
		for (Future<?> future : results) {
			boolean done = future.isDone();
			System.out.println(done?"已完成":"未完成");
			//get方法会阻塞线程,会一直等到线程执行完成拿到结果
			System.out.println("执行返回值: "+ future.get());
		}
		exec.shutdown();
	}
}
