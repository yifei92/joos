package joos;

import joos.ast.ASTBuilder;
import joos.commons.*;
import joos.exceptions.InvalidSyntaxException;
import joos.parser.Parser;
import joos.scanner.Scanner;
import joos.weeder.Weeder;
import java.util.List;

public class Main {

    public static void main(String[] args) {
    	System.out.println("Initializing");
    	Scanner scanner = new Scanner();
    	Parser parser = new Parser();
    	Weeder weeder = new Weeder();
    	ASTBuilder astBuilder = new ASTBuilder();

    	try {
    		System.out.println("Scanning");
    		List<Token> tokens = scanner.scan("");
    		System.out.println("Parsing");
    		ParseTreeNode parseTree = parser.parse(tokens);
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
