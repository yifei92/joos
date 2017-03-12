package joos.environment;

import joos.commons.ParseTreeNode;
import joos.commons.TokenType;
import joos.commons.TerminalToken;
import joos.exceptions.TypeLinkingException;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import static joos.environment.EnvironmentUtils.findImmediateNodeWithTokenType;
import static joos.environment.EnvironmentUtils.findNodeWithTokenType;
import static joos.environment.EnvironmentUtils.findNodesWithTokenType;

public class EnvironmentBuilder {

	/**
	 * Builds the environment tree using the given parse trees for each joos file.
	 */
	public static Map<String, Environment> build (List<ParseTreeNode> parseTrees,Map<String,ParseTreeNode> treeMap) throws TypeLinkingException {
		Map<String, Environment> packageMap = new HashMap<String, Environment>();
		for (ParseTreeNode parseTree : parseTrees) {
			ParseTreeNode packageDeclNode = findNodeWithTokenType(parseTree, TokenType.PACKAGE_DECLARATION);
			ParseTreeNode classOrInterfaceNode = findNodeWithTokenType(parseTree, TokenType.CLASS_DECLARATION);
			if (classOrInterfaceNode == null) {
				classOrInterfaceNode = findNodeWithTokenType(parseTree, TokenType.INTERFACE_DECLARATION);
			}

			String classOrInterfaceName = getClassOrInterfaceName(classOrInterfaceNode);
			String packageMapKey = classOrInterfaceName; // default package
			if (packageDeclNode != null) {
				String packageName = getPackageName(packageDeclNode);
				packageMapKey = packageName + "." + classOrInterfaceName;
			}
			if (packageMap.containsKey(packageMapKey)) {
				throw new TypeLinkingException(packageMapKey + " already exists");
			} else {
				// create a new environment for this new block
				Environment classOrInterfaceEnvironment  = new Environment(null, classOrInterfaceNode, classOrInterfaceName);
				if(packageDeclNode!=null){
					classOrInterfaceEnvironment.PackageName=getPackageName(packageDeclNode);
				}
				traverse(classOrInterfaceEnvironment, parseTree, 0);
				packageMap.put(packageMapKey, classOrInterfaceEnvironment);
				treeMap.put(packageMapKey, parseTree);
			}
		}
		// Return a root environment that containst the environment trees for all the parse trees
		return packageMap;
	}

	/**
	 * Traverses the parse tree and adds new child environments (with appropriate scopes and parents)
	 * as well as new names the given environment.
	 */
	private static void traverse (final Environment environment, final ParseTreeNode node, int count) throws TypeLinkingException {
		if (node == null) return;
		Environment nextEnvironment = environment;

		for (int i = 0 ; i < count ; i++) {
			//System.out.print(" ");
		}
		//System.out.println(node.token.getType());

		switch (node.token.getType()) {
      case IMPORT_DECLARATION:
				String packageFullName = "";
				ParseTreeNode namenode;
				if(node.children.get(0).token.getType()==TokenType.SINGLE_TYPE_IMPORT_DECLARATION){
					namenode=node.children.get(0).children.get(1);
					for(int i=0;i<namenode.children.size();i++){
						packageFullName+=((TerminalToken)namenode.children.get(i).token).getRawValue();
					}
					if(!environment.mSingleImports.contains(packageFullName)) {
						for(String singleimport : environment.mSingleImports){
							if(singleimport.substring(singleimport.lastIndexOf(".")+1,singleimport.length()).equals(packageFullName.substring(packageFullName.lastIndexOf(".")+1,packageFullName.length()))){
								throw new TypeLinkingException("single import clash");
							}
						}
						environment.mSingleImports.add(packageFullName);
					}
				} else{
					namenode=node.children.get(0).children.get(1);
					for(int i=0;i<namenode.children.size();i++){
						packageFullName+=((TerminalToken)namenode.children.get(i).token).getRawValue();
					}
					if(!environment.mOnDemandeImports.contains(packageFullName)) {
						for(String ondemandimport : environment.mOnDemandeImports){
							if(ondemandimport.substring(ondemandimport.lastIndexOf(".")+1,ondemandimport.length()).equals(packageFullName.substring(packageFullName.lastIndexOf(".")+1,packageFullName.length()))){
								throw new TypeLinkingException("on demand import clash");
							}
						}
						environment.mOnDemandeImports.add(packageFullName);
					}
				}
        break;
      case LOCAL_VARIABLE_DECLARATION: {
      	ParseTreeNode variableDeclarator = findNodeWithTokenType(node, TokenType.VARIABLE_DECLARATOR);
      	ParseTreeNode identifierNode = findNodeWithTokenType(variableDeclarator, TokenType.IDENTIFIER);
				String variableName = ((TerminalToken) identifierNode.token).getRawValue();
				// Make sure that no two variables in a class have the same name
				if (environment.mScope.token.getType() == TokenType.CLASS_DECLARATION &&
						environment.mVariableDeclarations.containsKey(variableName)) {
					throw new TypeLinkingException(
						"Variable " + variableName + " has already been declared in class " + environment.mName);
				} else if(checkForLocalVariableDuplicates(environment, variableName)) {
					throw new TypeLinkingException(
						"Variable " + variableName + " has already been declared in this method");

				} else {
					environment.mVariableDeclarations.put(variableName, variableDeclarator);
				}
      	break;
      }
			case FIELD_DECLARATION: {
      	List<ParseTreeNode> variableDeclarators = new ArrayList<>();
      	findNodesWithTokenType(node, TokenType.VARIABLE_DECLARATOR, variableDeclarators);
				for (ParseTreeNode variableDeclarator: variableDeclarators) {
					ParseTreeNode identifierNode = findNodeWithTokenType(variableDeclarator, TokenType.IDENTIFIER);
					String variableName = ((TerminalToken) identifierNode.token).getRawValue();
					// Make sure that no two variables in a class have the same name
					if (environment.mScope.token.getType() == TokenType.CLASS_DECLARATION &&
							environment.mVariableDeclarations.containsKey(variableName)) {
						throw new TypeLinkingException(
							"Variable " + variableName + " has already been declared in class " + environment.mName);
					} else if(checkForLocalVariableDuplicates(environment, variableName)) {
						throw new TypeLinkingException(
							"Variable " + variableName + " has already been declared in this method");

					} else {
						environment.mVariableDeclarations.put(variableName, node);
					}
				}
				break;
			}
			case BLOCK: // fall through
			case CONSTRUCTOR_DECLARATION: // fall through
			case ABSTRACT_METHOD_DECLARATION: // fall through
			case METHOD_DECLARATION:
				// create a new environment for the method.
				Environment methodEnvironment  = new Environment(environment, node, getMethodName(node));
				environment.mChildrenEnvironments.add(methodEnvironment);
				List<ParseTreeNode> paramVarNodes = new ArrayList<ParseTreeNode>();
				findFormalParameters(node, paramVarNodes);
				for (ParseTreeNode paramNode : paramVarNodes) {
					String name = ((TerminalToken) paramNode.token).getRawValue();
					if (methodEnvironment.mVariableDeclarations.containsKey(name)) {
						throw new TypeLinkingException(
							"Variable " + name + " already declared in this constructor");
					} else {
						methodEnvironment.mVariableDeclarations.put(
							((TerminalToken) paramNode.token).getRawValue(),
							paramNode);
					}
				}
				nextEnvironment = methodEnvironment;
				break;
			case IF_THEN_STATEMENT:
				ParseTreeNode statementNode = findImmediateNodeWithTokenType(node, TokenType.STATEMENT);
				// create a new environment for this new block
				Environment thenEnvironment  = new Environment(environment, statementNode, null);
				environment.mChildrenEnvironments.add(thenEnvironment);
				traverse(thenEnvironment, statementNode, count + 1);
				return;
			case IF_THEN_ELSE_STATEMENT: // fall through
			case IF_THEN_ELSE_STATEMENT_NO_SHORT_IF:
				// get the child node for the statement after the if
				ParseTreeNode ifStatementNode = findImmediateNodeWithTokenType(node, TokenType.STATEMENT_NO_SHORT_IF);
				// get the child node for the statement after the else
				TokenType elseStatementTokenType = node.token.getType() == TokenType.IF_THEN_ELSE_STATEMENT_NO_SHORT_IF ? TokenType.STATEMENT_NO_SHORT_IF : TokenType.STATEMENT;
				ParseTreeNode elseStatementNode = findImmediateNodeWithTokenType(node, elseStatementTokenType);
				// Create new environments for both the if and else statements
				Environment ifEnvironment  = new Environment(environment, ifStatementNode, null);
				Environment elseEnvironment  = new Environment(environment, elseStatementNode, null);
				environment.mChildrenEnvironments.add(ifEnvironment);
				environment.mChildrenEnvironments.add(elseEnvironment);
				// Traverse the if and else statement nodes with their own environments
				traverse(ifEnvironment, ifStatementNode, count + 1);
				traverse(elseEnvironment, elseStatementNode, count + 1);
				return;
			case WHILE_STATEMENT: // fall through
			case WHILE_STATEMENT_NO_SHORT_IF:
				// get the tokentype for the loop contents node
				TokenType loopContentsTokenType = node.token.getType() == TokenType.WHILE_STATEMENT_NO_SHORT_IF ? TokenType.STATEMENT_NO_SHORT_IF : TokenType.STATEMENT;
				// get the loop contents node
				ParseTreeNode loopContentsNode = findImmediateNodeWithTokenType(node, loopContentsTokenType);
				// create a new environment for the loop contents node
				Environment statementEnvironment  = new Environment(environment, loopContentsNode, null);
				environment.mChildrenEnvironments.add(statementEnvironment);
				// Traverse the loop contents node with its own environment
				traverse(statementEnvironment, loopContentsNode, count + 1);
				return;
			case FOR_STATEMENT: // fall through
			case FOR_STATEMENT_NO_SHORT_IF:
				// get the tokentype for the loop contents node
				TokenType forLoopContentsTokenType = node.token.getType() == TokenType.FOR_STATEMENT_NO_SHORT_IF ? TokenType.STATEMENT_NO_SHORT_IF : TokenType.STATEMENT;
				// get the loop contents node
				ParseTreeNode forLoopContentsNode = findImmediateNodeWithTokenType(node, forLoopContentsTokenType);
				// get the loop initialization node
				ParseTreeNode forLoopInitNode = findNodeWithTokenType(node, TokenType.FOR_INIT);

				// create a new environment for the loop contents node
				Environment forLoopEnvironment  = new Environment(environment, forLoopContentsNode, null);
				environment.mChildrenEnvironments.add(forLoopEnvironment);
				// Traverse the loop contents node and init node with its the forLoopEnvironment
				traverse(forLoopEnvironment, forLoopContentsNode, count + 1);
				traverse(forLoopEnvironment, forLoopInitNode, count + 1);
				return;
		}

		if (node.children != null) {
			for (ParseTreeNode child : node.children) {
				traverse(nextEnvironment, child, count + 1);
			}
		}
	}

	/**
	 * Checks the current method environment for duplicate variable names in overlappingpes
	 */
	private static boolean checkForLocalVariableDuplicates(
		Environment currentEnvironment,
		String variableName) {
		TokenType currentScopeType = currentEnvironment.mScope.token.getType();
		if (currentScopeType != TokenType.CLASS_DECLARATION && currentScopeType != TokenType.INTERFACE_DECLARATION) {
			if (currentEnvironment.mVariableDeclarations.containsKey(variableName)) {
				return true;
			} else {
				return checkForLocalVariableDuplicates(currentEnvironment.mParent, variableName);
			}
		} else {
			return false;
		}
	}

	private static String getPackageName(ParseTreeNode packageDeclNode) {
		ParseTreeNode nameNode = findNodeWithTokenType(packageDeclNode, TokenType.NAME);
		String packageFullName="";
		for(ParseTreeNode child : nameNode.children){
			packageFullName+=((TerminalToken)child.token).getRawValue();
		}
		return packageFullName;
	}

	private static void findFormalParameters(ParseTreeNode node, List<ParseTreeNode> paramVarNodes) {
		if (node.token.getType() == TokenType.FORMAL_PARAMETER) {
			ParseTreeNode paramIdentifierNode = findImmediateNodeWithTokenType(node, TokenType.IDENTIFIER);
			if (paramIdentifierNode != null) {
				paramVarNodes.add(paramIdentifierNode);
			} else {
				System.out.println("Error, FORMAL_PARAMETER with no identifier");
			}
		} else if (node.children != null) {
			for (ParseTreeNode child : node.children) {
				findFormalParameters(child, paramVarNodes);
			}
		}
		return;
	}

	public static String getClassOrInterfaceName(final ParseTreeNode classOrInterfaceNode) {
		for (ParseTreeNode child : classOrInterfaceNode.children) {
			if (child.token.getType() == TokenType.IDENTIFIER) {
				return ((TerminalToken) child.token).getRawValue();
			}
		}
		return null;
	}

	private static String getMethodName(final ParseTreeNode methodNode) {
		ParseTreeNode methodDeclaratorNode = findNodeWithTokenType(methodNode, TokenType.CONSTRUCTOR_DECLARATOR);
		if (methodDeclaratorNode == null) {
			methodDeclaratorNode = findNodeWithTokenType(methodNode, TokenType.METHOD_DECLARATOR);
		}
		if (methodDeclaratorNode == null) {
			return null;
		}
		ParseTreeNode identifierNode = findNodeWithTokenType(methodDeclaratorNode, TokenType.IDENTIFIER);
		if (identifierNode == null) {
			return null;
		}
		return ((TerminalToken) identifierNode.token).getRawValue();
	}
}
