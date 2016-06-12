package oop.ex6.variables;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BooleanVariable extends Variable {
	private boolean value;
	
	public static final String VALUE_REGEX = "(true|false|"+DoubleVariable.VALUE_REGEX+")";
	public static final String ASSIGNMENT = Variable.NAME_REGEX + "(\\s*=\\s*"+VALUE_REGEX+")?";
	public static final String ASSIGNMENT_LINE = Variable.NAME_REGEX + "(\\s*=\\s*"+VALUE_REGEX+");";
	public static final String TYPE = "boolean";
	public static final String DECLERATION = "(final \\s*)?" +  TYPE+"\\s*"+ASSIGNMENT+"(\\s*,\\s*"+ASSIGNMENT+")*;";

	public static boolean isBooleanVariableDec(String line){
		Pattern p = Pattern.compile(DECLERATION);
		Matcher m = p.matcher(line);
		return m.matches();
	}
	
	
	public BooleanVariable(String name) {
		super(name);
	}

	public BooleanVariable(String name, boolean value) {
		super(name);
		this.setValue(value);
	}
	
	public BooleanVariable(String name, boolean value, boolean fin){
		super(name);
		this.setValue(value);
		this.fin = fin;
	}
	
	public void setValue(boolean value){
		this.init = true;
		this.value = value;
	}
}
