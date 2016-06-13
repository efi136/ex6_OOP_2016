package oop.ex6.Exceptions;

public class UnExpectedEndOfFile extends Ex6Exceptions {

	/**
	 * An exception for an unexcpected end of file.
	 */
	private static final long serialVersionUID = -4783718252966982667L;

	private static final String ERROR_MSG = "Unexpected end of file";

	/**
	 * A constructor
	 * @param line_number - The line number.
	 */
	public UnExpectedEndOfFile(int line_number) {
		super(line_number);
	}
	/**
	 * Print error message.
	 */
	public void printErrorMsg(){
		System.err.println(ERROR_MSG);
	}
}
