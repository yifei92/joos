package joos.commons;

import java.util.List;
import java.util.Set;

import joos.commons.TokenType;

public class MethodSignature {
  public String name;
  public String type;
  public List<String> parameterTypes;
  public Set<TokenType> modifiers;
  public String origin;
  public MethodSignature(String name, String type, List<String> parameterTypes, Set<TokenType> modifiers, String origin) {
    this.name = name;
    this.type = type;
    this.parameterTypes = parameterTypes;
    this.modifiers = modifiers;
    this.origin = origin;
  }  

  /**
   * Returns true if this METHOD environment implements the given ABSTRACT_METHOD
   */
  public boolean implementsAbstractMethod(Environment abstractMethod, Map<String, Environment> packageMap) throws InvalidSyntaxException {
    if(name.equals(abstractMethod.mName)) {
      MethodSignature thisSignature = getMethodSignature(this, packageMap, null);
      MethodSignature otherSignature = getMethodSignature(abstractMethod, packageMap, null);
      if (parameterTypes == null && otherSignature.parameterTypes == null) {
        return true;
      }
      if (parameterTypes.isEmpty() && otherSignature.parameterTypes.isEmpty()) {
        return true;
      }
      if (parameterTypes.equals(otherSignature.parameterTypes)) {
        return true;
      }
    }
    return false;
  }

}
