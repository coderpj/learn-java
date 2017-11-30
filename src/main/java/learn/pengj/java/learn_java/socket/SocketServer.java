package learn.pengj.java.learn_java.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author pengj
 * @date 2017年11月30日
 *
 */
public class SocketServer {

	public static void main(String[] args) {
		
		String[] answers = {
				"client:hello!",
				"client:my name is client, what is your name?",
				"client:bye!"};
		
		ServerSocket server = null;
		Socket socket = null;
		DataInputStream dataInputStream = null;
		DataOutputStream dataOutputStream = null;
		String request = null;
		
		try {
			server = new ServerSocket(12580); // ip
			socket = server.accept();
			System.out.println("服务端启动，监听端口：12580");
			for(String answer : answers) {
				// 接收客户端请求
				dataInputStream = new DataInputStream(socket.getInputStream());
				request = dataInputStream.readUTF();
				System.out.println("服务端接收：" + request);
				// 回复客户端请求
				dataOutputStream = new DataOutputStream(socket.getOutputStream());
				dataOutputStream.writeUTF(answer);
				System.out.println("服务端发送：" + answer);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(dataOutputStream != null) {
				try {
					dataOutputStream.close();
					dataInputStream = null;
					System.out.println("dataOutputStream.close()");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(dataInputStream != null) {
				try {
					dataInputStream.close();
					dataInputStream = null;
					System.out.println("dataInputStream.close()");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(socket != null) {
				try {
					socket.close();
					socket = null;
					System.out.println("socket.close()");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(server != null) {
				try {
					server.close();
					server = null;
					System.out.println("server.close()");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
		
		
	}

}
