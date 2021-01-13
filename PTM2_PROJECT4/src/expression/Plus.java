package command.expression;

public class Plus extends BinaryExpression{

	// constructor
	public Plus(Expression left, Expression right) {
		super(left, right);
	}
	
	@Override
	// method 
	public double calculate() {
		return left.calculate() + right.calculate();
	}

}
