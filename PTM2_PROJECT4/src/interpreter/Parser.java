package interpreter;

import command.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Parser {
	String[] args;
	String commandName;
	Command command;
	
	public Parser(String[] args) {
		this.args = args;
				
		switch (args[0]) {
		
		case "return":  {
			commandName = "return";
			command = new ReturnCommand(Arrays.copyOfRange(args,1,args.length));
			break;
		}
		
		case "var":  {
			commandName = "var";
			command = new VarCommand(Arrays.copyOfRange(args,1,args.length));
			break;
		}
		
		case "openDataServer":  {
			commandName = "openDataServer"; 
			command = new OpenDataServerCommand(Arrays.copyOfRange(args,1,args.length));
			break;
		}
		
		case "connect":  {
			commandName = "connect";
			command = new ConnectCommand(Arrays.copyOfRange(args,1,args.length));
			break;
		}
		
		case "disconnect":  {
			commandName ="disconnect"; 
			command = new DisconnectCommand(Arrays.copyOfRange(args,1,args.length));
			break;
		}
		
		case "while":  {
			commandName = "while"; 
			command = new WhileCommand(Arrays.copyOfRange(args,1,args.length));
			break;
		}
		
		default:
			boolean flag = false;
			for (String a : args) {
				if(a.contains("=")) flag = true;
			}
			if (flag) {
				commandName = "var"; 
				command = new VarCommand(Arrays.copyOfRange(args,0,args.length));
				break;
			} else {
				//System.out.println("Unexpected value: " + args[0]);
			}	
			
		}
	}

	public Command getCommand() {
		return command;
	}
	public String getCommandName() {
		return commandName;
	}

	

}
