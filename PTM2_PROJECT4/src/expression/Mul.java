package command.expression;

public class Mul extends BinaryExpression{

	// constructor
	public Mul(Expression left, Expression right) {
		super(left, right);
	}
	
	@Override
	// method 
	public double calculate() {
		return left.calculate() * right.calculate();	
	}	
}
