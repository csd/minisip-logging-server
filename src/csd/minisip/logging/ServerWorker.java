package csd.minisip.logging;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

class ServerWorker extends Thread {

	DataInputStream is = null;
	File logFile;
	File crashReportFile;
	FileOutputStream logStreamOut;
	FileOutputStream crashStreamOut;
	
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
			//hack for the time being
			if(!("192.16.126.67".equals(clientIP))){
				logFile = new File(LoggingServer.logDirectory+"/"+clientIP+ "-MiniSIP"+System.currentTimeMillis()+".log");
				logStreamOut = new FileOutputStream(logFile,true);
				
			}else{
				return;
			}
			// Reads from the stream and writes to the log file
			int c,count=0;
			String type=null;
			while (true) {
				//Waits the thread
				try{
					Thread.sleep(10);
				}catch(Exception e){
					System.out.println("Error blocking the thread");
				}
				
				if((c = is.read()) != -1){
					//check for crash report and logs
					if(count == 0){
						if((char)c!='<'){
							crashReportFile = new File(LoggingServer.crashDirectory+"/"+clientIP+ "-MiniSIP"+System.currentTimeMillis()+".log");
							crashStreamOut = new FileOutputStream(crashReportFile,true);
							type="crash";
						}else{
							type="logging";
						}
					}
					count++;
					
					if("crash".equals(type)){
						crashStreamOut.write(c);
					}
					if("logging".equals(type)){
						logStreamOut.write(c);
					}
				}
			}
		
		} catch (IOException e) {
			System.out.println("Error reading from the socket stream " + e);
		}
	}
}