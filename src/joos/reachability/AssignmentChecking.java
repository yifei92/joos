package joos.reachability;

import java.util.HashSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import joos.commons.ParseTreeNode;
import joos.commons.TerminalToken;
import joos.commons.Token;
import joos.commons.TokenType;
import joos.environment.Environment;
import joos.environment.Environment.EnvironmentType;
import joos.exceptions.InvalidSyntaxException;
import static joos.environment.EnvironmentUtils.findNodeWithTokenType;
import static joos.environment.EnvironmentUtils.getNameFromTypeNode;
import static joos.environment.EnvironmentUtils.getEnvironmentType;

public class AssignmentChecking {
  static boolean p;

  public static void check(Environment environment, Map<String, Environment> packageMap) throws InvalidSyntaxException {
    if (environment.mName.equals("Je_8_DefiniteAssignment_SomethingAndAssignment")) p = true;
    for (Environment child : environment.mChildrenEnvironments) {
      if (getEnvironmentType(child) == EnvironmentType.METHOD) {
        AssignmentChecking.checkAssignment(child, child.mScope, packageMap, new HashMap());
      }
    }
    p = false;
  }

  public static Map<String, Boolean> checkAssignment(
    Environment environment,
    ParseTreeNode node,
    Map<String, Environment> packageMap,
    Map<String, Boolean> current
  ) throws InvalidSyntaxException {
    Map<String, Boolean> map = new HashMap(current);
    switch (node.token.getType()) {
      case BLOCK: // fall through
        if (environment.mScope == node) {
          break;
        } else {
          for (Environment child : environment.mChildrenEnvironments) {
            if (child.mScope == node) {
              return checkAssignment(child, node, packageMap, map);
            }
          }
        }
        break;
      case IF_THEN_ELSE_STATEMENT:
      case IF_THEN_ELSE_STATEMENT_NO_SHORT_IF:
        Map<String, Boolean> childMap = null;
        for (ParseTreeNode child : node.children) {
          if (child.token.getType() == TokenType.STATEMENT || child.token.getType() == TokenType.STATEMENT_NO_SHORT_IF) {
            for (Environment childEnv : environment.mChildrenEnvironments) {
              if (childEnv.mScope == child) {
                Map<String, Boolean> temp = checkAssignment(childEnv, child, packageMap, map);
                if (childMap == null) {
                  childMap = new HashMap(temp);
                } else {
                  for (String key : childMap.keySet()) {
                    childMap.put(key, childMap.get(key) && temp.get(key));
                  }
                }
              }
            }
          } else {
            map.putAll(checkAssignment(environment, child, packageMap, map));
          }
        }
        map.putAll(childMap);
        return map;
      case IF_THEN_STATEMENT:
        for (ParseTreeNode child : node.children) {
          if (child.token.getType() == TokenType.STATEMENT || child.token.getType() == TokenType.STATEMENT_NO_SHORT_IF) {
            for (Environment childEnv : environment.mChildrenEnvironments) {
              if (childEnv.mScope == child) {
                // check but ignore things that are assigned within
                checkAssignment(childEnv, node, packageMap, map);
                return map;
              }
            }
          }
        }
        break;
      case WHILE_STATEMENT:
      case WHILE_STATEMENT_NO_SHORT_IF:
      case FOR_STATEMENT:
      case FOR_STATEMENT_NO_SHORT_IF:
        for (ParseTreeNode child : node.children) {
          if (child.token.getType() == TokenType.STATEMENT || child.token.getType() == TokenType.STATEMENT_NO_SHORT_IF) {
            for (Environment childEnv : environment.mChildrenEnvironments) {
              if (childEnv.mScope == child) {
                checkAssignment(childEnv, node, packageMap, map);
                return map;
              }
            }
          }
        }
        break;
      case VARIABLE_DECLARATOR:
        if (node.children.size() == 3) {
          map.putAll(checkAssignment(environment, node.children.get(2), packageMap, map));
          map.put(((TerminalToken)findNodeWithTokenType(node, TokenType.IDENTIFIER).token).getRawValue(), true);
        } else {
          map.put(((TerminalToken)findNodeWithTokenType(node, TokenType.IDENTIFIER).token).getRawValue(), false);
        }
        return map;
      case ASSIGNMENT: {
        if (node.children.get(0).children.get(0).token.getType() == TokenType.NAME) {
          map.putAll(checkAssignment(environment, node.children.get(2), packageMap, map));
          ParseTreeNode nameNode = node.children.get(0).children.get(0);
          if (nameNode != null && nameNode.token.getType() == TokenType.NAME) {
            String s = getNameFromTypeNode(nameNode);
            if (map.containsKey(s)) {
              map.put(s, true);
            }
          }
          return map;
        }
        break;
      }
      case CONDITIONAL_AND_EXPRESSION:
      case CONDITIONAL_OR_EXPRESSION:
        if (node.children.size() == 3) {
          map.putAll(checkAssignment(environment, node.children.get(0), packageMap, map));
          checkAssignment(environment, node.children.get(2), packageMap, map);
          return map;
        } else {
          ParseTreeNode nameNode = node.children.get(0);
          if (nameNode != null && nameNode.token.getType() == TokenType.NAME) {
            String s = getNameFromTypeNode(nameNode);
            if (map.containsKey(s) && !map.get(s)) throw new InvalidSyntaxException("\"" + s + "\" in " + environment.getParentClassEnvironment().mName + "." + environment.getParentMethodEnvironment().mName + " might be unassigned");
          }
        }
        break;
      case ARRAY_ACCESS: {
        if (node.children != null) {
          ParseTreeNode nameNode = node.children.get(0);
          if (nameNode != null && nameNode.token.getType() == TokenType.NAME) {
            String s = getNameFromTypeNode(nameNode);
            if (map.containsKey(s) && !map.get(s)) throw new InvalidSyntaxException("\"" + s + "\" in " + environment.getParentClassEnvironment().mName + "." + environment.getParentMethodEnvironment().mName + " might be unassigned");
          }
        }
        break;
      }
      case ASSIGNMENT_EXPRESSION:
      case POSTFIX_EXPRESSION:
      case EXPRESSION:
      case CONSTANT_EXPRESSION:
      case CONDITIONAL_EXPRESSION:
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
            String s = getNameFromTypeNode(nameNode);
            if (map.containsKey(s) && !map.get(s)) throw new InvalidSyntaxException("\"" + s + "\" in " + environment.getParentClassEnvironment().mName + "." + environment.getParentMethodEnvironment().mName + " might be unassigned");
          }
        }
        break;
      default:
        break;
    }
    if (node.children != null) {
      for (ParseTreeNode child : node.children) {
        map.putAll(checkAssignment(environment, child, packageMap, map));
      }
    }
    if (environment.mScope == node) {
      Map<String, Boolean> retMap = new HashMap(current);
      for (String key : retMap.keySet()) {
        retMap.put(key, map.get(key));
      }
      return retMap;
    }
    return map;
  }
}
