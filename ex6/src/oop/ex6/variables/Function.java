package oop.ex6.variables;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Function {
	private String name;
	private int num_of_parameters;
	private Variable[] type_of_parameters;
	public static final String METHOD_CALL = Variable.NAME_REGEX + "[\\s*(\\s*]"+Variable.NAME_REGEX+"\\s*(,\\s*"+Variable.NAME_REGEX+"\\s*)*[)]\\s*;";
	private static final int START_INDEX_FOR_NAME = 0;
	
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
	
	public static Function getFunctionFromDec(String line){
		return new Function(line.substring(5, line.indexOf('(')), getVariablesFromDec(line));
	}
	
	public static Variable[] getVariablesFromDec(String line){
		// TODO:: finish this.
		int count = line.length() - line.replace(",", "").length();
		if (line.indexOf(')') - line.indexOf('(') == 1){
			// no variables.
			return null;
		}
		Variable[] vars = new Variable[count+1];
		Pattern p = Pattern.compile(Variable.METHOD_DECLERATION);
		Matcher m = p.matcher(line);
		Matcher m2;
		String type, name;
		// make sure that it starts from the variable names and not the function name.
		for(int i = 0; i < count; i++){
			m.find(line.indexOf('('));
			m2 = Pattern.compile(Variable.NAME_REGEX).matcher(m.group());
			m2.find();
			type = m2.group();
			m2.find();
			name = m2.group();
			switch(type){
			case BooleanVariable.TYPE:
				vars[i] = new BooleanVariable(name);
			case IntVariable.TYPE:
				vars[i] = new IntVariable(name);
			case StringVariable.TYPE:
				vars[i] = new StringVariable(name);
			case CharVariable.TYPE:
				vars[i] = new CharVariable(name);
			case DoubleVariable.TYPE:
				vars[i] = new DoubleVariable(name);
			}
		}
		return vars;
	}
	
	public static String[] getVariableNameFromFuncCall(String line){
		int count = line.length() - line.replace(",", "").length();
		if (line.indexOf(')') - line.indexOf('(') == 1){
			// no variables.
			return null;
		}
		String[] names = new String[count+1];
		Pattern p = Pattern.compile(Variable.NAME_REGEX);
		Matcher m = p.matcher(line);
		// make sure that it starts from the variable names and not the function name.
		m.find(START_INDEX_FOR_NAME);
		int start_index = m.end(); 
		for (int i=0; i<=count; i++){
			m.find(start_index);
			start_index = m.end();
			names[i] = m.group();
		}
		return names;
	}
	
	/**
	 * Checks if the line is a leagal function call for this function.
	 * @param line - the line to be checked.
	 * @param st - the symbol table.
	 * @return true if the function call is legal and false otherwise.
	 */
	public boolean isLineLegalFunctionCall(String line, SymbolTable st){
		//TODO: finish this.
		Pattern p = Pattern.compile(METHOD_CALL);
		Matcher m = p.matcher(line);
		if (m.matches()){
			return false;
		}
		String name = get_name(line);
		if (!name.equals(this.name)){
			return false;
		}
		String[] names = getVariableNameFromFuncCall(line);
		// in case it has no parameters.
		if (names == null){
			return this.num_of_parameters == 0;
		}
		String[] types = st.get_variables_type(names);
		if (names.length!=this.num_of_parameters){
			return false;
		}
		for (int i=0; i<types.length; i++){
			if (!this.type_of_parameters[i].equals(types[i])){
				if (!(this.type_of_parameters[i].equals(DoubleVariable.TYPE) && types[i].equals(IntVariable.TYPE))){
					return false;
				}
			}
			if (!st.isInit(names[i])){
				return false;
			}
		}
		return true;
	}

	public String getName(){
		return this.name;
	}
	
	public Function(String name, Variable[] types){
		this.name = name;
		this.type_of_parameters = types;
		this.num_of_parameters = types.length;
	}
	
}
