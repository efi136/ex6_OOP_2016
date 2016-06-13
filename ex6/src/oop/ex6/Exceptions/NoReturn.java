package oop.ex6.Exceptions;

public class NoReturn extends Ex6Exceptions {


	/**
	 * An exception for when there's no return call.
	 */
	private static final long serialVersionUID = 2315867714823250469L;
	private static final String ERROR_MSG = "No return at end of function in line %d.";
	
	/**
	 * A constructor
	 * @param line_number - The line number.
	 */
	public NoReturn(int line_number) {
		super(line_number);
	}
	/**
	 * Print error message.
	 */
	public void printErrorMsg(){
		System.err.println(String.format(ERROR_MSG, line_number));
	}
	
}