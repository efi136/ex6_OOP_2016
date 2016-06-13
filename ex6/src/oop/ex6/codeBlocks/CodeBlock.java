package oop.ex6.codeBlocks;

import oop.ex6.main.FileParser;
import oop.ex6.variables.BooleanVariable;
import oop.ex6.variables.DoubleVariable;
import oop.ex6.variables.IntVariable;
import oop.ex6.variables.Variable;

public class CodeBlock {
	public static final String COND = "("+BooleanVariable.VALUE_REGEX+"|"+
			DoubleVariable.VALUE_REGEX+"|"+IntVariable.VALUE_REGEX+"|"+Variable.NAME_REGEX+")";
	public static final String COMPLEX_COND = "(\\s*" + COND + "(\\s*([&]{2}|[|]{2})\\s*" + COND + ")*)";
	public static final String BLOCK_END = "}";
	
	/**
	 * This function gets a condition string and returns all of the variables\from this condition.
	 * @param condition - the string of the condition.
	 * @return
	 */
	protected static Variable[] getVariablesFromCondition(String condition){
		return new Variable[1];
		// TODO :: do this.
	}
	
	public boolean compile(FileParser parser){
		
		return true;
		
		//TODO:: finish this.
	}

}
