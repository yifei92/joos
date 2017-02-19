package joos;

import joos.ast.ASTBuilder;
import joos.filereader.FileScanner;
import joos.filereader.JoosFile;
import joos.commons.*;
import joos.exceptions.InvalidSyntaxException;
import joos.parser.Parser;
import joos.scanner.Scanner;
import joos.weeder.Weeder;
import java.util.List;
import java.util.Arrays;

public class Main {

	public static void main(String[] args) {
		String programString = null;

		List<JoosFile> files = FileScanner.scanFiles(args);
		if (files == null) {
			System.out.println("Error");
			System.exit(42);
		}
		Scanner scanner = new Scanner();
		Parser parser = new Parser();
		Weeder weeder = new Weeder();
		ASTBuilder astBuilder = new ASTBuilder();

		try {
			for (JoosFile joosFile : files) {
				System.out.println("Compiling file " + joosFile.mFilePath);
				ParseTreeNode parseTree = null;
				List<TerminalToken> tokens = scanner.scan(joosFile.mProgram);
				parseTree = parser.parse(tokens);
				weeder.weed(parseTree, joosFile.getFileName());
				astBuilder.convert(parseTree);
			}
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
