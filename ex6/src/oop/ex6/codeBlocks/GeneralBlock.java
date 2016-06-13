package oop.ex6.codeBlocks;

import oop.ex6.Exceptions.DuplicateVariable;
import oop.ex6.Exceptions.Ex6Exceptions;
import oop.ex6.main.FileParser;
import oop.ex6.variables.Function;
import oop.ex6.variables.SymbolTable;
import oop.ex6.variables.Variable;

public class GeneralBlock {

	SymbolTable st;
	
	public GeneralBlock(){
		this.st = new SymbolTable();
	}
	
	private void processGlobalVars(FileParser parser) throws Ex6Exceptions{
		int scopeCounter = 0;
		while(parser.hasMoreCommands()){
			String command = parser.getCommand();
			if(command.contains("{")){
				scopeCounter++;
			}
			else if(command.contains("}")){
				scopeCounter--;
			}
			else if(scopeCounter == 0){
				if(Variable.isVariableDec(command)){
					if(!this.st.add_global_variables(Variable.getVariablesFromDec(command, st))){
						throw new DuplicateVariable(parser.getIndex());
					}
				}
				else if(Variable.isAssignmentLine(command)){
					Variable.processAssignmentLine(command, st);
				}
				else if(MethodBlock.isLineMethodDec(command)){
					// do nothing.
				}
				else{
					throw new Ex6Exceptions(parser.getIndex());
				}
			}
			parser.advance();
		}
	}
	
	private void proccessMethodDeclerations(FileParser parser) throws Ex6Exceptions{
		int scopeCounter = 0;
		while(parser.hasMoreCommands()){
			String command = parser.getCommand();
			parser.advance();
			if(command.contains("{")){
				scopeCounter++;
			}
			else if(command.contains("}")){
				scopeCounter--;
			}
			else if(scopeCounter == 0){
				if(MethodBlock.isLineMethodDec(command)){
					if(!this.st.addGlobalFunction(Function.getFunctionFromDec(command))){
						throw new DuplicateVariable(parser.getIndex());
					}
				}
			}
		}
	}
	
	/**
	 * This function processes the global block.
	 * @param parser - the file
	 * @return
	 * @throws Ex6Exceptions 
	 */
	private boolean proccessBlock(FileParser parser) throws Ex6Exceptions{
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
				throw new Ex6Exceptions(parser.getIndex());
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
		try {
			this.processGlobalVars(parser);
		} catch (Ex6Exceptions e1) {
			e1.printErrorMsg();
			return false;
		}
		parser.reset();
		try {
			this.proccessMethodDeclerations(parser);
		} catch (Ex6Exceptions e1) {
			e1.printErrorMsg();
			return false;
		}
		parser.reset();
		try {
			this.proccessBlock(parser);
		} catch (Ex6Exceptions e) {
			e.printErrorMsg();
			return false;
		}
		return true;
	}
	
}
