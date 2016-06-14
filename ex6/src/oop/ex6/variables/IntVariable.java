package oop.ex6.variables;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import oop.ex6.Exceptions.Ex6Exceptions;
import oop.ex6.Exceptions.IncompatibleType;
import oop.ex6.Exceptions.UnInitializedFinal;
import oop.ex6.Exceptions.UsedBeforeAssignment;

public class IntVariable extends Variable {
	
	/**
	 * Regex expressions for an int variable
	 */
	public static final String VALUE_REGEX = "-?[0-9]+";
	public static final String ASSIGNMENT = Variable.NAME_REGEX + "(\\s*=\\s*("+VALUE_REGEX+"|" + Variable.NAME_REGEX +"))?";
	public static final String ASSIGNMENT_LINE = Variable.NAME_REGEX + "(\\s*=\\s*("+VALUE_REGEX+"|" + Variable.NAME_REGEX +"));";
	public static final String TYPE = "int";
	public static final String DECLERATION = "(final \\s*)?" + TYPE+"\\s*"+ASSIGNMENT+"(\\s*,\\s*"+ASSIGNMENT+")*\\s*;";
	
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
	 * Returns the variable declared in a part of a line.
	 * @param line - The line.
	 * @param start_index - The index in the line where the variable starts.
	 * @param fin - Is the variable final or not
	 * @param st - The symbol table.
	 * @return The variable declared in this line right after start index.
	 * @throws Ex6Exceptions - General exception.
	 */
	public static IntVariable getVariableFromLinePart(String line, int[] start_index, boolean fin, SymbolTable st) throws Ex6Exceptions{
		Pattern pattern = Pattern.compile(ASSIGNMENT);
		Matcher matcher = pattern.matcher(line);
		if(matcher.find(start_index[0])){
			start_index[0] = matcher.end();
			String assignment = matcher.group();
			matcher.reset(assignment);
			matcher.usePattern(Pattern.compile(Variable.NAME_REGEX)); //The name of the variable
			matcher.find();
			String name = matcher.group();
			matcher.usePattern(Pattern.compile(VALUE_REGEX)); //Find the value of the variable
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
					//Uninitialized final varible
					throw new UnInitializedFinal(name);
				}
				return new IntVariable(name);
			}
		}
		return new IntVariable("");
	
	}
	/**
	 * Return the type of this variable.
	 */
	public String getType(){
		return TYPE;
	}
	/**
	 * Checks if a line is a int variable decleration
	 * @param line - The line to check
	 * @return - True if it's a int variable decleration and false otherwise
	 */
	public static boolean isIntVariableDec(String line){
		Pattern p = Pattern.compile(DECLERATION);
		Matcher m = p.matcher(line);
		return m.matches();
	}
	/**
	 * A constructor
	 * @param name - The name of the variable
	 */
	public IntVariable(String name) {
		super(name);
	}
	/**
	 * A constructor
	 * @param name - The name of the variable
	 * @param value - The value of this variable
	 */
	public IntVariable(String name, int value) {
		super(name);
		this.setValue(value);
	}
	/**
	 * A constructor that also set wheter a variable is final
	 * @param name - The name of the variable
	 * @param value - The value of this variable
	 * @param fin - Whether the variable is final or not
	 */
	public IntVariable(String name, Object value, boolean fin){
		super(name);
		this.setValue(value);
		this.fin = fin;
	}
	
	/**
	 * This method clones the variable.
	 */
	public Variable clone(){
		if (this.init){
			return new IntVariable(name,null,fin);
		}
		else{
			return new IntVariable(name);
		}
	}
	/**
	 * Set the variable as initialized
	 * @param value - The value it's initizlied with
	 */
	public void setValue(Object value){
		this.init = true;
	}
	
}
