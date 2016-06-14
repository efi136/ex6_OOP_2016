package oop.ex6.Symbols;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import oop.ex6.Exceptions.Ex6Exceptions;
import oop.ex6.Exceptions.IncompatibleType;
import oop.ex6.Exceptions.UnInitializedFinal;
import oop.ex6.Exceptions.UsedBeforeAssignment;

public class BooleanVariable extends Variable {
	/**
	 * Regex expressions for a boolean variable
	 */
	//A value of a boolean variable:
	public static final String VALUE_REGEX = "(true|false|"+DoubleVariable.VALUE_REGEX+")";
	//An assignment of a boolean variable
	public static final String ASSIGNMENT = Variable.NAME_REGEX + "(\\s*=\\s*("+VALUE_REGEX+"|" + Variable.NAME_REGEX + "))?";
	//An assignment of a few boolean variables
	public static final String ASSIGNMENT_LINE = Variable.NAME_REGEX + "(\\s*=\\s*("+VALUE_REGEX+"|" + Variable.NAME_REGEX +"));";
	//The type
	public static final String TYPE = "boolean";
	//A decleration of a boolean variable
	public static final String DECLERATION = "(final \\s*)?" +  TYPE+"\\s*"+ASSIGNMENT+"(\\s*,\\s*"+ASSIGNMENT+")*\\s*;";

	/**
	 * Checks if a line is a boolean variable decleration
	 * @param line - The line to check
	 * @return - True if it's a boolean variable decleration and false otherwise
	 */
	public static boolean isBooleanVariableDec(String line){
		Pattern p = Pattern.compile(DECLERATION);
		Matcher m = p.matcher(line);
		return m.matches();
	}
	
	/**
	 * Returns all the variables declared in one line.
	 * @param line - The line to check.
	 * @param st - The symbol table.
	 * @return - an array of boolean variables.
	 * @throws Ex6Exceptions - A general exception.
	 */
	public static BooleanVariable[] getVariablesFromDec(String line, SymbolTable st) throws Ex6Exceptions{
		int count = line.length() - line.replace(",", "").length(); 
		count = count+1; //The number of variables in the line
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
	
	/**
	 * Returns the variable declared in a part of a line.
	 * @param line - The line.
	 * @param start_index - The index in the line where the variable starts.
	 * @param fin - Is the variable final or not
	 * @param st - The symbol table.
	 * @return The variable declared in this line right after start index.
	 * @throws Ex6Exceptions - General exception.
	 */
	public static BooleanVariable getVariableFromLinePart(String line, int[] start_index, boolean fin, SymbolTable st) throws Ex6Exceptions{
		Pattern pattern = Pattern.compile(ASSIGNMENT);
		Matcher matcher = pattern.matcher(line);
		if(matcher.find(start_index[0])){
			start_index[0] = matcher.end();
			String assignment = matcher.group();
			matcher.reset(assignment);
			matcher.usePattern(Pattern.compile(Variable.NAME_REGEX)); //Find the name
			matcher.find();
			String name = matcher.group();
			matcher.usePattern(Pattern.compile(VALUE_REGEX)); //Find the value
			if(matcher.find()){
				return new BooleanVariable(name, Boolean.getBoolean(matcher.group()), fin);
			}
			matcher.usePattern(Pattern.compile(Variable.NAME_REGEX));
			if(matcher.find()){ //If it's an asignment of a variable in a variable.
				String secondVariableName = matcher.group();
				if(st.isInit(secondVariableName)){
					if(st.get_variable_type(secondVariableName).equals(TYPE)){
						return new BooleanVariable(name, false, fin);
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
			else{ //If un initialized
				if(fin){
					//Uninitialized final
					throw new UnInitializedFinal(name);
				}
				return new BooleanVariable(name);
			}
		}
		return new BooleanVariable("");
	
	}
	
	/**
	 * Return the type of this variable.
	 */
	public String getType(){
		return TYPE;
	}
	
	/**
	 * A constructor
	 * @param name - The name of the variable
	 */
	public BooleanVariable(String name) {
		super(name);
	}
	/**
	 * A constructor
	 * @param name - The name of the variable
	 * @param value - The value of this variable
	 */
	public BooleanVariable(String name, boolean value) {
		super(name);
		this.setValue(value);
	}
	/**
	 * A constructor that also set wheter a variable is final
	 * @param name - The name of the variable
	 * @param value - The value of this variable
	 * @param fin - Whether the variable is final or not
	 */
	public BooleanVariable(String name, Object value, boolean fin){
		super(name);
		this.setValue(value);
		this.fin = fin;
	}
	
	/**
	 * This method clones the variable.
	 */
	public Variable clone(){
		if (this.init){
			return new BooleanVariable(name,null,fin);
		}
		else{
			return new BooleanVariable(name);
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
