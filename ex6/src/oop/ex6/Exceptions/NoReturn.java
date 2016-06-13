package oop.ex6.Exceptions;

public class NoReturn extends Ex6Exceptions {


	/**
	 * 
	 */
	private static final long serialVersionUID = 2315867714823250469L;
	private static final String ERROR_MSG = "No return at end of function in line %d.";
	
	public NoReturn(int line_number) {
		super(line_number);
	}
	
	public void printErrorMsg(){
		System.err.println(String.format(ERROR_MSG, line_number));
	}
	
}