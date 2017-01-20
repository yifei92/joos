package joos.commons;

import java.util.Set;
import joos.commons.TokenType;

public interface Token {
  public TokenType getType();
  public static Token getToken(TokenType tokenType) {
    return null;
  }
  public static Set<TokenType> getAllTokens() {
    return null;
  }
}
