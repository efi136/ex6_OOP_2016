package oop.ex6.Exceptions;

public class Ex6Exceptions extends Exception {

	/**
	 * A general exception.
	 */
	private static final String ERROR_MSG = "Error in line %d.";
	
	protected int line_number;
	
	/**
	 * A constructor
	 * @param line_number - The line number.
	 */
	public Ex6Exceptions(int line_number){
		this.line_number = line_number;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Print error message.
	 */
	public void printErrorMsg(){
		System.err.println(String.format(ERROR_MSG, line_number));
	}
	
}
