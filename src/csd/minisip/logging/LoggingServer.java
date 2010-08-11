package csd.minisip.logging;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Logging Server class which accepts the logging information from MiniSIP
 * 
 * @author kasun samarasinghe kasunws@kth.se
 * 
 */
public class LoggingServer {

	static Socket clientSocket = null;
	static ServerSocket serverSocket = null;

	public static void main(String args[]) {

		//TCP port on which it listens for logs
		int port_number = Integer.parseInt(args[0]);

		try {
			serverSocket = new ServerSocket(port_number);
			System.out.println("Logging Server started on port " + port_number);
		} catch (IOException e) {
			System.out.println(e);
		}

		while (true) {
			try {
				clientSocket = serverSocket.accept();
				ServerWorker handler = new ServerWorker(clientSocket);
				handler.start();
			} catch (IOException e) {
				System.out.println("Error accepting requests");
			}
		}
	}
}
