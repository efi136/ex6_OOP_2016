package oop.ex6.main;

import java.io.IOException;

import oop.ex6.codeBlocks.GeneralBlock;

public class Sjavac {

	//Wrong usage message:
	private static final String WRONG_USAGE_MSG = "Wrong usage. Correct input is: "
			+ "java oop.ex6.main.Sjavac source file name";
	//IO exception:
	private static final String IO_ERROR = "Unable to read the file.";
	//Error code:
	private static final int ERROR = 2;
	//Legal code:
	private static final int LEGAL = 0;
	//Illegal code:
	private static final int ILLEGAL = 1;
	
	/**
	 * Validate a s-java code file
	 * @param filename - The file name
	 * @return 0 for legal code, 1 for illegal code, 2 for an error
	 */
	private static int validate(String filename){
		FileParser parser = new FileParser(filename);
		try {
			//Read file and command lines:
			parser.parse();
		} catch (IOException e) {
			System.err.println(IO_ERROR);
			return ERROR;
		}
		//Remove white spaces:
		parser.removeWhiteSapce();
		GeneralBlock block = new GeneralBlock();
		//Call compile on the total block.
		if (block.compile(parser)){
			return LEGAL;
		}
		return ILLEGAL;
	}
	
	/**
	 * The main function
	 * @param args - The arguments the program gets
	 */
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
