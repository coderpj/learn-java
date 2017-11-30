package learn.pengj.java.learn_java.socket;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author pengj
 * @date 2017年11月30日
 *
 */
public class SocketServer {

	private static final int port = 8888;
	private static String[] answers = {
			"server:hello!",
			"server:hi client, my name is server!",
			"server:byebye!"};
	
	public static void main(String[] args) {
//		runServer1(port);
//		runServer2(port);
		runServer3(port);
	}
	
	/**
	 * @param port 端口
	 * 简单server端
	 */
	private static void runServer1(int port) {
		ServerSocket serverSocket = null;
		Socket socket = null;
		BufferedReader bufferedReader = null;
		PrintWriter printWriter = null;
		String request = null;
		
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("服务端启动，监听端口:" + port);
			socket = serverSocket.accept();
			System.out.println("socket连接...");
			while(true) {
				// 接收客户端请求
				bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				request = bufferedReader.readLine();
				System.out.println("服务端接收：" + request);
				// 回复客户端请求
				printWriter = new PrintWriter(socket.getOutputStream(), true);
				if(request.contains("hello")) {
					printWriter.println(answers[0]);
					System.out.println("服务端发送：" + answers[0]);
				} else if(request.contains("name")) {
					printWriter.println(answers[1]);
					System.out.println("服务端发送：" + answers[1]);
				} else if(request.contains("bye")) {
					printWriter.println(answers[2]);
					System.out.println("服务端发送：" + answers[2]);
					break;
				} else {
					printWriter.println("please re input...");
					System.out.println("服务端发送：please re input...");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(printWriter != null) {
				printWriter.close();
				printWriter = null;
				System.out.println("printWriter.close()");
			}
			if(bufferedReader != null) {
				try {
					bufferedReader.close();
					bufferedReader = null;
					System.out.println("bufferedReader.close()");
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
			if(serverSocket != null) {
				try {
					serverSocket.close();
					serverSocket = null;
					System.out.println("server.close()");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
		
	}
	
	/**
	 * @param port 端口
	 */
	private static void runServer2(int port) {
		ServerSocket serverSocket = null;
		Socket socket = null;
		DataInputStream dataInputStream = null;
		DataOutputStream dataOutputStream = null;
		String request = null;
		
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("服务端启动，监听端口:" + port);
			socket = serverSocket.accept();
			System.out.println("socket连接...");
			for(String answer : answers) {
				// 接收客户端请求
				dataInputStream = new DataInputStream(socket.getInputStream());
				request = dataInputStream.readUTF();
				System.out.println("服务端接收：" + request);
				// 回复客户端请求
				dataOutputStream = new DataOutputStream(socket.getOutputStream());
				dataOutputStream.writeUTF(answer + "\n");
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
			if(serverSocket != null) {
				try {
					serverSocket.close();
					serverSocket = null;
					System.out.println("server.close()");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
	}
	
	/**
	 * @param port 端口
	 * 线程池处理客户端请求
	 */
	private static void runServer3(int port) {
		ServerSocket serverSocket = null;
		Socket socket = null;
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("服务端启动，监听端口:" + port);
			// 使用线程池
			Executor executor = Executors.newFixedThreadPool(5);
			while(true) {
				socket = serverSocket.accept();
				System.out.println("socket连接...");
				executor.execute(new ServerThread(socket));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	static class ServerThread implements Runnable{
		private Socket socket;
		
		public ServerThread(Socket socket) {
			this.socket = socket;
		}
		
		@Override
		public void run() {
			BufferedReader bufferedReader = null;
			PrintWriter printWriter = null;
			String request = null;
			System.out.println("当前线程：" + Thread.currentThread().getName());
			try {
				System.out.println("服务端启动，监听端口:" + port);
				System.out.println("socket连接...");
				while(true) {
					// 接收客户端请求
					bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					request = bufferedReader.readLine();
					System.out.println("服务端接收：" + request);
					// 回复客户端请求
					printWriter = new PrintWriter(socket.getOutputStream(), true);
					if(request.contains("hello")) {
						printWriter.println(answers[0]);
						System.out.println("服务端发送：" + answers[0]);
					} else if(request.contains("name")) {
						printWriter.println(answers[1]);
						System.out.println("服务端发送：" + answers[1]);
					} else if(request.contains("bye")) {
						printWriter.println(answers[2]);
						System.out.println("服务端发送：" + answers[2]);
						break;
					} else {
						printWriter.println("please re input...");
						System.out.println("服务端发送：please re input...");
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if(printWriter != null) {
					printWriter.close();
					printWriter = null;
					System.out.println("printWriter.close()");
				}
				if(bufferedReader != null) {
					try {
						bufferedReader.close();
						bufferedReader = null;
						System.out.println("bufferedReader.close()");
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

}
