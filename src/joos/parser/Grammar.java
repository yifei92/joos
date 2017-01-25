package joos.parser;

import java.lang.Comparable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import joos.commons.NonterminalToken;
import joos.commons.ParseTreeNode;
import joos.commons.TerminalToken;
import joos.commons.Token;
import joos.commons.TokenType;
import joos.exceptions.InvalidSyntaxException;

public class Grammar {
	Set<TokenType> terminals;
	Set<TokenType> nonterminals;
	Map<TokenType, List<Production>> productionsByLHS;
	Map<Integer, Production> productions;
	Map<TokenType, Set<TokenType>> firstTerminals;
	Map<TokenType, Set<TokenType>> follows;
	TokenType start;
	Map<ProductionIndex, Set<ProductionIndex>> similarProductions;
	ItemSet itemSetTable;
	Map<Set<ProductionIndex>, ItemSet> itemSets;

	Entry startEntry;
	Map<Integer, Map<Integer, Entry>> entries;

	public Grammar(
		Set<TokenType> terminals,
		Set<TokenType> nonterminals,
		Map<TokenType, List<List<TokenType>>> productions,
		TokenType start
	) {
		this.terminals = terminals;
		this.nonterminals = nonterminals;
		this.start = start;
		this.productionsByLHS = new HashMap();
		this.productions = new HashMap();
		this.firstTerminals = new HashMap();
		this.entries = new HashMap();
		this.similarProductions = new HashMap();
		this.itemSets = new HashMap();
		this.follows = new HashMap();
		this.generateProductions(productions);
		for (TokenType symbol : this.nonterminals) {
			this.generateFirstTerminals(symbol);
		}
		for (TokenType symbol : this.terminals) {
			this.generateFirstTerminals(symbol);
		}
		this.follows.put(this.start, new HashSet(Arrays.asList(TokenType.EOF)));
		this.generateFollows(this.start);
		for (Production production : this.productions.values()) {
			for (int i = 0; i < production.rhs.size(); i++) {
				generateSimilarProductions(new ProductionIndex(production, i));
			}
		}
		this.generateAllItemSets();
	}

	private void generateProductions(Map<TokenType, List<List<TokenType>>> map) {
		int currentID = 0;
		for (TokenType lhs : map.keySet()) {
			List<List<TokenType>> list = map.get(lhs);
			List<Production> productionList = new ArrayList();
			for (List<TokenType> rhs : list) {
				Production production = new Production(currentID++, lhs, rhs);
				productionList.add(production);
				this.productions.put(production.id, production);
			}
			productionsByLHS.put(lhs, productionList);
		}
	}

	private void generateFirstTerminals(TokenType symbol) {
		if (this.firstTerminals.containsKey(symbol)) {
			return;
		}
		Set<TokenType> set = new HashSet();
		this.firstTerminals.put(symbol, set);
		if (this.terminals.contains(symbol)) {
			set.add(symbol);
		}
		else {
			for (Production production : this.productionsByLHS.get(symbol)) {
				int i = 0;
				for (;;) {
					if (production.rhs.size() > i) {
						generateFirstTerminals(production.rhs.get(i));
						boolean breakOut = true;
						for (TokenType t : this.firstTerminals.get(production.rhs.get(i))) {
							if (t == null) {
								breakOut = false;
							} else {
								set.add(t);
							}
						}
						if (breakOut) {
							break;
						}
					} else {
						set.add(null);
						break;
					}
					i++;
				}
			}
		}
	}

	private void generateFollows(TokenType symbol) {
		for (Production production : this.productionsByLHS.get(symbol)) {
			for (int i = 0; i < production.rhs.size(); i++) {
				TokenType rh = production.rhs.get(i);
				boolean generated = true;
				if (!this.follows.containsKey(rh)) {
					this.follows.put(rh, new HashSet());
					generated = false;
				}
				if (this.nonterminals.contains(rh)) {
					for (int j = i + 1; j <= production.rhs.size(); j++) {
						if (j == production.rhs.size()) {
							Set<TokenType> set = this.follows.get(symbol);
							if (!this.follows.get(rh).containsAll(set)) {
								generated = false;
								this.follows.get(rh).addAll(set);
							}
						} else {
							TokenType rh2 = production.rhs.get(j);
							boolean breakOut = true;
							for (TokenType t : this.firstTerminals.get(rh2)) {
								if (t == null) {
									breakOut = false;
								} else {
									if (!this.follows.get(rh).contains(t)) {
										generated = false;
										this.follows.get(rh).add(t);
									}
								}
							}
							if (breakOut) {
								break;
							}
						}
					}
					if (!generated) {
						generateFollows(rh);
					}
				}
			}
		}
	}

	private void generateSimilarProductions(ProductionIndex productionIndex) {
		Set<ProductionIndex> set = new HashSet();
		this.similarProductions.put(productionIndex, set);
		set.add(productionIndex);
		TokenType rh = productionIndex.production.rhs.get(productionIndex.index);
		if (this.nonterminals.contains(rh)) {
			for (Production childProduction : this.productionsByLHS.get(rh)) {
				if (childProduction.rhs.size() > 0) {
					ProductionIndex childProductionIndex = new ProductionIndex(childProduction, 0);
					if (!this.similarProductions.containsKey(childProductionIndex)) {
						generateSimilarProductions(childProductionIndex);
					}
					set.addAll(this.similarProductions.get(childProductionIndex));
				}
			}
		}
	}

	private void generateAllItemSets() {
		Production startProduction = this.productionsByLHS.get(this.start).get(0);
		ProductionIndex productionIndex = new ProductionIndex(startProduction, 0);
		Set<ProductionIndex> set = new HashSet();
		set.add(productionIndex);
		this.itemSetTable = new ItemSet(set);
		this.itemSetTable.links.put(this.start, new Action());
		this.itemSets.put(set, this.itemSetTable);
		generateItemSets(this.itemSetTable);
	}

	private void generateItemSets(ItemSet itemSet) {
		for (ProductionIndex productionIndex : itemSet.definingProductionIndices) {
			if (productionIndex.production.rhs.size() == productionIndex.index) {
				itemSet.productionIndices.add(productionIndex);
			} else {
				itemSet.productionIndices.addAll(this.similarProductions.get(productionIndex));
			}
		}
		Map<TokenType, Set<ProductionIndex>> productionsByToken = new HashMap();
		for (ProductionIndex productionIndex : itemSet.productionIndices) {
			if (productionIndex.index < productionIndex.production.rhs.size()) {
				TokenType rh = productionIndex.production.rhs.get(productionIndex.index);
				if (!productionsByToken.containsKey(rh)) {
					productionsByToken.put(rh, new HashSet());
				}
				productionsByToken.get(rh).add(new ProductionIndex(productionIndex.production, productionIndex.index + 1));
				if (this.firstTerminals.get(rh).contains(null)) {
					if (productionIndex.index < productionIndex.production.rhs.size() - 1) {
						TokenType rh2 = productionIndex.production.rhs.get(productionIndex.index + 1);
						for (TokenType t : this.firstTerminals.get(rh2)) {
							itemSet.links.put(t, new Action(new Reduction(rh, 0)));
						}
					} else {
						for (TokenType t : this.follows.get(productionIndex.production.lhs)) {
							itemSet.links.put(t, new Action(new Reduction(rh, 0)));
						}
					}
				}
			} else {
				for (TokenType t : this.follows.get(productionIndex.production.lhs)) {
					if (itemSet.links.containsKey(t)) {
						Action action = itemSet.links.get(t);
						if (action.type == ActionType.REDUCTION) {
							List<Reduction> list = new ArrayList();
							list.add(action.reduction);
							list.add(new Reduction(
								productionIndex.production.lhs,
								productionIndex.index
							));
							itemSet.links.put(t, new Action(list));
						} else if (action.type == ActionType.REDUCTIONS) {
							action.reductions.add(new Reduction(
								productionIndex.production.lhs,
								productionIndex.index
							));
						}
					} else {
						itemSet.links.put(t, new Action(new Reduction(
							productionIndex.production.lhs,
							productionIndex.index
						)));
					}
				}
			}
		}
		for (TokenType token : productionsByToken.keySet()) {
			Set<ProductionIndex> productionIndices = productionsByToken.get(token);
			if (!this.itemSets.containsKey(productionIndices)) {
				ItemSet childItemSet = new ItemSet(productionIndices);
				this.itemSets.put(productionIndices, childItemSet);
				generateItemSets(childItemSet);
			}
			itemSet.links.put(token, new Action(this.itemSets.get(productionIndices)));
		}
	}

	private void printFollows() {
		for (TokenType t : this.follows.keySet()) {
			System.out.print(t + " :");
			for (TokenType term : this.follows.get(t)) {
				System.out.print(" " + term + ",");
			}
			System.out.println();
		}
	}

	private void printItemSets() {
		List<ItemSet> values = new ArrayList(this.itemSets.values());
		Collections.sort(values);
		for (ItemSet itemSet : values) {
			System.out.println("ItemSet " + itemSet.id + ":");
			System.out.println("	Productions:");
			for (ProductionIndex productionIndex : itemSet.productionIndices) {
				int index = productionIndex.index;
				Production production = productionIndex.production;
				System.out.print("		(" + index + ") " + production.lhs + " =>");
				for (TokenType rh : production.rhs) {
					System.out.print(" " + rh);
				}
				System.out.println();
			}
			System.out.println("	Links:");
			for (TokenType token : itemSet.links.keySet()) {
				Action action = itemSet.links.get(token);
				System.out.print("		" + token + " -> ");
				switch (action.type) {
					case SUCCESS:
						System.out.print("SUCCESS");
						break;
					case ITEMSET:
						System.out.print(action.itemSet.id);
						break;
					case REDUCTION:
						System.out.print("Reduce: " + action.reduction.symbol + ", " + action.reduction.number);
						break;
				}
				System.out.println();
			}
			System.out.println();
		}
	}

	public ParseTreeNode parse(List<TerminalToken> tokens) throws InvalidSyntaxException {
		int tokensIndex = 0;
		Stack<ItemSet> stateStack = new Stack();
		stateStack.push(this.itemSetTable);
		Token token = tokens.get(0);
		List<ParseTreeNode> nodes = new ArrayList();
		ParseTreeNode node = new ParseTreeNode(token);
		while(tokensIndex < tokens.size()) {
			if (stateStack.peek().links.containsKey(token.getType()) || stateStack.peek().links.containsKey(null)) {
				Action action = null;
				if (stateStack.peek().links.containsKey(token.getType())) {
					action = stateStack.peek().links.get(token.getType());
				} else {
					action = stateStack.peek().links.get(null);
				}
				switch (action.type) {
					case SUCCESS:
						return node;
					case ITEMSET:
						nodes.add(node);
						stateStack.push(action.itemSet);
						tokensIndex++;
						token = tokens.get(tokensIndex);
						node = new ParseTreeNode(token);
						break;
					case REDUCTION: {
						for (int i = 0; i < action.reduction.number; i++) {
							stateStack.pop();
						}
						List<ParseTreeNode> currentChildren = new ArrayList(nodes.subList(
							nodes.size() - action.reduction.number,
							nodes.size()
						));
						nodes = new ArrayList(nodes.subList(0, nodes.size() - action.reduction.number));
						token = NonterminalToken.getToken(action.reduction.symbol);
						node = new ParseTreeNode(token, currentChildren);
						tokensIndex--;
						break;
					}
					case REDUCTIONS:
						Collections.sort(action.reductions, (r1, r2) -> {
							if (r1.number == r2.number) {
								if (r1.symbol == TokenType.PRIMARY_NO_NEW_ARRAY) {
									return 1;
								}
								if (r2.symbol == TokenType.PRIMARY_NO_NEW_ARRAY) {
									return -1;
								}
							}
							return r2.number - r1.number;
						});
						int numPopped = 0;
						for (Reduction reduction : action.reductions) {
							for (int i = 0; i < reduction.number - numPopped; i++) {
								stateStack.pop();
							}
							numPopped = reduction.number;
							if (stateStack.peek().links.containsKey(reduction.symbol)) {
								List<ParseTreeNode> currentChildren = new ArrayList(nodes.subList(
									nodes.size() - reduction.number,
									nodes.size()
								));
								nodes = new ArrayList(nodes.subList(0, nodes.size() - reduction.number));
								token = NonterminalToken.getToken(reduction.symbol);
								node = new ParseTreeNode(token, currentChildren);
								tokensIndex--;
								break;
							}
						}
						break;
				}
			} else {
				break;
			}
		}
		throw new InvalidSyntaxException("Parsing error at token '" + tokens.get(tokensIndex).getRawValue() + "'");
	}
}

class Production {
	public final int id;
	public final TokenType lhs;
	public final List<TokenType> rhs;

	public Production(int id, TokenType lhs, List<TokenType> rhs) {
		this.id = id;
		this.lhs = lhs;
		this.rhs = rhs;
	}
}

class ProductionIndex {
	public final Production production;
	public final int index;
	public ProductionIndex(Production production, int index) {
		this.production = production;
		this.index = index;
	}
	public boolean equals(Object o) {
		if (o instanceof ProductionIndex) {
			ProductionIndex other = (ProductionIndex)o;
			return other.production.equals(this.production) && other.index == this.index;
		}
		return false;
	}
	public int hashCode() {
		return this.production.hashCode() + index;
	}
	public String toString() {
		String s = "(" + this.index + ") " + this.production.lhs + "=>";
		for (TokenType t : production.rhs) {
			s += " " + t;
		}
		return s;
	}
}

class Reduction {
	public final TokenType symbol;
	public final int number;

	public Reduction(TokenType symbol, int number) {
		this.symbol = symbol;
		this.number = number;
	}

	public String toString() {
		return "reduce: (" + this.symbol + ", " + this.number + ")";
	}
}

class Entry {
	public final int productionID;
	public final int index;
	public final Map<TokenType, Action> actions;
	public final Map<TokenType, Action> epsilonMap;

	public Entry(int productionID, int index) {
		this.productionID = productionID;
		this.index = index;
		this.actions = new HashMap<TokenType, Action>();
		this.epsilonMap = new HashMap<TokenType, Action>();
	}
}

class Action {
	public final ActionType type;
	public final Reduction reduction;
	public final List<Reduction> reductions;
	public final ItemSet itemSet;

	public Action(Reduction reduction) {
		type = ActionType.REDUCTION;
		this.reduction = reduction;
		this.itemSet = null;
		this.reductions = null;
	}

	public Action(ItemSet itemSet) {
		type = ActionType.ITEMSET;
		this.itemSet = itemSet;
		this.reduction = null;
		this.reductions = null;
	}

	public Action(List<Reduction> reductions) {
		this.type = ActionType.REDUCTIONS;
		this.reductions = reductions;
		this.itemSet = null;
		this.reduction = null;
	}

	public Action() {
		this.type = ActionType.SUCCESS;
		this.itemSet = null;
		this.reduction = null;
		this.reductions = null;
	}

	public String toString() {
		switch (this.type) {
			case SUCCESS:
				return "SUCCESS";
			case REDUCTION:
				return this.reduction.toString();
			case ITEMSET:
				return "" + this.itemSet.id;
		}
		return super.toString();
	}
}

enum ActionType {
	SUCCESS, ITEMSET, REDUCTION, REDUCTIONS
}

class ItemSet implements Comparable<ItemSet> {
	public static int currentID;
	public final int id;
	public final Set<ProductionIndex> definingProductionIndices;
	public final Set<ProductionIndex> productionIndices;
	public final Map<TokenType, Action> links;


	public int compareTo(ItemSet itemSet) {
		return this.id - itemSet.id;
	}

	public ItemSet(Set<ProductionIndex> definingProductionIndices) {
		this.id = ItemSet.currentID++;
		this.links = new HashMap();
		this.definingProductionIndices = definingProductionIndices;
		this.productionIndices = new HashSet();
	}
}
