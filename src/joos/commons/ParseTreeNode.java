package joos.commons;

import joos.commons.Token;
import java.util.List;
import java.util.ArrayList;

public class ParseTreeNode {
	public final List<ParseTreeNode> children;
	public final Token token;

	public ParseTreeNode(Token token) {
		this.token = token;
		this.children = null;
	}

	public ParseTreeNode(Token token, List<ParseTreeNode> children) {
		this.token = token;
		this.children = children;
	}

	public void print() {
		this.print(0);
	}

	private void print(int depth) {
		for (int i = 0; i < depth; i++) {
			System.out.print("  ");
		}
		System.out.print(this.token.getType());
		System.out.println();
		if (this.children != null) {
			for (ParseTreeNode child : this.children) {
				child.print(depth + 1);
			}
		}
	}
}
