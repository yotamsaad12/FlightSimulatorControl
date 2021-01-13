package command;

import java.util.Observable;

public class VarSimulatorCommand extends Observable implements Command {

	String name;
	double value;
	
	public VarSimulatorCommand(String name, double value) {
		this.name = name;
		this.value = value;
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

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
		setChanged();
		notifyObservers(value);
	}

}
