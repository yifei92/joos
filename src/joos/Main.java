package joos;

import joos.ast.ASTBuilder;
import joos.codegeneration.SubTypingTesting;
import joos.exceptions.StaticAnalysisException;
import joos.exceptions.TypeLinkingException;
import joos.filereader.FileScanner;
import joos.filereader.JoosFile;
import joos.commons.*;
import joos.exceptions.InvalidSyntaxException;
import joos.exceptions.EnvironmentBuilderException;
import joos.parser.Parser;
import joos.scanner.Scanner;
import joos.staticanalysis.ReachabilityCheck;
import joos.typechecking.TypeCheckingEvaluator;
import joos.typelinking.TypeLinking;
import joos.weeder.Weeder;
import joos.environment.Environment;
import joos.environment.EnvironmentBuilder;
import joos.hierarchychecking.HierarchyChecking;
import joos.disambiguation.Disambiguation;
import joos.typechecking.TypeChecker;
import joos.reachability.AssignmentChecking;
import joos.codegeneration.CodeGeneration;

import java.util.*;
import java.io.IOException;
import java.io.StringWriter;

public class Main {

	public static void main(String[] args) {
		String programString = null;

		List<JoosFile> files = FileScanner.scanFiles(args);
		if (files == null) {
			System.out.println("Error");
			System.exit(42);
		}

		try {
			List<ParseTreeNode> parseTrees = new ArrayList<>();
			System.out.println(files.size());
			for (JoosFile joosFile : files) {
				Scanner scanner = new Scanner();
				Parser parser = new Parser();
				Weeder weeder = new Weeder();
				ASTBuilder astBuilder = new ASTBuilder();
				ParseTreeNode parseTree = null;
				List<TerminalToken> tokens = scanner.scan(joosFile.mProgram);
				Scanner.orderTokens(tokens);
				parseTree = parser.parse(tokens);
				weeder.weed(parseTree, joosFile.getFileName());
				astBuilder.convert(parseTree);
				parseTrees.add(parseTree);
			}
			Map<String, ParseTreeNode> treeMap= new HashMap<>();
			Map<String, Environment> packageMap = EnvironmentBuilder.build(parseTrees, treeMap);
			for (Environment environment : packageMap.values()) {
				HierarchyChecking.check(environment, packageMap);
			}
			for(String key : treeMap.keySet()){
				TypeLinking typeLinking=new TypeLinking();
				typeLinking.check(treeMap.get(key),packageMap.get(key),packageMap,key);
			}
			for (Environment environment : packageMap.values()) {
				Disambiguation.linkAllTypes(environment,packageMap);
			}
			for (Environment environment : packageMap.values()) {
				Disambiguation.linkAllNames(environment, packageMap);
			}
			for(String key : treeMap.keySet()){
				TypeCheckingEvaluator typeCheckingEvaluator =new TypeCheckingEvaluator();
				typeCheckingEvaluator.check(treeMap.get(key),packageMap,packageMap.get(key));
			}
			for (Environment environment : packageMap.values()) {
				TypeChecker.check(environment, packageMap);
			}
			for (Environment environment : packageMap.values()) {
				AssignmentChecking.check(environment, packageMap);
			}
			for(String key : treeMap.keySet()){
				ReachabilityCheck reachabilityCheck =new ReachabilityCheck();
				reachabilityCheck.check(treeMap.get(key),packageMap,packageMap.get(key));
			}
			SubTypingTesting subTypingTesting = new SubTypingTesting(packageMap);
			CodeGeneration codeGen = new CodeGeneration(packageMap, subTypingTesting);
			for (Environment environment : packageMap.values()) {
				codeGen.generateForClass(environment);
			}
		} catch (InvalidSyntaxException e) {
			// An error occured in one of the steps
			// e.printStackTrace();
			System.out.println(e.getMessage());
			System.out.println("Error");
			System.exit(42);
			return;
		}
		catch (TypeLinkingException e){
			System.out.println(e.getMessage());
			System.out.println("Error");
			System.exit(42);
			return;
		}
		catch (StaticAnalysisException e){
			System.out.println(e.getMessage());
			System.out.println("Error");
			System.exit(42);
			return;
		} catch (IOException e) {
			System.out.println(e.getMessage());
			System.out.println("Error");
			System.exit(42);
			return;
		} catch (EnvironmentBuilderException e) {
			System.out.println(e.getMessage());
			System.out.println("Error");
			e.printStackTrace();
			System.exit(43);
			return;
		}

		System.out.println("Success");
		System.exit(0);
	}
}
