package learn.pengj.java.learn_java.socket;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author pengj
 * @date 2017年11月30日
 *
 */
public class SocketClient {

	private static final String ip = "127.0.0.1";
	private static final int port = 8888;
	private static String[] talks = {
			"client:hello!",
			"client:my name is client, what is your name?",
			"client:bye!"};
	
	
	public static void main(String[] args) {
		runClient1(ip, port);
//		runClient2(ip, port);
	}
	
	private static void runClient1(String ip, int port) {
		Socket socket = null;
		String response = null;
		BufferedReader br = null;
		PrintWriter pw = null;

		try {
			socket = new Socket(ip, port);
			System.out.println("客户端启动...");
//			int i = 0;
//			while(true) {
//				pw = new PrintWriter(socket.getOutputStream(), true);
//				pw.println(talks[i]);
//				System.out.println("客户端发送:" + talks[i]);
//				br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
//				response = br.readLine();
//				System.out.println("客户端接收:" + response);
//				if(i == 2) {
//					break;
//				} else {
//					i++;
//				}
//			}
			for(String talk : talks) {
				pw = new PrintWriter(socket.getOutputStream(), true);
				pw.println(talk + "\n");
				System.out.println("客户端发送:" + talk);
				br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
				response = br.readLine();
				System.out.println("客户端接收:" + response);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(pw != null) {
				pw.close();
				pw = null;
				System.out.println("pw.close()");
			}
			if(br != null) {
				try {
					br.close();
					br = null;
					System.out.println("br.close()");
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
		}
	}
	
		
	private static void runClient2(String ip, int port) {
		
		String[] talks = {
				"client:hello!",
				"client:my name is client, what is your name?",
				"client:bye!"};
		
		Socket socket = null;
		DataInputStream dataInputStream = null;
		DataOutputStream dataOutputStream = null;
		String response = null;
		
		try {
			socket = new Socket(ip, port);
			dataInputStream = new DataInputStream(socket.getInputStream());
			dataOutputStream = new DataOutputStream(socket.getOutputStream());
			for(String talk : talks) {
				 // 服务端发送內容
				 dataOutputStream = new DataOutputStream(socket.getOutputStream());
				 dataOutputStream.writeUTF(talk);
				 System.out.println("客户端发送：" + talk);
				 // 接收服务端内容
//				 dataInputStream = new DataInputStream(socket.getInputStream());
//				 response = dataInputStream.readUTF();
//				 System.out.println("客户端接收：" + responose);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(dataOutputStream != null) {
				try {
					dataOutputStream.close();
					dataOutputStream = null;
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
		}
	}

}
