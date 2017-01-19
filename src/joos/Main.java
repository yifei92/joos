package joos;

import joos.ast.ASTBuilder;
import joos.commons.*;
import joos.exceptions.InvalidSyntaxException;
import joos.parser.Parser;
import joos.scanner.Scanner;
import joos.weeder.Weeder;
import java.util.List;
import java.util.Arrays;
import java.lang.StringBuilder;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;

public class Main {

    public static void main(String[] args) {

        StringBuilder program = new StringBuilder();
        if (args.length > 0 && args.length < 2) {
            try {
                FileReader fr = new FileReader(args[0]);
                BufferedReader br = new BufferedReader(fr);
                String currentLine;
                while ((currentLine = br.readLine()) != null) {
                    program.append(currentLine);
                }
                br.close();
                fr.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
                return;
            }
        } else {
            System.out.println("Invalid number of arguments!");
            System.out.println("Format: java joosc <filename>");
            return;
        }

        String programString = program.toString();
        if (programString == null || programString.length() == 0) {
            System.out.println("Program is empty!");
            return;
        }

        System.out.println("Initializing");
    	Scanner scanner = new Scanner();
    	Parser parser = new Parser();
    	Weeder weeder = new Weeder();
    	ASTBuilder astBuilder = new ASTBuilder();

    	try {
    		System.out.println("Scanning");
    		List<Token> tokens = scanner.scan(programString);
            System.out.println("tokens are:");
            for (Token token : tokens) {
                System.out.println(token + " , " + token.getRawValue());
            }
    		System.out.println("Parsing");
            List<String> tokenStrings = Arrays.asList("{", "{", "{", "b", "}", "}", "}");
    		ParseTreeNode parseTree = parser.parse(tokenStrings);
    		System.out.println("Weeding");
    		weeder.weed(parseTree);
    		System.out.println("Converting");
    		ASTTreeNode astTree = astBuilder.convert(parseTree);
		} catch (InvalidSyntaxException e) {
			// An error occured in one of the steps
    		System.out.println("Invalid!");
    		return;
		}
		System.out.println("Done!");
    }
}
