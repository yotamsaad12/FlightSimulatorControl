package command;
import java.lang.StringBuilder;
import java.util.ArrayList;
import java.util.List;

import interpreter.InterpreterCommand;
import interpreter.Lexer;
import interpreter.Parser;

public class WhileCommand implements Command {

	String[] c;
	
	public WhileCommand(String[] command) {
		// list of commands inside the loop[
		List<String> varsCommands = new ArrayList <String>();
		// condition left and right values
		VarCommand left, right = null;
		// the sign inside the condition
		String sign;
		this.c = command;
		StringBuilder sb = new StringBuilder("");
		//System.out.println("---while---");
		int i = 0;
		
		//find left
		while(!(c[i].matches("[<>!=]"))) {
			sb.append(c[i]);
			i++;
		}
		// if it is a avar then we will take hem from the map if not we will create a temp value
		if (InterpreterCommand.vars.containsKey(sb.toString())) {
			left = InterpreterCommand.vars.get(sb.toString());
		}else {
			left = new VarCommand("leftNum", Double.parseDouble(sb.toString()));
			InterpreterCommand.vars.put("leftNum", left);
		}
		
		
		//find sign / condition
		sb = new StringBuilder("");
		while(c[i].matches("[<>!=]")){
			sb.append(c[i]);
			i++;
		}
		sign = sb.toString();
		
		//find right
		sb = new StringBuilder("");
		while(!(c[i].contains("{"))) {
			sb.append(c[i]);
			i++;
		}
		
		// if it is a var then we will take hem from the map if not we will create a temp value
		if (InterpreterCommand.vars.containsKey(sb.toString())) {
			left = InterpreterCommand.vars.get(sb.toString());
		}else {
			right = new VarCommand("rightNum", Double.parseDouble(sb.toString()));
			InterpreterCommand.vars.put("rightNum", right);
		}
		
		
		i++;
		
		// find expressions
		sb = new StringBuilder("");
		
		while(!(c[i].contains("}"))) {
			if (c[i].contains("=")) {
				if (c[i-1].contains(c[i+1])) {
					c[i+1] = "0";
				}
			}
			if (c[i].contains(";")) {
				i++;
				varsCommands.add(sb.toString());
				sb = new StringBuilder("");
			}else {
				sb.append(c[i]);
				i++;				
			}
		}
		
		while(Condition.checkIf(left.getVar(), sign, right.getVar())) {
			for (String vc : varsCommands) {
				Lexer l = new Lexer(vc);
				String[] arr = l.execute();
				Parser p = new Parser(arr);
				InterpreterCommand.vars.put(((VarCommand) (p.getCommand())).getName(), (VarCommand) p.getCommand());
				//now we need to update the value of left and right, this is optional to use a map
				if(!left.getName().contains("leftNum")) {
					left = InterpreterCommand.vars.get(left.getName());
				}
				if(!right.getName().contains("rightNum")) {
					right = InterpreterCommand.vars.get(right.getName());
				}
				
			}
			
		}
		
		
	}
	
	@Override
	public double execute() {
		// TODO Auto-generated method stub
		return 0;
	}

}
