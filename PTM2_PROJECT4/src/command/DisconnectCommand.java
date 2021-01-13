package command;

import java.io.IOException;
import java.io.PrintWriter;

import interpreter.InterpreterCommand;

public class DisconnectCommand implements Command {

	String[] c;
	
	public DisconnectCommand(String[] command) {
		//System.out.println("---disconnect---");
		this.c = command;
		this.execute();
		

	}
	
	@Override
	public double execute() {
		try {
			PrintWriter out = new PrintWriter(ConnectCommand.clientSocket.getOutputStream());
			out.println("bye");
			//System.out.println("the client sent bye to the simulator");
			OpenDataServerCommand.server.disconnectServer();
			out.flush();
		} catch (IOException e) {}
	
		return 0;
	}

}
