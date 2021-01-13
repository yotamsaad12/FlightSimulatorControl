package test;

import interpreter.InterpreterCommand;


public class MyInterpreter {
	
	public static  int interpret(String[] lines){
		
		InterpreterCommand ic = new InterpreterCommand(lines);
		
		return (int) ic.interpret();
	}
}
