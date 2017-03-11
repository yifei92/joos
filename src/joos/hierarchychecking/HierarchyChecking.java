package joos.hierarchychecking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import joos.commons.MethodSignature;
import joos.commons.ParseTreeNode;
import joos.commons.TokenType;
import joos.environment.Environment;
import joos.environment.Environment.EnvironmentType;
import joos.exceptions.InvalidSyntaxException;
import static joos.environment.EnvironmentUtils.getEnvironmentModifiers;
import static joos.environment.EnvironmentUtils.getEnvironmentType;
import static joos.environment.EnvironmentUtils.getExtendedEnvironments;
import static joos.environment.EnvironmentUtils.getFullQualifiedNameFromTypeNode;
import static joos.environment.EnvironmentUtils.getImplementedEnvironments;

public class HierarchyChecking {
  static public void check(Environment environment, Map<String, Environment> packageMap) throws InvalidSyntaxException {
    EnvironmentType type = getEnvironmentType(environment);
    switch (type) {
      case CLASS: {
        checkCyclicity(environment, new HashSet(), packageMap);
        List<Environment> extendedEnvironments = getExtendedEnvironments(environment, packageMap);
        if (extendedEnvironments.size() == 1) {
          Environment extendedEnvironment = extendedEnvironments.get(0);
          if (getEnvironmentType(extendedEnvironment) == EnvironmentType.INTERFACE) {
            throw new InvalidSyntaxException("A class must not extend an interface.");
          }
          if (getEnvironmentModifiers(extendedEnvironment).contains(TokenType.FINAL)) {
            throw new InvalidSyntaxException("A class must not extend a final class.");
          }
        }
        List<Environment> implementedEnvironments = getImplementedEnvironments(environment, packageMap);
        Set<String> interfaceNames = new HashSet();
        for (Environment implementedEnvironment : implementedEnvironments) {
          if (getEnvironmentType(implementedEnvironment) == EnvironmentType.CLASS) {
            throw new InvalidSyntaxException("A class must not implement a class.");
          }
          String qualifiedName = implementedEnvironment.PackageName + implementedEnvironment.mName;
          if (interfaceNames.contains(qualifiedName)) {
            throw new InvalidSyntaxException("An interface must not be repeated in an implements clause.");
          }
          interfaceNames.add(qualifiedName);
        }
        environment.getMethodSignatures(packageMap);
        Set<List<String>> constructorSignatures = new HashSet();
        for (Environment childEnvironment : environment.mChildrenEnvironments) {
          EnvironmentType childType = getEnvironmentType(childEnvironment);
          if (childType == EnvironmentType.CONSTRUCTOR) {
            List<String> constructorSignature = getConstructorSignature(childEnvironment, packageMap);
            if (constructorSignatures.contains(constructorSignature)) {
              throw new InvalidSyntaxException("A class must not declare two constructors with the same parameter types.");
            }
            constructorSignatures.add(constructorSignature);
          }
        }
        break;
      }
      case INTERFACE: {
        checkCyclicity(environment, new HashSet(), packageMap);
        List<Environment> extendedEnvironments = getExtendedEnvironments(environment, packageMap);
        for (Environment extendedEnvironment : extendedEnvironments) {
          if (getEnvironmentType(extendedEnvironment) == EnvironmentType.CLASS) {
            throw new InvalidSyntaxException("An interface must not extend a class.");
          }
        }
        environment.getMethodSignatures(packageMap);
        break;
      }
    }
    for (Environment childEnvironment : environment.mChildrenEnvironments) {
      check(childEnvironment, packageMap);
    }
  }

  private static void checkCyclicity(Environment environment, Set<String> names, Map<String, Environment> packageMap) throws InvalidSyntaxException {
    String qualifiedName = environment.PackageName + "." + environment.mName;
    if (names.contains(qualifiedName)) {
      throw new InvalidSyntaxException("The hierarchy must be acyclic.");
    }
    names.add(qualifiedName);
    for (Environment extendedEnvironment : getExtendedEnvironments(environment, packageMap)) {
      checkCyclicity(extendedEnvironment, new HashSet(names), packageMap);
    }
    if (getEnvironmentType(environment) == EnvironmentType.CLASS) {
      for (Environment implementedEnvironment : getImplementedEnvironments(environment, packageMap)) {
        checkCyclicity(implementedEnvironment, new HashSet(names), packageMap);
      }
    }
  }

  private static List<String> getConstructorSignature(Environment environment, Map<String, Environment> packageMap) throws InvalidSyntaxException {
    if (getEnvironmentType(environment) != EnvironmentType.CONSTRUCTOR) return null;
    ParseTreeNode declarator = environment.mScope.children.get(1);
    List<String> parameterTypes = new ArrayList();
    if (declarator.children.get(2).children.size() > 0) {
      for (ParseTreeNode parameter : declarator.children.get(2).children.get(0).children) {
        if (parameter.token.getType() == TokenType.COMMA) continue;
        parameterTypes.add(getFullQualifiedNameFromTypeNode(environment, parameter.children.get(0), packageMap));
      }
    }
    return parameterTypes;
  }
}
