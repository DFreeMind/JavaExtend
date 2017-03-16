package com.weduoo.javaExtend.rpc_test.socket;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ServiceClient {

	public static void main(String[] args) throws Exception {
		// 向服务器发出请求建立连接
		Socket socket = new Socket("localhost", 8899);
		// 从socket中获取输入输出流
		InputStream inputStream = socket.getInputStream();
		OutputStream outputStream = socket.getOutputStream();

		PrintWriter pw = new PrintWriter(outputStream);
		pw.println("{service:com.weduoo.rpc.socket.GetDataServiceImpl,method:getData,params:hello}");
		pw.flush();

		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
		String result = br.readLine();
		System.out.println("received server response --> " +result);
		
		inputStream.close();
		outputStream.close();
		socket.close();
	}
}
