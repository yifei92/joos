package joos.environment;

import joos.commons.ParseTreeNode;
import java.util.List;

public class EnvironmentUtils {

	// Enumerates all of the different kinds of environments that are possible
	public enum EnvironmentType {ROOT, PACKAGE, CLASS, INTERFACE, CONSTRUCTOR, METHOD, ABSTRACT_METHOD, BLOCK};

	/**
	 * Given the root node from the environment tree and a scope returns the environment node for that scope
	 */
	public static Environment findEvironment(Environment environment, ParseTreeNode scope) {
		if (environment.mScope == scope) {
			return environment;
		} else if (environment.mChildrenEnvironments != null && environment.mChildrenEnvironments.size() != 0) {
			for (Environment env : environment.mChildrenEnvironments) {
				return findEvironment(env, scope);
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
				return findEvironment(env, name);
			}
		}
		return null;
	}

	/**
	 * Given an environment returns its type
	 */
	public EnvironmentType getEnvironmentType(Environment environment) {
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
			variableNameNode = environment.mParent.containsName(variableName);
		}
		return variableNameNode;
	}
}