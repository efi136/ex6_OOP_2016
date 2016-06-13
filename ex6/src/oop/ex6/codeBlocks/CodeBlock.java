package oop.ex6.codeBlocks;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import oop.ex6.main.FileParser;
import oop.ex6.variables.BooleanVariable;
import oop.ex6.variables.DoubleVariable;
import oop.ex6.variables.Function;
import oop.ex6.variables.IntVariable;
import oop.ex6.variables.SymbolTable;
import oop.ex6.variables.Variable;

public class CodeBlock {
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
		String minimized = condition.substring(condition.indexOf('('), condition.indexOf(')'));
		Pattern p = Pattern.compile(CONDITION_VARIABLE);
		Matcher m = p.matcher(minimized);
		int amount = (minimized.length() - minimized.replace("||", "").replace("&&", "").length())/2;
		String[] variables = new String[amount];
		for (int i = 0; i < amount-1; i++){
			m.find();
			variables[i] = minimized.substring(0, m.start()).replace(" ", "");
			minimized = minimized.substring(m.end());
		}
		variables[amount-1] = minimized.replace(" ", "");
		return variables;
	}
	
	protected static void compileHelper (FileParser parser, String line, SymbolTable st){
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
				//TODO: error double decleration
			}
		}
		else if (line.equals(MethodBlock.RETURN_STATMENT)){
			parser.advance();
		}
	}
	
	public void compile(FileParser parser){
		String line = parser.getCommand();
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
		parser.advance();
	}

}
