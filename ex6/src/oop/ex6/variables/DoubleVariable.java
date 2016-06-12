package oop.ex6.variables;

public class DoubleVariable extends Variable {
	private double value;
	
	public DoubleVariable(String name) {
		super(name);
	}

	public DoubleVariable(String name, double value) {
		super(name);
		this.setValue(value);
	}
	
	public void setValue(double value){
		this.init = true;
		this.value = value;
	}
	
}
