package oop.ex6.Symbols;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import oop.ex6.Exceptions.Ex6Exceptions;
import oop.ex6.Exceptions.FinalVariableAssignment;
import oop.ex6.Exceptions.IncompatibleType;
import oop.ex6.Exceptions.UndefinedVariableUsed;

public class Variable {

	protected String name;
	protected boolean init;
	protected boolean fin;
	
	//Regex expressions for variables declerations, assignments, values, and names.
	//Final:
	public static final String FINAL = "final";
	//A name:
	public static final String NAME_REGEX = "(((_)((\\w)+))|(([a-zA-Z])(\\w*)))";
	//A variable decxleration
	public static final String VARIABLE_DECLERATION = "("+BooleanVariable.DECLERATION+"|"+
	CharVariable.DECLERATION+"|"+DoubleVariable.DECLERATION+"|"+IntVariable.DECLERATION+"|"+
			StringVariable.DECLERATION+")";
	//The types possible:
	public static final String TYPES = "("+BooleanVariable.TYPE+"|"+CharVariable.TYPE+"|"+
			DoubleVariable.TYPE+"|"+IntVariable.TYPE+"|"+StringVariable.TYPE+")";
	//A line of an asignment
	public static final String ASSIGNMENT_LINE = "("+BooleanVariable.ASSIGNMENT_LINE+"|"+
			CharVariable.ASSIGNMENT_LINE+"|"+DoubleVariable.ASSIGNMENT_LINE+"|"
			+IntVariable.ASSIGNMENT_LINE+"|"+StringVariable.ASSIGNMENT_LINE+")";
	//The possible values
	public static final String VALUES = "("+BooleanVariable.VALUE_REGEX+"|"+CharVariable.VALUE_REGEX+"|"+
			DoubleVariable.VALUE_REGEX+"|"+IntVariable.VALUE_REGEX+"|"+StringVariable.VALUE_REGEX+")";
	//The variable declaration part of method declaration
	public static final String METHOD_DECLERATION = "("+TYPES+" "+NAME_REGEX+")";
		public static final String VALUE_OR_NAME = "("+NAME_REGEX+"|"+VALUES+")";

	/**
	 * Check if a line is a variable declaration
	 * @param line - The line
	 * @return true if it's a variable declaration, false otherwise
	 */
	public static boolean isVariableDec(String line){
		Pattern p = Pattern.compile(VARIABLE_DECLERATION);
		Matcher m = p.matcher(line);
		return m.matches();
	}
	
	/**
	 * Returns the variables from a line
	 * @param line - The line
	 * @param st - The symbol table
	 * @return - An array of variable containing the variables.
	 * @throws Ex6Exceptions
	 */
	public static Variable[] getVariablesFromDec(String line, SymbolTable st) throws Ex6Exceptions{
		String type;
		String [] parts = line.split(" ");
		if (parts[0].equals(FINAL) && parts.length>1){
			//If final
			type = parts[1];
		}
		else{
			type = parts[0];
		}
		switch(type){
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

	/**
	 * This function checks if this line is an assingment line. of a variable.
	 * @param line - the line to check.
	 * @return true if this as an assignment line. false otherwise.
	 */
	public static boolean isAssignmentLine(String line){
		return Pattern.matches(ASSIGNMENT_LINE, line);
	}
	
	/**
	 * This function prcesses an assignment line.
	 * @param line - the line to be provessed.
	 * @param st - the SymbolTable.
	 * @return true if the line is legal and false otherwise.
	 * @throws Ex6Exceptions 
	 */
	public static void processAssignmentLine(String line, SymbolTable st) throws Ex6Exceptions{
		String[] parts = line.split("=");
		String name = parts[0].trim();
		String value = parts[1].trim();
		value = value.substring(0, value.length()-1);
		String value_type = getValueType(value);
		// check for locals:
		// check if variable is in st.
		if (st.locals.get(name)!=null){
			Variable var = st.locals.get(name);
			if (var.fin){
				throw new FinalVariableAssignment(name);
				// assignment to final variable.
			}
			if (value_type!= st.get_variable_type(name)){
				if (!(value_type==IntVariable.TYPE&&st.get_variable_type(name)==DoubleVariable.TYPE)){
					if (!(st.get_variable_type(name)==BooleanVariable.TYPE && 
							(value_type.equals(IntVariable.TYPE) || value_type.equals(DoubleVariable.TYPE) ) ) ){
						throw new IncompatibleType(name);
						// wrong type assigned to var.
					}	
				}
			}
			var.init = true;
			return;
		}
		// check for globals:
		// check if variable is in st.
		if (st.globals.get(name)==null){
			throw new UndefinedVariableUsed(name);
			// variable not defined at assignment.
		}
		else {
			Variable var = st.globals.get(name);
			if (var.fin){
				throw new FinalVariableAssignment(name);
				// assignment to final variable.
				
			}
			if (value_type!= st.get_variable_type(name)){
				if (!(value_type==IntVariable.TYPE&&st.get_variable_type(name)==DoubleVariable.TYPE)){
					throw new IncompatibleType(name);
					// wrong type addigned to var.
				}
			}
			var.init = true;
		}
	}
	
	/**
	 * This function gets a string representation of a value and returns its type.
	 * @param value - a string representation of a value.
	 * @return the type of the value.
	 */
	public static String getValueType(String value){
		if (Pattern.matches(IntVariable.VALUE_REGEX, value)){
			return IntVariable.TYPE;
		}
		else if (Pattern.matches(DoubleVariable.VALUE_REGEX, value)){
			return DoubleVariable.TYPE;
		}
		else if (Pattern.matches(BooleanVariable.VALUE_REGEX, value)){
			return BooleanVariable.TYPE;
		}
		else if (Pattern.matches(CharVariable.VALUE_REGEX, value)){
			return CharVariable.TYPE;
		}
		else if (Pattern.matches(StringVariable.VALUE_REGEX, value)){
			return StringVariable.TYPE;
		}
		else {
			return "Null";
		}
	}
	
	
	public String getType(){
		return "";
	}
	/**
	 * A constructor
	 * @param name  -The name of the variable
	 */
	public Variable(String name){
		this.name = name;
		this.init = false;
		this.fin = false;
	}
	
	/**
	 * Clone a variable
	 */
	public Variable clone(){
		return new Variable(name);
	}
	
	/**
	 * Return the name of the variable
	 * @return the name of the variable
	 */
	public String getName() {
		return name;
	}
	
}
