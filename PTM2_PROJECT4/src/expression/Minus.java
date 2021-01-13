package command.expression;

public class Minus extends BinaryExpression{

	// constructor
	public Minus(Expression left, Expression right) {
		super(left, right);
	}

	@Override
	// method 
	public double calculate() {
		return left.calculate() - right.calculate();
	}
}
