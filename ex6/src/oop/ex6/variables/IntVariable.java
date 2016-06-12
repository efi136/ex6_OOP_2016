package oop.ex6.variables;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IntVariable extends Variable {
	
	private int value;
	public static final String VALUE_REGEX = "-?[0-9]+";
	public static final String ASSIGNMENT = Variable.NAME_REGEX + "(\\s*=\\s*"+VALUE_REGEX+")?";
	public static final String ASSIGNMENT_LINE = Variable.NAME_REGEX + "(\\s*=\\s*"+VALUE_REGEX+");";
	public static final String TYPE = "int";
	public static final String DECLERATION = "(final \\s*)?" + TYPE+"\\s*"+ASSIGNMENT+"(\\s*,\\s*"+ASSIGNMENT+")*;";
	
	public static boolean isIntVariableDec(String line){
		Pattern p = Pattern.compile(DECLERATION);
		Matcher m = p.matcher(line);
		return m.matches();
	}
	
	public IntVariable(String name) {
		super(name);
	}

	public IntVariable(String name, int value) {
		super(name);
		this.setValue(value);
	}
	
	public IntVariable(String name, int value, boolean fin){
		super(name);
		this.setValue(value);
		this.fin = fin;
	}
	
	public void setValue(int value){
		this.init = true;
		this.value = value;
	}
	
}
