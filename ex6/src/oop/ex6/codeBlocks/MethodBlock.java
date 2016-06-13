package oop.ex6.codeBlocks;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import oop.ex6.variables.SymbolTable;
import oop.ex6.variables.Variable;

public class MethodBlock extends CodeBlock {
	public static final String VARIABLE_DEC = "(\\s*(final\\s+)?" + Variable.TYPES +"\\s+"+ Variable.NAME_REGEX + "\\s*)";
	public static final String VARIABLES_DEC = "((" + VARIABLE_DEC + "(,"+ VARIABLE_DEC + ")*)|"
			+ VARIABLE_DEC + "?)";
	public static final String BLOCK_START = "\\s*void\\s+"+Variable.NAME_REGEX+"\\s*[(]"+VARIABLES_DEC+"[)]\\s*[{]";
	private static final int START_INDEX_FOR_NAME = 5;

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
	
	

}
