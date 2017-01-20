package joos.parser;

import java.util.List;
import joos.commons.Token;
import joos.commons.TerminalToken;
import joos.commons.NonterminalToken;
import joos.commons.ParseTreeNode;
import joos.exceptions.InvalidSyntaxException;
import joos.parser.Grammar;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Arrays;

public class Parser {

  /**
   * Given a list of valid Tokens returns a valid parse tree root node
   * will the rest of the parse tree below it.
   */
  public ParseTreeNode parse(List<TerminalToken> tokens) throws InvalidSyntaxException {
    return JavaGrammar.grammar.parse(tokens);
  }
}
