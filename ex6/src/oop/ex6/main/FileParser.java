package oop.ex6.main;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.*;

public class FileParser {
	/**
	 * A class representing a file.
	 */
	
	//The name of the code file:
	private String filename;
	//An array of the commands (meaningful lines) in the file
	private String[] commands;
	//The index we're currently looking at at the file
	private int index;
	//A white space and comments regex expression
	static String WHITE_SPACE_PATTERN = "\\s*(//)(.*)||(\\s)*";
	
	/**
	 * Reset the index we're looking at
	 */
	public void reset(){
		this.index = 0;
	}
	
	/**
	 * A constructor with a file name
	 * @param filename - The file name
	 */
	public FileParser(String filename){
		this.filename = filename;
	}
	
	/**
	 * Create the commands array
	 * @throws IOException - throws an IO exception
	 */
	public void parse() throws IOException{
		List<String> lst =  java.nio.file.Files.readAllLines(Paths.get(filename));
		this.commands = lst.toArray(new String[lst.size()]); 
	}
	
	/**
	 * Remove all the unneccesary white spaces and comments
	 */
	public void removeWhiteSapce(){
		boolean[] white_space = new boolean[this.commands.length];
		Pattern whitespace = Pattern.compile(WHITE_SPACE_PATTERN);
		Matcher matcher;
		int counter = 0;
		// find all the lines that are not whitespace.
		for (int i=0; i<this.commands.length; i++){
			matcher = whitespace.matcher(this.commands[i]);
			white_space[i] = matcher.matches();
			if (!white_space[i]){
				counter++;
			}
		}
		// only keep the non whitespace lines.
		String[] not_white_space = new String[counter];
		int index = 0;
		for (int i=0;i<white_space.length; i++){
			if (!white_space[i]){
				not_white_space[index] = this.commands[i];
				// replace all space sequences with single space.
				not_white_space[index] = not_white_space[index].replaceAll("\\s+", " ").trim();
				index++;
			}
		}
		this.commands = not_white_space;
	}
	
	/**
	 * Print all the commands of the file
	 */
	public void printCommands(){
		for (int i=0; i<this.commands.length; i++){
			System.out.println(this.commands[i]);
		}
	}
	
	/**
	 * Returns an array of the commands
	 * @return an array of the commands
	 */
	public String[] getCommands(){
		return this.commands;
	}
	
	/**
	 * Returns the command we're currently looking at.
	 * @return the command we're currently looking at.
	 */
	public String getCommand(){
		return this.commands[index];
	}
	
	/**
	 * Return the last command we looked at.
	 * @return the last command we looked at.
	 */
	public String getLastCommand(){
		return this.commands[index-1];
	}
	
	/**
	 * Advance the index we're looking at
	 */
	public void advance(){
		this.index++;
	}
	
	/**
	 * Check if the file has more commnds
	 * @return True if there are commands left, false otherwise
	 */
	public boolean hasMoreCommands(){
		return this.commands.length>this.index;
	}

	/**
	 * Returns the current index we're looking at
	 * @return the current index we're looking at
	 */
	public int getIndex() {
		return this.index;
	}
}
