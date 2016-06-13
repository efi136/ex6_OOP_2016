package oop.ex6.Exceptions;

public class UnExpectedEndOfFile extends Ex6Exceptions {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4783718252966982667L;

	private static final String ERROR_MSG = "Unexpected end of file";

	public UnExpectedEndOfFile(int line_number) {
		super(line_number);
	}

	public void printErrorMsg(){
		System.err.println(ERROR_MSG);
	}
}
