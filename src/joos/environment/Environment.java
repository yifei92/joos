package joos.environment;

import joos.commons.ParseTreeNode;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;;
import java.util.HashMap;

public class Environment {

	// A reference to the parent environment
	public final Environment mParent;
	// A reference to the ast node that is this environment
	public final ParseTreeNode mScope;

	// A reference to the names declared in this environment.
	public Map<String, ParseTreeNode> mNames; 
	// A reference to the child environments of this environment
	public List<Environment> mChildrenEnvironments;

	public Environment(Environment parent, ParseTreeNode scope) {
		mParent = parent;
		mScope = scope;
		mNames = new HashMap<>();
		mChildrenEnvironments = new ArrayList<>();
	}

	/**
	 * Looks in this environment for the given name. 
	 * If the name does not exist in the current environment we recursively search all parent environments.
	 * If no name is found in this environment or the parent environments null is returned.
	 */
	public ParseTreeNode containsName(String name) {
		ParseTreeNode nameNode = mNames != null ? mNames.get(name) : null;
		if (nameNode == null && mParent != null) {
			nameNode = mParent.containsName(name);
		}
		return nameNode;
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
			System.out.print("S: " + mScope.token.getType() + " ");
		}
		
		for (String name : mNames.keySet()) {
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