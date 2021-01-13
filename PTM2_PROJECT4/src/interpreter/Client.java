package interpreter;
import java.net.*;
import java.io.*;

public class Client implements Runnable {
	
	static String ip;
	static int port;
	static PrintWriter out;
	public static Socket s;
	public volatile static boolean stop;
;

	public Client(String ip, int port) {
		Client.port = port;
		Client.ip = ip;

	}
	
	@Override
	public void run() {
		try {
			
				s  = new Socket(ip,port);
				out = new PrintWriter(s.getOutputStream());
				//System.out.println("Client has connected to:"+ip );
				
			
		} catch (Exception e) {
			//System.out.println("Client had truble connected");
		}
		
	}
	
	public static Socket getSocket() {
		return s;
	}
	
	

	
}
