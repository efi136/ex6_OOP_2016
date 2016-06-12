package oop.ex6.variables;

public class BooleanVariable extends Variable {
	private boolean value;
	
	public static final String VALUE_REGEX = "(true|false|"+DoubleVariable.VALUE_REGEX+")";
	public static final String DECLERATION = "boolean ("+Variable.NAME_REGEX+")";
	
	public BooleanVariable(String name) {
		super(name);
	}

	public BooleanVariable(String name, boolean value) {
		super(name);
		this.setValue(value);
	}
	
	public void setValue(boolean value){
		this.init = true;
		this.value = value;
	}
}
