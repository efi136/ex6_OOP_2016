package oop.ex6.variables;

public class StringVariable extends Variable {
	
	private String value;
	public static final String VALUE_REGEX = "\"\\w*\"";
	public static final String ASSIGNMENT = Variable.NAME_REGEX + "(\\s*=\\s*"+VALUE_REGEX+")?";
	public static final String TYPE = "String";
	public static final String DECLERATION = TYPE+"\\s*"+ASSIGNMENT+"(\\s*,\\s*"+ASSIGNMENT+")*;";
	public StringVariable(String name) {
		super(name);
	}

	public StringVariable(String name, String value) {
		super(name);
		this.setValue(value);
	}
	
	public void setValue(String value){
		this.init = true;
		this.value = value;
	}
	
}
