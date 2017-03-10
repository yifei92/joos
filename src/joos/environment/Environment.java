package joos.environment;

import joos.commons.ParseTreeNode;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import joos.commons.Type;
import joos.commons.TokenType;

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
