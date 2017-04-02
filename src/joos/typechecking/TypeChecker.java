package joos.typechecking;

import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import joos.commons.ParseTreeNode;
import joos.environment.Environment;
import joos.environment.Environment.EnvironmentType;
import joos.exceptions.InvalidSyntaxException;
import joos.commons.TokenType;
import joos.commons.Type;
import joos.commons.TerminalToken;
import static joos.environment.EnvironmentUtils.getEnvironmentType;
import static joos.environment.EnvironmentUtils.getExtendedEnvironments;
import static joos.environment.EnvironmentUtils.containsConstructor;
import static joos.environment.EnvironmentUtils.getEnvironmentModifiers;
import static joos.environment.EnvironmentUtils.findNodesWithTokenType;
import static joos.environment.EnvironmentUtils.getAllInstantiationEnvironments;
import static joos.environment.EnvironmentUtils.findNodeWithTokenType;
import static joos.environment.EnvironmentUtils.getEnvironmentForFieldDeclaration;
import static joos.environment.EnvironmentUtils.getEnvironmentForMethodDeclaration;
import static joos.environment.EnvironmentUtils.getEnvironmentFromTypeNode;
import static joos.environment.EnvironmentUtils.moveUpToClassEnvironment;
import static joos.environment.EnvironmentUtils.getImplementedEnvironments;
import static joos.environment.EnvironmentUtils.getMethodSignatureFromArgsList;
import static joos.environment.EnvironmentUtils.getEnvironmentFromTypeName;
import static joos.environment.EnvironmentUtils.findImmediateNodeWithTokenType;
import static joos.environment.EnvironmentUtils.getMethodNameFromInvocation;

public class TypeChecker {

  static public void check(Environment environment, Map<String, Environment> packageMap) throws InvalidSyntaxException {
    EnvironmentType type = getEnvironmentType(environment);
    switch (type) {
      case CLASS: {
        checkForAbstractClassInstantiation(environment, packageMap);
        checkConstructorUsageForProtectedAccess(environment, packageMap);
        checkConstructorImportsForProtectedAccess(environment, packageMap);
        checkUsageForProtectedMethodAccess(environment, packageMap);
        checkForBitwiseOpts(environment);
        checkForZeroArgParentConstructor(environment, packageMap);
        break;
      }
      case INTERFACE: {
        break;
      }
      case CONSTRUCTOR:
        checkConstructorName(environment);
      case METHOD: {
        break;
      }
    }
    for (Environment childEnvironment : environment.mChildrenEnvironments) {
      check(childEnvironment, packageMap);
    }
  }

  // Check that the name of a constructor is the same as the name of its enclosing class.
  private static void checkConstructorName(Environment constructorEnvironment) throws InvalidSyntaxException {
    if (!constructorEnvironment.mName.equals(constructorEnvironment.mParent.mName)) {
      throw new InvalidSyntaxException(
        "Constructor name " + 
        constructorEnvironment.mName + 
        " must be the same as class name " + 
        constructorEnvironment.mParent.mName);
    }
  }

  //A constructor in a class other than java.lang.Object implicitly calls the zero-argument 
  //constructor of its superclass. Check that this zero-argument constructor exists.
  private static void checkForZeroArgParentConstructor(Environment classEnvironment, Map<String, Environment> packageMap) throws InvalidSyntaxException {
    boolean containsDefaultConstructor = classEnvironment.containsZeroArgConstructors(packageMap, true);
    if (!containsDefaultConstructor) {
      throw new InvalidSyntaxException(
        "Class " + classEnvironment.mName +  " does not have a default constructor in its super class");
    }
  }

  // Check that no objects of abstract classes are created.
  private static void checkForAbstractClassInstantiation(Environment environment, Map<String, Environment> packageMap) throws InvalidSyntaxException {
    List<Environment> environments = getAllInstantiationEnvironments(environment, packageMap);
    for (Environment env: environments) {
      if(getEnvironmentModifiers(env).contains(TokenType.ABSTRACT)) {
        throw new InvalidSyntaxException("abstract " + env.mName + " is being instantiated!");
      }
    }
  }

  // Check that no bitwise operations occur.
  private static void checkForBitwiseOpts(Environment environment) throws InvalidSyntaxException {
    List<ParseTreeNode> bitwiseOptNodes = new ArrayList<>();

    List<ParseTreeNode> eagerNodes = new ArrayList<>();
    findNodesWithTokenType(environment.mScope, TokenType.AND_EXPRESSION, eagerNodes);
    findNodesWithTokenType(environment.mScope, TokenType.INCLUSIVE_OR_EXPRESSION, eagerNodes);
    if (eagerNodes.size() > 0) {
      for (ParseTreeNode eagerNode : eagerNodes) {
        if (eagerNode.children != null) {
          for (ParseTreeNode child : eagerNode.children) {
            TokenType type = child.token.getType();
            if (type != TokenType.BOOL_OP_EAGER_AND && type != TokenType.BOOL_OP_EAGER_OR) {
              Type nodeType = child.getFirstType();
              if (nodeType == null || (!nodeType.name.equals("boolean") && !nodeType.name.equals("java.lang.Boolean"))) {
                bitwiseOptNodes.add(eagerNode);
              }
            }
          }
        }
      }
    }
    findNodesWithTokenType(environment.mScope, TokenType.BITWISE_XOR, bitwiseOptNodes);
    findNodesWithTokenType(environment.mScope, TokenType.OP_LEFT_SHIFT, bitwiseOptNodes);
    findNodesWithTokenType(environment.mScope, TokenType.OP_RIGHT_SHIFT, bitwiseOptNodes);
    findNodesWithTokenType(environment.mScope, TokenType.OP_UNSIGNED_RIGHT_SHIFT, bitwiseOptNodes);
    

    if (bitwiseOptNodes.size() > 0) {
      throw new InvalidSyntaxException("Error! Detected bitwise operation!");
    }
  }

  // Check that the implicit this variable is not accessed in a static method or in the initializer of a static field.
  private static void checkForThisFromStaticField(Environment classEnvironment) throws InvalidSyntaxException {
    if (classEnvironment.mVariableDeclarations != null && classEnvironment.mVariableDeclarations.size() > 0) {
      for (ParseTreeNode variableDeclaration : classEnvironment.mVariableDeclarations.values()) {
        List<ParseTreeNode> modifiers = new ArrayList<>();
        findNodesWithTokenType(variableDeclaration, TokenType.MODIFIER, modifiers);
        if (modifiers.size() > 0) {
          for(ParseTreeNode modifier: modifiers) {
            if (modifier.children.get(0).token.getType() == TokenType.STATIC) {
              // we have a static field declaration. 
              // check if its assigment references this
              List<ParseTreeNode> thisNodes = new ArrayList<>();
              findNodesWithTokenType(variableDeclaration, TokenType.THIS, thisNodes);
              if (thisNodes.size() > 0) {
                throw new InvalidSyntaxException("Error! Cannot reference \"this\" in static field declaration");
              }
            }
          }
        }
      }
    }
  }

  // Check that the implicit this variable is not accessed in a static method or in the initializer of a static field.
  private static void checkForThisFromStaticMethod(Environment methodEnvironment) throws InvalidSyntaxException {
    Set<TokenType> modifiers = getEnvironmentModifiers(methodEnvironment);
    if (modifiers.contains(TokenType.STATIC)) {
      List<ParseTreeNode> thisNodes = new ArrayList<>();
      findNodesWithTokenType(methodEnvironment.mScope, TokenType.THIS, thisNodes);
      if (thisNodes.size() > 0) {
        throw new InvalidSyntaxException("Error! Cannot reference \"this\" in static method " + methodEnvironment.mName);
      }
    }
  }

  public static void checkUsageForProtectedFieldAccess(Environment environment, ParseTreeNode nameNode, Map<String, Environment> packageMap) throws InvalidSyntaxException {
    // Check this usage of a variable for protected type access
    List<ParseTreeNode> identifierNodes = new ArrayList<>();
    findNodesWithTokenType(nameNode, TokenType.IDENTIFIER, identifierNodes);
    for(ParseTreeNode identifierNode : identifierNodes) {
      Type type = identifierNode.type;
      if (type != null) {
        // check if this type.name is protected in type.environment and if so whether or not type.environment
        // extends the given environment
        Environment typeEnvironment = type.environment;
        if (typeEnvironment != null) {
          Set<TokenType> modifiers = typeEnvironment.getFieldModifiers(type.name, packageMap);
          if(modifiers != null && modifiers.contains(TokenType.PROTECTED)) {
            if (!typeEnvironment.PackageName.equals(environment.PackageName)) {
              Environment usageClassEnvironment = moveUpToClassEnvironment(environment);
              Environment declarationEnvironment = typeEnvironment.getVariableDeclarationEnvronment(type.name, packageMap);
              if (!usageClassEnvironment.extendsEnvironment(declarationEnvironment, packageMap)) {
                throw new InvalidSyntaxException(
                  "Protected " + type.name + " is protected and cannot be referenced from " + environment.PackageName);
              }
              if(!typeEnvironment.extendsEnvironment(usageClassEnvironment, packageMap)) {
                throw new InvalidSyntaxException(
                  "Protected " + type.name + " is protected and cannot be referenced from " + environment.PackageName);
              }
            }
          }
        }
      }
    }
  }

  public static void checkUsageForProtectedMethodAccess(Environment classEnvironment, Map<String, Environment> packageMap) throws InvalidSyntaxException {
    // Check this usage of a variable for protected type access
    List<ParseTreeNode> methodInvocations = new ArrayList<>();
    findNodesWithTokenType(classEnvironment.mScope, TokenType.METHOD_INVOCATION, methodInvocations);
    for(ParseTreeNode methodInvocation : methodInvocations) {
      String methodName = getMethodNameFromInvocation(methodInvocation);
      List<String> invocationSignature = new ArrayList<>();
      ParseTreeNode argsListOptNode = findImmediateNodeWithTokenType(methodInvocation, TokenType.ARGUMENT_LIST_OPT);
      if (argsListOptNode != null) {
        if(argsListOptNode.children != null && argsListOptNode.children.size() > 0) {
          getMethodSignatureFromArgsList(argsListOptNode.children.get(0), invocationSignature);
        }
      }
      Type type;
      ParseTreeNode primaryNode = findImmediateNodeWithTokenType(methodInvocation, TokenType.PRIMARY);
      if(primaryNode != null) {
        type = primaryNode.type;
      } else {
        ParseTreeNode nameNode = findImmediateNodeWithTokenType(methodInvocation, TokenType.NAME);
        type = nameNode.type;
      }
      if (type != null) {
        // check if this type.name is protected in type.environment and if so whether or not type.environment
        // extends the given environment
        Environment typeEnvironment = type.environment;
        if (typeEnvironment != null) {
          Environment declarationEnvironment = typeEnvironment.getMethodEnvironment(methodName, invocationSignature, packageMap);
          Set<TokenType> modifiers = getEnvironmentModifiers(declarationEnvironment);
          if(modifiers != null && modifiers.contains(TokenType.PROTECTED)) {
            if (!typeEnvironment.PackageName.equals(classEnvironment.PackageName)) {
              if (modifiers.contains(TokenType.STATIC)) {
                // access is not of an instance so the below exceptions do not apply
                throw new InvalidSyntaxException(
                  "Protected " + type.name + " is protected and cannot be referenced from " + classEnvironment.PackageName);
              }
              declarationEnvironment = moveUpToClassEnvironment(declarationEnvironment);
              if (!classEnvironment.extendsEnvironment(declarationEnvironment, packageMap)) {
                throw new InvalidSyntaxException(
                  "Protected " + type.name + " is protected and cannot be referenced from " + classEnvironment.PackageName);
              }
              if(!typeEnvironment.extendsEnvironment(classEnvironment, packageMap)) {
                throw new InvalidSyntaxException(
                  "Protected " + type.name + " is protected and cannot be referenced from " + classEnvironment.PackageName);
              }
            }
          }
        }
      }
    }
  }

  private static void checkConstructorUsageForProtectedAccess(Environment classEnvironment, Map<String, Environment> packageMap) throws InvalidSyntaxException  {
    List<ParseTreeNode> constructorUsages = new ArrayList<>();
    findNodesWithTokenType(classEnvironment.mScope, TokenType.CLASS_INSTANCE_CREATION_EXPRESSION, constructorUsages);
    for (ParseTreeNode constructorUsage: constructorUsages) {
      ParseTreeNode constructorTypeNode = findNodeWithTokenType(constructorUsage, TokenType.CLASS_OR_INTERFACE_TYPE);
      Environment definitionEnvironment = getEnvironmentFromTypeNode(classEnvironment, constructorTypeNode, packageMap);
      Set<TokenType> modifiers = getEnvironmentModifiers(definitionEnvironment);
      // Check the class if its protected
      if (modifiers.contains(TokenType.PROTECTED)) {
        if (!definitionEnvironment.PackageName.equals(classEnvironment.PackageName)) {
          throw new InvalidSyntaxException(
            "Protected " + definitionEnvironment.mName + " is protected and cannot be referenced from " + classEnvironment.PackageName);
        }
      }
      // check the constructor if its protected
      ParseTreeNode constructorArgsListNode = findNodeWithTokenType(constructorUsage, TokenType.ARGUMENT_LIST);
      List<String> constructorInvocationSignature = new ArrayList<>();
      getMethodSignatureFromArgsList(constructorArgsListNode, constructorInvocationSignature);
      for(Environment child: definitionEnvironment.mChildrenEnvironments) {
        if (getEnvironmentType(child) == EnvironmentType.CONSTRUCTOR) {
          List<String> constructorSignature = child.getConstructorSignature(packageMap);
          if (constructorInvocationSignature.isEmpty() && constructorSignature.isEmpty() ||
              constructorInvocationSignature.equals(constructorSignature)) {
            Set<TokenType> modifs = getEnvironmentModifiers(child);
            if (modifs != null && modifs.contains(TokenType.PROTECTED)) {
              if (!definitionEnvironment.PackageName.equals(classEnvironment.PackageName)) {
                throw new InvalidSyntaxException(
                  "Protected " + definitionEnvironment.mName + " is protected and cannot be referenced from " + classEnvironment.PackageName);
              }
            }
          }
        }
      }
    }
  }

  private static void checkConstructorImportsForProtectedAccess(Environment classEnvironment, Map<String, Environment> packageMap)throws InvalidSyntaxException  {
    for (String imprt: classEnvironment.mSingleImports) {
      Environment importedClassEnvironment = packageMap.get(imprt);
      Set<TokenType> modifiers = getEnvironmentModifiers(importedClassEnvironment);
      if (modifiers.contains(TokenType.PROTECTED)) {
        if (!importedClassEnvironment.PackageName.equals(classEnvironment.PackageName)) {
          if (!classEnvironment.extendsEnvironment(importedClassEnvironment, packageMap) && 
              !classEnvironment.implementsEnvironment(importedClassEnvironment, packageMap)) {
            throw new InvalidSyntaxException(
              "Protected " + importedClassEnvironment.mName + " is protected and cannot be referenced from " + classEnvironment.PackageName);
          }
        }
      }
    }
  }
}
