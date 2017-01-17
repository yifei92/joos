package joos.parser;

import java.util.List;
import joos.commons.Token;
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
  public ParseTreeNode parse(List<String> tokens) throws InvalidSyntaxException {
    HashSet<String> terminals = new HashSet<String>();
    terminals.add("{");
    terminals.add("a");
    terminals.add("b");
    terminals.add("}");
    HashSet<String> nonterminals = new HashSet<String>();
    nonterminals.add("S");
    nonterminals.add("A");
    HashMap<String, List<List<String>>> productions = new HashMap();
    productions.put("S", Arrays.asList(Arrays.asList("{", "A", "}"), Arrays.asList("A")));
    productions.put("A", Arrays.asList(Arrays.asList("{", "A", "}"), Arrays.asList("a"), Arrays.asList("b")));
    Grammar grammar = new Grammar(
      terminals,
      nonterminals,
      productions,
      "S"
    );
    grammar.parse(tokens);

    return null;
  }
}
