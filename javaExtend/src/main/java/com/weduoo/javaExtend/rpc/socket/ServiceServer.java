package com.weduoo.javaExtend.rpc.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * RPC通讯的服务端
 * @author iBook
 *
 */
public class ServiceServer {
	public static void main(String[] args) 
								throws IOException {
		//创建一个serversocket,并绑定到本机的8899端口
		ServerSocket server = new ServerSocket();
		server.bind(new InetSocketAddress("localhost", 8899));
		while(true){
			//accept是一个阻塞方法,直到产生一个连接
			Socket socket = server.accept();
			//下面是接收到调用时的处理逻辑,每次启动一个新线程去处理
			new Thread(new ServiceServerTask(socket)).start();
		}
	}
}
