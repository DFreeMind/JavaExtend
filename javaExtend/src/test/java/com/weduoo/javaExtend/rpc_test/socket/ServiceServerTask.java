package com.weduoo.javaExtend.rpc_test.socket;

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

public class ServiceServerTask implements Runnable {
	Socket socket;
	InputStream in = null;
	OutputStream out = null;

	public ServiceServerTask(Socket socket) {
		this.socket = socket;
	}

	// 业务逻辑：跟客户端进行数据交互
	@Override
	public void run() {
		try {
			System.out.println("server服务线程启动 -->");
			// 从socket连接中获取到与client之间的网络通信输入输出流
			in = socket.getInputStream();
			out = socket.getOutputStream();

			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			// 从网络通信输入流中读取客户端发送过来的数据
			// 注意：socketinputstream的读数据的方法都是阻塞的
			String param = br.readLine();
			System.out.println("server received -->" + param);

			/**
			 * 作业： 假如客户端发送过来的数据是一个json串：
			 * {interface:"com.weduoo.rpc.socket.GetDataService",
			 * method:"getData",params:"hello"}
			 * 将以下业务调用逻辑写成更加通用的：
			 * 可以根据客户端发过来的调用类名、调用方法名、调用该参数来灵活调用 《反射》
			 * 
			 */

			Gson gson = new Gson();
			HashMap fromJson = gson.fromJson(param, HashMap.class);
			String serviceName = (String) fromJson.get("service");
			String methodName = (String) fromJson.get("method");
			String parameter = (String) fromJson.get("params");
			//通过服务名反射出调用的服务器
			Class<?> service = Class.forName(serviceName);
			Method method = service.getMethod(methodName, String.class);
			String result = (String) method.invoke(service.newInstance(), parameter);

			/*
			 * GetDataServiceImpl getDataServiceImpl = new GetDataServiceImpl();
			 * String result = getDataServiceImpl.getData(param);
			 */

			// 将调用结果写到sokect的输出流中，以发送给客户端
			PrintWriter pw = new PrintWriter(out);
			pw.println(result);
			pw.flush();

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
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
