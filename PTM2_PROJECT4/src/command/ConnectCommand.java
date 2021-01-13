package command;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectCommand implements Command {

	static Socket clientSocket;
	String[] c;
	String ip;
	int port;
	static PrintWriter out;

	
	public ConnectCommand(String[] command) {
		this.c = command;
		this.ip = this.c[0];
		this.port = Integer.parseInt(c[1]);
	//	System.out.println("---connect---");
		execute();
	}
	@Override
	public double execute() {
		try {
			
			clientSocket = new Socket(c[0], Integer.parseInt(c[1]));
			out = new PrintWriter(clientSocket.getOutputStream());
			//System.out.println("Client is connected to a remote Server.");
			
		} catch (NumberFormatException | IOException e) { e.printStackTrace(); }
		
		return 0;
	}

}
