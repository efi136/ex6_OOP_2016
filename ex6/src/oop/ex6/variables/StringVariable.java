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
	
	public static StringVariable[] getVariablesFromDec(String line, SymbolTable st){
		int count = line.length() - line.replace(",", "").length();
		count = count+1;
		StringVariable[] vars = new StringVariable[count];
		boolean fin = line.startsWith(FINAL);
		// This is an array only to pass the boolean by reference.
		int[] start_index = {TYPE.length() + 1};
		if (fin){
			start_index[0]+=FINAL.length();
		}
		for (int i=0; i<count; i++){
			vars[i] = getVariableFromLinePart(line, start_index, fin, st);
		}
		return vars;
	}
	
	public static StringVariable getVariableFromLinePart(String line, int[] start_index, boolean fin, SymbolTable st){
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
				if(st.isInit(secondVariableName)){
					if(st.get_variable_type(secondVariableName).equals(TYPE)){
						return new StringVariable(name, "", fin);
					}
					else{
						//TODO: Not same type
					}
				}
				else {
					//TODO: Not valid variable
				}
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
	
	public String getType(){
		return TYPE;
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
