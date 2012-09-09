package yang.cs.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.logging.Logger;

public class NioClient {

	private InetSocketAddress inetSocketAddress;
	private static final Logger log = Logger.getLogger(NioClient.class
			.getName());

	public NioClient(String host, int port) {
		inetSocketAddress = new InetSocketAddress(host, port);
	}

	public void send(String data) {
		try {
			SocketChannel socketChannel = SocketChannel.open(inetSocketAddress);
			socketChannel.configureBlocking(false);
			ByteBuffer byteBuffer = ByteBuffer.allocate(512);
			socketChannel.write(ByteBuffer.wrap(data.getBytes()));
			while (true) {
				byteBuffer.clear();
				int readBytes = socketChannel.read(byteBuffer);
				if (readBytes > 0) {
					byteBuffer.flip();
					log.info("Client: readBytes = " + readBytes);
					log.info("Client: data = " + byteBuffer.toString());
					socketChannel.close();
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new NioClient("localhost", 2009).send("Hi, this is dolly speaking!");
	}

}
