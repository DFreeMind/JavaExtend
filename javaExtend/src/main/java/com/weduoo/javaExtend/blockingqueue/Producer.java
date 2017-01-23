package com.weduoo.javaExtend.blockingqueue;

import java.util.concurrent.BlockingQueue;

/**
 * 生成者,用来生成队里中的元素
 * @author iBook
 *
 */
public class Producer implements Runnable {
	private BlockingQueue<String> queue;
	public Producer(BlockingQueue<String> queue){
		this.queue = queue;
	}
	public void run() {
		String name = "Producer 产生线程"+Thread.currentThread().getName();
		try {
			//会阻塞线程,直到队列有足够的使用的空间
			queue.put(name);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("生产者产生线程:"+Thread.currentThread().getName());
	}
}
