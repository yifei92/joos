package joos.environment;

import joos.exceptions.InvalidSyntaxException;
import joos.commons.ParseTreeNode;
import joos.commons.TokenType;
import joos.commons.TerminalToken;
import joos.commons.Token;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

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
	 * Returns true if the given environment contains a constructor or is itself a constructor
	 */
	public static boolean containsConstructor(Environment environment) {
		if (getEnvironmentType(environment) == EnvironmentType.CONSTRUCTOR) {
			return true;
		}
		for (Environment child : environment.mChildrenEnvironments) {
			if (getEnvironmentType(child) == EnvironmentType.CONSTRUCTOR) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns a list of all of the Environments of all the instantiations within the given
	 * Environment's scope
	 */
	public static List<Environment> getAllInstantiationEnvironments(Environment environment, Map<String, Environment> packageMap) throws InvalidSyntaxException {
		List<ParseTreeNode> instantiationNodes = new ArrayList<>();
		findNodesWithTokenType(environment.mScope, TokenType.CLASS_INSTANCE_CREATION_EXPRESSION, instantiationNodes);
		List<Environment> environments = new ArrayList<>();
		for (ParseTreeNode instantiationNode : instantiationNodes) {
			ParseTreeNode classTypeNode = findImmediateNodeWithTokenType(instantiationNode, TokenType.CLASS_TYPE);
			environments.add(getEnvironmentFromName(environment, classTypeNode, packageMap));
		}
		return environments;
	}

	public static ParseTreeNode findImmediateNodeWithTokenType(final ParseTreeNode node, final TokenType type) {
		if (node.token.getType() == type) return node;
		if (node.children != null) {
			for (ParseTreeNode child : node.children) {
				if (child.token.getType() == type) return child;
			}
		}
		return null;
	}

	/**
	 * Finds all child nodes of the given root of TokenType type and adds them to the given nodes list.
	 */
	public static void findNodesWithTokenType(final ParseTreeNode root, final TokenType type, final List<ParseTreeNode> nodes) {
		if (root == null) {
			return;
		}
		if (root.token.getType() == type) {
			nodes.add(root);
			return;
		}
		if (root.children != null) {
			for (ParseTreeNode child : root.children) {
				findNodesWithTokenType(child, type, nodes);
			}
		}
	}

	public static Set<TokenType> getEnvironmentModifiers(Environment environment) {
		if (environment.mScope == null) return null;
		switch (environment.mScope.token.getType()) {
			case INTERFACE_DECLARATION:
			case CLASS_DECLARATION:
			case CONSTRUCTOR_DECLARATION:
				if (environment.mScope.children.get(0).children.size() == 0) {
					return new HashSet();
				} else {
					Set<TokenType> set = new HashSet();
					for (ParseTreeNode child : environment.mScope.children.get(0).children) {
						set.add(child.children.get(0).token.getType());
					}
					return set;
				}
			case ABSTRACT_METHOD_DECLARATION:
			case METHOD_DECLARATION:
				if (environment.mScope.children.get(0).children.get(0).children.size() == 0) {
					return new HashSet();
				} else {
					Set<TokenType> set = new HashSet();
					for (ParseTreeNode child : environment.mScope.children.get(0).children.get(0).children) {
						set.add(child.children.get(0).token.getType());
					}
					return set;
				}
		}
		return null;
	}

	public static List<Environment> getExtendedEnvironments(Environment environment, Map<String, Environment> packageMap) throws InvalidSyntaxException {
		if (environment.mScope == null) return null;
		EnvironmentType type = getEnvironmentType(environment);
		List<Environment> list = new ArrayList();
		switch (type) {
			case CLASS:
				if (environment.mScope.children.get(3).children.size() == 0) {
					if (!(environment.PackageName + "." + environment.mName).equals("java.lang.Object")) {
						list.add(packageMap.get("java.lang.Object"));
					}
					return list;
				} else {
					list.add(getEnvironmentFromName(environment, environment.mScope.children.get(3).children.get(0).children.get(1), packageMap));
					return list;
				}
			case INTERFACE:
				if (environment.mScope.children.get(3).children.size() == 0) {
					return list;
				} else {
					Set<String> names = new HashSet();
					ParseTreeNode n = environment.mScope.children.get(3).children.get(0);
					while(n != null) {
						Environment extendedEnvironment;
						if (n.children.size() == 2) {
							extendedEnvironment = getEnvironmentFromName(environment, n.children.get(1), packageMap);
							n = null;
						} else {
							extendedEnvironment = getEnvironmentFromName(environment, n.children.get(2), packageMap);
							n = n.children.get(0);
						}
						String name = extendedEnvironment.PackageName + "." + extendedEnvironment.mName;
						if (names.contains(name)) {
							throw new InvalidSyntaxException("An interface must not be repeated an extends clause of an interface.");
						}
						names.add(name);
						list.add(extendedEnvironment);
					}
					return list;
				}
		}
		return null;
	}

	public static List<Environment> getImplementedEnvironments(Environment environment, Map<String, Environment> packageMap) throws InvalidSyntaxException {
		if (environment.mScope == null) return null;
		EnvironmentType type = getEnvironmentType(environment);
		List<Environment> list = new ArrayList();
		switch (type) {
			case CLASS:
				if (environment.mScope.children.get(4).children.size() == 0) {
					return list;
				} else {
					Set<String> names = new HashSet();
					for (ParseTreeNode node : environment.mScope.children.get(4).children.get(0).children.get(1).children) {
						if (node.token.getType() == TokenType.COMMA) continue;
						Environment extendedEnvironment = getEnvironmentFromName(environment, node, packageMap);
						String name = extendedEnvironment.PackageName + "." + extendedEnvironment.mName;
						if (names.contains(name)) {
							throw new InvalidSyntaxException("An interface must not be repeated an implements clause of a class.");
						}
						names.add(name);
						list.add(extendedEnvironment);
					}
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

	public static String getFullQualifiedName(Environment environment, ParseTreeNode name, Map<String, Environment> packageMap) throws InvalidSyntaxException {
		String identifier;
		switch (name.token.getType()) {
			case TYPE:
			case REFERENCE_TYPE:
			case CLASS_OR_INTERFACE_TYPE:
			case CLASS_TYPE:
			case INTERFACE_TYPE:
				return getFullQualifiedName(environment, name.children.get(0), packageMap);
			case PRIMITIVE_TYPE:
				return getFirstTerminal(name).getRawValue();
			case ARRAY_TYPE:
				return getFullQualifiedName(environment, name.children.get(0), packageMap) + "[]";
			case NAME:
				if (name.children.size() > 1) {
					String qualifiedName = "";
					for (ParseTreeNode child : name.children) {
						qualifiedName += ((TerminalToken)child.token).getRawValue();
					}
					return qualifiedName;
				} else {
					identifier = ((TerminalToken)name.children.get(0).token).getRawValue();
				}
				break;
			case SIMPLE_NAME:
				identifier = ((TerminalToken)name.children.get(0).token).getRawValue();
				break;
			case IDENTIFIER:
				identifier = ((TerminalToken)name.token).getRawValue();
				break;
			default:
				throw new InvalidSyntaxException("Invalid node passed");
		}
		if (packageMap.containsKey(identifier)) {
			return identifier;
		}
		for (String importName : environment.mSingleImports) {
			if (importName.length() >= identifier.length() && importName.substring(importName.length() - identifier.length()).equals(identifier)) {
				return importName;
			}
		}
		for (String importName : environment.mOnDemandeImports) {
			for (String packageName : packageMap.keySet()) {
				if (packageName.length() >= importName.length() && packageName.substring(0, importName.length()).equals(importName)) {
					if (packageMap.get(packageName).mName.equals(identifier)) {
						return packageName;
					}
				}
			}
		}
		String localName = environment.PackageName + "." + identifier;
		if (packageMap.containsKey(localName)) return localName;
		throw new InvalidSyntaxException("Qualified Name not found");
	}

	public static Environment getEnvironmentFromName(Environment environment, ParseTreeNode name, Map<String, Environment> packageMap) throws InvalidSyntaxException {
		return packageMap.get(getFullQualifiedName(environment, name, packageMap));
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
