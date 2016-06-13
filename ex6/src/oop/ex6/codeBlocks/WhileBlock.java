package oop.ex6.codeBlocks;

import oop.ex6.main.FileParser;

public class WhileBlock extends CodeBlock {
	
	public static final String BLOCK_NAME = "while";
	public static final String BLOCK_START = "\\s*"+BLOCK_NAME+"\\s*[(]"+COMPLEX_COND+"[)]\\s*[{]";
	
	@Override
	public boolean compile(FileParser parser){
		
		return true;
		
		//TODO:: finish this.
	}

}
