package csd.minisip.logging;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

class ServerWorker extends Thread {

	DataInputStream is = null;
	BufferedReader bufferedReader = null;
	BufferedWriter bufferedWriter = null;
	File logFile;
	FileOutputStream logStreamOut;
	
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
			bufferedReader = new BufferedReader(new InputStreamReader(is));
			
			// Reads from the stream and writes to the log file
			int count=0;
			while (true) {
				String line=bufferedReader.readLine();
				
				//Checks whether the stream is a log stream
				if(line != null && line.contains("<log>")){
					if(count == 0){
						logFile = new File(LoggingServer.logDirectory+"/"+clientIP+ "-MiniSIP"+System.currentTimeMillis()+".log");
						logStreamOut = new FileOutputStream(logFile,true);
						bufferedWriter = new BufferedWriter(new OutputStreamWriter(logStreamOut));
					}
					
					if(bufferedWriter != null){
						bufferedWriter.write(line+"\n");
						bufferedWriter.flush();
					}
					count++;
				}
				
				//Waits the thread
				try{
					Thread.sleep(5);
				}catch(Exception e){
					System.out.println("Error blocking the thread");
				}
			}
		
		} catch (IOException e) {
			System.out.println("Error reading from the socket stream " + e);
		}
	}
}