package oop.ex6.Exceptions;

public class UnInitializedFinal extends Ex6Exceptions {

	/**
	 * An error for when a final variable is not initialized.
	 */
	private static final long serialVersionUID = 5046009217224179732L;

	
	private static final String ERROR_MSG = "Uninitialized final %s.";

	private String name;
	
	/**
	 * A constructor
	 * @param name - The name of the variable.
	 */
	public UnInitializedFinal(String name){
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
