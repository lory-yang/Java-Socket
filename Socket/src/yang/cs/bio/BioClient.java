package yang.cs.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;

public class BioClient {

	private int port = 2009;
	private String host;
	
	public BioClient(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public void send(byte[] data) {
		Socket socket = null;
		OutputStream out = null;
		InputStream in = null;
		try {
			socket = new Socket(host, port);
			out = socket.getOutputStream();
			out.write(data);
			out.flush();
			in = socket.getInputStream();
			byte[] buffer = new byte[128];
			int receivedBytes;
			if((receivedBytes = in.read(buffer))!=-1){
				System.out.println("Client: received msg from server: " + new String(buffer, 0, receivedBytes));
			}
			/*out.write("bye".getBytes());
			out.flush();*/
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(socket != null){
					socket.close();
				}
				if(out != null){
					out.close();
				}
				if(in !=null){
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		StringBuffer data = new StringBuffer();
		Date start = new Date();
		for (int i = 0; i < 5000; i++) {
			data.delete(0, data.length());
			data.append("I'm a client ").append(i).append(".");
			BioClient client = new BioClient("localhost", 2009);
			client.send(data.toString().getBytes());
		}
		Date end = new Date();
		long cost = end.getTime() - start.getTime();
		System.out.println("Time cost: " + cost);
	}

}
