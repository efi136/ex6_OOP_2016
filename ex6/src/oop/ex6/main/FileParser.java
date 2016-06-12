package oop.ex6.main;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.*;

public class FileParser {

	private String filename;
	private String[] commands;
	private int index;
	static String WHITE_SPACE_PATTERN = "(//)(.*)||(\\s)*";
	
	public FileParser(String filename){
		this.filename = filename;
	}
	
	public void parse() throws IOException{
		List<String> lst =  java.nio.file.Files.readAllLines(Paths.get(filename));
		this.commands = lst.toArray(new String[lst.size()]); 
	}
	
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
				not_white_space[index] = not_white_space[index].replaceAll(" +", " ");
				index++;
			}
		}
		this.commands = not_white_space;
	}
	
	public void printCommands(){
		for (int i=0; i<this.commands.length; i++){
			System.out.println(this.commands[i]);
		}
	}
	
	public String[] getCommands(){
		return this.commands;
	}

	public String getCommand(){
		return this.commands[index];
	}
	
	public void advance(){
		this.index++;
	}
	
	public boolean hasMoreCommands(){
		return this.commands.length>this.index;
	}
}
