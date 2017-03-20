package com.weduoo.javaExtend.rpc.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class NioClient implements Runnable {
	private BlockingQueue<String> words;
	private Random random;
	
	@Override
	public void run() {
		//通过open方法打开一个还没有连接的socket
		SocketChannel channel = null;
		//多路复用SelectableChannel对象,open方法
		Selector selector = null;
		try {
			//打开一个socket channel通过以下方法
			//SelectorProvider.provider().openSocketChannel();
			channel = SocketChannel.open();
			//设置channel的阻塞属性,false表示非阻塞,true表示阻塞
			channel.configureBlocking(false);
			//连接到当前channel的socket
			channel.connect(new InetSocketAddress("localhost", 8899));
			//打开一个selector通过调用以下方法
			//SelectorProvider.provider().openSelector();
			selector = Selector.open();
			//将当前channel注册到给定的selector上,并返回一个SelectionKey
			channel.register(selector, SelectionKey.OP_CONNECT);
			boolean isOver = false;
			while( !isOver ){
				//选取一组准备好IO操作的channel的key.此方法是一个阻塞方法,只有当至少有一个channel
				//被选中是才会返回.此时selector的wakeup方法被调用
				selector.select();
				Iterator<SelectionKey> keys = 
						selector.selectedKeys().iterator();
				while(keys.hasNext()){
					SelectionKey key = keys.next();
					keys.remove();
					//测试当前key的channel的socket连接操作是否完成或者失败
					if(key.isConnectable()){
						//当前channel连接操作是否在进行中
						if(channel.isConnectionPending()){
							if(channel.finishConnect()){
								key.interestOps(SelectionKey.OP_READ);
								//将给定的buffer写入到channel
								channel.write(CharsetTool.
										encode(CharBuffer.wrap(getWords())));
								sleep();
							}else{
								//将当前key从所有的key sets中移除,并将溢出的key加入到
								//cancelled-key set中
								key.cancel();
							}
						}
					//测试当前key的channel是否可读,不可读返回false	
					}else if(key.isReadable()){
						ByteBuffer byteBuffer = ByteBuffer.allocate(128);
						channel.read(byteBuffer);
						byteBuffer.flip();
						CharBuffer charBuffer = CharsetTool.decode(byteBuffer);
						String answer = charBuffer.toString();
						System.err.println(Thread.currentThread().getId()+" -- " + answer);
						String word = getWords();
						if(word != null){
							channel.write(CharsetTool.encode(CharBuffer.wrap(word)));
						}else{
							isOver = true;
						}
						sleep();
					}
				}
			}
		} catch (Exception e) {
		}finally{
			if(channel != null){
				try{
					channel.close();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
			if(selector != null){
				try {
					selector.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	private void init(){
		words = new ArrayBlockingQueue<>(5);
		try {
			words.put("hi");
			words.put("who");
			words.put("what");
			words.put("where");
			words.put("bye");
			words.put("now");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		random = new Random();
	}
	private void sleep() {
		try {
			TimeUnit.SECONDS.sleep(random.nextInt(3));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private String getWords() {
		//将queue头部的数据返回,同时将queue的头部数据删除
		System.err.println(words);
		return words.poll();
	}
	
	public static void main(String[] args) {
		for (int i = 0; i < 1; i++) {
			NioClient nioClient = new NioClient();
			nioClient.init();
			new Thread(nioClient).start();
		}
	}
}
