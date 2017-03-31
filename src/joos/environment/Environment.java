package joos.environment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import joos.exceptions.InvalidSyntaxException;
import joos.exceptions.EnvironmentBuilderException;
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
  private List<Environment> mAllImplementedEnvironments;
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
	public Environment(Environment parent, ParseTreeNode scope, String name) throws EnvironmentBuilderException {
		mParent = parent;
		mScope = scope;
		mName = name;
    if (mName == null || mScope == null) {
      throw new EnvironmentBuilderException("Cannot create a new Environment with a null name or scope");
    }
		if(parent==null) {
			mSingleImports = new ArrayList<>();
			mOnDemandeImports = new ArrayList<>();
			mOnDemandeImports.add("java.lang");
		} else {
			mSingleImports=parent.mSingleImports;
			mOnDemandeImports=parent.mOnDemandeImports;
			PackageName=parent.PackageName;
		}
		mVariableDeclarations = new LinkedHashMap<>();
		mChildrenEnvironments = new ArrayList<>();
	}

  /**
   * Given a usage node (name node) returns the field declaration in which this usage node was used.
   * Can only be use in a class environment
   */
  public ParseTreeNode findVariableDeclarationForUsage(ParseTreeNode usageNode) {
    if (EnvironmentUtils.getEnvironmentType(this) != EnvironmentType.CLASS) {
      System.out.println("findVariableDeclarationForUsage can only be used in a class");
    }
    for (ParseTreeNode fieldDeclarationNode: mVariableDeclarations.values()) {
      if(fieldDeclarationNode.contains(usageNode)) {
        return fieldDeclarationNode;
      }
    }
    return null;
  }

  /**
   * Given a field name checks this environment and all parent environments for the given name and 
   * returns its modifiers.
   * Returns null if no such field exists
   */
  public Set<TokenType> getFieldModifiers(String name, Map<String, Environment> packageMap) throws InvalidSyntaxException {
    ParseTreeNode variableDeclaration = mVariableDeclarations.get(name); 
    if (variableDeclaration != null) {
      return EnvironmentUtils.getAllModifiers(variableDeclaration);
    } else {
      List<Environment> directParents = EnvironmentUtils.getExtendedEnvironments(this, packageMap);
      if (directParents != null) {
        Set<TokenType> mods;
        for(Environment parent : directParents) {
          mods = parent.getFieldModifiers(name, packageMap);
          if (mods != null) {
            return mods;
          }
        }
      }
      return null;
    }
  }

  /**
   * Searches this environment and its parent environments for the declaration of the given name.
   * Returns the environment that declares the given name.
   */
  public Environment getVariableDeclarationEnvronment(String name, Map<String, Environment> packageMap) throws InvalidSyntaxException {
    if (mVariableDeclarations.get(name) != null) {
      return this;
    }
    List<Environment> directParents = EnvironmentUtils.getExtendedEnvironments(this, packageMap);
    if (directParents != null) {
      Environment declEnv;
      for(Environment parent : directParents) {
        declEnv = parent.getVariableDeclarationEnvronment(name, packageMap);
        if (declEnv != null) {
          return declEnv;
        }
      }
    }
    return null;
  }

  /**
   * Given a field name returns whether or not this field in this environment is static
   */
  public boolean isFieldStatic(String name) {
    ParseTreeNode variableDeclaration = mVariableDeclarations.get(name);
    if (variableDeclaration == null) {
      return false;
    }
    return EnvironmentUtils.getAllModifiers(variableDeclaration).contains(TokenType.STATIC);
  }

  /**
   * Returns true if this METHOD environment implements the given ABSTRACT_METHOD
   */
  public boolean implementsAbstractMethod(Environment abstractMethod, Map<String, Environment> packageMap) throws InvalidSyntaxException {
    if(mName.equals(abstractMethod.mName)) {
      MethodSignature thisSignature = this.getMethodSignature(packageMap, null);
      MethodSignature otherSignature = abstractMethod.getMethodSignature(packageMap, null);
      if (thisSignature.parameterTypes == null && otherSignature.parameterTypes == null) {
        return true;
      }
      if (thisSignature.parameterTypes.isEmpty() && otherSignature.parameterTypes.isEmpty()) {
        return true;
      }
      if (thisSignature.parameterTypes.equals(otherSignature.parameterTypes)) {
        return true;
      }
    }
    return false;
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

  public boolean implementsEnvironment(Environment environment, Map<String, Environment> packageMap) throws InvalidSyntaxException {
    if (this == environment) {
      return true;
    }
    List<Environment> directParents = EnvironmentUtils.getImplementedEnvironments(this, packageMap);
    if (directParents != null) {
      boolean doesExtend = false;
      for (Environment parent : directParents) {
        doesExtend = parent.implementsEnvironment(environment, packageMap);
        if (doesExtend) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Returns all of the method environments in this class (not for parent classes)
   */
  public List<Environment> getAllMethodEnvironments() {
    if (getEnvironmentType(this) != EnvironmentType.CLASS) {
      System.out.println("getAllMethodEnvironments can only be called on a class environment!");
      return null;
    }
    List<Environment> methods = new ArrayList<>();
    if(mChildrenEnvironments != null) {
      for(Environment child : mChildrenEnvironments) {
        if (getEnvironmentType(child) == EnvironmentType.METHOD || getEnvironmentType(child) == EnvironmentType.ABSTRACT_METHOD) {
          methods.add(child);
        }
      }
    }
    return methods;
  }

  /**
   * Given a method name and signature returns the environment of the corresponding method if
   * it can be found in this environment or a parent class environment
   */
  public Environment getMethodEnvironment(String name, List<String> signature, Map<String, Environment> packageMap) throws InvalidSyntaxException {
    if(getEnvironmentType(this) != EnvironmentType.CLASS && getEnvironmentType(this) != EnvironmentType.INTERFACE) {
      System.out.println("Environment.getMethodEnvironment can only be called on a class environment");
      return null;
    }
    if (mChildrenEnvironments != null) {
      for(Environment child : mChildrenEnvironments) {
        if(getEnvironmentType(child) == EnvironmentType.METHOD || getEnvironmentType(child) == EnvironmentType.ABSTRACT_METHOD) {
          // check this the method name and args list
          if(child.mName.equals(name)) {
            MethodSignature sig = child.getMethodSignature(packageMap, null);
            if(sig != null) {
              List<String> paramTypes = sig.parameterTypes;
              if (paramTypes.isEmpty() && signature.isEmpty() || signature.equals(paramTypes)) {
                return child;
              }
            }
          }
        }
      }
    }
    List<Environment> extendedEnvironments = getExtendedEnvironments(this, packageMap);
    if(extendedEnvironments != null) {
      Environment foundEnv;
      for (Environment ext : extendedEnvironments) {
        foundEnv = ext.getMethodEnvironment(name, signature, packageMap);
        if(foundEnv != null) {
          return foundEnv;
        }
      }
    }
    return null;
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

  /**
   * checks if all this environment's parent evironments contain default constructors
   */
  public boolean containsZeroArgConstructors(Map<String, Environment> packageMap, boolean isFirstCheck) throws InvalidSyntaxException {
    if (getEnvironmentType(this) != EnvironmentType.CLASS) {
      System.out.println("containsZeroArgConstructor can only be called on a class environment");
      return false;
    }
    boolean defaultConstrFound = false;
    if (!isFirstCheck) {
      if (mChildrenEnvironments != null) {
        for(Environment child : mChildrenEnvironments) {
          List<String> constrSig = child.getConstructorSignature(packageMap);
          if (constrSig != null && constrSig.size() == 0 && child.mName.equals(mName)) {
            defaultConstrFound = true;
            break;
          }
        }
      }
    } else {
      defaultConstrFound = true;
    }
    List<Environment> extendedEnvs = getExtendedEnvironments(this, packageMap);
    boolean parentsContainDefaultConstr = true;
    if(extendedEnvs != null) {
      for(Environment parent : extendedEnvs) {
        if(!parent.containsZeroArgConstructors(packageMap, false)) {
          parentsContainDefaultConstr = false;
        }
      }
    }
    return defaultConstrFound && parentsContainDefaultConstr;
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
            MethodSignature methodSignature = childEnvironment.getMethodSignature(packageMap, environment.mName + "." + environment.PackageName);
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
                    if (!getEnvironmentModifiers(environment).contains(TokenType.ABSTRACT) && methodSignature.modifiers.contains(TokenType.PROTECTED) && implementedSignature.modifiers.contains(TokenType.PUBLIC)) {
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

  public List<Environment> getAllImplementedEnvironments(Map<String, Environment> packageMap) throws InvalidSyntaxException {
    if (mAllImplementedEnvironments == null) {
      mAllImplementedEnvironments = getAllImplementedEnvironmentsRecursive(packageMap);
    } 
    return mAllImplementedEnvironments;
  }

  /**
   * Returns a list of every interface environment that is implemented by the given environment.
   * Recall that interfaces can extend other interfaces
   */
  private List<Environment> getAllImplementedEnvironmentsRecursive(Map<String, Environment> packageMap) throws InvalidSyntaxException {
    List<Environment> implemented = new ArrayList<>();
    EnvironmentType type = getEnvironmentType(this);
    if (type == EnvironmentType.INTERFACE) {
      List<Environment> extended = getExtendedEnvironments(this, packageMap);
      if (extended != null) {
        implemented.addAll(extended);
      }
    } else if(type == EnvironmentType.CLASS) {
      List<Environment> impl = getImplementedEnvironments(this, packageMap);
      if(impl != null) {
        implemented.addAll(impl);
      }
    }
    if(implemented != null) {
      List<Environment> parentImplementations = new ArrayList<>();
      for(Environment parent : implemented) {
        List<Environment> impls = parent.getAllImplementedEnvironments(packageMap);
        if (impls != null) {
          parentImplementations.addAll(impls);
        }
      }
      implemented.addAll(parentImplementations);
    }
    return implemented;
  }

  public MethodSignature getMethodSignature(Map<String, Environment> packageMap, String origin) throws InvalidSyntaxException {
    ParseTreeNode declarator = mScope.children.get(0).children.get(2);
    List<String> parameterTypes = new ArrayList();
    if (declarator.children.get(2).children.size() > 0) {
      for (ParseTreeNode parameter : declarator.children.get(2).children.get(0).children) {
        if (parameter.token.getType() == TokenType.COMMA) continue;
        parameterTypes.add(getFullQualifiedNameFromTypeNode(this, parameter.children.get(0), packageMap));
      }
    }
    ParseTreeNode typeNode = mScope.children.get(0).children.get(1);
    String type = typeNode.token.getType() == TokenType.VOID ? "void" : getFullQualifiedNameFromTypeNode(this, typeNode, packageMap);
    Set<TokenType> modifiers = new HashSet(getEnvironmentModifiers(this));
    if (getEnvironmentType(this) == EnvironmentType.ABSTRACT_METHOD) modifiers.add(TokenType.ABSTRACT);
    return new MethodSignature(mName, type, parameterTypes, modifiers, origin);
  }

	public Environment getParentMethodEnvironment() {
		Environment env = this;
		for (;;) {
			EnvironmentType envType = getEnvironmentType(env);
			switch (envType) {
				case CLASS:
					return null;
				case METHOD:
					return env;
			}
			if (env.mParent == null) return null;
			env = env.mParent;
		}
	}

	public Environment getParentClassEnvironment() {
		Environment env = this;
		for (;;) {
			EnvironmentType envType = getEnvironmentType(env);
			switch (envType) {
				case CLASS:
					return env;
			}
			if (env.mParent == null) return null;
			env = env.mParent;
		}
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
