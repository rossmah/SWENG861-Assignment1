package edu.psgv.sweng861;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Scanner;


/**
 * This class (Main) reads in a file that is an Argument in runtime, asks the user for 
 * the maximum characters it should read in, and print statistics to the console.
 * @author Holly Rossmann
 *
 */
public class Main {

	/**
     * main() accepts a single commmand line parameter.
     * @param arg[0] path to the input text file.
     */
    public static void main(String[] args) {
    	if (args.length != 1) {
			System.out.println("Missing command line argument w/ file name");
			System.exit(1);;
		}
    	
    	String fileName = args[0];
    	Scanner scanner = new Scanner(System.in);
    		
		boolean validInput = false;
		int maxCharacters = 0;
		
		System.out.println("Enter number of maximum characters to read in: ");
		do {
			//Ask user for number of characters to read in
			String line = scanner.nextLine();
			maxCharacters = 0;
			try {
				maxCharacters = Integer.parseInt(line);
			} 
			catch (NumberFormatException e) {
				System.err.println("Please enter an integer.");
				continue;
			}
			if (maxCharacters <= 0 ) {
				System.err.println("Please enter a positive value.");
			}
			else {
				//if input is a positive number, check if it is less than total length of file
				
				try {
		            String lines = readTextFile(fileName);
		            
		            int totalCharLength = lines.length();
		            
		            if (totalCharLength < maxCharacters) {
		                System.err.println("Error: Number of characters in the file (" + totalCharLength + ") is less than " + maxCharacters + ". Please try again.");
		            }
		            else {
		            	validInput = true;

		            	printStatistics(lines, maxCharacters);
		            	printReversedLowerCase(lines);
		            }

		        } 
				catch (IOException e) {
		            System.out.println("Error reading the file: " + e.getMessage());
		        }
			}
		} while (!validInput);
		
		scanner.close();
    }
    
	    
	    
    /**
     * readTextFile() reads the contents of a text file and returns a long string containing the lines of the file.
     * @param filePath path to the input file
     * @return string with all lines from file.
     */

	private static String readTextFile(String filePath) throws IOException {
		
		//Needs to read in entire file regardless of maxChars, since some statistics rely on info from total file
		
		StringBuilder content = new StringBuilder();
	    String line = "";
	    int totalCharacters = 0;
	    
	    try {
	    	
	    	// FileReader used to read in text files in the default encoding.
	    	FileReader fileReader = new FileReader(filePath);
		
	    	//Wrap FileReader in BufferedReader.
	    	BufferedReader bufferedReader = new BufferedReader(fileReader);
	    	
	        while ((line = bufferedReader.readLine()) != null) { 
	            // Add the current line to the content, including newline character
	            content.append(line).append("\n");
	            
	            // Update the total character count
	            totalCharacters += line.length() + 1; // +1 for the newline character
	        }
	    } catch (FileNotFoundException ex) {
	        System.err.println("Unable to open file '" + filePath + "'");
	        System.exit(2);
	    } catch (IOException ex) {
	        System.err.println("Error reading file '" + filePath + "'");
	        System.exit(3);
	    }
	    return content.toString();
	}

	    
	/**
     * printStatistics() reads the contents of a text file and returns a long string containing the lines of the file.
     * @param fullText is the string that holds entire file contents
     * @param maxChars is the count of how many characters the user said to read in
     * @return string with all lines from file.
     */   	
    
    private static void printStatistics(String fullText, int maxChars) {
    	
    	//Here is where we trim the total file string to the length of maxChars. 
    	//It could not be done earlier since some statistics rely on information from the complete file
    	
    	String limitedText = fullText.substring(0, maxChars);
        
    	int wordCount = 0;
        int numberCount = 0;
        int totalCharacters = 0;
        int spaceCount = 0;
        int punctuationCount = 0;
        int uppercaseCount = 0;
        int lowercaseCount = 0;

        boolean inWord = false;

        //Gets statistics for the full text
        for (char ch : fullText.toCharArray()) {
            if (Character.isLetter(ch)) {
                if (!inWord) {
                    wordCount++; //Keeps track of word count
                    inWord = true;
                }
            }
            else {
                inWord = false;
                if (Character.isDigit(ch)) {
                    numberCount++; //keeps track of number count
                }
            }

            if (ch != '\n') { // Exclude line breaks from totalCharacters count
                totalCharacters++; //keeps track of total character count
            }
        }
        
        //Gets statistics for the limited text
        for (char ch : limitedText.toCharArray()) {
            if (Character.isLetter(ch)) {
                
                if (Character.isUpperCase(ch)) {
                    uppercaseCount++; //Keeps track of uppercase count
                } else {
                    lowercaseCount++; //Keeps track of lowercase count
                }
            } else {
                inWord = false;
                if (Character.isDigit(ch)) {
                    //numberCount++; //keeps track of number count
                } 
                else if (Character.isWhitespace(ch) && ch != '\n') 
                { // Exclude newline characters from space count
                    spaceCount++; //keeps track of spaces count
                }
                else if (!Character.isWhitespace(ch)) {
                    // Assuming any non-whitespace character that is not a letter or digit is punctuation
                    punctuationCount++; //keeps track of punctuation count
                }
            }	           
        }
	    	
        System.out.println("Statistics based on " + maxChars + " chars read in of " + fullText.length() + " in total:");
        System.out.println("Total Word Count (In Entire File): " + wordCount);
        System.out.println("Total Number Count (In Entire File): " + numberCount);
        System.out.println("Total Characters including spaces (In Entire File): " + totalCharacters);
        System.out.println("Space Count of Selection: " + spaceCount);
        System.out.println("Punctuation Count of Selection: " + punctuationCount);
        System.out.println("Uppercase Count of Selection: " + uppercaseCount);
        System.out.println("Lowercase Count of Selection: " + lowercaseCount);
    }

    
    /**
     * printReversedLowerCase() prints out the content of the complete file in reverse order while converting all letters to lower case.
     * @param text is the string that holds entire file contents
     */   
    
    private static void printReversedLowerCase(String text) {
        String reversed = new StringBuilder(text).reverse().toString().toLowerCase();
        System.out.println("Full Text - reversed and converted to lowercase:\n" + reversed);
    }
}
