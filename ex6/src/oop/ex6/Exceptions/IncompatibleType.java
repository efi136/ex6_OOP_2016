package oop.ex6.Exceptions;

public class IncompatibleType extends Ex6Exceptions {

	/**
	 * 
	 */
	private static final long serialVersionUID = 129940353898442853L;

	private String name;
	
	private static final String ERROR_MSG = "Incompatible type: %s.";

	public IncompatibleType(String name){
		super(0);
		this.name = name;
	}
	
	public void printErrorMsg(){
		System.err.println(String.format(ERROR_MSG, name));
	}
	
	
}
