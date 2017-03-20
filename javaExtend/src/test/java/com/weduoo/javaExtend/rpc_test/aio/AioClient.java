package com.weduoo.javaExtend.rpc_test.aio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.Future;

public class AioClient {
	
	private AsynchronousSocketChannel client = null;
	
	public AioClient(String hostname,int port) throws Exception{
		client = AsynchronousSocketChannel.open();
		Future<?> future = client.connect(new InetSocketAddress(hostname, port));
		Object object = future.get();
		System.out.println(object);
	}
	
	public void write(byte b){
		ByteBuffer byteBuffer = ByteBuffer.allocate(32);
		byteBuffer.put(b);
		byteBuffer.flip();
		client.write(byteBuffer);
		
	}
	
	
	public static void main(String[] args) throws Exception {
		
		AioClient aioClient = new AioClient("127.0.0.1", 7080);
		aioClient.write("阿斯顿发个凤凰股份撒是梵蒂冈发电公司是短发是洒洒水是大法官是的发放给是梵蒂冈圣达菲个是大法官圣达菲个".getBytes()[0]);
		
		
		
	}

}
