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
}
