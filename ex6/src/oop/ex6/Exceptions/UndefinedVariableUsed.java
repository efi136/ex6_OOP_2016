package oop.ex6.Exceptions;

public class UndefinedVariableUsed extends Ex6Exceptions {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8895732691503054958L;

	private String name;
	
	private static final String ERROR_MSG = "Variable used before decleration in line %s.";

	public UndefinedVariableUsed(String name){
		super(0);
		this.name = name;
	}
	
	public void printErrorMsg(){
		System.err.println(String.format(ERROR_MSG, name));
	}
	
}
