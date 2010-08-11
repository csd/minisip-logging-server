package csd.minisip.logging;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

class ServerWorker extends Thread {

	DataInputStream is = null;
	File fileOut;
	FileOutputStream streamOut;
	
	Socket clientSocket = null;
	

	public ServerWorker(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	public void run() {
		
		//retrieves the IP address of the MiniSIP client
		String clientIP = clientSocket.getInetAddress().toString().substring(1);
		System.out.println("Recevied Logs from " + clientIP);
		
		try {
			is = new DataInputStream(clientSocket.getInputStream());
			fileOut = new File(clientIP+"-MiniSIP.log");
			streamOut = new FileOutputStream(fileOut);
			
			// Reads from the stream and writes to the log file
			
			int c;
			while ((c = is.read()) != -1) {
				streamOut.write(c);
			}

			streamOut.close();
			is.close();
			clientSocket.close();
			
		} catch (IOException e) {
			System.out.println("Error reading from the socket stream ");
		}
	}
}