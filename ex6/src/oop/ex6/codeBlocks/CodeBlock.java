package oop.ex6.codeBlocks;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import oop.ex6.Exceptions.DuplicateVariable;
import oop.ex6.Exceptions.Ex6Exceptions;
import oop.ex6.Exceptions.UnExpectedEndOfFile;
import oop.ex6.main.FileParser;
import oop.ex6.variables.BooleanVariable;
import oop.ex6.variables.DoubleVariable;
import oop.ex6.variables.Function;
import oop.ex6.variables.IntVariable;
import oop.ex6.variables.SymbolTable;
import oop.ex6.variables.Variable;

public class CodeBlock {
	//Regex expressions for conditions and blocks
	public static final String COND = "("+BooleanVariable.VALUE_REGEX+"|"+
			DoubleVariable.VALUE_REGEX+"|"+IntVariable.VALUE_REGEX+"|"+Variable.NAME_REGEX+")";
	public static final String COMPLEX_COND = "(\\s*" + COND + "(\\s*([&]{2}|[|]{2})\\s*" + COND + ")*)";
	public static final String BLOCK_END = "}";
	public static final String CONDITION_VARIABLE = "[|]{2}|[&]{2}";
	
	protected SymbolTable st;

	/**
	 * This function gets a condition string and returns all of the variables\from this condition.
	 * @param condition - the string of the condition.
	 * @return
	 */
	protected static String[] getVariableNamesFromCondition(String condition){
		String minimized = condition.substring(condition.indexOf('(')+1, condition.indexOf(')'));
		Pattern p = Pattern.compile(CONDITION_VARIABLE);
		Matcher m = p.matcher(minimized);
		int amount = (minimized.length() - minimized.replace("||", "").replace("&&", "").length())/2 + 1 ;
		String[] variables = new String[amount];
		for (int i = 0; i < amount-1; i++){
			m.find();
			variables[i] = minimized.substring(0, m.start()).replace(" ", "");
			minimized = minimized.substring(m.end());
		}
		variables[amount-1] = minimized.replace(" ", "");
		return variables;
	}
	
	/**
	 * A function that compiles a block.
	 * @param parser - The file parser.
	 * @param line - The line we're at.
	 * @param st - SymbolTable.
	 * @throws Ex6Exceptions - A general exception
	 */
	protected static void compileHelper (FileParser parser, String line, SymbolTable st) throws Ex6Exceptions{
		if (Function.checkIfLineIsMethodCall(line, st)){
			parser.advance();
		}
		else if (IfBlock.isLineLegalIfBlock(line, st)){
			IfBlock block = new IfBlock(st);
			block.compile(parser);
		}
		else if (WhileBlock.isLineLegalWhileBlock(line, st)){
			WhileBlock block = new WhileBlock(st);
			block.compile(parser);
		}
		else if (Variable.isAssignmentLine(line)){
			Variable.processAssignmentLine(line, st);
			parser.advance();
		}
		else if(Variable.isVariableDec(line)){
			if(!st.add_local_variables(Variable.getVariablesFromDec(line, st))){
				throw new DuplicateVariable(parser.getIndex());
			}
			parser.advance();
		}
		else if (line.equals(MethodBlock.RETURN_STATMENT)){
			parser.advance();
		}
		else {
			throw new Ex6Exceptions(parser.getIndex());
		}
	}
	
	/**
	 * Compile a file parser.
	 * @param parser - A file parser.
	 * @throws Ex6Exceptions - A general exception.
	 */
	public void compile(FileParser parser) throws Ex6Exceptions{
		String line = parser.getCommand();
		parser.advance();
		// now start parsing until you hit }
		while((!parser.getCommand().equals(CodeBlock.BLOCK_END)) && parser.hasMoreCommands()){
			line = parser.getCommand();
			// try to parse all of the legal code parts.
			compileHelper(parser, line, st);
		}
		if (!parser.hasMoreCommands()){
			throw new UnExpectedEndOfFile(parser.getIndex());
		}
		parser.advance();
	}

}
