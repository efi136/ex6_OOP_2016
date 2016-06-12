package oop.ex6.variables;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CharVariable extends Variable {
	private char value;
	
	public static final String VALUE_REGEX = "\'.\'";
	public static final String ASSIGNMENT = Variable.NAME_REGEX + "(\\s*=\\s*"+VALUE_REGEX+")?";
	public static final String ASSIGNMENT_LINE = Variable.NAME_REGEX + "(\\s*=\\s*"+VALUE_REGEX+");";
	public static final String TYPE = "char";
	public static final String DECLERATION = "(final \\s*)?" + TYPE+"\\s*"+ASSIGNMENT+"(\\s*,\\s*"+ASSIGNMENT+")*;";
	
	public static boolean isCharVariableDec(String line){
		Pattern p = Pattern.compile(DECLERATION);
		Matcher m = p.matcher(line);
		return m.matches();
	}
	
	public CharVariable(String name) {
		super(name);
	}
	
	public CharVariable(String name, char value) {
		super(name);
		this.setValue(value);
	}
	
	public CharVariable(String name, char value, boolean fin){
		super(name);
		this.setValue(value);
		this.fin = fin;
	}
	
	public void setValue(char value){
		this.init = true;
		this.value = value;
	}
}
