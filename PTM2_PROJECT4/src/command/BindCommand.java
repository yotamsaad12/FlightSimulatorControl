package command;

public class BindCommand implements Command {

	String[] c;
	
	public BindCommand(String[] command) {
		this.c = command;
		System.out.println("---bind---");

	}
	
	@Override
	public double execute(String[] arg) {
		// TODO Auto-generated method stub
		return 0;
	}

}
