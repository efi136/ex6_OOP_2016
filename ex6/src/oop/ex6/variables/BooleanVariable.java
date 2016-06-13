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
	
	public static BooleanVariable[] getVariablesFromDec(String line, SymbolTable st){
		int count = line.length() - line.replace(",", "").length();
		count = count+1;
		BooleanVariable[] vars = new BooleanVariable[count];
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
	
	public static BooleanVariable getVariableFromLinePart(String line, int[] start_index, boolean fin, SymbolTable st){
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
				return new BooleanVariable(name, Boolean.getBoolean(matcher.group()), fin);
			}
			matcher.usePattern(Pattern.compile(Variable.NAME_REGEX));
			if(matcher.find()){
				String secondVariableName = matcher.group();
				if(st.isInit(secondVariableName)){
					if(st.get_variable_type(secondVariableName).equals(TYPE)){
						return new BooleanVariable(name, false, fin);
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
				return new BooleanVariable(name);
			}
		}
		return new BooleanVariable("");
	
	}
	
	public String getType(){
		return TYPE;
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
