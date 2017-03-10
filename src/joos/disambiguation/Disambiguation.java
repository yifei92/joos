package joos.disambiguation;

import joos.commons.ParseTreeNode;
import joos.environment.Environment;
import joos.exceptions.InvalidSyntaxException;
import joos.commons.TerminalToken;
import joos.commons.TokenType;
import static joos.environment.EnvironmentUtils.getEnvironmentType;
import static joos.environment.EnvironmentBuilder.findNodeWithTokenType;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class Disambiguation {

  

  public static Environment getType(Environment environment, String name, Map<String, Environment> packageMap) throws InvalidSyntaxException {
    return null;
  }

  public static void linkNames(Environment environment, Map<String, Environment> packageMap) throws InvalidSyntaxException {
    if (environment.mVariableToType == null) {
      environment.mVariableToType = new HashMap();
      linkTypes(environment, environment.mScope);
    }
    List<String> names = getAllNames(environment.mScope);
    outer: for (String name : names) {
      int dotIndex = name.indexOf('.');
      String prefix;
      if (dotIndex != -1) {
        prefix = name.substring(0, name.indexOf('.'));
      } else {
        prefix = name;
      }
      Environment env = environment;
      do {
        if (env.mVariableDeclarations.containsKey(prefix)) {
          continue outer;
        }
        for (Environment child : env.mChildrenEnvironments) {
          switch (getEnvironmentType(child)) {
            case METHOD:
              if (child.mName.equals(name)) {
                continue outer;
              }
          }
        }
        env = env.mParent;
      } while (env != null);
      Environment typeEnvironment = getEnvironmentFromName(prefix, environment, packageMap);
      if (typeEnvironment == null) {
        System.out.println(name);
        for (String packageName : packageMap.keySet()) {
          // System.out.println(packageName);
          if (name.length() >= packageName.length() && name.substring(0, packageName.length()).equals(packageName)) {
            typeEnvironment = packageMap.get(packageName);
            dotIndex = name.indexOf('.', packageName.length());
          }
        }
      }
      if (typeEnvironment != null && dotIndex == -1) continue;
      if (typeEnvironment != null && dotIndex != -1) {
        int secondDotIndex = name.indexOf('.', dotIndex);
        String nextName;
        if (secondDotIndex == -1) {
          nextName = name.substring(dotIndex, secondDotIndex);
        } else {
          nextName = name.substring(dotIndex);
        }
        if (typeEnvironment.mVariableDeclarations.containsKey(nextName)) {
          continue;
        }
      }
      throw new InvalidSyntaxException("Name cannot be resolved");
    }
    for (Environment child : environment.mChildrenEnvironments) {
      linkNames(child, packageMap);
    }
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

  static Environment getEnvironmentFromName(String name, Environment environment, Map<String, Environment> packageMap) {
    if (packageMap.containsKey(name)) {
      return packageMap.get(name);
    }
    for (String importName : environment.mSingleImports) {
      if (importName.length() >= name.length() && importName.substring(importName.length() - name.length()).equals(name)) {
        return packageMap.get(importName);
      }
    }
    for (String importName : environment.mOnDemandeImports) {
      for (String packageName : packageMap.keySet()) {
        if (packageName.length() >= importName.length() && packageName.substring(0, importName.length()).equals(importName)) {
          if (packageMap.get(packageName).mName.equals(name)) {
            return packageMap.get(packageName);
          }
        }
      }
    }
    String localName = environment.PackageName + "." + name;
    if (packageMap.containsKey(localName)) return packageMap.get(localName);
    return null;
  }

  static void linkTypes(Environment environment, ParseTreeNode node) {
    switch (node.token.getType()) {
			case BLOCK: // fall through
			case IF_THEN_STATEMENT:
			case IF_THEN_ELSE_STATEMENT: // fall through
			case IF_THEN_ELSE_STATEMENT_NO_SHORT_IF:
			case WHILE_STATEMENT: // fall through
			case WHILE_STATEMENT_NO_SHORT_IF:
			case FOR_STATEMENT: // fall through
      case ABSTRACT_METHOD_DECLARATION:
			case FOR_STATEMENT_NO_SHORT_IF:
        if (environment.mScope == node) break;
        return;
      case CONSTRUCTOR_DECLARATION: {
        if (environment.mScope == node) {
          ParseTreeNode params = node.children.get(1).children.get(2);
          if (params.children != null && params.children.size() > 0) {
            for (ParseTreeNode param : params.children.get(0).children) {
              if (param.token.getType() == TokenType.COMMA) continue;
              String name = ((TerminalToken)findNodeWithTokenType(param.children.get(1), TokenType.IDENTIFIER).token).getRawValue();
              String type = getFullNameFromNameNode(param.children.get(0));
              environment.mVariableToType.put(name, type);
            }
          }
          break;
        } else {
          return;
        }
      }
      case METHOD_DECLARATION: {
        if (environment.mScope == node) {
          ParseTreeNode params = node.children.get(0).children.get(2).children.get(2);
          if (params.children != null && params.children.size() > 0) {
            for (ParseTreeNode param : params.children.get(0).children) {
              if (param.token.getType() == TokenType.COMMA) continue;
              String name = ((TerminalToken)findNodeWithTokenType(param.children.get(1), TokenType.IDENTIFIER).token).getRawValue();
              String type = getFullNameFromNameNode(param.children.get(0));
              environment.mVariableToType.put(name, type);
            }
          }
          break;
        } else {
          String name = ((TerminalToken)findNodeWithTokenType(node.children.get(0).children.get(2), TokenType.IDENTIFIER).token).getRawValue();
          String type = getFullNameFromNameNode(node.children.get(0).children.get(1));
          environment.mVariableToType.put(name, type);
          return;
        }
      }
      case FIELD_DECLARATION: {
        String type = getFullNameFromNameNode(node.children.get(1));
        for (ParseTreeNode child : node.children.get(2).children) {
          String name = ((TerminalToken)findNodeWithTokenType(child, TokenType.IDENTIFIER).token).getRawValue();
          environment.mVariableToType.put(name, type);
        }
        return;
      }
      case LOCAL_VARIABLE_DECLARATION: {
        String type = getFullNameFromNameNode(node.children.get(0));
        for (ParseTreeNode child : node.children.get(1).children) {
          String name = ((TerminalToken)findNodeWithTokenType(child, TokenType.IDENTIFIER).token).getRawValue();
          environment.mVariableToType.put(name, type);
        }
        return;
      }
      default:
        break;
    }
    if (node.children != null) {
      for (ParseTreeNode child : node.children) {
        linkTypes(environment, child);
      }
    }
  }

  static List<String> getAllNames(ParseTreeNode node) {
    List<String> list = new ArrayList();
    switch (node.token.getType()) {
      case IMPORT_DECLARATION:
			case VARIABLE_DECLARATOR:
			case BLOCK: // fall through
			case CONSTRUCTOR_DECLARATION: // fall through
			case ABSTRACT_METHOD_DECLARATION: // fall through
			case METHOD_DECLARATION:
			case IF_THEN_STATEMENT:
			case IF_THEN_ELSE_STATEMENT: // fall through
			case IF_THEN_ELSE_STATEMENT_NO_SHORT_IF:
			case WHILE_STATEMENT: // fall through
			case WHILE_STATEMENT_NO_SHORT_IF:
			case FOR_STATEMENT: // fall through
			case FOR_STATEMENT_NO_SHORT_IF:
      case INTERFACES:
      case SUPER_CLAUSE:
        return list;
      case NAME:
        list.add(getFullNameFromNameNode(node));
        return list;
      default:
        if (node.children != null) {
          for (ParseTreeNode child : node.children) {
            list.addAll(getAllNames(child));
          }
        }
        return list;
    }
  }

  public static String getFullNameFromNameNode(ParseTreeNode name) {
		switch (name.token.getType()) {
			case TYPE:
			case REFERENCE_TYPE:
			case CLASS_OR_INTERFACE_TYPE:
			case CLASS_TYPE:
			case INTERFACE_TYPE:
				return getFullNameFromNameNode(name.children.get(0));
			case PRIMITIVE_TYPE:
				return getFirstTerminal(name).getRawValue();
			case ARRAY_TYPE:
				return getFullNameFromNameNode(name.children.get(0)) + "[]";
			case NAME:
				if (name.children.size() > 1) {
					String qualifiedName = "";
					for (ParseTreeNode child : name.children) {
						qualifiedName += ((TerminalToken)child.token).getRawValue();
					}
					return qualifiedName;
				} else {
					return ((TerminalToken)name.children.get(0).token).getRawValue();
				}
			case SIMPLE_NAME:
				return ((TerminalToken)name.children.get(0).token).getRawValue();
			case IDENTIFIER:
				return ((TerminalToken)name.token).getRawValue();
      case VOID:
        return "void";
			default:
				return null;
		}
  }
}
