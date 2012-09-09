package yang.cs.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BioMultiThreadServer extends Thread {

	private int port;
	private int sequence = 1;
	
	private ExecutorService pool;
	private final int poolSize = 1000;
	
	private final int threadCount = 1000;
	
	public BioMultiThreadServer(int port) {
		this.port = port;
		this.pool = Executors.newFixedThreadPool(poolSize);
	}
	
	@Override
	public void run() {
		Socket socket = null;
		try {
			boolean flag = false;
			int count = 0;
			ServerSocket serverSocket = new ServerSocket(port);
			Date start = null;
			while (true) {
				socket = serverSocket.accept();
				if (!flag) {
					start = new Date();
					flag = true;
				}
				pool.execute(new RequestHandler(socket));
				if(++count== threadCount){
					flag = false;
					Date end = new Date();
					System.out.println(threadCount+" client requests spends: " + (end.getTime() -start.getTime()));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	class RequestHandler implements Runnable{
		
		private Socket socket;
		
		public RequestHandler(Socket socket) {
			this.socket = socket;
		}
		
		@Override
		public void run() {
			try {
				InputStream in = socket.getInputStream();
				OutputStream out = socket.getOutputStream();
				byte[] buffer = new byte[128];
				int receivedBytes;
				String msg = "";
				if ((receivedBytes = in.read(buffer)) != -1) {
					msg = new String(buffer, 0, receivedBytes);
					System.out.println("Server: received client message: " + msg);
					if (msg.startsWith("I'm a client")) {
						String resp = "I'm the server, you'r the " + sequence++
								+ "'th client";
						out.write(resp.getBytes());
					}
					/*if(msg.startsWith("bye")){
						break;
					}*/
				}
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BioMultiThreadServer server = new BioMultiThreadServer(2009);
		server.start();
	}

}
