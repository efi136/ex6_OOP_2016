package oop.ex6.codeBlocks;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import oop.ex6.main.FileParser;
import oop.ex6.variables.Function;
import oop.ex6.variables.SymbolTable;
import oop.ex6.variables.Variable;

public class MethodBlock extends CodeBlock {
	public static final String VARIABLE_DEC = "(\\s*(final\\s+)?" + Variable.TYPES +"\\s+"+ Variable.NAME_REGEX + "\\s*)";
	public static final String VARIABLES_DEC = "((" + VARIABLE_DEC + "(,"+ VARIABLE_DEC + ")*)|"
			+ VARIABLE_DEC + "?)";
	public static final String BLOCK_START = "\\s*void\\s+"+Variable.NAME_REGEX+"\\s*[(]"+VARIABLES_DEC+"[)]\\s*[{]";
	private static final int START_INDEX_FOR_NAME = 5;
	public static final String RETURN_STATMENT = "return;";
	
		
	public MethodBlock(SymbolTable st){
		this.st = new SymbolTable(st);
		this.st.resetLocals();
	}
	
	public static boolean isLineMethodDec(String line){
		Pattern p = Pattern.compile(BLOCK_START);
		Matcher m = p.matcher(line);
		return m.matches();
	}

	private static String getNameFromDec(String line){
		Pattern p = Pattern.compile(Variable.NAME_REGEX);
		Matcher m = p.matcher(line);
		m.find(START_INDEX_FOR_NAME);
		return line.substring(m.start(), m.end());
	}
	
	@Override
	public void compile(FileParser parser){
		String line = parser.getCommand();
		Variable[] local_vars = Function.getVariablesFromDec(line);
		if (!this.st.add_local_variables(local_vars)){
			// error. More than one variable with the same name.
			// TODO :: Throw exception.
		}
		parser.advance();
		// now start parsing until you hit }
		while(parser.getCommand().equals(CodeBlock.BLOCK_END) && parser.hasMoreCommands()){
			line = parser.getCommand();
			// try to parse all of the legal code parts.
			compileHelper(parser, line, st);
		}
		if (!parser.hasMoreCommands()){
			// TODO:: throw unexpected end of file.
			
		}
		// TODO:: check if the last line was return.
		if (!parser.getLastCommand().equals(RETURN_STATMENT)){
			// TODO:: throw error.
			// not return statment before method ends.
		}
		parser.advance();		
	}

}
