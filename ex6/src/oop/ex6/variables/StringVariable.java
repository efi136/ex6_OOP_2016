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
	
	public static StringVariable getVariableFromLinePart(String line, int[] start_index, boolean fin){
		Pattern pattern = Pattern.compile(ASSIGNMENT);
		Matcher matcher = pattern.matcher(line);
		if(matcher.find(start_index[0])){
			start_index[0] = matcher.end();
			String assignment = matcher.group();
			matcher.reset(assignment);
			matcher.usePattern(Pattern.compile(Variable.NAME_REGEX));
			matcher.find();
			String name = matcher.group();
			matcher.usePattern(Pattern.compile(VALUE_REGEX));
			if(matcher.find()){
				return new StringVariable(name, matcher.group(), fin);
			}
			matcher.usePattern(Pattern.compile(Variable.NAME_REGEX));
			if(matcher.find()){
				String secondVariableName = matcher.group();
				//TODO: If there'se String a = b
			}
			else{
				if(fin){
					//TODO: If variable is final but not initiallized
				}
				return new StringVariable(name);
			}
		}
		return new StringVariable("");
	
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
