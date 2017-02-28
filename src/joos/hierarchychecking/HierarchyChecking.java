package joos.hierarchychecking;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

import joos.exceptions.InvalidSyntaxException;
import joos.commons.ParseTreeNode;
import joos.commons.TokenType;
import joos.environment.Environment;
import joos.environment.EnvironmentUtils.EnvironmentType;
import static joos.environment.EnvironmentUtils.getEnvironmentType;
import static joos.environment.EnvironmentUtils.getEnvironmentModifiers;
import static joos.environment.EnvironmentUtils.getFullQualifiedName;
import static joos.environment.EnvironmentUtils.getExtendedEnvironments;
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
        getAllMethodSignatures(environment, packageMap);
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
        getAllMethodSignatures(environment, packageMap);
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

  private static Map<String, Map<List<String>, MethodSignature>> getAllMethodSignatures(Environment environment, Map<String, Environment> packageMap) throws InvalidSyntaxException {
    EnvironmentType type = getEnvironmentType(environment);
    switch(type) {
      case CLASS:
      case INTERFACE:
        Map<String, Map<List<String>, MethodSignature>> methodSignatures = new HashMap();
        for (Environment childEnvironment : environment.mChildrenEnvironments) {
          EnvironmentType childType = getEnvironmentType(childEnvironment);
          if (childType == EnvironmentType.METHOD) {
            MethodSignature methodSignature = getMethodSignature(childEnvironment, packageMap);
            if (methodSignatures.containsKey(methodSignature.name)) {
              if (methodSignatures.get(methodSignature.name).containsKey(methodSignature.parameterTypes)) {
                throw new InvalidSyntaxException("A class must not declare two methods with the same signature.");
              }
            } else {
              methodSignatures.put(methodSignature.name, new HashMap());
            }
            methodSignatures.get(methodSignature.name).put(methodSignature.parameterTypes, methodSignature);
          }
        }
        List<Environment> implementedEnvironments = getImplementedEnvironments(environment, packageMap);
        if (implementedEnvironments != null) {
          for (Environment implementedEnvironment : implementedEnvironments) {
            Map<String, Map<List<String>, MethodSignature>> implementedSignatures = getAllMethodSignatures(implementedEnvironment, packageMap);
            for (String name : implementedSignatures.keySet()) {
              for (List<String> parameterTypes : implementedSignatures.get(name).keySet()) {
                MethodSignature implementedSignature = implementedSignatures.get(name).get(parameterTypes);
                implementedSignature.modifiers.add(TokenType.ABSTRACT);
                if (methodSignatures.containsKey(name)) {
                  if (methodSignatures.get(name).containsKey(parameterTypes)) {
                    MethodSignature methodSignature = methodSignatures.get(name).get(parameterTypes);
                    if (!methodSignature.type.equals(implementedSignature.type)) {
                      throw new InvalidSyntaxException("A class or interface must not contain (declare or inherit) two methods with the same signature but different return types");
                    }
                    if (methodSignature.modifiers.contains(TokenType.PROTECTED) && implementedSignature.modifiers.contains(TokenType.PUBLIC)) {
                      throw new InvalidSyntaxException("A protected method must not replace a public method.");
                    }
                  } else {
                    methodSignatures.get(name).put(parameterTypes, implementedSignatures.get(name).get(parameterTypes));
                  }
                } else {
                  methodSignatures.put(name, new HashMap());
                  methodSignatures.get(name).put(parameterTypes, implementedSignatures.get(name).get(parameterTypes));
                }
              }
            }
          }
        }

        List<Environment> extendedEnvironments = getExtendedEnvironments(environment, packageMap);
        for (Environment extendedEnvironment : extendedEnvironments) {
          Map<String, Map<List<String>, MethodSignature>> extendedSignatures = getAllMethodSignatures(extendedEnvironment, packageMap);
          for (String name : extendedSignatures.keySet()) {
            for (List<String> parameterTypes : extendedSignatures.get(name).keySet()) {
              MethodSignature extendedSignature = extendedSignatures.get(name).get(parameterTypes);
              if (methodSignatures.containsKey(name)) {
                if (methodSignatures.get(name).containsKey(parameterTypes)) {
                  MethodSignature methodSignature = methodSignatures.get(name).get(parameterTypes);
                  if (!methodSignature.type.equals(extendedSignature.type)) {
                    throw new InvalidSyntaxException("A class or interface must not contain (declare or inherit) two methods with the same signature but different return types");
                  }
                  if (!methodSignature.modifiers.contains(TokenType.STATIC) && extendedSignature.modifiers.contains(TokenType.STATIC)) {
                    throw new InvalidSyntaxException("A nonstatic method must not replace a static method.");
                  }
                  if (methodSignature.modifiers.contains(TokenType.PROTECTED) && extendedSignature.modifiers.contains(TokenType.PUBLIC)) {
                    throw new InvalidSyntaxException("A protected method must not replace a public method.");
                  }
                  if (extendedSignature.modifiers.contains(TokenType.FINAL)) {
                    throw new InvalidSyntaxException("A method must not replace a final method.");
                  }
                } else {
                  methodSignatures.get(name).put(parameterTypes, extendedSignatures.get(name).get(parameterTypes));
                }
              } else {
                methodSignatures.put(name, new HashMap());
                methodSignatures.get(name).put(parameterTypes, extendedSignatures.get(name).get(parameterTypes));
              }
            }
          }
        }

        if (!getEnvironmentModifiers(environment).contains(TokenType.ABSTRACT)) {
          for (String name : methodSignatures.keySet()) {
            for (List<String> parameterTypes : methodSignatures.get(name).keySet()) {
              MethodSignature methodSignature = methodSignatures.get(name).get(parameterTypes);
              if (methodSignature.modifiers.contains(TokenType.ABSTRACT)) {
                throw new InvalidSyntaxException("A class that contains (declares or inherits) any abstract methods must be abstract.");
              }
            }
          }
        }
        return methodSignatures;
    }
    return null;
  }

  private static MethodSignature getMethodSignature(Environment environment, Map<String, Environment> packageMap) {
    if (getEnvironmentType(environment) != EnvironmentType.METHOD) return null;
    ParseTreeNode declarator = environment.mScope.children.get(0).children.get(2);
    List<String> parameterTypes = new ArrayList();
    if (declarator.children.get(2).children.size() > 0) {
      for (ParseTreeNode parameter : declarator.children.get(2).children.get(0).children) {
        if (parameter.token.getType() == TokenType.COMMA) continue;
        parameterTypes.add(getFullQualifiedName(environment, parameter.children.get(0), packageMap));
      }
    }
    ParseTreeNode typeNode = environment.mScope.children.get(0).children.get(1);
    String type = typeNode.token.getType() == TokenType.VOID ? "void" : getFullQualifiedName(environment, typeNode, packageMap);
    return new MethodSignature(environment.mName, type, parameterTypes, getEnvironmentModifiers(environment));
  }

  private static List<String> getConstructorSignature(Environment environment, Map<String, Environment> packageMap) {
    if (getEnvironmentType(environment) != EnvironmentType.CONSTRUCTOR) return null;
    ParseTreeNode declarator = environment.mScope.children.get(1);
    List<String> parameterTypes = new ArrayList();
    if (declarator.children.get(2).children.size() > 0) {
      for (ParseTreeNode parameter : declarator.children.get(2).children.get(0).children) {
        if (parameter.token.getType() == TokenType.COMMA) continue;
        parameterTypes.add(getFullQualifiedName(environment, parameter.children.get(0), packageMap));
      }
    }
    return parameterTypes;
  }
}

class MethodSignature {
  public String name;
  public String type;
  public List<String> parameterTypes;
  public Set<TokenType> modifiers;
  public MethodSignature(String name, String type, List<String> parameterTypes, Set<TokenType> modifiers) {
    this.name = name;
    this.type = type;
    this.parameterTypes = parameterTypes;
    this.modifiers = modifiers;
  }
}
