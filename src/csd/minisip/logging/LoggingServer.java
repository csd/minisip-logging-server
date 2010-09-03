package csd.minisip.logging;

import java.io.File;
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

	static String logDirectory=null;
	static String crashDirectory=null;
	static Socket clientSocket = null;
	static ServerSocket serverSocket = null;

	public static void main(String args[]) {

		if(args.length!=6){
			System.out.println("Invalid Arguements");
			System.out.println("Usage:");
			System.out.println("-p <port> -ld <log directory> -cd <crash directory>");
			System.exit(1);
		}
		
		// TCP port on which it listens for logs
		int port_number = 0;
		port_number = Integer.parseInt(args[1]);

		// Log directory to save the log files
		logDirectory = args[3];
		
		//Crash directory to save the crash reports
		crashDirectory= args[5];

		// Creates the logging folder
		File loggingDirectory = new File(logDirectory);
		if (!loggingDirectory.exists()) {
			if (loggingDirectory.mkdir()) {
				System.out.println("Creating the Log directory in "
						+ logDirectory);
			}
		}

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
