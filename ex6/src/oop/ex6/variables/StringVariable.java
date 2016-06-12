package oop.ex6.variables;

public class StringVariable extends Variable {

	private String value;
	
	public StringVariable(String name) {
		super(name);
	}

	public StringVariable(String name, String value) {
		super(name);
		this.setValue(value);
	}
	
	public void setValue(String value){
		this.init = true;
		this.value = value;
	}
	
}
