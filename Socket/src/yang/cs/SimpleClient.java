/**
 * The project's code are similar with code in 
 * http://blog.csdn.net/shirdrn/article/details/6254821
 * http://blog.csdn.net/shirdrn/article/details/6263692
 */
package yang.cs;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class SimpleClient {

	private static void run() throws UnknownHostException, IOException, ClassNotFoundException {
		Socket socket = null;
		ObjectOutputStream out = null;
		ObjectInputStream in = null;
		try {
			socket = new Socket("localhost", 2009);
			System.out.println("Connected to locahost:2009");
			out = new ObjectOutputStream(socket.getOutputStream());
			out.flush();
			//in = new ObjectInputStream(socket.getInputStream());
			//System.out.println((String)in.readObject());
			out.writeObject("Hello World!");
			out.flush();
			//System.out.println((String)in.readObject());
			out.writeObject("bye");
			out.flush();
			//System.out.println((String)in.readObject());
		} finally {
			if (socket != null) {
				socket.close();
			}
			if (in != null) {
				in.close();
			}
			if (out != null) {
				out.close();
			}
		}
	}

	/**
	 * @param args
	 * @throws IOException 
	 * @throws UnknownHostException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
		SimpleClient.run();

	}

}
