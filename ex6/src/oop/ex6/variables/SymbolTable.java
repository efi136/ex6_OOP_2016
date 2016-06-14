package oop.ex6.variables;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SymbolTable {

	public static final String NOT_IN_TABLE_TYPE = "NULL";
	public static final String TRUE = "true";
	public static final String FALSE = "false";
	HashMap<String, Variable> globals;
	HashMap<String, Variable> locals;
	HashMap<String, Function> functions;
	
	
	public SymbolTable(){
		this.globals = new HashMap<String, Variable>();
		this.locals = new HashMap<String, Variable>();
		this.functions = new HashMap<String, Function>();
		// add the constunts as global variables.
		this.globals.put(TRUE, new BooleanVariable(TRUE, true, true));
		this.globals.put(FALSE, new BooleanVariable(FALSE, false, true));
	}
	
	/**
	 * Copy constructor.
	 * @param st - the SymbolTable to copy.
	 */
	public SymbolTable(SymbolTable st){
		this.globals = new HashMap<String,Variable>();
		for (String name:st.globals.keySet()){
			Variable var = st.globals.get(name);
			Variable temp_var = var.clone();
			this.globals.put(name, temp_var);
		}
		this.locals = st.locals;
		this.functions = st.functions;
	}

	public String[] get_variables_type(String[] names){
		String[] types = new String[names.length];
		for (int i=0; i<types.length; i++){
			types[i] = this.get_variable_type(names[i]);
		}
		return types;
	}
	
	public void resetLocals(){
		this.locals = new HashMap<String, Variable>();
	}
	
	public String get_variable_type(String name){
		Variable var;
		Pattern p = Pattern.compile(Variable.VALUES);
		Matcher m = p.matcher(name);
		if (m.matches()){
			// check order: Int, Double, Boolean, Char, String
			if (Pattern.matches(IntVariable.VALUE_REGEX, name)){
				return IntVariable.TYPE;
			}
			else if (Pattern.matches(DoubleVariable.VALUE_REGEX, name)){
				return DoubleVariable.TYPE;
			}
			else if (Pattern.matches(BooleanVariable.VALUE_REGEX, name)){
				return BooleanVariable.TYPE;
			}
			else if (Pattern.matches(CharVariable.VALUE_REGEX, name)){
				return CharVariable.TYPE;
			}
			else if (Pattern.matches(StringVariable.VALUE_REGEX, name)){
				return StringVariable.TYPE;
			}
		}
		var = locals.get(name);
		if (var !=null){
			return var.getType();
		}
		var = globals.get(name);
		if (var != null){
			return var.getType();
		}
		return NOT_IN_TABLE_TYPE;
	}
	
	/**
	 * returns true if the variable name is global or if it is initialized.
	 * @param name - the variable name.
	 * @return - true if it is global or if it is initialized.
	 */
	public boolean isInit(String name){
		Pattern p = Pattern.compile(Variable.VALUES);
		Matcher m = p.matcher(name);
		if (m.matches()){
			return true;
		}
		Variable var = locals.get(name);
		if (var !=null){
			return var.init;
		}
		var = globals.get(name);
		if (var != null){
			return var.init;
		}
		return false;
	}
	
	/**
	 * returns true if the variable name is global or if it is initialized.
	 * @param name - the variable name.
	 * @return - true if it is global or if it is initialized.
	 */
	public boolean isInit(String[] names){
		for (String name : names){
			if (!this.isInit(name)){
				return false;
			}
		}
		return true;
	}
	
	public boolean add_global_variables(Variable[] variables){
		for(Variable variable : variables){
			if(globals.containsKey(variable.getName())){
				return false;
			}
			globals.put(variable.getName(), variable);
		}
		return true;
	}
	
	public boolean add_local_variables(Variable[] variables){
		for(Variable variable : variables){
			if(locals.containsKey(variable.getName())){
				return false;
			}
			locals.put(variable.getName(), variable);
		}
		return true;
	}
	
	
	public boolean addGlobalFunction(Function func){
		if(this.functions.containsKey(func.getName())){
			return false;
		}
		this.functions.put(func.getName(), func);
		return true;
	}
}
