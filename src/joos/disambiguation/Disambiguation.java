package joos.disambiguation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;
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
import static joos.environment.EnvironmentUtils.getFullQualifiedNameFromTypeNode;
import static joos.environment.EnvironmentUtils.getNameFromTypeNode;
import static joos.environment.EnvironmentUtils.getTypeFromTypeNode;
import static joos.environment.EnvironmentUtils.findNodeWithRawValue;

public class Disambiguation {

  static boolean p;

  public static void linkAllTypes(Environment environment,Map<String, Environment> packageMap) throws InvalidSyntaxException {
    if (environment.mVariableToType == null) {
      environment.mVariableToType = new HashMap();
      linkTypes(environment, environment.mScope, packageMap);
    }
  }
  public static void linkAllNames(Environment environment, Map<String, Environment> packageMap) throws InvalidSyntaxException {
    if (environment.mName.equals("Integer")) p = true;
    linkNames(environment, environment.mScope, packageMap, null, false);
    p = false;
  }

  static void linkTypes(Environment environment, ParseTreeNode node, Map<String, Environment> packageMap) throws InvalidSyntaxException {
    switch (node.token.getType()) {
			case BLOCK: // fall through
      case ABSTRACT_METHOD_DECLARATION:
        if (environment.mScope == node) {
          break;
        } else {
          for (Environment child : environment.mChildrenEnvironments) {
            if (child.mScope == node) {
              if (child.mVariableToType == null) {
                child.mVariableToType = new HashMap();
                linkTypes(child, node, packageMap);
              }
              return;
            }
          }
        }
        break;
      case IF_THEN_ELSE_STATEMENT_NO_SHORT_IF:
      case IF_THEN_ELSE_STATEMENT:
        if (node.children != null) {
          boolean willRet = false;
          for (ParseTreeNode child : node.children) {
            if (child.token.getType() == TokenType.STATEMENT || child.token.getType() == TokenType.STATEMENT_NO_SHORT_IF) {
              for (Environment childEnv : environment.mChildrenEnvironments) {
                if (childEnv.mScope == child) {
                  linkTypes(childEnv, child, packageMap);
                  willRet = true;
                }
              }
            }
          }
          if (willRet) return;
        }
        break;
      case IF_THEN_STATEMENT:
      case WHILE_STATEMENT:
      case WHILE_STATEMENT_NO_SHORT_IF:
      case FOR_STATEMENT:
      case FOR_STATEMENT_NO_SHORT_IF:
        if (node.children != null) {
          for (ParseTreeNode child : node.children) {
            if (child.token.getType() == TokenType.STATEMENT || child.token.getType() == TokenType.STATEMENT_NO_SHORT_IF) {
              for (Environment childEnv : environment.mChildrenEnvironments) {
                if (childEnv.mScope == child) {
                  if (childEnv.mVariableToType == null) {
                    childEnv.mVariableToType = new HashMap();
                    linkTypes(childEnv, node, packageMap);
                  }
                  return;
                }
              }
            }
          }
        }
        break;
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
          for (Environment child : environment.mChildrenEnvironments) {
            if (child.mScope == node) {
              if (child.mVariableToType == null) {
                child.mVariableToType = new HashMap();
                linkTypes(child, node, packageMap);
              }
              return;
            }
          }
        }
        break;
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
          for (Environment child : environment.mChildrenEnvironments) {
            if (child.mScope == node) {
              if (child.mVariableToType == null) {
                child.mVariableToType = new HashMap();
                linkTypes(child, node, packageMap);
              }
              return;
            }
          }
        }
        break;
      }
      case FIELD_DECLARATION: {
        Set<TokenType> set = new HashSet();
        for (ParseTreeNode child : node.children.get(0).children) {
          set.add(child.children.get(0).token.getType());
        }
        Type type = getTypeFromTypeNode(environment, node.children.get(1), packageMap);
        type.decl = node;
        type.modifiers = set;
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

  private static void linkMethodName(Environment environment, ParseTreeNode node, Map<String, Environment> packageMap) throws InvalidSyntaxException {
    String name = getNameFromTypeNode(node);
    int dotIndex = name.lastIndexOf('.');
    if (dotIndex == -1) {
      Environment env = environment;
      while (getEnvironmentType(env) != EnvironmentType.CLASS) {
        if (
          getEnvironmentType(env) == EnvironmentType.METHOD &&
          env.getMethodSignature(packageMap, "").modifiers.contains(TokenType.STATIC)
        ) {
          throw new InvalidSyntaxException("Method \"" + name + "\" in " + env.mParent.mName + "." + env.mName + " cannot be accessed without a full qualified name.");
        }
        env = env.mParent;
      }
      node.type = new Type(env.PackageName + "." + env.mName);
    } else {
      linkName(environment, node, name.substring(0, dotIndex), packageMap, environment, false, null, false);
    }
  }

  public static void linkNames(Environment environment, ParseTreeNode node, Map<String, Environment> packageMap, ParseTreeNode declaration, boolean shouldBeType) throws InvalidSyntaxException {
    switch (node.token.getType()) {
			case BLOCK: // fall through
      case ABSTRACT_METHOD_DECLARATION:
      case CONSTRUCTOR_DECLARATION:
      case METHOD_DECLARATION:
        if (environment.mScope == node) {
          break;
        } else {
          for (Environment child : environment.mChildrenEnvironments) {
            if (child.mScope == node) {
              linkNames(child, node, packageMap, declaration, shouldBeType);
              return;
            }
          }
        }
        break;
      case REFERENCE_TYPE:
        return;
      case IF_THEN_ELSE_STATEMENT:
      case IF_THEN_ELSE_STATEMENT_NO_SHORT_IF:
        if (node.children != null) {
          for (ParseTreeNode child : node.children) {
            if (child.token.getType() == TokenType.STATEMENT || child.token.getType() == TokenType.STATEMENT_NO_SHORT_IF) {
              for (Environment childEnv : environment.mChildrenEnvironments) {
                if (childEnv.mScope == child) {
                  linkNames(childEnv, child, packageMap, declaration, shouldBeType);
                }
              }
            } else {
              linkNames(environment, child, packageMap, declaration, shouldBeType);
            }
          }
          return;
        }
        break;
      case IF_THEN_STATEMENT:
      case WHILE_STATEMENT:
      case WHILE_STATEMENT_NO_SHORT_IF:
      case FOR_STATEMENT:
      case FOR_STATEMENT_NO_SHORT_IF:
        if (node.children != null) {
          for (ParseTreeNode child : node.children) {
            if (child.token.getType() == TokenType.STATEMENT || child.token.getType() == TokenType.STATEMENT_NO_SHORT_IF) {
              for (Environment childEnv : environment.mChildrenEnvironments) {
                if (childEnv.mScope == child) {
                  linkNames(childEnv, node, packageMap, declaration, shouldBeType);
                  return;
                }
              }
            }
          }
        }
        break;
      case LOCAL_VARIABLE_DECLARATION:
      case FIELD_DECLARATION:
        declaration = node;
        break;
      case ARRAY_ACCESS:
        if (node.children != null) {
          ParseTreeNode nameNode = node.children.get(0);
          if (nameNode != null && nameNode.token.getType() == TokenType.NAME) {
            String s = getNameFromTypeNode(nameNode);
            linkName(environment, nameNode, s, packageMap, environment, false, declaration, shouldBeType);
            TypeChecker.checkUsageForProtectedFieldAccess(environment, nameNode, packageMap);
            linkNames(environment, node.children.get(2), packageMap, declaration, shouldBeType);
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
            String s = getNameFromTypeNode(nameNode);
            linkName(environment, nameNode, s, packageMap, environment, node.token.getType() == TokenType.LEFT_HAND_SIDE, declaration, shouldBeType);
            TypeChecker.checkUsageForProtectedFieldAccess(environment, nameNode, packageMap);
            return;
          }
        }
        break;
      case CAST_EXPRESSION:
        if (node.children.size() == 5) {
          Type subType = getTypeFromTypeNode(environment, node.children.get(1), packageMap);
          node.children.get(1).type = Type.newType(
            subType.name,
            subType,
            node.children.get(1)
          );
          linkNames(environment, node.children.get(4), packageMap, declaration, shouldBeType);
        } else {
          linkNames(environment, node.children.get(1), packageMap, declaration, true);
          linkNames(environment, node.children.get(3), packageMap, declaration, shouldBeType);
        }
        return;
      case METHOD_INVOCATION:
        if (node.children.get(0).token.getType() == TokenType.NAME) {
          linkMethodName(environment, node.children.get(0), packageMap);
        }
      default:
        break;
    }
    if (node.children != null) {
      for (ParseTreeNode child : node.children) {
        linkNames(environment, child, packageMap, declaration, shouldBeType);
      }
    }
  }

  private static boolean linkNameToVariable(
    Environment environment,
    String name,
    ParseTreeNode node,
    Map<String, Environment> packageMap,
    Environment usageEnvironment,
    boolean shouldBeStatic,
    boolean isFirstScope,
    boolean isLeftHandSide,
    ParseTreeNode declaration
  ) throws InvalidSyntaxException {
    int dotIndex = name.indexOf('.');
    String prefix;
    if (dotIndex != -1) {
      prefix = name.substring(0, dotIndex);
    } else {
      prefix = name;
    }
    // find the identifier node under the given node for this prefix and set its type environment
    // to the given environment
    ParseTreeNode prefixIdentifierNode = findNodeWithRawValue(node, prefix);
    if (prefixIdentifierNode != null && prefixIdentifierNode.type == null) {
      prefixIdentifierNode.type = 
        Type.newObject(prefix, environment, environment.mScope);
    }
    if (
      environment.mVariableDeclarations.containsKey(prefix) &&
      (
        !isFirstScope ||
        isLeftHandSide ||
        (getEnvironmentType(environment) == EnvironmentType.CLASS && getEnvironmentType(usageEnvironment) != EnvironmentType.CLASS) ||
        (
          !(!isLeftHandSide && declaration != null && declaration == environment.mVariableDeclarations.get(prefix)) &&
          environment.mVariableDeclarations.get(prefix).isBefore(node)
        )
      )
    ) {
      boolean isPrefixDeclarationStatic = environment.isFieldStatic(prefix);
      if (getEnvironmentType(environment) == EnvironmentType.CLASS && shouldBeStatic && !isPrefixDeclarationStatic) {
        throw new InvalidSyntaxException(prefix + " in " + environment.mName + " should be static");
      } else if (!shouldBeStatic && isPrefixDeclarationStatic) {
        throw new InvalidSyntaxException(prefix + " in " + environment.mName + " should not be static");
      }
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
              node.type.modifiers = new HashSet();
              node.type.modifiers.add(TokenType.FINAL);
              node.type.modifiers.add(TokenType.PUBLIC);
              return true;
            }
          }
          if (linkNameToVariable(packageMap.get("java.lang.Object"), name.substring(dotIndex + 1), node, packageMap, usageEnvironment, false, false, isLeftHandSide, declaration)) {
            return true;
          }
        } else {
          if (packageMap.containsKey(type.name)) {
            if (linkNameToVariable(packageMap.get(type.name), name.substring(dotIndex + 1), node, packageMap, usageEnvironment, false, false, isLeftHandSide, declaration)) {
              return true;
            }
          }
        }
      }
    }
    if (getEnvironmentType(environment) == EnvironmentType.CLASS && environment.mVariableDeclarations.containsKey(prefix)) {
      throw new InvalidSyntaxException("Illegal forward reference of \"" + prefix + "\" in " + environment.mName);
    }
    switch (getEnvironmentType(environment)) {
      case CLASS:
        for (Environment extendedEnvironment : getExtendedEnvironments(environment, packageMap)) {
          if (linkNameToVariable(extendedEnvironment, name, node, packageMap, usageEnvironment, shouldBeStatic, isFirstScope, isLeftHandSide, declaration)) return true;
        }
        break;
      default:
      if (linkNameToVariable(environment.mParent, name, node, packageMap, usageEnvironment, shouldBeStatic, isFirstScope, isLeftHandSide, declaration)) return true;
    }
    return false;
  }

  static void linkName(
    Environment environment,
    ParseTreeNode node,
    String name,
    Map<String, Environment> packageMap,
    Environment usageEnvironment,
    boolean isLeftHandSide,
    ParseTreeNode declaration,
    boolean shouldBeType
  ) throws InvalidSyntaxException {
    if (!shouldBeType) {
      boolean shouldBeStatic = false;
      // if node is in a static environment then shouldBeStatic = true else shouldBeStatic = false
      Environment methodEnvironment = environment.getParentMethodEnvironment();
      if (methodEnvironment != null && methodEnvironment.getMethodSignature(packageMap, "").modifiers.contains(TokenType.STATIC)) shouldBeStatic = true;
      if (EnvironmentUtils.getEnvironmentType(environment) == EnvironmentType.CLASS) {
        ParseTreeNode fieldDeclarationNode = environment.findVariableDeclarationForUsage(node);
        if (fieldDeclarationNode != null) {
          if (findNodeWithTokenType(fieldDeclarationNode, TokenType.STATIC) != null) {
            shouldBeStatic = true;
          }
        }
      }
      if (linkNameToVariable(environment, name, node, packageMap, usageEnvironment, shouldBeStatic, true, isLeftHandSide, declaration)) return;
    }
    int dotIndex = name.indexOf('.');
    String prefix;
    if (dotIndex != -1) {
      prefix = name.substring(0, dotIndex);
    } else {
      prefix = name;
    }    
    Environment typeEnvironment = null;
    try {
      typeEnvironment = getEnvironmentFromTypeName(environment, prefix, packageMap);
    } catch (InvalidSyntaxException e) {
      for (String packageName : packageMap.keySet()) {
        if (name.length() >= packageName.length() && name.substring(0, packageName.length()).equals(packageName)) {
          Environment env = packageMap.get(packageName);
          if (env.PackageName.length() != 0 || (env.PackageName.length() == 0 && environment.PackageName.length() == 0)) {
            typeEnvironment = env;
            dotIndex = name.indexOf('.', packageName.length());
            if (dotIndex == -1) {
              prefix = name;
            } else {
              prefix = name.substring(0, dotIndex);
            }
          }
        }
      }
    }      
    if (typeEnvironment != null && dotIndex == -1) {
      Type subType = Type.newObject(
        getFullQualifiedNameFromTypeName(environment, prefix, packageMap),
        typeEnvironment,
        node
      );
      node.type = Type.newType(
        subType.name,
        subType,
        node
      );
      return;
    }
    if (!shouldBeType && typeEnvironment != null && dotIndex != -1) {
      if (linkNameToVariable(typeEnvironment, name.substring(dotIndex + 1), node, packageMap, usageEnvironment, true, false, isLeftHandSide, declaration)) return;
    }
    throw new InvalidSyntaxException("Name \"" + name + "\" cannot be resolved");
  }
}
