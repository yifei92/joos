package joos.environment;

import joos.exceptions.InvalidSyntaxException;
import joos.commons.ParseTreeNode;
import joos.commons.TokenType;
import joos.commons.TerminalToken;
import joos.commons.Token;
import joos.environment.Environment.EnvironmentType;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

public class EnvironmentUtils {

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
		if (currentParseTreeNode.children != null) {
			for (ParseTreeNode child : currentParseTreeNode.children) {
				foundEnvironment = search(nextEnvironment, child, targetParseTreeNode);
				if (foundEnvironment != null) {
					return foundEnvironment;
				}
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
		if (environment.mType != null) return environment.mType;
		if (environment.mScope == null) return EnvironmentType.ROOT;
		switch (environment.mScope.token.getType()) {
			case INTERFACE_DECLARATION:
				environment.mType = EnvironmentType.INTERFACE;
				break;
			case CLASS_DECLARATION:
				environment.mType = EnvironmentType.CLASS;
				break;
			case CONSTRUCTOR_DECLARATION:
				environment.mType = EnvironmentType.CONSTRUCTOR;
				break;
			case ABSTRACT_METHOD_DECLARATION:
				environment.mType = EnvironmentType.ABSTRACT_METHOD;
				break;
			case METHOD_DECLARATION:
				environment.mType = EnvironmentType.METHOD;
				break;
			default:
				environment.mType = EnvironmentType.BLOCK;
				break;
		}
		return environment.mType;
	}

	public static Set<TokenType> getEnvironmentModifiers(Environment environment) {
		if (environment.mModifiers != null) return environment.mModifiers;
		if (environment.mScope == null) return null;
		switch (environment.mScope.token.getType()) {
			case INTERFACE_DECLARATION:
			case CLASS_DECLARATION:
			case CONSTRUCTOR_DECLARATION:
				if (environment.mScope.children.get(0).children.size() == 0) {
					environment.mModifiers = new HashSet();
					return environment.mModifiers;
				} else {
					Set<TokenType> set = new HashSet();
					for (ParseTreeNode child : environment.mScope.children.get(0).children) {
						set.add(child.children.get(0).token.getType());
					}
					environment.mModifiers = set;
					return set;
				}
			case ABSTRACT_METHOD_DECLARATION:
			case METHOD_DECLARATION:
				if (environment.mScope.children.get(0).children.get(0).children.size() == 0) {
					environment.mModifiers = new HashSet();
					return environment.mModifiers;
				} else {
					Set<TokenType> set = new HashSet();
					for (ParseTreeNode child : environment.mScope.children.get(0).children.get(0).children) {
						set.add(child.children.get(0).token.getType());
					}
					environment.mModifiers = set;
					return set;
				}
		}
		return null;
	}

	public static List<Environment> getExtendedEnvironments(Environment environment, Map<String, Environment> packageMap) throws InvalidSyntaxException {
		if (environment.mExtendedEnvironments != null) return environment.mExtendedEnvironments;
		if (environment.mScope == null) return null;
		EnvironmentType type = getEnvironmentType(environment);
		List<Environment> list = new ArrayList();
		switch (type) {
			case CLASS:
				if (environment.mScope.children.get(3).children.size() == 0) {
					if (!(environment.PackageName + "." + environment.mName).equals("java.lang.Object")) {
						list.add(packageMap.get("java.lang.Object"));
					}
					environment.mExtendedEnvironments = list;
					return list;
				} else {
					list.add(getEnvironmentFromTypeNode(environment, environment.mScope.children.get(3).children.get(0).children.get(1), packageMap));
					environment.mExtendedEnvironments = list;
					return list;
				}
			case INTERFACE:
				if (environment.mScope.children.get(3).children.size() == 0) {
					environment.mExtendedEnvironments = list;
					return list;
				} else {
					Set<String> names = new HashSet();
					ParseTreeNode n = environment.mScope.children.get(3).children.get(0);
					while(n != null) {
						Environment extendedEnvironment;
						if (n.children.size() == 2) {
							extendedEnvironment = getEnvironmentFromTypeNode(environment, n.children.get(1), packageMap);
							n = null;
						} else {
							extendedEnvironment = getEnvironmentFromTypeNode(environment, n.children.get(2), packageMap);
							n = n.children.get(0);
						}
						String name = extendedEnvironment.PackageName + "." + extendedEnvironment.mName;
						if (names.contains(name)) {
							throw new InvalidSyntaxException("An interface must not be repeated an extends clause of an interface.");
						}
						names.add(name);
						list.add(extendedEnvironment);
					}
					environment.mExtendedEnvironments = list;
					return list;
				}
		}
		return null;
	}

	public static List<Environment> getImplementedEnvironments(Environment environment, Map<String, Environment> packageMap) throws InvalidSyntaxException {
		if (environment.mImplementedEnvironments != null) return environment.mImplementedEnvironments;
		if (environment.mScope == null) return null;
		EnvironmentType type = getEnvironmentType(environment);
		List<Environment> list = new ArrayList();
		switch (type) {
			case CLASS:
				if (environment.mScope.children.get(4).children.size() == 0) {
					environment.mImplementedEnvironments = list;
					return list;
				} else {
					Set<String> names = new HashSet();
					for (ParseTreeNode node : environment.mScope.children.get(4).children.get(0).children.get(1).children) {
						if (node.token.getType() == TokenType.COMMA) continue;
						Environment extendedEnvironment = getEnvironmentFromTypeNode(environment, node, packageMap);
						String name = extendedEnvironment.PackageName + "." + extendedEnvironment.mName;
						if (names.contains(name)) {
							throw new InvalidSyntaxException("An interface must not be repeated an implements clause of a class.");
						}
						names.add(name);
						list.add(extendedEnvironment);
					}
					environment.mImplementedEnvironments = list;
					return list;
				}
		}
		return null;
	}

	private static TerminalToken getFirstTerminal(ParseTreeNode node) {
    if (node.token instanceof TerminalToken) {
      return (TerminalToken)node.token;
    }
    for (ParseTreeNode child : node.children) {
      TerminalToken token = getFirstTerminal(child);
      if (token != null) return token;
    }
    return null;
  }

	public static String getNameFromTypeNode(ParseTreeNode name) {
		if (name.name != null) return name.name;
		switch (name.token.getType()) {
			case TYPE:
			case REFERENCE_TYPE:
			case CLASS_OR_INTERFACE_TYPE:
			case CLASS_TYPE:
			case INTERFACE_TYPE:
				name.name = getNameFromTypeNode(name.children.get(0));
				name.primitive = name.children.get(0).primitive;
				return name.name;
			case PRIMITIVE_TYPE:
				name.primitive = true;
				name.name = getFirstTerminal(name).getRawValue();
				return name.name;
			case ARRAY_TYPE:
				name.name = getNameFromTypeNode(name.children.get(0)) + "[]";
				name.primitive = name.children.get(0).primitive;
				return name.name;
			case NAME:
				if (name.children.size() > 1) {
					String qualifiedName = "";
					for (ParseTreeNode child : name.children) {
						qualifiedName += ((TerminalToken)child.token).getRawValue();
					}
					name.name = qualifiedName;
					return name.name;
				} else {
					name.name = ((TerminalToken)name.children.get(0).token).getRawValue();
					return name.name;
				}
			case SIMPLE_NAME:
				name.name = ((TerminalToken)name.children.get(0).token).getRawValue();
				return name.name;
			case IDENTIFIER:
				name.name = ((TerminalToken)name.token).getRawValue();
				return name.name;
			default:
				return null;
		}
  }

	public static String getFullQualifiedNameFromTypeName(Environment environment, String identifier, Map<String, Environment> packageMap) throws InvalidSyntaxException {
		boolean isArrayType = false;
		if (identifier.length() > 2 && identifier.substring(identifier.length() - 2).equals("[]")) {
			isArrayType = true;
			identifier = identifier.substring(0, identifier.length() - 2);
		}
		if (packageMap.containsKey(identifier)) {
			if (isArrayType) return identifier + "[]";
			return identifier;
		}
		for (String importName : environment.mSingleImports) {
			if (importName.length() >= identifier.length() && importName.substring(importName.length() - identifier.length()).equals(identifier)) {
				if (isArrayType) return importName + "[]";
				return importName;
			}
		}
		for (String importName : environment.mOnDemandeImports) {
			for (String packageName : packageMap.keySet()) {
				if (packageName.length() >= importName.length() && packageName.substring(0, importName.length()).equals(importName)) {
					if (packageMap.get(packageName).mName.equals(identifier)) {
						if (isArrayType) return packageName + "[]";
						return packageName;
					}
				}
			}
		}
		String localName = environment.PackageName + "." + identifier;
		if (packageMap.containsKey(localName)) {
			if (isArrayType) return localName + "[]";
			return localName;
		}
		throw new InvalidSyntaxException("Qualified Name not found");
	}

	public static String getFullQualifiedNameFromTypeNode(Environment environment, ParseTreeNode name, Map<String, Environment> packageMap) throws InvalidSyntaxException {
		String identifier = getNameFromTypeNode(name);
		if (name.primitive) return identifier;
		return getFullQualifiedNameFromTypeName(environment, identifier, packageMap);
	}


	public static Environment getEnvironmentFromTypeName(Environment environment, String name, Map<String, Environment> packageMap) throws InvalidSyntaxException {
		return packageMap.get(getFullQualifiedNameFromTypeName(environment, name, packageMap));
	}

	public static Environment getEnvironmentFromTypeNode(Environment environment, ParseTreeNode name, Map<String, Environment> packageMap) throws InvalidSyntaxException {
		return packageMap.get(getFullQualifiedNameFromTypeNode(environment, name, packageMap));
	}

	/**
	 * Looks in the given environment for whether or not the given variable name is declared in it.
	 * If the name does not exist in the current environment we recursively search all parent environments.
	 * If no name is found in this environment or the parent environments null is returned.
	 */
	public static ParseTreeNode containsVariableNameDeclaration(Environment environment, String variableName) {
		ParseTreeNode variableNameNode = environment.mVariableDeclarations != null ? environment.mVariableDeclarations.get(variableName) : null;
		if (variableNameNode == null && environment.mParent != null) {
			variableNameNode = containsVariableNameDeclaration(environment.mParent, variableName);
		}
		return variableNameNode;
	}
}
