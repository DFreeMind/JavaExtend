package com.weduoo.javaExtend.rpc.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.HashMap;

import com.google.gson.Gson;

/**
 * 服务器调用处理流程
 * @author iBook
 *
 */
public class ServiceServerTask implements Runnable {

	Socket socket;
	InputStream in = null;
	OutputStream out = null;
	
	public ServiceServerTask(Socket socket) {
		this.socket = socket;
	}
	//业务逻辑:与客户端进行数据交互
	@Override
	public void run() {
		System.out.println("server 服务线程启动");
		//从socket连接中获取到与client之间的网络通讯的输入输出流
		try {
			in = socket.getInputStream();
			out = socket.getOutputStream();
			
			//从输入流中读取客户端发送来的数据
			//注意：socketinputstream的读数据的方法都是阻塞的
			BufferedReader br = 
					new BufferedReader(new InputStreamReader(in));
			String line = br.readLine();
			System.out.println("server received -- > " + line);
			
			/**
			 * 执行客户端调用的方法,并将执行的结果返回
			 *   {service:"com.weduoo.javaExtend.rpc.socket.GetDataServiceImpl",
			 * method:"getData",params:"hello"}
			 * 将以下业务调用逻辑写成更加通用的：
			 * 可以根据客户端发过来的调用类名、调用方法名、调用该参数来灵活调用 反射
			 */
			Gson gson = new Gson();
			HashMap fromJson = gson.fromJson(line, HashMap.class);
			String serviceName = (String)fromJson.get("service");
			String methodName = (String)fromJson.get("method");
			String params = (String) fromJson.get("params");
			//通过反射,执行相关操作
			Class<?> service = Class.forName(serviceName);
			Method method = service.getMethod(methodName, String.class);
			String result = (String) method.invoke(service.newInstance(), params);
			
			//将结果写入到socket输出流中,已发送给客户端
			PrintWriter pw = new PrintWriter(out);
			pw.println(result);
			pw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				in.close();
				out.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
