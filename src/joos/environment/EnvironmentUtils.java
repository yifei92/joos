package joos.environment;

import joos.commons.ParseTreeNode;
import java.util.List;

public class EnvironmentUtils {

	// Enumerates all of the different kinds of environments that are possible
	public enum EnvironmentType {ROOT, PACKAGE, CLASS, INTERFACE, CONSTRUCTOR, METHOD, ABSTRACT_METHOD, BLOCK};

	/**
	 * Given the root node from the environment tree and the root node of the parse tree and a node that we want to find the 
	 * environment of
	 * returns the environment that node is part of.
	 */
	public static Environment findEvironment(Environment rootEnvironment, ParseTreeNode rootParseTreeNode, ParseTreeNode node) {
		return search(rootEnvironment, rootParseTreeNode, node);
	}

	/**
	 * Searches the currentParseTreeNode of environment currentEnvironment for targetParseTreeNode.
	 * Once targetParseTreeNode its environment is returned.
	 */
	private static Environment search(Environment currentEnvironment, ParseTreeNode currentParseTreeNode, ParseTreeNode targetParseTreeNode) {
		// if our current parse tree node is the scope of any of the children of the current environment then we need to switch our 
		// next environment to the new child environment
		Environment nextEnvironment = currentEnvironment;
		for (Environment childEnvironment : currentEnvironment.mChildrenEnvironments) {
			if (childEnvironment.mScope == currentParseTreeNode) {
				nextEnvironment = childEnvironment;
			}
		}
		// check if our current parse tree node is the target parse tree node
		if (currentParseTreeNode == targetParseTreeNode) {
			// We have found the environment for the target parse tree node
			return nextEnvironment;
		}
		// If we have not found the target parse tree node then  check the children of the currentParseTreeNode using the next environment
		Environment foundEnvironment = null;
		for (ParseTreeNode child : currentParseTreeNode.children) {
			foundEnvironment = search(nextEnvironment, child, targetParseTreeNode);
			if (foundEnvironment != null) {
				return foundEnvironment;
			}
		}
		return null;
	} 

	/**
	 * Given the root node from the environment tree and a name returns the environment that has that name.
	 * This will be a class, interface, constructor, method, or abstract method.
	 */
	public static Environment findEnvironment(Environment environment, String name) {
		if (environment.mName == name) {
			return environment;
		} else if (environment.mChildrenEnvironments != null && environment.mChildrenEnvironments.size() != 0) {
			for (Environment env : environment.mChildrenEnvironments) {
				return findEnvironment(env, name);
			}
		}
		return null;
	}

	/**
	 * Given an environment returns its type
	 */
	public static EnvironmentType getEnvironmentType(Environment environment) {
		if (environment.mScope == null) return EnvironmentType.ROOT;
		switch (environment.mScope.token.getType()) {
			case INTERFACE_DECLARATION:
				return EnvironmentType.INTERFACE;
			case CLASS_DECLARATION: 
				return EnvironmentType.CLASS;
			case CONSTRUCTOR_DECLARATION:
				return EnvironmentType.CONSTRUCTOR;
			case ABSTRACT_METHOD_DECLARATION:
				return EnvironmentType.ABSTRACT_METHOD;
			case METHOD_DECLARATION:
				return EnvironmentType.METHOD;
		}
		return EnvironmentType.BLOCK;
	}

	/**
	 * Looks in the given environment for whether or not the given variable name is declared in it.
	 * If the name does not exist in the current environment we recursively search all parent environments.
	 * If no name is found in this environment or the parent environments null is returned.
	 */
	public static ParseTreeNode containsVariableNameDeclaration(Environment environment, String variableName) {
		ParseTreeNode variableNameNode = environment.mNames != null ? environment.mNames.get(variableName) : null;
		if (variableNameNode == null && environment.mParent != null) {
			variableNameNode = containsVariableNameDeclaration(environment.mParent, variableName);
		}
		return variableNameNode;
	}
}