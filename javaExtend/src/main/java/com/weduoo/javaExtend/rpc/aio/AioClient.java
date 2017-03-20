package com.weduoo.javaExtend.rpc.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AioClient {

	private  AsynchronousSocketChannel client = null;
	public AioClient(String hostname, int port) 
			throws IOException, InterruptedException,
								ExecutionException{
		client = AsynchronousSocketChannel.open();
		Future<Void> future = 
				client.connect(new InetSocketAddress(hostname,port));
		Void obj = future.get();
		System.err.println(obj);
	}
	
	public void write(byte[] b){
		ByteBuffer bb = ByteBuffer.allocate(32);
		bb.put(b);
		bb.flip();
		//将给定的buffer写入channel
		client.write(bb);
	}
	
	public static void main(String[] args) 
			throws IOException, InterruptedException, ExecutionException {
		AioClient aioClient = new AioClient("localhost", 8899);
		byte[] ba = "hello aio".getBytes();
		aioClient.write(ba);
//		for(int i = 0; i < ba.length ; i++){
//			System.err.println(ba[i]);
//			aioClient.write(ba[i]);
//		}
	}
}
