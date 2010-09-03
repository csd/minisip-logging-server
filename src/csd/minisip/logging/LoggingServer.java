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

		// TCP port on which it listens for logs
		int port_number = 0;
		port_number = Integer.parseInt(args[0]);

		// Log directory to save the log files
		logDirectory = args[1];

		// Creates the logging folder
		File loggingDirectory = new File(logDirectory);
		if (!loggingDirectory.exists()) {
			if (loggingDirectory.mkdir()) {
				System.out.println("Creating the Log directory in "
						+ logDirectory);
			}
		}

		// Crash report directory
		crashDirectory = args[2];
		
		// Creates the Crash
		File crashReportDirectory = new File(crashDirectory);
		if (!crashReportDirectory.exists()) {
			if (crashReportDirectory.mkdir()) {
				System.out.println("Creating the Crash report directory in "
						+ crashDirectory);
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
