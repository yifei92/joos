package joos.commons;

import joos.commons.Token;
import joos.commons.TerminalToken;
import joos.environment.Environment;
import joos.environment.EnvironmentUtils;
import java.util.List;
import java.util.ArrayList;

public class ParseTreeNode {
	public final List<ParseTreeNode> children;
	public final Token token;
	public Type type;
	public String name;
	public boolean primitive = false;
	public boolean in;
	public boolean out;

	public ParseTreeNode(Token token) {
		this.token = token;
		this.children = null;
	}

	public ParseTreeNode(Token token, List<ParseTreeNode> children) {
		this.token = token;
		this.children = children;
	}

	/**
	 * Returns true if the first terminal node that is a child of this node is before
	 * the first terminal node of the other node
	 */
	public boolean isBefore(ParseTreeNode other) {
		ParseTreeNode thisTerminalNode = getFirstTerminalNode();
		ParseTreeNode otherTerminalNode = other.getFirstTerminalNode();
		return thisTerminalNode.token.getIndex() < otherTerminalNode.token.getIndex();
	}

	/**
	 * Returns true of the given node other is a descendant of this node
	 */
	public boolean contains(ParseTreeNode other) {
		if (this == other) {
			return true;
		}
		boolean contains = false;
		if (children != null) {
			for(ParseTreeNode child : children) {
				contains = child.contains(other);
				if(contains) {
					return true;
				}
			}
		}
		return false;
	}

	public ParseTreeNode getFirstTerminalNode() {
		if (token instanceof TerminalToken) {
			return this;
		}
		if (children != null) {
			ParseTreeNode firstTerminalNode = null;
			for (ParseTreeNode child: children) {
				firstTerminalNode = child.getFirstTerminalNode();
				if (firstTerminalNode != null) {
					return firstTerminalNode;
				} 
			}
		}
		return null;
	}

	public void print() {
		this.print(0);
	}

	public void printWithEnvironments(Environment rootEnvironment, ParseTreeNode rootParseTreeNode) {
		this.printWithEnvironments(rootEnvironment, rootParseTreeNode, 0);
	}

	public void printWithEnvironments(Environment rootEnvironment, ParseTreeNode rootParseTreeNode, int depth) {
		for (int i = 0; i < depth; i++) {
			System.out.print("  ");
		}
		System.out.print(this.token.getType());
		Environment thisEnvironment = EnvironmentUtils.findEvironment(rootEnvironment, rootParseTreeNode, this);
		String environmentString = " E: ";
		if (thisEnvironment != null) {
			environmentString += EnvironmentUtils.getEnvironmentType(thisEnvironment).toString() + " ";
			if (thisEnvironment.mName != null) {
				environmentString += thisEnvironment.mName;
			}
		}
		System.out.print(environmentString);
		System.out.println();
		if (this.children != null) {
			for (ParseTreeNode child : this.children) {
				child.printWithEnvironments(rootEnvironment, rootParseTreeNode, depth + 1);
			}
		}
	}

	private void print(int depth) {
		for (int i = 0; i < depth; i++) {
			System.out.print("  ");
		}
		System.out.print(this.token.getType());
		System.out.print(" ");
		if (this.token instanceof TerminalToken) {
			System.out.print(((TerminalToken)this.token).getIndex() + " ");
		}
		if (this.token.getType() == TokenType.IDENTIFIER) {
			System.out.print(((TerminalToken)this.token).getRawValue());
		}
		if (this.type != null) {
			System.out.print(
				"T:"
				+this.type.type+" "
				+this.type.name+" ");
			if(this.type.environment != null && this.type.environment.mName != null) {
				System.out.print(this.type.environment.mName+" ");
			} else {
				System.out.print(" <envname>");
			}
			if(this.type.modifiers != null) {
				for(TokenType mod : this.type.modifiers) {
					System.out.print(" " + mod);
				}
			}
		}
		System.out.println();
		if (this.children != null) {
			for (ParseTreeNode child : this.children) {
				child.print(depth + 1);
			}
		}
	}

	/*
	 * Iterates over this and its children for the first node with  type set and returns that
	 */
	public Type getFirstType() {
		if (this.type != null) {
			return this.type;
		}
		if (children != null) {
			Type childType = null;
			for (ParseTreeNode child : children) {
				childType = child.getFirstType();
				if (childType != null) {
					return childType;
				}
			}
		}
		return null;
	}

	//helper funtion
	static public String getfullnamefromnamenode(ParseTreeNode namenode){
		String name="";
		for(ParseTreeNode child : namenode.children){
			name+= ((TerminalToken)child.token).getRawValue();
		}
		return name;
	}
}
