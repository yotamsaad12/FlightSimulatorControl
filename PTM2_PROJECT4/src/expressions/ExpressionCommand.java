package command.expression;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;


public class ExpressionCommand {

	public static double calc(String expression) {
		Queue<String> queue = new LinkedList<String>();
		Stack<String> stack = new Stack<String>();
		Stack<Expression> stackExp = new Stack<Expression>();
		String[] split = expression.split("(?<=[-+*/()])|(?=[-+*/()])");
		
	for (int i = 0; i < split.length; i++) {
		/*if (split.length == 1) {
			System.out.println(split[0]);
			return Math.round(Double.parseDouble(split[0]));
		}*/
		if (Character.isDigit(split[i].charAt(0))) {
			queue.add(split[i]);
		}
		else {
			switch (split[i].charAt(0)) {
			case '(':
				stack.add("(");
				break;
				
			case ')':
				while(stack.peek() != "(") {
					queue.add(stack.pop());
				}
				stack.pop();
				break;
				
			case '+':
				if (stack.isEmpty() || stack.peek() == "(") {
					stack.add("+");
				}
				else {
					queue.add(stack.pop());
					i--;
				}
				break;
			case '-':
				if (stack.isEmpty() || stack.peek() == "(") {
					stack.add("-");
				}
				else {
					queue.add(stack.pop());
					i--;
				}
				break;
				
			case '*':
				if (stack.isEmpty() || stack.peek() == "(" || stack.peek() == "-" || stack.peek() == "+" ) {
					stack.add("*");
				}
				else {
					queue.add(stack.pop());
					i--;
				}
				break;
				
			case '/':
				if (stack.isEmpty() || stack.peek() == "(" || stack.peek() == "-" || stack.peek() == "+" ) {
					stack.add("/");
				}
				else {
					queue.add(stack.pop());
					i--;
				}
				break;
				
			default: 
				break;
			}
			
			
		}
		
	}
	while (!stack.isEmpty()) {
		queue.add(stack.pop());
	}
	
	while (!queue.isEmpty()) {
		if (Character.isDigit(queue.peek().charAt(0))) {
			stackExp.push(new Number(Double.parseDouble(queue.poll())));
		}
		else {
			Expression right = stackExp.pop();
			Expression left = stackExp.pop();
			
			switch (queue.poll()) {
		    case "/":
		    	stackExp.push(new Div(left, right));
		        break;
		    case "*":
		    	stackExp.push(new Mul(left, right));
		        break;
		    case "+":
		    	stackExp.push(new Plus(left, right));
		        break;
		    case "-":
		    	stackExp.push(new Minus(left, right));
		        break;
			}
		}
	}
		return Math.round(stackExp.pop().calculate());
	}
}
