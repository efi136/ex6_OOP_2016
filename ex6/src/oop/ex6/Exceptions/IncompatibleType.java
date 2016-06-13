package oop.ex6.Exceptions;

public class IncompatibleType extends Ex6Exceptions {

	/**
	 * An exception for when trying to assign an incompatible type to a variable.
	 */
	private static final long serialVersionUID = 129940353898442853L;

	private String name;
	
	private static final String ERROR_MSG = "Incompatible type: %s.";
	
	/**
	 * A constructor
	 * @param name - The name of the variable.
	 */
	public IncompatibleType(String name){
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
