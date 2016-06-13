package oop.ex6.Exceptions;

public class FinalVariableAssignment extends Ex6Exceptions {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5930623043752102337L;
	private String name;
	
	private static final String ERROR_MSG = "Final variable : %s assigned to.";

	
	public FinalVariableAssignment(String name) {
		super(0);
		this.name = name;
	}

	
	public void printErrorMsg(){
		System.err.println(String.format(ERROR_MSG, name));
	}
}
