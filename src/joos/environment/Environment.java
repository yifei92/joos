package joos.environment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import joos.exceptions.InvalidSyntaxException;
import joos.commons.MethodSignature;
import joos.commons.ParseTreeNode;
import joos.commons.TokenType;
import joos.commons.Type;
import static joos.environment.EnvironmentUtils.getEnvironmentModifiers;
import static joos.environment.EnvironmentUtils.getEnvironmentType;
import static joos.environment.EnvironmentUtils.getExtendedEnvironments;
import static joos.environment.EnvironmentUtils.getFullQualifiedNameFromTypeNode;
import static joos.environment.EnvironmentUtils.getImplementedEnvironments;

public class Environment {
	public enum EnvironmentType {ROOT, PACKAGE, CLASS, INTERFACE, CONSTRUCTOR, METHOD, ABSTRACT_METHOD, BLOCK};

	public final String mName;
	// A reference to the parent environment
	public final Environment mParent;
	// A reference to the ast node that is this environment
	public final ParseTreeNode mScope;

	// A reference to the names declared in this environment.
	public Map<String, ParseTreeNode> mVariableDeclarations;

	public Map<String, Type> mVariableToType;

	public List<Environment> mExtendedEnvironments;
	public List<Environment> mImplementedEnvironments;
	public Set<TokenType> mModifiers;
	public EnvironmentType mType;
  private Map<String, Map<List<String>, MethodSignature>> mMethodSignatures;


  public List<String> mSingleImports;
	public List<String> mOnDemandeImports;

	public String PackageName="";

    // A reference to the child environments of this environment
	public List<Environment> mChildrenEnvironments;

	/**
	 * @param  parent reference to the parent environment
	 * @param  scope  reference to the ParseTreeNode that counts as
	 * @param  name   name of this Environment (name of the class )
	 */
	public Environment(Environment parent, ParseTreeNode scope, String name) {
		mParent = parent;
		mScope = scope;
		mName = name;
		if(parent==null) {
			mSingleImports = new ArrayList<>();
			mOnDemandeImports = new ArrayList<>();
			mOnDemandeImports.add("java.lang");
		} else {
			mSingleImports=parent.mSingleImports;
			mOnDemandeImports=parent.mOnDemandeImports;
			PackageName=parent.PackageName;
		}
		mVariableDeclarations = new HashMap<>();
		mChildrenEnvironments = new ArrayList<>();
	}

  /**
   * Returns true if this environment is a child of the given environment
   */
  public boolean extendsEnvironment(Environment environment, Map<String, Environment> packageMap) throws InvalidSyntaxException {
    if (this == environment) {
      return true;
    }
    List<Environment> directParents = EnvironmentUtils.getExtendedEnvironments(this, packageMap);
    if (directParents != null) {
      boolean doesExtend = false;
      for (Environment parent : directParents) {
        doesExtend = parent.extendsEnvironment(environment, packageMap);
        if (doesExtend) {
          return true;
        }
      }
    }
    return false;
  }

	/**
	 * If the method signature exists within this class or is a method in a parent class then the full method 
	 * signature will be returned. Null otherwise
	 */
	public MethodSignature findMethodSignature(Map<String, Environment> packageMap, MethodSignature partialSignature) throws InvalidSyntaxException {
		if (mName.equals(partialSignature.name)) {
			for (Environment child : mChildrenEnvironments) {
				if (EnvironmentUtils.getEnvironmentType(child) == EnvironmentType.CONSTRUCTOR) {
					List<String> constructorParamTypes = child.getConstructorSignature(packageMap);
					if (constructorParamTypes != null) {
						if(constructorParamTypes.equals(partialSignature.parameterTypes)) {
							return new MethodSignature(
								partialSignature.name,
								partialSignature.name,
								partialSignature.parameterTypes,
								null,
								null);
						}
					}
				}
			}
		}
		Map<String, Map<List<String>, MethodSignature>> methodSignatures = getMethodSignatures(packageMap);	
		if (methodSignatures != null) {
			Map<List<String>, MethodSignature> things = methodSignatures.get(partialSignature.name);
			if (things != null) {
				MethodSignature methSig = things.get(partialSignature.parameterTypes);
				if(methSig != null) {
					return methSig;
				}
			}
		}
		List<Environment> extendedEnvironments = EnvironmentUtils.getExtendedEnvironments(this, packageMap);
		for (Environment env : extendedEnvironments) {
			MethodSignature methsig = env.findMethodSignature(packageMap, partialSignature);
			if (methsig != null) {
				return methsig;
			}
		}
		return null;
	}

	public Map<String, Map<List<String>, MethodSignature>> getMethodSignatures(Map<String, Environment> packageMap) throws InvalidSyntaxException  {
		if (mMethodSignatures == null) {
			mMethodSignatures = getAllMethodSignatures(this, packageMap);
		}
		return mMethodSignatures;
	}

	public List<String> getConstructorSignature(Map<String, Environment> packageMap) throws InvalidSyntaxException {
		return sGetConstructorSignature(this, packageMap);
	}

  private static List<String> sGetConstructorSignature(Environment environment, Map<String, Environment> packageMap) throws InvalidSyntaxException {
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

	private static Map<String, Map<List<String>, MethodSignature>> getAllMethodSignatures(Environment environment, Map<String, Environment> packageMap) throws InvalidSyntaxException {
    if (environment.mMethodSignatures != null) return environment.mMethodSignatures;
    EnvironmentType type = getEnvironmentType(environment);
    switch(type) {
      case CLASS:
      case INTERFACE:
        Map<String, Map<List<String>, MethodSignature>> methodSignatures = new HashMap();
        for (Environment childEnvironment : environment.mChildrenEnvironments) {
          EnvironmentType childType = getEnvironmentType(childEnvironment);
          if (childType == EnvironmentType.METHOD || childType == EnvironmentType.ABSTRACT_METHOD) {
            MethodSignature methodSignature = getMethodSignature(childEnvironment, packageMap, environment.mName + "." + environment.PackageName);
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

        List<Environment> extendedEnvironments = getExtendedEnvironments(environment, packageMap);
        if (type == EnvironmentType.INTERFACE && extendedEnvironments.size() == 0) {
          Map<String, Map<List<String>, MethodSignature>> extendedSignatures = getAllMethodSignatures(packageMap.get("java.lang.Object"), packageMap);
          for (String name : extendedSignatures.keySet()) {
            for (List<String> parameterTypes : extendedSignatures.get(name).keySet()) {
              MethodSignature extendedSignature = extendedSignatures.get(name).get(parameterTypes);
              extendedSignature.modifiers.add(TokenType.ABSTRACT);
              if (methodSignatures.containsKey(name)) {
                if (methodSignatures.get(name).containsKey(parameterTypes)) {
                  MethodSignature methodSignature = methodSignatures.get(name).get(parameterTypes);
                  if (!methodSignature.type.equals(extendedSignature.type)) {
                    throw new InvalidSyntaxException("A class or interface must not contain (declare or inherit) two methods with the same signature but different return types");
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
                  if (methodSignature.modifiers.contains(TokenType.STATIC) && !extendedSignature.modifiers.contains(TokenType.STATIC)) {
                    throw new InvalidSyntaxException("A static method must not replace a nonstatic method.");
                  }
                  if (methodSignature.modifiers.contains(TokenType.PROTECTED) && extendedSignature.modifiers.contains(TokenType.PUBLIC)) {
                    throw new InvalidSyntaxException("A protected method must not replace a public method.");
                  }
                  if (extendedSignature.modifiers.contains(TokenType.FINAL) && !extendedSignature.origin.equals(methodSignature.origin)) {
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

        List<Environment> implementedEnvironments = getImplementedEnvironments(environment, packageMap);
        if (implementedEnvironments != null) {
          for (Environment implementedEnvironment : implementedEnvironments) {
            Map<String, Map<List<String>, MethodSignature>> implementedSignatures = getAllMethodSignatures(implementedEnvironment, packageMap);
            for (String name : implementedSignatures.keySet()) {
              for (List<String> parameterTypes : implementedSignatures.get(name).keySet()) {
                MethodSignature implementedSignature = implementedSignatures.get(name).get(parameterTypes);
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

        if (type == EnvironmentType.CLASS && !getEnvironmentModifiers(environment).contains(TokenType.ABSTRACT)) {
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

  private static MethodSignature getMethodSignature(Environment environment, Map<String, Environment> packageMap, String origin) throws InvalidSyntaxException {
    ParseTreeNode declarator = environment.mScope.children.get(0).children.get(2);
    List<String> parameterTypes = new ArrayList();
    if (declarator.children.get(2).children.size() > 0) {
      for (ParseTreeNode parameter : declarator.children.get(2).children.get(0).children) {
        if (parameter.token.getType() == TokenType.COMMA) continue;
        parameterTypes.add(getFullQualifiedNameFromTypeNode(environment, parameter.children.get(0), packageMap));
      }
    }
    ParseTreeNode typeNode = environment.mScope.children.get(0).children.get(1);
    String type = typeNode.token.getType() == TokenType.VOID ? "void" : getFullQualifiedNameFromTypeNode(environment, typeNode, packageMap);
    Set<TokenType> modifiers = new HashSet(getEnvironmentModifiers(environment));
    if (getEnvironmentType(environment) == EnvironmentType.ABSTRACT_METHOD) modifiers.add(TokenType.ABSTRACT);
    return new MethodSignature(environment.mName, type, parameterTypes, modifiers, origin);
  }


	public void print() {
		this.print(0);
	}

	private void print(int depth) {
		for (int i = 0; i < depth; i++) {
			System.out.print("  ");
		}

		if (mScope == null) {
			System.out.print("S: root ");
		} else {
			System.out.print("S: " + (mName == null ? mScope.token.getType() : mName) + " ");
		}

		for (String name : mVariableDeclarations.keySet()) {
			System.out.print("N: " + name + " ");
		}
		System.out.println("");

		if (mChildrenEnvironments != null) {
			for (Environment child : mChildrenEnvironments) {
				child.print(depth + 1);
			}
		}
	}
}
