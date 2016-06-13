package oop.ex6.codeBlocks;

import oop.ex6.main.FileParser;
import oop.ex6.variables.SymbolTable;
import oop.ex6.variables.Variable;

public class GeneralBlock {

	SymbolTable st;
	
	public GeneralBlock(){
		this.st = new SymbolTable();
	}
	
	private void processGlobalVars(FileParser parser){
		int scopeCounter = 0;
		while(parser.hasMoreCommands()){
			String command = parser.getCommand();
			if(command.contains("{")){
				scopeCounter++;
			}
			if(command.contains("}")){
				scopeCounter--;
			}
			if(scopeCounter == 0){
				parser.advance();
				if(Variable.isVariableDec(command)){
					if(!this.st.add_global_variables(Variable.getVariablesFromDec(command, st))){
						//TODO: error double decleration
					}
				}
			}
		}
	}
	
	private void proccessMethodDeclerations(FileParser parser){
		int scopeCounter = 0;
		while(parser.hasMoreCommands()){
			String command = parser.getCommand();
			if(command.contains("{")){
				scopeCounter++;
			}
			if(command.contains("}")){
				scopeCounter--;
			}
			if(scopeCounter == 0){
				parser.advance();
				if(MethodBlock.isLineMethodDec(command)){
					if(!this.st.addGlobalFunctions(Function.getFunctionsFromDec(command, st))){
						//TODO: error double decleration
					}
				}
			}
		}
	}
	
	public boolean compile(FileParser parser){
		//TODO:: finish this.
		return true;
	}
	
}
