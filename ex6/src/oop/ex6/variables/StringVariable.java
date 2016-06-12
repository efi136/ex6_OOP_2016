package oop.ex6.variables;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringVariable extends Variable {
	
	private String value;
	public static final String VALUE_REGEX = "\"\\w*\"";
	public static final String ASSIGNMENT = Variable.NAME_REGEX + "(\\s*=\\s*"+VALUE_REGEX+")?";
	public static final String ASSIGNMENT_LINE = Variable.NAME_REGEX + "(\\s*=\\s*"+VALUE_REGEX+");";
	public static final String TYPE = "String";
	public static final String DECLERATION = "(final \\s*)?" + TYPE+"\\s*"+ASSIGNMENT+"(\\s*,\\s*"+ASSIGNMENT+")*;";
	
	public static boolean isStringVariableDec(String line){
		Pattern p = Pattern.compile(DECLERATION);
		Matcher m = p.matcher(line);
		return m.matches();
	}
	
	public StringVariable(String name) {
		super(name);
	}

	public StringVariable(String name, String value) {
		super(name);
		this.setValue(value);
	}
	
	public StringVariable(String name, String value, boolean fin){
		super(name);
		this.setValue(value);
		this.fin = fin;
	}
	
	public void setValue(String value){
		this.init = true;
		this.value = value;
	}
	
}
