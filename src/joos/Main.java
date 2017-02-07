package joos;

import joos.ast.ASTBuilder;
import joos.filereader.FileScanner;
import joos.commons.*;
import joos.exceptions.InvalidSyntaxException;
import joos.parser.Parser;
import joos.scanner.Scanner;
import joos.weeder.Weeder;
import java.util.List;
import java.util.Arrays;

public class Main {
    public void $asd() {

    }

	public static void main(String[] args) {
		String programString = null;
		FileScanner fileScanner = new FileScanner();
		if (args.length > 0 && args.length < 2) {
			programString = fileScanner.readFile(args[0]);
		} else {
			System.out.println("Invalid number of arguments!");
			System.out.println("Format: java joosc <filename>");
			return;
		}

		if (programString == null || programString.length() == 0) {
			System.out.println("Program is empty!");
			return;
		}

		Scanner scanner = new Scanner();
		Parser parser = new Parser();
		Weeder weeder = new Weeder();
		ASTBuilder astBuilder = new ASTBuilder();
		ParseTreeNode parseTree = null;
		try {
			List<TerminalToken> tokens = scanner.scan(programString);
			for (TerminalToken tok : tokens) {
				System.out.print(tok.mRawValue + " ");
			}
			System.out.println("");
			parseTree = parser.parse(tokens);
			String path=args[0];
			String filename=path;
			if(path.lastIndexOf("/")!=-1){
				filename=path.substring(path.lastIndexOf("/")+1);
			}
			weeder.weed(parseTree,filename);
			astBuilder.convert(parseTree);
			//parseTree.print();
		} catch (InvalidSyntaxException e) {
			// An error occured in one of the steps
			System.out.println(e.getMessage());
			System.out.println("Error");
			System.exit(42);
			return;
		}


		System.out.println("Success");
		System.exit(0);
	}
}
