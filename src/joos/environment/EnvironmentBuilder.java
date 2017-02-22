package joos.environment;

import joos.commons.ParseTreeNode;
import joos.commons.TokenType;
import joos.commons.TerminalToken;
import java.util.List;
import java.util.ArrayList;

public class EnvironmentBuilder {
	
	/**
	 * Builds the environment tree using the given parse trees for each joos file.
	 */
	public static Environment build (List<ParseTreeNode> parseTrees) {
		Environment rootEnvironment = new Environment(null, null, null);
		for (ParseTreeNode parseTree : parseTrees) {
			traverse(rootEnvironment, parseTree);
		}
		// Return a root environment that containst the environment trees for all the parse trees
		return rootEnvironment;
	}

	/**
	 * Traverses the parse tree and adds new child environments (with appropriate scopes and parents)
	 * as well as new names the given environment.
	 */
	private static void traverse (final Environment environment, final ParseTreeNode node) {
		if (node == null) return; 
		Environment nextEnvironment = environment;
		switch (node.token.getType()) {
			case INTERFACE_DECLARATION: // fall through
			case CLASS_DECLARATION: 
				// create a new environment for this new block
				Environment newEnvironment  = new Environment(environment, node, getClassOrInterfaceName(node));
				environment.mChildrenEnvironments.add(newEnvironment);
				nextEnvironment = newEnvironment;
				break;
			case VARIABLE_DECLARATOR:
				ParseTreeNode identifierNode = findNodeWithTokenType(node, TokenType.IDENTIFIER);
				environment.mNames.put(
					((TerminalToken) identifierNode.token).getRawValue(),
					identifierNode);
				break;
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
					methodEnvironment.mNames.put(
						((TerminalToken) paramNode.token).getRawValue(),
						paramNode);
				}
				nextEnvironment = methodEnvironment;
				break;
			case IF_THEN_STATEMENT:
				ParseTreeNode statementNode = findNodeWithTokenType(node, TokenType.STATEMENT);
				// create a new environment for this new block
				Environment thenEnvironment  = new Environment(environment, statementNode, null);
				environment.mChildrenEnvironments.add(thenEnvironment);
				traverse(thenEnvironment, statementNode);
				return;
			case IF_THEN_ELSE_STATEMENT: // fall through
			case IF_THEN_ELSE_STATEMENT_NO_SHORT_IF:
				// get the child node for the statement after the if
				ParseTreeNode ifStatementNode = findNodeWithTokenType(node, TokenType.STATEMENT_NO_SHORT_IF);
				// get the child node for the statement after the else
				TokenType elseStatementTokenType = node.token.getType() == TokenType.IF_THEN_ELSE_STATEMENT_NO_SHORT_IF ? TokenType.STATEMENT_NO_SHORT_IF : TokenType.STATEMENT;
				ParseTreeNode elseStatementNode = findNodeWithTokenType(node, elseStatementTokenType);
				// Create new environments for both the if and else statements
				Environment ifEnvironment  = new Environment(environment, ifStatementNode, null);
				environment.mChildrenEnvironments.add(ifEnvironment);
				Environment elseEnvironment  = new Environment(environment, elseStatementNode, null);
				environment.mChildrenEnvironments.add(elseEnvironment);
				// Traverse the if and else statement nodes with their own environments
				traverse(ifEnvironment, ifStatementNode);
				traverse(elseEnvironment, elseStatementNode);
				return;
			case WHILE_STATEMENT: // fall through
			case WHILE_STATEMENT_NO_SHORT_IF:
				// get the tokentype for the loop contents node
				TokenType loopContentsTokenType = node.token.getType() == TokenType.WHILE_STATEMENT_NO_SHORT_IF ? TokenType.STATEMENT_NO_SHORT_IF : TokenType.STATEMENT;
				// get the loop contents node
				ParseTreeNode loopContentsNode = findNodeWithTokenType(node, loopContentsTokenType);
				// create a new environment for the loop contents node
				Environment statementEnvironment  = new Environment(environment, loopContentsNode, null);
				environment.mChildrenEnvironments.add(statementEnvironment);
				// Traverse the loop contents node with its own environment
				traverse(statementEnvironment, loopContentsNode);
				return;
			case FOR_STATEMENT: // fall through
			case FOR_STATEMENT_NO_SHORT_IF:
				// get the tokentype for the loop contents node
				TokenType forLoopContentsTokenType = node.token.getType() == TokenType.FOR_STATEMENT_NO_SHORT_IF ? TokenType.STATEMENT_NO_SHORT_IF : TokenType.STATEMENT;
				// get the loop contents node
				ParseTreeNode forLoopContentsNode = findNodeWithTokenType(node, forLoopContentsTokenType);
				// get the loop initialization node
				ParseTreeNode forLoopInitNode = findNodeWithTokenType(node, TokenType.FOR_INIT);

				// create a new environment for the loop contents node
				Environment forLoopEnvironment  = new Environment(environment, forLoopContentsNode, null);
				environment.mChildrenEnvironments.add(forLoopEnvironment);
				// Traverse the loop contents node and init node with its the forLoopEnvironment
				traverse(forLoopEnvironment, forLoopContentsNode);
				traverse(forLoopEnvironment, forLoopInitNode);
				return;
		}
		if (node.children != null) {
			for (ParseTreeNode child : node.children) {
				traverse(nextEnvironment, child);
			}
		}
	}

	private static void findFormalParameters(ParseTreeNode node, List<ParseTreeNode> paramVarNodes) {
		if (node.token.getType() == TokenType.FORMAL_PARAMETER) {
			ParseTreeNode paramIdentifierNode = findNodeWithTokenType(node, TokenType.IDENTIFIER);
			if (paramIdentifierNode != null) {
				paramVarNodes.add(paramIdentifierNode);
			} else {
				System.out.println("Error, FORMAL_PARAMETER with no identifier");
			}
		} else if (node.children != null && node.children.size() > 0) {
			for (ParseTreeNode child : node.children) {
				findFormalParameters(child, paramVarNodes);
			}
		} 
		return;
	}

	/**
	 * Traverses the given root node's children for the first TokenType node and returns it.
	 * null otherwise
	 */
	private static ParseTreeNode findNodeWithTokenType(ParseTreeNode root, TokenType type) {
		if (root == null) {
			return null;
		}
		if (root.token.getType() == type) {
			return root;
		}
		if (root.children != null) {
			for (ParseTreeNode child : root.children) {
				ParseTreeNode nodeWithTokenType = findNodeWithTokenType(child, type);
				if (nodeWithTokenType != null) {
					return nodeWithTokenType;
				}
			}
		}
		return null;
	}

	private static String getClassOrInterfaceName(ParseTreeNode classOrInterfaceNode) {
		for (ParseTreeNode child : classOrInterfaceNode.children) {
			if (child.token.getType() == TokenType.IDENTIFIER) {
				return ((TerminalToken) child.token).getRawValue();
			}
		}
		return null;
	}

	private static String getMethodName(ParseTreeNode methodNode) {
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