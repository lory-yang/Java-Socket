package yang.cs.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class BioServer extends Thread {

	private int port = 2009;
	private int sequence = 1;

	public BioServer(int port) {
		this.port = port;
	}

	@Override
	public void run() {
		Socket socket = null;
		try {
			ServerSocket serverSocket = new ServerSocket(port);
			while (true) {
				socket = serverSocket.accept();
				handleSocket(socket);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		super.run();
	}

	private void handleSocket(Socket socket) throws IOException {
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
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BioServer server = new BioServer(2009);
		server.start();
	}

}
