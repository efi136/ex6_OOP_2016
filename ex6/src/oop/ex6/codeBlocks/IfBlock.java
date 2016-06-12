package oop.ex6.codeBlocks;

import oop.ex6.variables.BooleanVariable;
import oop.ex6.variables.CharVariable;
import oop.ex6.variables.DoubleVariable;
import oop.ex6.variables.IntVariable;
import oop.ex6.variables.Variable;

public class IfBlock extends CodeBlock {
	public static final String BLOCK_NAME = "if";
	public static final String BLOCK_START = "\\s*"+BLOCK_NAME+"\\s*[(]"+COMPLEX_COND+"[)]\\s*[{]";
	
}
