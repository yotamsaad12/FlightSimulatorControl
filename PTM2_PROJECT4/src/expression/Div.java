package command.expression;

public class Div extends BinaryExpression {

	// constructor
	public Div(Expression left, Expression right) {
		super(left, right);
	}

	@Override
	// method 
	public double calculate() {
		return left.calculate()/right.calculate();	
	}
}
