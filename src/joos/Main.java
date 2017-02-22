package joos;

import joos.ast.ASTBuilder;
import joos.filereader.FileScanner;
import joos.filereader.JoosFile;
import joos.commons.*;
import joos.exceptions.InvalidSyntaxException;
import joos.parser.Parser;
import joos.scanner.Scanner;
import joos.weeder.Weeder;
import joos.environment.Environment;
import joos.environment.EnvironmentBuilder;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

	static boolean a() {
		return false;
	}
	public static void main(String[] args) {
		String programString = null;

		List<JoosFile> files = FileScanner.scanFiles(args);
		if (files == null) {
			System.out.println("Error");
			System.exit(42);
		}

		try {
			List<ParseTreeNode> parseTrees = new ArrayList<>();
			for (JoosFile joosFile : files) {
				Scanner scanner = new Scanner();
				Parser parser = new Parser();
				Weeder weeder = new Weeder();
				ASTBuilder astBuilder = new ASTBuilder();
				System.out.println("Scanning, parsing, weeding file " + joosFile.mFilePath);
				ParseTreeNode parseTree = null;
				List<TerminalToken> tokens = scanner.scan(joosFile.mProgram);
				/*for (TerminalToken token : tokens) {
					System.out.print(token.getRawValue() + " ");
				}
				System.out.println("");*/
				parseTree = parser.parse(tokens);
				weeder.weed(parseTree, joosFile.getFileName());
				astBuilder.convert(parseTree);
				parseTrees.add(parseTree);
			}
			System.out.println("Building environment");
			Environment environment = EnvironmentBuilder.build(parseTrees);
			environment.print();
			for (ParseTreeNode parseTree : parseTrees) {
				parseTree.printWithEnvironments(environment, parseTree);
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
