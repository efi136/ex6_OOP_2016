package oop.ex6.codeBlocks;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import oop.ex6.variables.BooleanVariable;
import oop.ex6.variables.DoubleVariable;
import oop.ex6.variables.IntVariable;
import oop.ex6.variables.SymbolTable;

public class WhileBlock extends CodeBlock {
	
	public static final String BLOCK_NAME = "while";
	public static final String BLOCK_START = "\\s*"+BLOCK_NAME+"\\s*[(]"+COMPLEX_COND+"[)]\\s*[{]";
	
	public WhileBlock(SymbolTable st){
		this.st = new SymbolTable(st);
	}
	
	/**
	 * This function checks if the line is a valid while statement.
	 * @param line - the line to be checked.
	 * @param st - the symbolTable
	 * @return true if this line is legal and false otherwise.
	 */
	public static boolean isLineLegalWhileBlock(String line, SymbolTable st) {
		Pattern p = Pattern.compile(BLOCK_START);
		Matcher m = p.matcher(line);
		if (!m.matches()){
			return false;
		}
		String condition;
		condition = line.substring(6);
		String[] names = getVariableNamesFromCondition(condition);
		if (!st.isInit(names)){
			// TODO:: add exception here.
			// uninitialized variable in if.
			return false;
		}
		String[] types = st.get_variables_type(names);
		boolean cond = true;
		for (String type: types){
			switch (type){
			case IntVariable.TYPE:
			case DoubleVariable.TYPE:
			case BooleanVariable.TYPE:
				break;
			default:
				cond = false;
			}
		}
		if (!cond){
			// TODO:: Throw exception.
			// Bad type for condition.
		}
		return cond;
		
	}

}
