package com.weduoo.javaExtend.rpc.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NioServer {

	private ByteBuffer readBuffer;
	private Selector selector;
	//初始化方法
	private void init(){
		readBuffer = ByteBuffer.allocate(1024);
		ServerSocketChannel ssChannel;
		try {
			ssChannel = ServerSocketChannel.open();
			ssChannel.configureBlocking(false);
			ssChannel.socket().bind(new InetSocketAddress(8899));
			
			selector = Selector.open();
			ssChannel.register(selector, SelectionKey.OP_ACCEPT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//监听程序
	private void listen(){
		while(true){
			try {
				selector.select();
				Iterator<SelectionKey> keys = 
						selector.selectedKeys().iterator();
				while(keys.hasNext()){
					SelectionKey key = keys.next();
					keys.remove();
					//处理事件
					handleKey(key);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	private void handleKey(SelectionKey key) throws IOException {
		// TODO Auto-generated method stub
		SocketChannel channel = null;
		try{
			if(key.isAcceptable()){
				ServerSocketChannel sChannel = 
						(ServerSocketChannel) key.channel();
				channel = sChannel.accept();
				channel.configureBlocking(false);
				channel.register(selector, SelectionKey.OP_READ);
			}else if(key.isReadable()){
				channel = (SocketChannel) key.channel();
				readBuffer.clear();
				int count = channel.read(readBuffer);
				if(count > 0){
					readBuffer.flip();
					CharBuffer charBuffer = CharsetTool.decode(readBuffer);
					String question = charBuffer.toString();
					String answer = getAnswer(question);
					channel.write(CharsetTool.encode(CharBuffer.wrap(answer)));
				}else{
					channel.close();
				}
			}
		}catch(Throwable t){
			t.printStackTrace();
			if(channel != null){
				channel.close();
			}
		}
		
	}
	private String getAnswer(String question) {
		String answer = null;
		switch(question){
			case "who":
				answer = "我是皮皮虾\n";
			case "what":
				answer = "我们来一起干大事\n";
				break;
			case "where":
				answer = "我来自阳澄湖\n";
				break;
			case "hi":
				answer = "hello\n";
				break;
			case "bye":
				answer = "byebye\n";
				break;
			default:
				answer = "请输入 who， 或者what， 或者where";
		}
		return answer;
	}
	public static void main(String[] args) {
		NioServer nioServer = new NioServer();
		nioServer.init();
		System.err.println("server started -- > 8899");
		nioServer.listen();
	}
}
