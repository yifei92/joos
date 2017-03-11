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

public class TypeChecker {

  static public void check(Environment environment, Map<String, Environment> packageMap) throws InvalidSyntaxException {
    EnvironmentType type = getEnvironmentType(environment);
    switch (type) {
      case CLASS: {
        checkForAbstractClassInstantiation(environment, packageMap);
        checkConstructorUsageForProtectedAccess(environment, packageMap);
        checkConstructorImportsForProtectedAccess(environment, packageMap);
        checkForBitwiseOpts(environment);
        break;
      }
      case INTERFACE: {
        break;
      }
      case CONSTRUCTOR:
        checkConstructorName(environment);
        checkForZeroArgParentConstructor(environment, packageMap);
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
  private static void checkForZeroArgParentConstructor(Environment constructorEnvironment, Map<String, Environment> packageMap) throws InvalidSyntaxException {
    List<Environment> extendedEnvironments = getExtendedEnvironments(constructorEnvironment.mParent, packageMap);
    if (extendedEnvironments.size() > 0 && !containsConstructor(extendedEnvironments.get(0))) {
      throw new InvalidSyntaxException(
        "Class " + constructorEnvironment.mName +  " does not have a default constructor in its super class");
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
    findNodesWithTokenType(environment.mScope, TokenType.BOOL_OP_EAGER_AND, bitwiseOptNodes);
    findNodesWithTokenType(environment.mScope, TokenType.BITWISE_XOR, bitwiseOptNodes);
    findNodesWithTokenType(environment.mScope, TokenType.BOOL_OP_EAGER_OR, bitwiseOptNodes);
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

  //Check that all accesses of protected fields, methods and constructors are in 
  //a subtype of the type declaring the entity being accessed, or in the same 
  //package as that type.
  //
  //Checks if the given nameNode.type is protected from the given environment
  public static void checkUsageForProtectedFieldAccess(Environment environment, ParseTreeNode nameNode, Map<String, Environment> packageMap) throws InvalidSyntaxException {
    // Check this usage of a variable for protected type access
    ParseTreeNode declarationNode = nameNode.type.decl;
    List<ParseTreeNode> modifiers = new ArrayList<>();
    findNodesWithTokenType(declarationNode, TokenType.MODIFIER, modifiers);
    for (ParseTreeNode modifier : modifiers) {
      if (modifier.children.get(0).token.getType() == TokenType.PROTECTED) {
        Environment declarationEnvironment = getEnvironmentForFieldDeclaration(declarationNode, packageMap);
        if (!declarationEnvironment.PackageName.equals(environment.PackageName)) {
          throw new InvalidSyntaxException("Protected " + declarationEnvironment.mName + " is protected and cannot be referenced from " + environment.PackageName);
        } else {
          Environment usageClassEnvironment = moveUpToClassEnvironment(environment);
          List<Environment> extendedEnvironments = getExtendedEnvironments(usageClassEnvironment, packageMap);
          if (!extendedEnvironments.contains(declarationEnvironment)) {
            throw new InvalidSyntaxException("Protected " + declarationEnvironment.mName + " is protected and cannot be referenced from " + environment.PackageName);
          }
          List<Environment> implementedEnvironments = getImplementedEnvironments(environment, packageMap);
          if (!implementedEnvironments.contains(declarationEnvironment)) {
            throw new InvalidSyntaxException("Protected " + declarationEnvironment.mName + " is protected and cannot be referenced from " + environment.PackageName);
          }
        }
      }
    }
  }

  // TODO: call this like checkUsageForProtectedFieldAccess is called in Disambiguation
  //Checks if the given nameNode.type is protected from the given environment
  public static void checkUsageForProtectedMethodAccess(Environment environment, ParseTreeNode nameNode, Map<String, Environment> packageMap) throws InvalidSyntaxException {
    // Check this usage of a variable for protected type access
    ParseTreeNode declarationNode = nameNode.type.decl;
    List<ParseTreeNode> modifiers = new ArrayList<>();
    findNodesWithTokenType(declarationNode, TokenType.MODIFIER, modifiers);
    for (ParseTreeNode modifier : modifiers) {
      if (modifier.children.get(0).token.getType() == TokenType.PROTECTED) {
        Environment declarationEnvironment = getEnvironmentForMethodDeclaration(declarationNode, packageMap);
        if (!declarationEnvironment.PackageName.equals(environment.PackageName)) {
          throw new InvalidSyntaxException("Protected " + declarationEnvironment.mName + " is protected and cannot be referenced from " + environment.PackageName);
        } else {
          Environment usageClassEnvironment = moveUpToClassEnvironment(environment);
          List<Environment> extendedEnvironments = getExtendedEnvironments(usageClassEnvironment, packageMap);
          if (!extendedEnvironments.contains(declarationEnvironment)) {
            throw new InvalidSyntaxException("Protected " + declarationEnvironment.mName + " is protected and cannot be referenced from " + environment.PackageName);
          }
          List<Environment> implementedEnvironments = getImplementedEnvironments(environment, packageMap);
          if (!implementedEnvironments.contains(declarationEnvironment)) {
            throw new InvalidSyntaxException("Protected " + declarationEnvironment.mName + " is protected and cannot be referenced from " + environment.PackageName);
          }
        }
      }
    }
  }

  private static void checkConstructorUsageForProtectedAccess(Environment classEnvironment, Map<String, Environment> packageMap) throws InvalidSyntaxException  {
    List<ParseTreeNode> constructorTypeUsages = new ArrayList<>();
    findNodesWithTokenType(classEnvironment.mScope, TokenType.CLASS_OR_INTERFACE_TYPE, constructorTypeUsages);
    for (ParseTreeNode constructorTypeUsage: constructorTypeUsages) {
      Environment definitionEnvironment = getEnvironmentFromTypeNode(classEnvironment, constructorTypeUsage, packageMap);
      Set<TokenType> modifiers = getEnvironmentModifiers(definitionEnvironment);
      if (modifiers.contains(TokenType.PROTECTED)) {
        if (!definitionEnvironment.PackageName.equals(classEnvironment.PackageName)) {
          throw new InvalidSyntaxException("Protected " + definitionEnvironment.mName + " is protected and cannot be referenced from " + classEnvironment.PackageName);
        } else {
          List<Environment> extendedEnvironments = getExtendedEnvironments(classEnvironment, packageMap);
          if (!extendedEnvironments.contains(definitionEnvironment)) {
            throw new InvalidSyntaxException("Protected " + definitionEnvironment.mName + " is protected and cannot be referenced from " + classEnvironment.PackageName);
          }
          List<Environment> implementedEnvironments = getImplementedEnvironments(classEnvironment, packageMap);
          if (!implementedEnvironments.contains(definitionEnvironment)) {
            throw new InvalidSyntaxException("Protected " + definitionEnvironment.mName + " is protected and cannot be referenced from " + classEnvironment.PackageName);
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
          throw new InvalidSyntaxException("Protected " + importedClassEnvironment.mName + " is protected and cannot be referenced in imports in " + classEnvironment.PackageName);
        } else {
          List<Environment> extendedEnvironments = getExtendedEnvironments(classEnvironment, packageMap);
          if (!extendedEnvironments.contains(importedClassEnvironment)) {
            throw new InvalidSyntaxException("Protected " + importedClassEnvironment.mName + " is protected and cannot be referenced from " + classEnvironment.PackageName);
          }
          List<Environment> implementedEnvironments = getImplementedEnvironments(classEnvironment, packageMap);
          if (!implementedEnvironments.contains(importedClassEnvironment)) {
            throw new InvalidSyntaxException("Protected " + importedClassEnvironment.mName + " is protected and cannot be referenced from " + classEnvironment.PackageName);
          }
        }
      }
    }
  }
}
