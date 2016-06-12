package oop.ex6.variables;

public class Variable {

	protected String name;
	protected boolean init;
	public static final String NAME_REGEX = "(((_)((\\w)+))|(([a-zA-Z])(\\w*)))";
	
	public Variable(String name){
		this.name = name;
		this.init = false;
	}
	
	public String getName() {
		return name;
	}
	
}
