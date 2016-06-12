package oop.ex6.variables;

public class CharVariable extends Variable {
	private char value;
	
	public static final String VALUE_REGEX = "\'.\'";

	public CharVariable(String name) {
		super(name);
	}

	public CharVariable(String name, char value) {
		super(name);
		this.setValue(value);
	}
	
	public void setValue(char value){
		this.init = true;
		this.value = value;
	}
}
