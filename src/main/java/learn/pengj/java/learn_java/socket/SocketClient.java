package learn.pengj.java.learn_java.socket;

import java.io.BufferedReader;
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

	public static void main(String[] args) {
		
			String[] talks = {
					"client:hello!",
					"client:my name is client, what is your name?",
					"client:bye!"};
			
			Socket socket = null;
			String response = null;
			BufferedReader br = null;
			PrintWriter pw = null;
			
			try {
				socket = new Socket("127.0.0.1", 12580); // ip,port
				br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
				pw = new PrintWriter(socket.getOutputStream(), true);
				for(String talk : talks) {
					pw.println(talk);
					System.out.println("客户端发送:" + talk);
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

}
