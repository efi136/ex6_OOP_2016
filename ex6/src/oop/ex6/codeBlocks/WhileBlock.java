package oop.ex6.codeBlocks;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import oop.ex6.Exceptions.Ex6Exceptions;
import oop.ex6.Exceptions.IncompatibleType;
import oop.ex6.Exceptions.UsedBeforeAssignment;
import oop.ex6.Symbols.BooleanVariable;
import oop.ex6.Symbols.DoubleVariable;
import oop.ex6.Symbols.IntVariable;
import oop.ex6.Symbols.SymbolTable;

public class WhileBlock extends CodeBlock {
	
	//The block name:
	public static final String BLOCK_NAME = "while";
	//The start of a while block
	public static final String BLOCK_START = "\\s*"+BLOCK_NAME+"\\s*[(]"+COMPLEX_COND+"[)]\\s*[{]";
	
	//A while block constructor
	public WhileBlock(SymbolTable st){
		this.st = new SymbolTable(st);
	}
	
	/**
	 * This function checks if the line is a valid while statement.
	 * @param line - the line to be checked.
	 * @param st - the symbolTable
	 * @return true if this line is legal and false otherwise.
	 * @throws Ex6Exceptions 
	 */
	public static boolean isLineLegalWhileBlock(String line, SymbolTable st) throws Ex6Exceptions {
		Pattern p = Pattern.compile(BLOCK_START);
		Matcher m = p.matcher(line);
		if (!m.matches()){
			//If the block doesn't start as a while block
			return false;
		}
		String condition;
		condition = line.substring(5); //The condition for the while block
		String[] names = getVariableNamesFromCondition(condition); //The names of the variables
		if (!st.isInit(names)){
			throw new UsedBeforeAssignment(names[0]);
			// uninitialized variable in if.
		}
		String[] types = st.getVariablesType(names); //The types of the variables
		boolean cond = true;
		for (String type: types){
			switch (type){
			case IntVariable.TYPE:
			case DoubleVariable.TYPE:
			case BooleanVariable.TYPE:
				break;
			default:
				//If the condition is not int, double, or boolean
				cond = false;
			}
		}
		if (!cond){
			throw new IncompatibleType(names[0]);
			// Bad type for condition.
		}
		return cond;
		
	}

}
