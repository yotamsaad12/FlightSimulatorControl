package command;

import java.util.Observable;
import java.util.Observer;

import command.expression.ExpressionCommand;
import interpreter.InterpreterCommand;

public class VarCommand implements Command, Observer {

	String[] c;
	double value;
	String name;
	
	public VarCommand(String name, double value) {
		this.name = name;
		this.value = value;
	}
	
	public VarCommand(String[] command) {
		//System.out.println("---var---");
		
		this.c = command;	
		value = 0;
		
		StringBuilder sb = new StringBuilder("");
		// x
		if(c.length == 1 && c[0].length() == 1) {
			name = command[0];
		}
		
		//x=241   or   y=x+3
		if(c.length == 1 && c[0].length() > 1) {
			String[] split = c[0].split("(?<!^)(?=\\D)|(?<=\\D)");
			for (int i = 0; i < split.length; i++) {
				if (!(InterpreterCommand.vars.containsKey(split[i])) && i==0) {
					name = split[i];
					sb.append("0");
				}
				else if(InterpreterCommand.vars.containsKey(split[i]) && i==0) {
					name = InterpreterCommand.vars.get(split[i]).getName();
					sb.append((int)(InterpreterCommand.vars.get(split[i]).getVar()));
				}
				else if(InterpreterCommand.vars.containsKey(split[i]) && i>0) {
					sb.append((int)(InterpreterCommand.vars.get(split[i]).getVar()));
				}
				else if (split[i].matches("=")) {
					sb.append("+");
				}
				else {
					sb.append(split[i]);
				}
			}	
				
			value = ExpressionCommand.calc(sb.toString());
			if (InterpreterCommand.bindToMap.containsKey(name)) {
				double tmpVal = InterpreterCommand.bindToMap.get(name).getValue();
				InterpreterCommand.bindToMap.get(name).setValue(ExpressionCommand.calc(sb.toString()));
				value = tmpVal;
			}
		}
		if (c.length > 1) {
			name = command[0];
			// Detect bind command inside the current command
			if (c[1].contains("=") && c[2].contains("bind")) {
				StringBuilder sb2 = new StringBuilder("");
				sb2.append(c[3].charAt(c[3].length()-1));
				String vscName = sb2.toString().toLowerCase();
				
				
				if (InterpreterCommand.bindMap.containsKey(c[3])) {
					InterpreterCommand.bindMap.get(c[3]).addObserver(this);
					
					//this is how we update the value after bind!!!
					InterpreterCommand.bindMap.get(c[3]).setValue(InterpreterCommand.bindMap.get(c[3]).getValue());
					//add to the map that detact who bound to who
					InterpreterCommand.bindToMap.put(name, InterpreterCommand.bindMap.get(c[3]));
				}else {
					//System.out.println(c[3] + "is not exist yet in the bind map");

				}
			}
			// if length of argument is more then 1 and not include bind
			if (!c[2].contains("bind")) {
				for (int i = 0; i < c.length; i++){
					if (!(InterpreterCommand.vars.containsKey(c[i])) && i==0) {
						name = c[i];
						sb.append("0");
					}
					else if(InterpreterCommand.vars.containsKey(c[i]) && i==0) {
						name = InterpreterCommand.vars.get(c[i]).getName();
						sb.append((int)(InterpreterCommand.vars.get(c[i]).getVar()));
					}
					else if(InterpreterCommand.vars.containsKey(c[i]) && i>0) {
						sb.append((int)(InterpreterCommand.vars.get(c[i]).getVar()));
					}
					else if (c[i].matches("=") && (!c[i-1].contains(c[i+1]))) {
						sb.append("+");
					} 
					else if(c[i].matches("=") && c[i-1].contains(c[i+1])) {
						sb.append("+0");
						i++;
					}
					else {
						sb.append(c[i]);
					}
				}
				value = ExpressionCommand.calc(sb.toString());
				if (InterpreterCommand.bindToMap.containsKey(name)) {
					double tmpVal = InterpreterCommand.bindToMap.get(name).getValue();
					InterpreterCommand.bindToMap.get(name).setValue(ExpressionCommand.calc(sb.toString()));
					value = tmpVal;
				}
			}
			
		}
		
	}
	
	@Override
	public double execute() {
		// TODO Auto-generated method stub
		return value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getVar() {
		return value;
	}

	public void setVar(double var) {
		this.value = var;
	}

	@Override
	public void update(Observable o, Object arg) {
		//System.out.println(this.name + " has updated from: " + this.value + " to: "+ (double)arg);
		setVar((double)arg);
	}

}
