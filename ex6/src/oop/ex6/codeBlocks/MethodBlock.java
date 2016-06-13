package oop.ex6.codeBlocks;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import oop.ex6.Exceptions.Ex6Exceptions;
import oop.ex6.Exceptions.NoReturn;
import oop.ex6.Exceptions.UnExpectedEndOfFile;
import oop.ex6.Exceptions.UndefinedVariableUsed;
import oop.ex6.main.FileParser;
import oop.ex6.variables.Function;
import oop.ex6.variables.SymbolTable;
import oop.ex6.variables.Variable;

public class MethodBlock extends CodeBlock {
	//Regular expressions for Declerations and blocks.
	public static final String VARIABLE_DEC = "(\\s*(final\\s+)?" + Variable.TYPES +"\\s+"+ Variable.NAME_REGEX + "\\s*)";
	public static final String VARIABLES_DEC = "((" + VARIABLE_DEC + "(,"+ VARIABLE_DEC + ")*)|"
			+ VARIABLE_DEC + "?)";
	public static final String BLOCK_START = "\\s*void\\s+"+Variable.NAME_REGEX+"\\s*[(]"+VARIABLES_DEC+"[)]\\s*[{]";
	private static final int START_INDEX_FOR_NAME = 5;
	public static final String RETURN_STATMENT = "return;";
	
	/**
	 * A simple constructor
	 * @param st - The symbol table.
	 */
	public MethodBlock(SymbolTable st){
		this.st = new SymbolTable(st);
		this.st.resetLocals();
	}
	
	/**
	 * Checks if a line is a method decleration.
	 * @param line - The line.
	 * @return True if it is else otherwise.
	 */
	public static boolean isLineMethodDec(String line){
		Pattern p = Pattern.compile(BLOCK_START);
		Matcher m = p.matcher(line);
		return m.matches();
	}
	
	/**
	 * Returns the name of the method for declaration.
	 * @param line - The line of the declaration.
	 * @return The name of the declaration.
	 */
	@SuppressWarnings("unused")
	private static String getNameFromDec(String line){
		Pattern p = Pattern.compile(Variable.NAME_REGEX);
		Matcher m = p.matcher(line);
		m.find(START_INDEX_FOR_NAME);
		return line.substring(m.start(), m.end());
	}
	
	/**
	 * Compile a method block
	 */
	@Override
	public void compile(FileParser parser) throws Ex6Exceptions{
		String line = parser.getCommand();
		Variable[] local_vars = Function.getVariablesFromDec(line);
		if ((local_vars!=null) && !this.st.add_local_variables(local_vars)){
			// error. More than one variable with the same name.
			throw new UndefinedVariableUsed(local_vars[0].getName());
		}
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
		if (!parser.getLastCommand().equals(RETURN_STATMENT)){
			// no return statment before method ends.
			throw new NoReturn(parser.getIndex());
		}
		parser.advance();		
	}

}
