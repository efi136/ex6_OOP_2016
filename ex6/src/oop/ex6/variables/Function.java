package oop.ex6.variables;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Function {
	private String name;
	private int num_of_parameters;
	private String[] type_of_parameters;
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
	
	public static String[] get_variable_names(String line){
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
		String[] names = get_variable_names(line);
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
				return false;
			}
			if (!st.is_global_or_init(names[i])){
				return false;
			}
		}
		return true;
	}
	
}
