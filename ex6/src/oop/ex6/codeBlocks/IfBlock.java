package oop.ex6.codeBlocks;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import oop.ex6.Exceptions.Ex6Exceptions;
import oop.ex6.Exceptions.UsedBeforeAssignment;
import oop.ex6.Symbols.BooleanVariable;
import oop.ex6.Symbols.DoubleVariable;
import oop.ex6.Symbols.IntVariable;
import oop.ex6.Symbols.SymbolTable;

public class IfBlock extends CodeBlock {
	/**
	 * The block name
	 */
	public static final String BLOCK_NAME = "if";
	/**
	 * A regex expression of the start of an If block
	 */
	public static final String BLOCK_START = "\\s*"+BLOCK_NAME+"\\s*[(]"+COMPLEX_COND+"[)]\\s*[{]";
	
	/**
	 * A simple constructor
	 * @param st - The symbol table.
	 */
	public IfBlock(SymbolTable st){
		this.st = new SymbolTable(st);
	}
	
	/**
	 * This function checks if the line is a valid if statement.
	 * @param line - the line to be checked.
	 * @param st - the symbolTable
	 * @return true if this line is legal and false otherwise.
	 * @throws Ex6Exceptions 
	 */
	public static boolean isLineLegalIfBlock(String line, SymbolTable st) throws Ex6Exceptions{
		Pattern p = Pattern.compile(BLOCK_START);
		Matcher m = p.matcher(line);
		if (!m.matches()){
			//If not a legal if statement
			return false;
		}
		String condition;
		condition = line.substring(3); //Delete "if "
		String[] names = getVariableNamesFromCondition(condition);
		if (!st.isInit(names)){
			throw new UsedBeforeAssignment(names[0]);
			// uninitialized variable in if.
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
		return cond;
		
	}
}
