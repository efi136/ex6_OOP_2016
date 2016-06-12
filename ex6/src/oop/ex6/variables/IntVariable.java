package oop.ex6.variables;

public class IntVariable extends Variable {
	
	private int value;
	public static final String VALUE_REGEX = "-?[0-9]+";
	public static final String ASSIGNMENT = Variable.NAME_REGEX + "(\\s*=\\s*"+VALUE_REGEX+")?";
	public static final String TYPE = "int";
	public static final String DECLERATION = TYPE+"\\s*"+ASSIGNMENT+"(\\s*,\\s*"+ASSIGNMENT+")*;";
	
	public IntVariable(String name) {
		super(name);
	}

	public IntVariable(String name, int value) {
		super(name);
		this.setValue(value);
	}
	
	public void setValue(int value){
		this.init = true;
		this.value = value;
	}
	
}
