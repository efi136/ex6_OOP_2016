package oop.ex6.variables;

public class DoubleVariable extends Variable {
	private double value;
	public static final String VALUE_REGEX = "-?[0-9]+(.[0-9])?[0-9]*";
	
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
