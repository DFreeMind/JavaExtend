package com.weduoo.javaExtend.blockingqueue;

import java.util.concurrent.ArrayBlockingQueue;

public class BlockingQueueTest {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) throws InterruptedException {
		ArrayBlockingQueue queue = new ArrayBlockingQueue(2);
		
		Producer producer = new Producer(queue);
		Consumer consumer = new Consumer(queue);
		for (int i = 0; i < 3; i++) {
			new Thread(producer,"Producer "+(i+1)).start();
		}
		for (int i = 0; i < 4; i++) {
			new Thread(consumer,"Consumer "+(i+1)).start();
		}
	}
}
