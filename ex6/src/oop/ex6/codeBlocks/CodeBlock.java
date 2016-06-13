package oop.ex6.codeBlocks;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
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
	public static final String CONDITION_VARIABLE = "([&]{2}|[|]{2})";
	
	/**
	 * This function gets a condition string and returns all of the variables\from this condition.
	 * @param condition - the string of the condition.
	 * @return
	 */
	protected static String[] getVariableNamesFromCondition(String condition){
		String minimized = condition.substring(condition.indexOf('('), condition.indexOf(')')); //TODO: check if this includes ( and )
		Pattern p = Pattern.compile(CONDITION_VARIABLE);
		Matcher m = p.matcher(minimized);
		int amount = (minimized.length() - minimized.replace("||", "").replace("&&", "").length())/2;
		String[] variables = new String[amount];
		for (int i = 0; i < amount-1; i++){
			m.find();
			variables[i] = minimized.substring(0, m.start()).replace(" ", "");
			minimized = minimized.substring(m.end());
		}
		variables[amount-1] = minimized.replace(" ", "");
		return variables;
	}
	
	public boolean compile(FileParser parser){
		
		return true;
		
		//TODO:: finish this.
	}

}
