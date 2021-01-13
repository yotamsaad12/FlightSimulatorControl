package interpreter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

import command.OpenDataServerCommand;
import command.VarCommand;
import command.VarSimulatorCommand;
public class Server {

	int port;
	int latency;
	public volatile static boolean stop;
	Object notifier;



	public Server(int port, int latency) {
		this.port = port;
		this.latency = latency;
		Server.stop = false;
		notifier = OpenDataServerCommand.getNotifier();


	}
	
	public static void disconnectServer() {
		//System.out.println("the server is disconnected" );
		stop = true;
	}
	
	public void runServer() {
		try {
			ServerSocket serverSocket = new ServerSocket(port);
			//System.out.println("Server is alive and waiting for connection...");
			Socket clientSocket = serverSocket.accept();
			String fromClient;
			String[] varNames = {"simX","simY","simZ"};
			//System.out.println("Client connected");
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			
			while(!stop) {
				fromClient = in.readLine();
				try {
					Thread.sleep(1000 / latency);
				} catch (Exception e) {}
				if (fromClient != null) {				
					String[] split = fromClient.split(",");
					//System.out.println("This is from client: " + fromClient);
						for (int i = 0; i < split.length; i++) {
								InterpreterCommand.bindMap.put(varNames[i], new VarSimulatorCommand(varNames[i], Double.parseDouble(split[i]) ));
						}
					}
					synchronized (notifier) {
						notifier.notify();
					}
				}
				
			if (stop) {
				//System.out.println("server stoped");
				clientSocket.close();
				serverSocket.close();
			}
			
		} catch (IOException e) {}
		
	}
	
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public int getParameter() {
		return latency;
	}
	public void setParameter(int parameter) {
		this.latency = parameter;
	}

	
}
