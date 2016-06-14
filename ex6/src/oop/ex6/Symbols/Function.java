package oop.ex6.Symbols;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import oop.ex6.codeBlocks.CodeBlock;

public class Function {
	/**
	 * The name of the function
	 */
	private String name;
	/**
	 * The number of parameters in the function
	 */
	private int num_of_parameters;
	/**
	 * The types of the paramters
	 */
	private Variable[] type_of_parameters;
	/**
	 * A regex expression of a method call
	 */
	public static final String METHOD_CALL = Variable.NAME_REGEX + "\\s*"
			+ "[(]\\s*("+Variable.VALUE_OR_NAME+"\\s*(\\s*,\\s*"+Variable.VALUE_OR_NAME+"\\s*)*)?[)]\\s*;";
	private static final int START_INDEX_FOR_NAME = 0;
	
	/**
	 * Returns the name of the method declared in this line.
	 * @param line - The line to check.
	 * @return the name of the method declared in this line.
	 */
	public static String get_name(String line){
		Pattern p = Pattern.compile(Variable.NAME_REGEX);
		Matcher m = p.matcher(line);
		m.find(START_INDEX_FOR_NAME);
		return line.substring(m.start(), m.end());
	}
	
	/**
	 * Checks if this line is a leagal method call.
	 * @param line - the line to be checked.
	 * @param st - the symbol table.
	 * @return true if this line is a leagal method call and false otherwise.
	 */
	public static boolean checkIfLineIsMethodCall(String line, SymbolTable st){
		HashMap<String, Function> map = st.functions;
		for (String key: map.keySet()) {
			if (map.get(key).isLineLegalFunctionCall(line, st)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns the function declared in a line
	 * @param line - The line
	 * @return the function declared in this line
	 */
	public static Function getFunctionFromDec(String line){
		return new Function(line.substring(5, line.indexOf('(')), getVariablesFromDec(line));
	}
	
	/**
	 * Returns the variable arguments in a method decleration.
	 * @param line - The line of the decleration.
	 * @return the variable arguments in a method decleration.
	 */
	public static Variable[] getVariablesFromDec(String line){
		int count = line.length() - line.replace(",", "").length(); //The number of variables in the declerations
		if (line.indexOf(')') - line.indexOf('(') == 1){
			// no variables.
			return null;
		}
		String cut = line.substring(line.indexOf('(')+1,line.lastIndexOf(')')); // The part between ()
		String[] parts = cut.split(",");
		Variable[] vars = new Variable[count+1];
		Matcher m2;
		String type, name;
		m2 = Pattern.compile(Variable.NAME_REGEX).matcher(cut);
		// make sure that it starts from the variable names and not the function name.
		for(int i = 0; i <= count; i++){
			String[] dec_parts = parts[i].trim().split(" ");
			boolean fin = dec_parts.length == 3; //Is final or not
			if(fin){
				m2.find();
			}
			m2.find();
			type = m2.group(); //Find the type of the variable
			m2.find();
			name = m2.group(); //Find the name of the variable
			switch(type){ //Create the variable instances
			case BooleanVariable.TYPE:
				vars[i] = new BooleanVariable(name, true, fin);
				break;
			case IntVariable.TYPE:
				vars[i] = new IntVariable(name, 0, fin);
				break;
			case StringVariable.TYPE:
				vars[i] = new StringVariable(name, "", fin);
				break;
			case CharVariable.TYPE:
				vars[i] = new CharVariable(name, 'a', fin);
				break;
			case DoubleVariable.TYPE:
				vars[i] = new DoubleVariable(name, 0, fin);
				break;
			}
		}
		return vars;
	}
	

	
	
	/**
	 * Checks if the line is a leagal function call for this function.
	 * @param line - the line to be checked.
	 * @param st - the symbol table.
	 * @return true if the function call is legal and false otherwise.
	 */
	public boolean isLineLegalFunctionCall(String line, SymbolTable st){
		Pattern p = Pattern.compile(METHOD_CALL);
		Matcher m = p.matcher(line);
		if (!m.matches()){
			//If doens't match a method call
			return false;
		}
		String name = get_name(line); // The name of the function
		if (!name.equals(this.name)){
			return false;
		}
		String[] names = CodeBlock.getVariableNameFromFuncCall(line); //The name of the variables called
		// in case it has no parameters.
		if (names == null){
			return this.num_of_parameters == 0;
		}
		String[] types = st.get_variables_type(names);
		if (names.length!=this.num_of_parameters){
			return false;
		}
		// check if the types are okay.
		for (int i=0; i<types.length; i++){
			if (!this.type_of_parameters[i].getType().equals(types[i])){
				if (!(this.type_of_parameters[i].getType().equals(DoubleVariable.TYPE) && types[i].equals(IntVariable.TYPE))){
					if (!(this.type_of_parameters[i].getType().equals(BooleanVariable.TYPE)
							&& (types[i].equals(IntVariable.TYPE) || types[i].equals(DoubleVariable.TYPE) ) ) ){
						return false;
					}
				}
				
			}
			if (!st.isInit(names[i])){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Returns the name of this function.
	 * @return the name of this function.
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * A constructor that recieves a name and variables.
	 * @param name - The name of the function
	 * @param types - The types of variables this function needs.
	 */
	public Function(String name, Variable[] types){
		this.name = name;
		this.type_of_parameters = types;
		if (types == null){
			this.num_of_parameters = 0;
		}
		else{
			this.num_of_parameters = types.length;
		}
	}
	
}
