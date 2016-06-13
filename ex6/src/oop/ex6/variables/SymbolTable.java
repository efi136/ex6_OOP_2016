package oop.ex6.variables;

import java.util.HashMap;

public class SymbolTable {

	public static final String NOT_IN_TABLE_TYPE = "NULL";
	HashMap<String, Variable> globals;
	HashMap<String, Variable> locals;
	HashMap<String, Function> functions;
	
	
	public SymbolTable(){
		this.globals = new HashMap<String, Variable>();
		this.locals = new HashMap<String, Variable>();
		this.functions = new HashMap<String, Function>();
		
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
	public boolean is_global_or_init(String name){
		Variable var;
		var = locals.get(name);
		if (var !=null){
			return var.init;
		}
		var = globals.get(name);
		if (var != null){
			return true;
		}
		return false;
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
	
	public boolean addGlobalFunctions(Function[] functions){
		for(Function func : functions){
			if(this.functions.containsKey(func.getName())){
				return false;
			}
			this.functions.put(func.getName(), func);
		}
		return true;
	}
}
