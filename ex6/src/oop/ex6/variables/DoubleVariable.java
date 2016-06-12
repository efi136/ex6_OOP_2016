package oop.ex6.variables;

public class DoubleVariable extends Variable {
	private double value;
	public static final String VALUE_REGEX = "-?[0-9]+(.[0-9])?[0-9]*";
	public static final String ASSIGNMENT = Variable.NAME_REGEX + "(\\s*=\\s*"+VALUE_REGEX+")?";
	public static final String ASSIGNMENT_LINE = Variable.NAME_REGEX + "(\\s*=\\s*"+VALUE_REGEX+");";
	public static final String TYPE = "double";
	public static final String DECLERATION = "(final \\s*)?" + TYPE+"\\s*"+ASSIGNMENT+"(\\s*,\\s*"+ASSIGNMENT+")*;";
	public DoubleVariable(String name) {
		super(name);
	}

	public DoubleVariable(String name, double value) {
		super(name);
		this.setValue(value);
	}
	
	public DoubleVariable(String name, double value, boolean fin){
		super(name);
		this.setValue(value);
		this.fin = fin;
	}
	
	public void setValue(double value){
		this.init = true;
		this.value = value;
	}
	
}
