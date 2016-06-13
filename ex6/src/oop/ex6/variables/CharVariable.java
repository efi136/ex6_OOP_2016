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

	public static CharVariable[] getVariablesFromDec(String line){
		int count = line.length() - line.replace(",", "").length();
		count = count+1;
		CharVariable[] vars = new CharVariable[count];
		boolean fin = line.startsWith(FINAL);
		// This is an array only to pass the boolean by reference.
		int[] start_index = {TYPE.length() + 1};
		if (fin){
			start_index[0]+=FINAL.length();
		}
		for (int i=0; i<count; i++){
			vars[i] = getVariableFromLinePart(line, start_index, fin);
		}
		return vars;
	}
	
	public static CharVariable getVariableFromLinePart(String line, int[] start_index, boolean fin){
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
				return new CharVariable(name, matcher.group().charAt(0), fin);
			}
			matcher.usePattern(Pattern.compile(Variable.NAME_REGEX));
			if(matcher.find()){
				String secondVariableName = matcher.group();
				//TODO: If there'se char a = b
			}
			else{
				if(fin){
					//TODO: If variable is final but not initiallized
				}
				return new CharVariable(name);
			}
		}
		return new CharVariable("");
	
	}
	
	public String getType(){
		return TYPE;
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
