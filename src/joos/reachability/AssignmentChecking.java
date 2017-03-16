package joos.reachability;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import joos.commons.ParseTreeNode;
import joos.commons.TerminalToken;
import joos.commons.Token;
import joos.commons.TokenType;
import joos.environment.Environment;
import joos.exceptions.InvalidSyntaxException;
import static joos.environment.EnvironmentUtils.findNodeWithTokenType;
import static joos.environment.EnvironmentUtils.getNameFromTypeNode;

public class AssignmentChecking {
  public static Set<String> checkAssignment(
    Environment environment,
    ParseTreeNode node,
    Map<String, Environment> packageMap,
    Set<String> current
  ) throws InvalidSyntaxException {
    Set<String> set = new HashSet(current);
    switch (node.token.getType()) {
      case BLOCK: // fall through
        if (environment.mScope == node) {
          break;
        } else {
          for (Environment child : environment.mChildrenEnvironments) {
            if (child.mScope == node) {
              return checkAssignment(child, node, packageMap, set);
            }
          }
        }
        break;
      case IF_THEN_ELSE_STATEMENT:
      case IF_THEN_ELSE_STATEMENT_NO_SHORT_IF:
        Set<String> childSet = null;
        for (ParseTreeNode child : node.children) {
          if (child.token.getType() == TokenType.STATEMENT || child.token.getType() == TokenType.STATEMENT_NO_SHORT_IF) {
            for (Environment childEnv : environment.mChildrenEnvironments) {
              if (childEnv.mScope == child) {
                Set<String> temp = checkAssignment(childEnv, child, packageMap, set);
                if (childSet == null) {
                  childSet = new HashSet(temp);
                } else {
                  childSet.retainAll(temp);
                }
              }
            }
          } else {
            set.addAll(checkAssignment(environment, child, packageMap, set));
          }
        }
        set.addAll(childSet);
        return set;
      case IF_THEN_STATEMENT:
        for (ParseTreeNode child : node.children) {
          if (child.token.getType() == TokenType.STATEMENT || child.token.getType() == TokenType.STATEMENT_NO_SHORT_IF) {
            for (Environment childEnv : environment.mChildrenEnvironments) {
              if (childEnv.mScope == child) {
                // check but ignore things that are assigned within
                checkAssignment(childEnv, node, packageMap, set);
              }
            }
          }
        }
        return set;
      case WHILE_STATEMENT:
      case WHILE_STATEMENT_NO_SHORT_IF:
      case FOR_STATEMENT:
      case FOR_STATEMENT_NO_SHORT_IF:
        for (ParseTreeNode child : node.children) {
          if (child.token.getType() == TokenType.STATEMENT || child.token.getType() == TokenType.STATEMENT_NO_SHORT_IF) {
            for (Environment childEnv : environment.mChildrenEnvironments) {
              if (childEnv.mScope == child) {
                set.addAll(checkAssignment(childEnv, node, packageMap, set));
              }
            }
          }
        }
        return set;
      case VARIABLE_DECLARATOR:
        if (node.children.size() == 3) {
          set.addAll(checkAssignment(environment, node.children.get(2), packageMap, set));
          set.add(((TerminalToken)findNodeWithTokenType(node, TokenType.IDENTIFIER).token).getRawValue());
        }
        return set;
      case ASSIGNMENT: {
        set.addAll(checkAssignment(environment, node.children.get(2), packageMap, set));
        ParseTreeNode nameNode = node.children.get(0).children.get(0);
        if (nameNode != null && nameNode.token.getType() == TokenType.NAME) {
          String s = getNameFromTypeNode(nameNode);
          if (!s.contains(".")) {
            set.add(s);
          }
        }
        return set;
      }
      case ARRAY_ACCESS: {
        if (node.children != null) {
          ParseTreeNode nameNode = node.children.get(0);
          if (nameNode != null && nameNode.token.getType() == TokenType.NAME) {
            String s = getNameFromTypeNode(nameNode);
            if (!s.contains(".") && !set.contains(s)) throw new InvalidSyntaxException("\"" + s + "\" might be undefined");
          }
        }
        break;
      }
      case POSTFIX_EXPRESSION:
      case EXPRESSION:
      case CONSTANT_EXPRESSION:
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
            String s = getNameFromTypeNode(nameNode);
            if (!s.contains(".") && !set.contains(s)) throw new InvalidSyntaxException("\"" + s + "\" might be undefined");
          }
        }
        break;
      default:
        break;
    }
    if (node.children != null) {
      for (ParseTreeNode child : node.children) {
        set.addAll(checkAssignment(environment, child, packageMap, set));
      }
    }
    if (node == environment.mScope) {
      set.removeAll(environment.mVariableDeclarations.keySet());
    }
    return set;
  }
}
