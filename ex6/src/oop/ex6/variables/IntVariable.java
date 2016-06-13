package oop.ex6.variables;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import oop.ex6.Exceptions.Ex6Exceptions;
import oop.ex6.Exceptions.IncompatibleType;
import oop.ex6.Exceptions.UnInitializedFinal;
import oop.ex6.Exceptions.UsedBeforeAssignment;

public class IntVariable extends Variable {
	
	@SuppressWarnings("unused")
	private int value;
	public static final String VALUE_REGEX = "-?[0-9]+";
	public static final String ASSIGNMENT = Variable.NAME_REGEX + "(\\s*=\\s*"+VALUE_REGEX+")?";
	public static final String ASSIGNMENT_LINE = Variable.NAME_REGEX + "(\\s*=\\s*"+VALUE_REGEX+");";
	public static final String TYPE = "int";
	public static final String DECLERATION = "(final \\s*)?" + TYPE+"\\s*"+ASSIGNMENT+"(\\s*,\\s*"+ASSIGNMENT+")*;";
	
	/**
	 * Returns all the variables declared in this line.
	 * @param line - the line to be parsed.
	 * @return - all the variables declared in this line.
	 * @throws Ex6Exceptions 
	 */
	public static IntVariable[] getVariablesFromDec(String line, SymbolTable st) throws Ex6Exceptions{
		int count = line.length() - line.replace(",", "").length();
		count = count+1;
		IntVariable[] vars = new IntVariable[count];
		boolean fin = line.startsWith(FINAL);
		// This is an array only to pass the int by reference.
		int[] start_index = {TYPE.length() + 1};
		if (fin){
			start_index[0]+=FINAL.length();
		}
		for (int i=0; i<count; i++){
			vars[i] = getVariableFromLinePart(line, start_index, fin, st);
		}
		return vars;
	}
	
	/**
	 * 
	 * @param line - the line to get the variable from.
	 * @param start_index - the starting index of the line from which to start parsing.
	 * @param fin - if the variable should be final or not.
	 * @return The variable declared in this line starting from start_index.
	 * int name =4, name1=2
	 * @throws Ex6Exceptions 
	 */
	public static IntVariable getVariableFromLinePart(String line, int[] start_index, boolean fin, SymbolTable st) throws Ex6Exceptions{
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
				return new IntVariable(name, Integer.parseInt(matcher.group()), fin);
			}
			matcher.usePattern(Pattern.compile(Variable.NAME_REGEX));
			if(matcher.find()){
				String secondVariableName = matcher.group();
				if(st.isInit(secondVariableName)){
					if(st.get_variable_type(secondVariableName).equals(TYPE)){
						return new IntVariable(name, 0, fin);
					}
					else{
						//Not same type
						throw new IncompatibleType(name);
					}
				}
				else {
					//Not valid variable
					throw new UsedBeforeAssignment(name);
				}
			}
			else{
				if(fin){
					throw new UnInitializedFinal(name);
				}
				return new IntVariable(name);
			}
		}
		return new IntVariable("");
	
	}
	
	public String getType(){
		return TYPE;
	}
	
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
