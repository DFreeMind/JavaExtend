package com.weduoo.javaExtend.rpc.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;

/**
 * 
 * @author iBook
 *
 */
public class AioServer {

	//传入端口创建一个Aio服务
	public AioServer(int port) throws IOException{
		//创建一个面向流的socket的异步通道jdk1.7版本之后
		//通过open方法创建一个server-socket异步通道
		//最后通过bind方法绑定一个端口,默认绑定本地地址
		//在AsynchronousServerSocketChannel的javaDoc中有次示例
		final AsynchronousServerSocketChannel listener = 
				AsynchronousServerSocketChannel
				.open().bind(new InetSocketAddress(port));
		listener.accept(null,new CompletionHandler<AsynchronousSocketChannel, Void>() {
			//操作完成之后调用
			@Override
			public void completed(AsynchronousSocketChannel ch, Void v) {
				// 接收下一次操作
				listener.accept(null, this);
				//处理当前连接的逻辑
				try {
					handle(ch);
				} catch (InterruptedException | ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//操作失败之后调用
			@Override
			public void failed(Throwable exc, Void v) {
				System.err.println("AIO调用失败");
			}
		});
	}
	//连接的处理逻辑
	public void handle(AsynchronousSocketChannel ch) 
			throws InterruptedException, ExecutionException {
		ByteBuffer byteBuffer = ByteBuffer.allocate(32);
		//从通道中读取字节到给定的buffer中
		ch.read(byteBuffer).get();
		byteBuffer.flip();
		System.err.println("服务端接收:"+new String(byteBuffer.array()));
		
	}
	
	public static void main(String[] args) throws Exception {
		AioServer aioServer = new AioServer(8899);
		System.err.println("服务监听端口:" + 8899);
		Thread.sleep(100000);
	}
	
}
