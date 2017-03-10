package joos.disambiguation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import joos.commons.ParseTreeNode;
import joos.commons.TerminalToken;
import joos.commons.TokenType;
import joos.commons.Type;
import joos.environment.Environment;
import joos.environment.Environment.EnvironmentType;
import joos.exceptions.InvalidSyntaxException;
import static joos.environment.EnvironmentBuilder.findNodeWithTokenType;
import static joos.environment.EnvironmentUtils.getEnvironmentFromTypeName;
import static joos.environment.EnvironmentUtils.getEnvironmentType;
import static joos.environment.EnvironmentUtils.getExtendedEnvironments;
import static joos.environment.EnvironmentUtils.getNameFromTypeNode;

public class Disambiguation {
  public static void disambiguate(Environment environment, Map<String, Environment> packageMap) throws InvalidSyntaxException {
    if (environment.mVariableToType == null) {
      environment.mVariableToType = new HashMap();
      linkTypes(environment, environment.mScope);
    }
    linkNames(environment, environment.mScope, packageMap);
    if (environment.mChildrenEnvironments != null) {
      for (Environment child : environment.mChildrenEnvironments) {
        disambiguate(child, packageMap);
      }
    }
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
              String typeName = getNameFromTypeNode(param.children.get(0));
              Type type = new Type(typeName);
              type.decl = param;
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
              String typeName = getNameFromTypeNode(param.children.get(0));
              Type type = new Type(typeName);
              type.decl = param;
              environment.mVariableToType.put(name, type);
            }
          }
          break;
        } else {
          String name = ((TerminalToken)findNodeWithTokenType(node.children.get(0).children.get(2), TokenType.IDENTIFIER).token).getRawValue();
          String typeName = getNameFromTypeNode(node.children.get(0).children.get(1));
          Type type = new Type(typeName);
          type.decl = node.children.get(0);
          environment.mVariableToType.put(name, type);
          return;
        }
      }
      case FIELD_DECLARATION: {
        String typeName = getNameFromTypeNode(node.children.get(1));
        Type type = new Type(typeName);
        type.decl = node;
        for (ParseTreeNode child : node.children.get(2).children) {
          String name = ((TerminalToken)findNodeWithTokenType(child, TokenType.IDENTIFIER).token).getRawValue();
          environment.mVariableToType.put(name, type);
        }
        return;
      }
      case LOCAL_VARIABLE_DECLARATION: {
        String typeName = getNameFromTypeNode(node.children.get(0));
        Type type = new Type(typeName);
        type.decl = node;
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

  public static void linkNames(Environment environment, ParseTreeNode node, Map<String, Environment> packageMap) throws InvalidSyntaxException {
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
      case CONSTRUCTOR_DECLARATION:
      case METHOD_DECLARATION:
        if (node == environment.mScope) break;
        return;
      case POSTFIX_EXPRESSION:
      case LEFT_HAND_SIDE:
      case ARRAY_ACCESS:
        if (node.children.get(0).token.getType() == TokenType.NAME) {
          linkName(environment, node.children.get(0), packageMap);
        }
      default:
        break;
    }
    if (node.children != null) {
      for (ParseTreeNode child : node.children) {
        linkNames(environment, child, packageMap);
      }
    }
  }

  static void linkName(Environment environment, ParseTreeNode node, Map<String, Environment> packageMap) throws InvalidSyntaxException {
    String name = getNameFromTypeNode(node);
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
        node.type = env.mVariableToType.get(prefix);
        return;
      }
      if (getEnvironmentType(env) == EnvironmentType.CLASS) {
        List<Environment> extendedEnvs = getExtendedEnvironments(env, packageMap);
        if (extendedEnvs != null && extendedEnvs.size() > 0) {
          env = extendedEnvs.get(0);
        }
      }
      env = env.mParent;
    } while (env != null);
    Environment typeEnvironment = getEnvironmentFromTypeName(environment, prefix, packageMap);
    if (typeEnvironment == null) {
      for (String packageName : packageMap.keySet()) {
        if (name.length() >= packageName.length() && name.substring(0, packageName.length()).equals(packageName)) {
          typeEnvironment = packageMap.get(packageName);
          dotIndex = name.indexOf('.', packageName.length());
        }
      }
    }
    if (typeEnvironment != null && dotIndex == -1) {
      //this shouldnt happen
      System.out.println("*********************");
      System.out.println("This Shouldn't Happen");
      System.out.println("*********************");
    }
    if (typeEnvironment != null && dotIndex != -1) {
      int secondDotIndex = name.indexOf('.', dotIndex);
      String nextName;
      if (secondDotIndex == -1) {
        nextName = name.substring(dotIndex, secondDotIndex);
      } else {
        nextName = name.substring(dotIndex);
      }
      if (typeEnvironment.mVariableDeclarations.containsKey(nextName)) {
        node.type = typeEnvironment.mVariableToType.get(nextName);
      }
    }
    throw new InvalidSyntaxException("Name cannot be resolved");
  }
}
