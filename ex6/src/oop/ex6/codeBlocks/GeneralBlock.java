package oop.ex6.codeBlocks;

import oop.ex6.Exceptions.DuplicateVariable;
import oop.ex6.Exceptions.Ex6Exceptions;
import oop.ex6.Symbols.Function;
import oop.ex6.Symbols.SymbolTable;
import oop.ex6.Symbols.Variable;
import oop.ex6.main.FileParser;

public class GeneralBlock {

	SymbolTable st;
	
	/**
	 * a general constructor
	 */
	public GeneralBlock(){
		this.st = new SymbolTable();
	}
	
	/**
	 * Proccess all the global variables in a file.
	 * @param parser - The file parser.
	 * @throws Ex6Exceptions - A general exception
	 */
	private void processGlobalVars(FileParser parser) throws Ex6Exceptions{
		int scopeCounter = 0;
		while(parser.hasMoreCommands()){
			String command = parser.getCommand(); //Next line
			if(command.contains("{")){
				//If entered a new scope
				scopeCounter++;
			}
			else if(command.contains("}")){
				//if exited a scope
				scopeCounter--;
			}
			else if(scopeCounter == 0){
				//if outside of all scopes
				if(Variable.isVariableDec(command)){ //Check if a legal variable decleratoin
					if(!this.st.add_global_variables(Variable.getVariablesFromDec(command, st))){
						throw new DuplicateVariable(parser.getIndex());
					}
				}
				else if(Variable.isAssignmentLine(command)){
					//check if a legal variable assignment
					Variable.processAssignmentLine(command, st);
				}
				else if(MethodBlock.isLineMethodDec(command)){
					//Check if a legal method declerations
					// do nothing.
				}
				else{
					//If none of the possible cases
					throw new Ex6Exceptions(parser.getIndex());
				}
			}
			parser.advance();
		}
	}
	
	/**
	 * Process method declerations
	 * @param parser - The files parser.
	 * @throws Ex6Exceptions - The general exception.
	 */
	private void proccessMethodDeclerations(FileParser parser) throws Ex6Exceptions{
		int scopeCounter = 0;
		while(parser.hasMoreCommands()){
			String command = parser.getCommand(); //Next command
			parser.advance();
			if(command.contains("{")){
				if(scopeCounter == 0){
					if(MethodBlock.isLineMethodDec(command)){
						//Check if the line is a method decleration
						if(!this.st.addGlobalFunction(Function.getFunctionFromDec(command))){
							//If there's an error with the variables in the function's decleration
							throw new DuplicateVariable(parser.getIndex());
						}
					}
				}
				scopeCounter++;
			}
			else if(command.contains("}")){
				scopeCounter--;
			}
			else if(scopeCounter == 0){
				if(MethodBlock.isLineMethodDec(command)){
					//Check if the line is a method decleration
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
			//Process global variables
			this.processGlobalVars(parser);
		} catch (Ex6Exceptions e1) {
			e1.printErrorMsg();
			return false;
		}
		parser.reset();
		try {
			//Process method declerations
			this.proccessMethodDeclerations(parser);
		} catch (Ex6Exceptions e1) {
			e1.printErrorMsg();
			return false;
		}
		parser.reset();
		try {
			//Process blocks (if an while)
			this.proccessBlock(parser);
		} catch (Ex6Exceptions e) {
			e.printErrorMsg();
			return false;
		}
		return true;
	}
	
}
