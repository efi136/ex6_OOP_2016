package oop.ex6.Exceptions;

public class UsedBeforeAssignment extends Ex6Exceptions {

	/**
	 * An exception for when a variable is used before assignment.
	 */
	private static final long serialVersionUID = 7175759802918153146L;

	
	private static final String ERROR_MSG = "Variable used before assignment :%s.";

	private String name;
	
	/**
	 * A constructor
	 * @param name - The name of the variable.
	 */
	public UsedBeforeAssignment(String name){
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
