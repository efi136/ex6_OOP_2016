package oop.ex6.variables;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Function {
	private int num_of_parameters;
	private String[] type_of_parameters;
	public static final String METHOD_CALL = Variable.NAME_REGEX + "[\\s*(\\s*]"+Variable.NAME_REGEX+"\\s*(,\\s*"+Variable.NAME_REGEX+"\\s*)*[)]\\s*;";
	
	private String[] get_variable_names(String line){
		return new String[1];
		//TODO:: finish this.
	}
	
	/**
	 * Checks if the line is a leagal function call.
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
		String[] names = this.get_variable_names(line);
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
