package oop.ex6.variables;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Variable {

	protected String name;
	protected boolean init;
	protected boolean fin;
	
	public static final String FINAL = "final ";
	public static final String NAME_REGEX = "(((_)((\\w)+))|(([a-zA-Z])(\\w*)))";
	public static final String VARIABLE_DECLERATION = "("+BooleanVariable.DECLERATION+"|"+
	CharVariable.DECLERATION+"|"+DoubleVariable.DECLERATION+"|"+IntVariable.DECLERATION+"|"+
			StringVariable.DECLERATION+")";
	public static final String TYPES = "("+BooleanVariable.TYPE+"|"+CharVariable.TYPE+"|"+
			DoubleVariable.TYPE+"|"+IntVariable.TYPE+"|"+StringVariable.TYPE+")";
	public static final String ASSIGNMENT_LINE = "("+BooleanVariable.ASSIGNMENT_LINE+"|"+
			CharVariable.ASSIGNMENT_LINE+"|"+DoubleVariable.ASSIGNMENT_LINE+"|"
			+IntVariable.ASSIGNMENT_LINE+"|"+StringVariable.ASSIGNMENT_LINE+")";
	public static final String VALUES = "("+BooleanVariable.VALUE_REGEX+"|"+CharVariable.VALUE_REGEX+"|"+
			DoubleVariable.VALUE_REGEX+"|"+IntVariable.VALUE_REGEX+"|"+StringVariable.VALUE_REGEX+")";
	public static final String METHOD_DECLERATION = "("+TYPES+" "+NAME_REGEX+")";
	
	public static boolean isVariableDec(String line){
		Pattern p = Pattern.compile(VARIABLE_DECLERATION);
		Matcher m = p.matcher(line);
		return m.matches();
	}
	
	public static Variable[] getVariablesFromDec(String line, SymbolTable st){
		Pattern p = Pattern.compile(VARIABLE_DECLERATION);
		Matcher m = p.matcher(line);
		m.find();
		switch(m.group()){
		case BooleanVariable.TYPE:
			return BooleanVariable.getVariablesFromDec(line, st);
		case IntVariable.TYPE:
			return IntVariable.getVariablesFromDec(line, st);
		case StringVariable.TYPE:
			return StringVariable.getVariablesFromDec(line, st);
		case CharVariable.TYPE:
			return CharVariable.getVariablesFromDec(line, st);
		case DoubleVariable.TYPE:
			return DoubleVariable.getVariablesFromDec(line, st);
		}
		return new Variable[0];
	}
	
	public String getType(){
		return "";
	}
	
	public Variable(String name){
		this.name = name;
		this.init = false;
		this.fin = false;
	}
	
	public String getName() {
		return name;
	}
	
}
