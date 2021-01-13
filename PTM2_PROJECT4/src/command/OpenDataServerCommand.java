package command;

import interpreter.Server;

public class OpenDataServerCommand implements Command {
	static Server server;
	static Object notifier = new Object();
	String[] c;
	
	public OpenDataServerCommand(String[] command) {
		this.c = command;
		//System.out.println("---open server---");
		this.execute();

	}
	@Override
	public double execute() {
		server = new Server(Integer.parseInt (c[0]), Integer.parseInt(c[1]));
		try {
			new Thread(()-> server.runServer()).start();
			//server.run();
		} catch (Exception e) {}
		
		synchronized(notifier)	{
			 try {
				notifier.wait();
			} catch (InterruptedException e) {}
		}
		return 0;
	}
		
	public static Object getNotifier() {
		return notifier;
	}

}
