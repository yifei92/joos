package joos.disambiguation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import joos.commons.ParseTreeNode;
import joos.commons.TerminalToken;
import joos.commons.TokenType;
import joos.commons.Type;
import joos.commons.Type.TypeType;
import joos.environment.Environment;
import joos.environment.Environment.EnvironmentType;
import joos.environment.EnvironmentUtils;
import joos.exceptions.InvalidSyntaxException;
import joos.typechecking.TypeChecker;
import static joos.environment.EnvironmentUtils.findNodeWithTokenType;
import static joos.environment.EnvironmentUtils.getEnvironmentFromTypeName;
import static joos.environment.EnvironmentUtils.getEnvironmentType;
import static joos.environment.EnvironmentUtils.getExtendedEnvironments;
import static joos.environment.EnvironmentUtils.getFullQualifiedNameFromTypeName;
import static joos.environment.EnvironmentUtils.getNameFromTypeNode;
import static joos.environment.EnvironmentUtils.getTypeFromTypeNode;

public class Disambiguation {

  public static void linkAllTypes(Environment environment,Map<String, Environment> packageMap) throws InvalidSyntaxException {
    if (environment.mVariableToType == null) {
      environment.mVariableToType = new HashMap();
      linkTypes(environment, environment.mScope, packageMap);
    }
    if (environment.mChildrenEnvironments != null) {
      for (Environment child : environment.mChildrenEnvironments) {
        linkAllTypes(child,packageMap);
      }
    }
  }
  public static void linkAllNames(Environment environment, Map<String, Environment> packageMap) throws InvalidSyntaxException {
    linkNames(environment, environment.mScope, packageMap);
    if (environment.mChildrenEnvironments != null) {
      for (Environment child : environment.mChildrenEnvironments) {
        linkAllNames(child, packageMap);
      }
    }
  }

  static void linkTypes(Environment environment, ParseTreeNode node,Map<String, Environment> packageMap) throws InvalidSyntaxException {
    switch (node.token.getType()) {
			case BLOCK: // fall through
      case ABSTRACT_METHOD_DECLARATION:
        if (environment.mScope == node) {
          break;
        }
        return;
      case IF_THEN_STATEMENT:
      case IF_THEN_ELSE_STATEMENT:
      case IF_THEN_ELSE_STATEMENT_NO_SHORT_IF:
      case WHILE_STATEMENT:
      case WHILE_STATEMENT_NO_SHORT_IF:
      case FOR_STATEMENT:
      case FOR_STATEMENT_NO_SHORT_IF:
        if (node.children != null) {
          for (ParseTreeNode child : node.children) {
            if (child.token.getType() != TokenType.STATEMENT) {
              linkTypes(environment, child,packageMap);
            }
          }
        }
        return;
      case CONSTRUCTOR_DECLARATION: {
        if (environment.mScope == node) {
          ParseTreeNode params = node.children.get(1).children.get(2);
          if (params.children != null && params.children.size() > 0) {
            for (ParseTreeNode param : params.children.get(0).children) {
              if (param.token.getType() == TokenType.COMMA) continue;
              String name = ((TerminalToken)findNodeWithTokenType(param.children.get(1), TokenType.IDENTIFIER).token).getRawValue();
              Type type = getTypeFromTypeNode(environment, param.children.get(0), packageMap);
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
              Type type = getTypeFromTypeNode(environment, param.children.get(0), packageMap);
              environment.mVariableToType.put(name, type);
            }
          }
          break;
        } else {
          // String name = ((TerminalToken)findNodeWithTokenType(node.children.get(0).children.get(2), TokenType.IDENTIFIER).token).getRawValue();
          // String typeName = EnvironmentUtils.getFullQualifiedNameFromTypeNode(environment,node.children.get(0).children.get(1),packageMap);
          // Type type = new Type(typeName);
          // type.decl = node.children.get(0);
          // environment.mVariableToType.put(name, type);
          return;
        }
      }
      case FIELD_DECLARATION: {
        Type type = getTypeFromTypeNode(environment, node.children.get(1), packageMap);
        for (ParseTreeNode child : node.children.get(2).children) {
          String name = ((TerminalToken)findNodeWithTokenType(child, TokenType.IDENTIFIER).token).getRawValue();
          environment.mVariableToType.put(name, type);
        }
        return;
      }
      case LOCAL_VARIABLE_DECLARATION: {
        Type type = getTypeFromTypeNode(environment, node.children.get(0), packageMap);
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
        linkTypes(environment, child,packageMap);
      }
    }
  }

  public static void linkNames(Environment environment, ParseTreeNode node, Map<String, Environment> packageMap) throws InvalidSyntaxException {
    switch (node.token.getType()) {
			case BLOCK: // fall through
      case ABSTRACT_METHOD_DECLARATION:
      case CONSTRUCTOR_DECLARATION:
      case METHOD_DECLARATION:
      case REFERENCE_TYPE:
        if (node == environment.mScope) break;
        return;
      case IF_THEN_STATEMENT:
      case IF_THEN_ELSE_STATEMENT:
      case IF_THEN_ELSE_STATEMENT_NO_SHORT_IF:
      case WHILE_STATEMENT:
      case WHILE_STATEMENT_NO_SHORT_IF:
      case FOR_STATEMENT:
      case FOR_STATEMENT_NO_SHORT_IF:
        if (node.children != null) {
          for (ParseTreeNode child : node.children) {
            System.out.println(child.token.getType());
            if (child.token.getType() != TokenType.STATEMENT) {
              linkNames(environment, child, packageMap);
            }
          }
        }
        return;
      case ARRAY_ACCESS:
        if (node.children != null) {
          ParseTreeNode nameNode = node.children.get(0);
          if (nameNode != null && nameNode.token.getType() == TokenType.NAME) {
            linkName(environment, nameNode, packageMap);
            TypeChecker.checkUsageForProtectedFieldAccess(environment, nameNode, packageMap);
            return;
          }
        }
        break;
      case POSTFIX_EXPRESSION:
      case LEFT_HAND_SIDE:
      case EXPRESSION:
      case CONSTANT_EXPRESSION:
      case ASSIGNMENT_EXPRESSION:
      case CONDITIONAL_EXPRESSION:
      case CONDITIONAL_OR_EXPRESSION:
      case CONDITIONAL_AND_EXPRESSION:
      case INCLUSIVE_OR_EXPRESSION:
      case EXCLUSIVE_OR_EXPRESSION:
      case AND_EXPRESSION:
      case EQUALITY_EXPRESSION:
      case RELATIONAL_EXPRESSION:
      case SHIFT_EXPRESSION:
      case ADDITIVE_EXPRESSION:
      case MULTIPLICATIVE_EXPRESSION:
      case UNARY_EXPRESSION_NOT_PLUS_MINUS:
      case UNARY_EXPRESSION:
      case ARGUMENT_LIST:
      case PRIMARY:
      case PRIMARY_NO_NEW_ARRAY:
        if (node.children != null && node.children.size() == 1) {
          ParseTreeNode nameNode = node.children.get(0);
          if (nameNode != null && nameNode.token.getType() == TokenType.NAME) {
            linkName(environment, nameNode, packageMap);
            System.out.println(nameNode.type.name + " " + nameNode.name);
            TypeChecker.checkUsageForProtectedFieldAccess(environment, nameNode, packageMap);
            return;
          }
        }
        break;
      default:
        break;
    }
    if (node.children != null) {
      for (ParseTreeNode child : node.children) {
        linkNames(environment, child, packageMap);
      }
    }
  }

  private static boolean linkNameToVariable(Environment environment, String name, ParseTreeNode node, Map<String, Environment> packageMap) throws InvalidSyntaxException {
    int dotIndex = name.indexOf('.');
    String prefix;
    if (dotIndex != -1) {
      prefix = name.substring(0, dotIndex);
    } else {
      prefix = name;
    }
    if (environment.mVariableToType.containsKey(prefix)) {
      Type type = environment.mVariableToType.get(prefix);
      if (dotIndex == -1) {
        node.type = type;
        return true;
      } else {
        if (type.type == TypeType.ARRAY) {
          int secondDotIndex = name.indexOf('.', dotIndex + 1);
          if (secondDotIndex == -1) {
            String nextName = name.substring(dotIndex + 1);
            if (nextName.equals("length")) {
              node.type = Type.newPrimitive("int", null);
              return true;
            }
          }
          if (linkNameToVariable(packageMap.get("java.lang.Object"), name.substring(dotIndex + 1), node, packageMap)) {
            return true;
          }
        } else {
          if (packageMap.containsKey(type.name)) {
            if (linkNameToVariable(packageMap.get(type.name), name.substring(dotIndex + 1), node, packageMap)) {
              return true;
            }
          }
        }
      }
    }
    switch (getEnvironmentType(environment)) {
      case CLASS:
        for (Environment extendedEnvironment : getExtendedEnvironments(environment, packageMap)) {
          if (linkNameToVariable(extendedEnvironment, name, node, packageMap)) return true;
        }
        break;
      default:
      if (linkNameToVariable(environment.mParent, name, node, packageMap)) return true;
    }
    return false;
  }

  static void linkName(Environment environment, ParseTreeNode node, Map<String, Environment> packageMap) throws InvalidSyntaxException {
    String name = getNameFromTypeNode(node);
    if (linkNameToVariable(environment, name, node, packageMap)) return;
    int dotIndex = name.indexOf('.');
    String prefix;
    if (dotIndex != -1) {
      prefix = name.substring(0, dotIndex);
    } else {
      prefix = name;
    }
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
      node.type = new Type(getFullQualifiedNameFromTypeName(environment, prefix, packageMap));
      return;
    }
    if (typeEnvironment != null && dotIndex != -1) {
      if (linkNameToVariable(typeEnvironment, name.substring(dotIndex), node, packageMap)) return;
    }
    System.out.println(name);
    throw new InvalidSyntaxException("Name cannot be resolved");
  }
}
