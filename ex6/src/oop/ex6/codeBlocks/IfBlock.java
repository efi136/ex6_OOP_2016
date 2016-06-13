package oop.ex6.codeBlocks;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import oop.ex6.main.FileParser;
import oop.ex6.variables.BooleanVariable;
import oop.ex6.variables.CharVariable;
import oop.ex6.variables.DoubleVariable;
import oop.ex6.variables.IntVariable;
import oop.ex6.variables.SymbolTable;
import oop.ex6.variables.Variable;

public class IfBlock extends CodeBlock {
	public static final String BLOCK_NAME = "if";
	public static final String BLOCK_START = "\\s*"+BLOCK_NAME+"\\s*[(]"+COMPLEX_COND+"[)]\\s*[{]";
	
	
	public static boolean isLineLegalIfBlock(String line, SymbolTable st){
		Pattern p = Pattern.compile(BLOCK_START);
		Matcher m = p.matcher(line);
		if (!m.matches()){
			return false;
		}
		String condition;
		
		Variables[] vars = getVariablesFromCondition(condition);
	}
	
	
	
	@Override
	public boolean compile(FileParser parser){
		
		return true;
		
		//TODO:: finish this.
	}

}
