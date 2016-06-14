package oop.ex6.codeBlocks;

import oop.ex6.Exceptions.DuplicateVariable;
import oop.ex6.Exceptions.Ex6Exceptions;
import oop.ex6.Exceptions.UnExpectedEndOfFile;
import oop.ex6.Symbols.BooleanVariable;
import oop.ex6.Symbols.DoubleVariable;
import oop.ex6.Symbols.Function;
import oop.ex6.Symbols.IntVariable;
import oop.ex6.Symbols.SymbolTable;
import oop.ex6.Symbols.Variable;
import oop.ex6.main.FileParser;

public class CodeBlock {
	//Regex expressions for conditions and blocks:
	//Condition:
	public static final String COND = "("+BooleanVariable.VALUE_REGEX+"|"+
			DoubleVariable.VALUE_REGEX+"|"+IntVariable.VALUE_REGEX+"|"+Variable.NAME_REGEX+")";
	//Condition with || or &&
	public static final String COMPLEX_COND = "(\\s*" + COND + "\\s*(\\s*([&]{2}|[|]{2})\\s*" + COND + ")*)";
	//Block end
	public static final String BLOCK_END = "}";
	//|| or &&
	public static final String CONDITION_VARIABLE = "[|]{2}|[&]{2}";
	
	protected SymbolTable st;

	/**
	 * Returns the name of the variables in a function call
	 * @param line - the line with the function call
	 * @return the name of the variables in a function call
	 */
	public static String[] getVariableNameFromFuncCall(String line){
		int count = line.length() - line.replace(",", "").length(); //Count the number of variables
		if (line.indexOf(')') - line.indexOf('(') == 1){
			// no variables.
			return null;
		}
		String[] names = new String[count+1];
		String vars = line.substring(line.indexOf('(')+1,line.lastIndexOf(')')); //The part inside ()
		names = vars.split(","); //The names of the variables
		// make sure that it starts from the variable names and not the function name.
		for (int i=0; i<=count; i++){
			names[i] = names[i].trim();
		}
		return names;
	}
	
	/**
	 * This function gets a condition string and returns all of the variables\from this condition.
	 * @param condition - the string of the condition.
	 * @return
	 */
	protected static String[] getVariableNamesFromCondition(String condition){
		String doctored_line = condition.replaceAll(CONDITION_VARIABLE, ",");
		return getVariableNameFromFuncCall(doctored_line);
	}
	
	/**
	 * A function that compiles a block.
	 * @param parser - The file parser.
	 * @param line - The line we're at.
	 * @param st - SymbolTable.
	 * @throws Ex6Exceptions - A general exception
	 */
	protected static void compileHelper (FileParser parser, String line, SymbolTable st) throws Ex6Exceptions{
		if (Function.checkIfLineIsMethodCall(line, st)){ //Check if a line is a method call
			parser.advance();
		}
		else if (IfBlock.isLineLegalIfBlock(line, st)){ //Check if it's a legal if block
			IfBlock block = new IfBlock(st);
			block.compile(parser);
		}
		else if (WhileBlock.isLineLegalWhileBlock(line, st)){ //Check if it's a legal while block
			WhileBlock block = new WhileBlock(st);
			block.compile(parser);
		}
		else if (Variable.isAssignmentLine(line)){ //Check if it's a variable assignment
			Variable.processAssignmentLine(line, st);
			parser.advance();
		}
		else if(Variable.isVariableDec(line)){ //check if it's a varaible decleration
			if(!st.addLocalVariables(Variable.getVariablesFromDec(line, st))){
				throw new DuplicateVariable(parser.getIndex());
			}
			parser.advance();
		}
		else if (line.equals(MethodBlock.RETURN_STATMENT)){ //Check if a return statement
			parser.advance();
		}
		else { //If none of the possible cases for the line
			throw new Ex6Exceptions(parser.getIndex());
		}
	}
	
	/**
	 * Compile a file parser.
	 * @param parser - A file parser.
	 * @throws Ex6Exceptions - A general exception.
	 */
	public void compile(FileParser parser) throws Ex6Exceptions{
		String line = parser.getCommand(); //Next line
		parser.advance();
		// now start parsing until you hit }
		while((!parser.getCommand().equals(CodeBlock.BLOCK_END)) && parser.hasMoreCommands()){
			//While not finished going over the block
			line = parser.getCommand();
			// try to parse all of the legal code parts.
			compileHelper(parser, line, st);
		}
		if (!parser.hasMoreCommands()){ //If file ended before expected
			throw new UnExpectedEndOfFile(parser.getIndex());
		}
		parser.advance();
	}

}
