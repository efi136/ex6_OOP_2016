package oop.ex6.codeBlocks;

import oop.ex6.main.FileParser;
import oop.ex6.variables.SymbolTable;

public class GeneralBlock {

	SymbolTable st;
	
	public GeneralBlock(){
		this.st = new SymbolTable();
	}
	
	private void processGlobalVars(FileParser parser){
		
	}
	
	public boolean compile(FileParser parser){
		//TODO:: finish this.
		return true;
	}
	
}
