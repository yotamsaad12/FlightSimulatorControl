package interpreter;

public class Lexer {
	
	String arg;
	
	public Lexer(String arg) {
		this.arg = arg;
	}
	
	// remove all spaces from the array
	public String[] Spacecheck(String[] args) {
		int size = 0;
		int i = 0;
		for (String a : args) {
			if (!(a.matches("[\\t\\s]") || a.matches(""))) {
				size++;	
			}
		}
		String[] checked = new String[size];
		for (String a : args) {
			if (!(a.matches("[\\t\\s]") || a.matches(""))) {
				checked[i] = a;
				i++;	
			}
		}
		return checked;
	}
	
	// take a String and split it into an array of Strings
	public String[] execute() {
		String[] arr = arg.split("[\\t\\s]");
		
		return this.Spacecheck(arr);
	}
}
