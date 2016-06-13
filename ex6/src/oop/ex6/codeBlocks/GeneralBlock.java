package oop.ex6.codeBlocks;

import oop.ex6.main.FileParser;
import oop.ex6.variables.Function;
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
			parser.advance();
			if(command.contains("{")){
				scopeCounter++;
			}
			if(command.contains("}")){
				scopeCounter--;
			}
			if(scopeCounter == 0){
				if(Variable.isVariableDec(command)){
					if(!this.st.add_global_variables(Variable.getVariablesFromDec(command, st))){
						//TODO: error double decleration
					}
				}
				else if(Variable.isAssignmentLine(command)){
					Variable.processAssignmentLine(command, st);
				}
			}
		}
	}
	
	private void proccessMethodDeclerations(FileParser parser){
		int scopeCounter = 0;
		while(parser.hasMoreCommands()){
			String command = parser.getCommand();
			parser.advance();
			if(command.contains("{")){
				scopeCounter++;
			}
			if(command.contains("}")){
				scopeCounter--;
			}
			if(scopeCounter == 0){
				if(MethodBlock.isLineMethodDec(command)){
					if(!this.st.addGlobalFunction(Function.getFunctionFromDec(command))){
						//TODO: error double decleration
					}
				}
			}
		}
	}
	
	/**
	 * This function processes the global block.
	 * @param parser - the file
	 * @return
	 */
	private boolean proccessBlock(FileParser parser){
		// the leagal operations here are method decleration and global variable decleration.
		while(parser.hasMoreCommands()){
			String command = parser.getCommand();
			if (Variable.isVariableDec(command)){
				parser.advance();
				// We already delt with that when getting the global vars.
			}
			else if (Variable.isAssignmentLine(command)){
				Variable.processAssignmentLine(command, this.st);
			}
			else if (MethodBlock.isLineMethodDec(command)){
				MethodBlock block = new MethodBlock(this.st);
				// compile this method.
				block.compile(parser);
			}
			else{
				// this line should not be here. Error.
				// TODO:: error.
				return false;
			}
		}
		return true;
	}
	
	/**
	 * This function runs the compilation for this file.
	 * @param parser - the file parser.
	 * @return true if the compilation succeded. false otherwise.
	 */
	public boolean compile(FileParser parser){
		this.processGlobalVars(parser);
		parser.reset();
		this.proccessMethodDeclerations(parser);
		parser.reset();
		this.proccessBlock(parser);
		return true;
	}
	
}
