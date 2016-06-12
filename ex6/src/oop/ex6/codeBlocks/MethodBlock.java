package oop.ex6.codeBlocks;

import oop.ex6.variables.Variable;

public class MethodBlock extends CodeBlock {
	public static final String VARIABLE_DEC = "(\\s*(final\\s+)?" + Variable.TYPES +"\\s+"+ Variable.NAME_REGEX + "\\s*)";
	public static final String VARIABLES_DEC = "((" + VARIABLE_DEC + "(,"+ VARIABLE_DEC + ")*)|"
			+ VARIABLE_DEC + "?)";
	public static final String BLOCK_START = "\\s*void\\s+"+Variable.NAME_REGEX+"\\s*[(]"+VARIABLES_DEC+"[)]\\s*[{]";
}
