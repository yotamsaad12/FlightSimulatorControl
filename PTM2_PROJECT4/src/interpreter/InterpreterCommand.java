package interpreter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import command.Command;
import command.VarCommand;
import command.VarSimulatorCommand;

public class InterpreterCommand {
	
	//the arguments from MyInterpreter
	String[] args;
	// <Name of the command, The command>
	Map<String, Command> commands;
	// <Name of the variable, The variable> 
	public static Map<String, VarCommand> vars;
	// <The name of the variable in the simulator, List of the variables in our program that bind to it>
	public static Map<String, VarSimulatorCommand> bindMap;
	// <The name of the varCommand, varSimulator he is bind to it>
	public static Map<String, VarSimulatorCommand> bindToMap;

	
	public InterpreterCommand(String args[]) {
		commands = new HashMap<String, Command>();
		vars = new HashMap<String, VarCommand>();
		bindMap = new HashMap<String, VarSimulatorCommand>();
		bindToMap = new HashMap<String, VarSimulatorCommand>();
		
		
		for (int i = 0; i < args.length; i++) {
			String arg = args[i];
			if (arg.contains("{")) {
				StringBuilder sb = new StringBuilder("");
				while(i < args.length) {
					if (args[i].contains("}")) { 
						sb.append(" "+args[i]);					
						break; 
						}
					
					sb.append(args[i]);
					i++;
					
					char check = sb.toString().charAt(sb.toString().length() - 1);
					if (check != '{') {						
						sb.append(" ;");
					}
					
				}
				arg = sb.toString();
				
			}
			Lexer l = new Lexer(arg);
			String[] arr = l.execute();
			Parser p = new Parser(arr);
			
			// when we know this is a "var"
			if(arr[0].matches("var") || arr[0].contains("=")) {
				vars.put(((VarCommand) (p.getCommand())).getName(), (VarCommand) p.getCommand());
			} else {		
				commands.put(p.getCommandName(), p.getCommand());
			}
			
		}
	}
	
	public double interpret() {
		return commands.get("return").execute();
	}
	
	public static void cleanObservers() {
		for (Entry<String, VarSimulatorCommand> e : bindMap.entrySet()) {
			e.getValue().deleteObservers();
		}
	}

}
