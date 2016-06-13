package oop.ex6.Exceptions;

public class DuplicateVariable extends Ex6Exceptions {

	/**
	 * An exception class for when there's a duplicate variable decleration.
	 */
	private static final long serialVersionUID = -7484240876571121311L;
	private static final String ERROR_MSG = "Duplicate variable decleration in line %d.";

	
	/**
	 * A constructor
	 * @param line_number - The line number.
	 */
	public DuplicateVariable(int line_number){
		super(line_number);
	}
	
	/**
	 * Print error message.
	 */
	public void printErrorMsg(){
		System.err.println(String.format(ERROR_MSG, line_number));
	}
	
}
