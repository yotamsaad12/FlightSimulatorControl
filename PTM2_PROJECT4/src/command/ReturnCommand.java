package command;
import command.expression.ExpressionCommand;
import interpreter.InterpreterCommand;
public class ReturnCommand implements Command {

	String[] c;
	double value;
	
	public ReturnCommand(String[] command) {
		this.value = 0;
		this.c = command;
	//	System.out.println("---return---");
		
		// return x 
		if (c.length == 1 && InterpreterCommand.vars.containsKey(c[0])) {
			value = InterpreterCommand.vars.get(c[0]).getVar();
		}
		if(c.length == 1 && !isExpression()) {
			varExpression();
		}
		
		
		// 228 * 5 - (8+2)
		if(isExpression()) { // if is it an expression
			StringBuilder sb = new StringBuilder("");
			for (String str : c) {
				sb.append(str);
			}
			value = ExpressionCommand.calc(sb.toString());
		} else if(c.length == 1 && InterpreterCommand.vars.containsKey(c[0])) {
			value = InterpreterCommand.vars.get(c[0]).getVar();
		}
	}

	// method that check if the array is an expression
	private boolean isExpression() {
		for (int i = 0; i < c.length; i++) {
			if (!(c[i].matches("([-+*/()])|(?=[-+*/()])") || c[i].contains("(") || Character.isDigit(c[i].charAt(0)))){
				return false;
			}
		}
		return true;
	}
	
	//x+y*z
	private void varExpression() {
		String[] split = c[0].split("");
		this.c = new String[split.length];
		for  (int i = 0; i < split.length; i++) {
			if (InterpreterCommand.vars.containsKey(split[i])) {
				if (split[i].matches("x")) { // CHANGED!!!!!!!!!!!!
					c[i] = "0";
				}else {
				c[i] = "" + InterpreterCommand.vars.get(split[i]).getVar();}
			} else if(InterpreterCommand.bindToMap.containsKey(split[i])) {
				c[i] = "" + InterpreterCommand.bindToMap.get(split[i]).getValue();
			}else {
				c[i] = split[i];
			}
		}
	}
	
	@Override
	public double execute() {
		return value;
	}

}
