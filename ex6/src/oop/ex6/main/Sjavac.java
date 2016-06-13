package oop.ex6.main;

import java.io.IOException;

import oop.ex6.codeBlocks.GeneralBlock;

public class Sjavac {

	private static final String WRONG_USAGE_MSG = "Wrong usage. Correct input is: "
			+ "java oop.ex6.main.Sjavac source file name";
	private static final String IO_ERROR = "Unable to read the file.";
	private static final int ERROR = 2;
	private static final int LEGAL = 0;
	private static final int ILLEGAL = 1;
	
	private static int validate(String filename){
		FileParser parser = new FileParser(filename);
		try {
			parser.parse();
		} catch (IOException e) {
			System.err.println(IO_ERROR);
			return ERROR;
		}
		parser.removeWhiteSapce();
		GeneralBlock block = new GeneralBlock();
		if (block.compile(parser)){
			return LEGAL;
		}
		return ILLEGAL;
	}
	
	
	public static void main(String[] args) {
		if (args.length == 1){
			String filename = args[0];
			System.out.println(validate(filename));
		}
		else{
			System.out.println(ERROR);
			System.err.println(WRONG_USAGE_MSG);
		}
	}

}
