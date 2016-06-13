package oop.ex6.Exceptions;

public class UsedBeforeAssignment extends Ex6Exceptions {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7175759802918153146L;

	
	private static final String ERROR_MSG = "Variable used before assignment :%s.";

	private String name;
	
	public UsedBeforeAssignment(String name){
		super(0);
		this.name = name;
	}
	
	public void printErrorMsg(){
		System.err.println(String.format(ERROR_MSG, name));
	}
	
}
