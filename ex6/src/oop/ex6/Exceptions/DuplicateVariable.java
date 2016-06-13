package oop.ex6.Exceptions;

public class DuplicateVariable extends Ex6Exceptions {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7484240876571121311L;
	private static final String ERROR_MSG = "Duplicate variable decleration in line %d.";

	
	
	public DuplicateVariable(int line_number){
		super(line_number);
	}
	
	public void printErrorMsg(){
		System.err.println(String.format(ERROR_MSG, line_number));
	}
	
}
