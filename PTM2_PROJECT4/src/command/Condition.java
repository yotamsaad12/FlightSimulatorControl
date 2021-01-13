package command;

public class Condition {

	public Condition() {
		// TODO Auto-generated constructor stub
	}

	
	public static boolean checkIf(double left, String sign, double right) {
		switch (sign) {
		case "<": {
			
			return left < right;
		}
		case ">": {
			
			return left > right;
		}
		case "==": {
			
			return left == right;
		}
		case "!=": {
			
			return left != right;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + sign);
		}
		
	}
	
}
