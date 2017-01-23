package com.weduoo.javaExtend.blockingqueue;

import java.util.concurrent.BlockingQueue;

/**
 * 消费者,去消费生产者生产的数据
 * @author iBook
 *
 */
public class Consumer implements Runnable {
	private BlockingQueue<String> queue;
	public Consumer(BlockingQueue<String> queue){
		this.queue = queue;
	}
	public void run() {
		String consumer = Thread.currentThread().getName();
		try {
			//会阻塞线程,直到队列中有值
			String take = queue.take();
			System.out.println(consumer + "消费者消费:"+take);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
