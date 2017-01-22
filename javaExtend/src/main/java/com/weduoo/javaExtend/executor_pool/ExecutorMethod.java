package com.weduoo.javaExtend.executor_pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Executors中含有的方法
 * @author iBook
 *
 */
public class ExecutorMethod {
	public static void main(String[] args) {
		//单线程线程池
		ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
		//创建可重复利用的线程池
		ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
		
		int availableProcessors = Runtime.getRuntime().availableProcessors();
		//创建固定数量的线程的线程池
		ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(availableProcessors);
		//创建调度执行的任务的线程池
		ScheduledExecutorService newScheduledThreadPool = Executors.newScheduledThreadPool(availableProcessors);
		//创建只有一个线程的可调度线程池
		ScheduledExecutorService newSingleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor();
		
	}
}
