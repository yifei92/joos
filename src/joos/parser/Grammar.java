package joos.parser;

import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.Stack;
import java.util.ArrayList;

public class Grammar {
  Set<String> terminals;
  Set<String> nonterminals;
  Map<String, List<Production>> productionsByLHS;
  Map<Integer, Production> productions;
  Map<String, List<String>> firstTerminals;
  String start;

  Entry startEntry;
  Map<Integer, Map<Integer, Entry>> entries;

  public Grammar(
    Set<String> terminals,
    Set<String> nonterminals,
    Map<String, List<List<String>>> productions,
    String start
  ) {
    this.terminals = terminals;
    this.nonterminals = nonterminals;
    this.start = start;
    this.productionsByLHS = new HashMap<String, List<Production>>();
    this.productions = new HashMap<Integer, Production>();
    this.firstTerminals = new HashMap<String, List<String>>();
    this.entries = new HashMap<Integer, Map<Integer, Entry>>();
    this.generateProductions(productions);
    this.generateFirstTerminals(this.start);
    this.generateProductionGraph();
    Production startProduction = this.productionsByLHS.get(this.start).get(0);
    this.startEntry = this.entries.get(startProduction.id).get(0);
    this.printGraph();
  }

  private void generateProductions(Map<String, List<List<String>>> map) {
    int currentID = 0;
    for (String lhs : map.keySet()) {
      List<List<String>> list = map.get(lhs);
      List<Production> productionList = new ArrayList();
      for (List<String> rhs : list) {
        Production production = new Production(currentID++, lhs, rhs);
        productionList.add(production);
        this.productions.put(production.id, production);
      }
      productionsByLHS.put(lhs, productionList);
    }
  }

  private void generateFirstTerminals(String symbol) {
    ArrayList<String> list = new ArrayList();
    if (this.terminals.contains(symbol)) {
      list.add(symbol);
    }
    else {
      for (Production production : this.productionsByLHS.get(symbol)) {
        generateFirstTerminals(production.rhs.get(0));
        list.addAll(this.firstTerminals.get(production.rhs.get(0)));
      }
    }
    this.firstTerminals.put(symbol, list);
  }

  private void generateProductionGraph() {
    for (String lhs : this.productionsByLHS.keySet()) {
      for (Production production : this.productionsByLHS.get(lhs)) {
        HashMap<Integer, Entry> map = new HashMap();
        for (int i = 0; i < production.rhs.size(); i++) {
          map.put(i, new Entry(production.id, i));
        }
        this.entries.put(production.id, map);
      }
    }
    for (String lhs : this.productionsByLHS.keySet()) {
      for (Production production : this.productionsByLHS.get(lhs)) {
        Map<Integer, Entry> productionEntries = this.entries.get(production.id);
        int size = production.rhs.size();
        for (int i = 0; i < production.rhs.size(); i++) {
          Entry entry = productionEntries.get(i);
          String rh = production.rhs.get(i);
          if (i == size - 1) {
            entry.actions.put(rh, new Action(new Reduction(production.lhs, size)));
          } else {
            entry.actions.put(rh, new Action(this.entries.get(production.id).get(i + 1)));
          }
          if (this.nonterminals.contains(rh)) {
            Map<String, Entry> map = entry.epsilonMap;
            for (Production childProduction : this.productionsByLHS.get(rh)) {
              for (String firstTerminal : this.firstTerminals.get(childProduction.rhs.get(0))) {
                map.put(firstTerminal, this.entries.get(childProduction.id).get(0));
              }
            }
          }
        }

      }
    }
  }

  public void printGraph() {
    for (int productionID : this.entries.keySet()) {
      Production production = this.productions.get(productionID);
      System.out.print("Production " + productionID + ": " + production.lhs + " ->");
      for (String rh : production.rhs) {
        System.out.print(" " + rh);
      }
      System.out.println();
      for (int index : this.entries.get(productionID).keySet()) {
        System.out.println("  @" + index);
        Entry entry = this.entries.get(productionID).get(index);
        for (String symbol : entry.actions.keySet()) {
          Action action = entry.actions.get(symbol);
          if (action.entry != null) {
            System.out.println("    " + symbol + " => (" + action.entry.productionID + ", " + action.entry.index + ")");
          } else {
            System.out.println("    " + symbol + " => reduce " + action.reduction.symbol);
          }
        }
        for (String symbol : entry.epsilonMap.keySet()) {
          Entry epsilonEntry = entry.epsilonMap.get(symbol);
          System.out.println("    epsilon-" + symbol + " => (" + epsilonEntry.productionID + ", " + epsilonEntry.index + ")");
        }
        System.out.println();
      }
      System.out.println();
      System.out.println();
    }
  }

  public void parse(List<String> tokens) {
    int tokensIndex = 0;
    Stack<Entry> entryStack = new Stack<Entry>();
    entryStack.push(this.startEntry);
    String token = tokens.get(0);
    while (tokensIndex < tokens.size()) {
      System.out.println(token);
      if (entryStack.peek().actions.containsKey(token)) {
        Action action = entryStack.peek().actions.get(token);
        if (action.entry != null) {
          entryStack.push(action.entry);
          tokensIndex++;
          token = tokens.get(tokensIndex);
        } else {
          for (int i = 0; i < action.reduction.number - 1; i++) {
            entryStack.pop();
          }
          token = action.reduction.symbol;
        }
      } else if (entryStack.peek().epsilonMap.containsKey(token)) {
        entryStack.push(entryStack.peek().epsilonMap.get(token));
      } else {
        if (entryStack.size() == 1 && token == this.start && tokensIndex == tokens.size() - 1) {
          System.out.println("SUCCESS");
          break;
        } else if (entryStack.size() > 1 && this.nonterminals.contains(token)) {
          entryStack.pop();
        } else {
          System.out.println("ERROR");
          break;
        }
      }
    }
  }
}

class Production {
  public final int id;
  public final String lhs;
  public final List<String> rhs;

  public Production(int id, String lhs, List<String> rhs) {
    this.id = id;
    this.lhs = lhs;
    this.rhs = rhs;
  }
}

class Reduction {
  public final String symbol;
  public final int number;

  public Reduction(String symbol, int number) {
    this.symbol = symbol;
    this.number = number;
  }
}

class Entry {
  public final int productionID;
  public final int index;
  public final Map<String, Action> actions;
  public final Map<String, Entry> epsilonMap;

  public Entry(int productionID, int index) {
    this.productionID = productionID;
    this.index = index;
    this.actions = new HashMap<String, Action>();
    this.epsilonMap = new HashMap<String, Entry>();
  }
}

class Action {
  public final Reduction reduction;
  public final Entry entry;

  public Action(Reduction reduction) {
    this.reduction = reduction;
    this.entry = null;
  }

  public Action(Entry entry) {
    this.entry = entry;
    this.reduction = null;
  }
}
