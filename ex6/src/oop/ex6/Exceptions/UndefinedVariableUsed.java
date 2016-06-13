package oop.ex6.Exceptions;

public class UndefinedVariableUsed extends Ex6Exceptions {

	/**
	 * An error for when a variable is used before decleration.
	 */
	private static final long serialVersionUID = -8895732691503054958L;

	private String name;
	
	private static final String ERROR_MSG = "Variable used before decleration in line %s.";

	/**
	 * A constructor
	 * @param name - The name of the variable.
	 */
	public UndefinedVariableUsed(String name){
		super(0);
		this.name = name;
	}
	/**
	 * Print error message.
	 */
	public void printErrorMsg(){
		System.err.println(String.format(ERROR_MSG, name));
	}
	
}
