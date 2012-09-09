package yang.cs;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleService {

	private static void run() throws IOException, ClassNotFoundException {
		ServerSocket socketSv = null;
		ObjectInputStream inputStream = null;
		ObjectOutputStream outputStream = null;
		try {
			socketSv = new ServerSocket(2009, 10);
			System.out.println("Waiting for connection...");
			Socket connection = socketSv.accept();
			System.out.println("Connection received: "
					+ connection.getInetAddress().getHostName());
			outputStream = new ObjectOutputStream(connection.getOutputStream());
			//outputStream.flush();
			inputStream = new ObjectInputStream(connection.getInputStream());
			//outputStream.flush();
			System.out.println("Send msg");
			String msg = "";
			do {
				msg = inputStream.readLine();
				System.out.print(msg);
			} while (msg != null);
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
				if (outputStream != null) {
					outputStream.close();
				}
				if (socketSv != null) {
					socketSv.close();
				}
			} catch (IOException e) {
				throw e;
			}
		}
	}

	/**
	 * @param args
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		SimpleService.run();
	}

}
