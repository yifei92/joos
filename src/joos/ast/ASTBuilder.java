package joos.ast;

import joos.commons.ParseTreeNode;
import joos.commons.ASTTreeNode;
import joos.commons.TerminalToken;
import joos.commons.TokenType;
import joos.exceptions.InvalidSyntaxException;

import java.util.ArrayList;
import java.util.List;

public class ASTBuilder {

	/**
	 * Given a parse tree converts it into ast.
	 */
	public ParseTreeNode convert(ParseTreeNode parseTree) throws InvalidSyntaxException {
		switch (parseTree.token.getType()) {
			case MODIFIERS:
				if (parseTree.children.size() > 1) {
					ParseTreeNode current = parseTree.children.get(0);
					while (current.children.size() > 1) {
						parseTree.children.add(0, current.children.get(1));
						current = current.children.get(0);
					}
					parseTree.children.add(0, current.children.get(0));
				}
				break;
			case FORMAL_PARAMETER_LIST:
				if (parseTree.children.size() > 1) {
					ParseTreeNode current = parseTree.children.get(0);
					while (current.children.size() > 1) {
						parseTree.children.add(0, current.children.get(2));
						current = current.children.get(0);
					}
					parseTree.children.add(0, current.children.get(0));
				}
				break;
			case IMPORT_DECLARATIONS:
				if (parseTree.children.size() > 1) {
					ParseTreeNode current = parseTree.children.get(0);
					while (current.children.size() > 1) {
						parseTree.children.add(0, current.children.get(1));
						current = current.children.get(0);
					}
					parseTree.children.add(0, current.children.get(0));
				}
				break;
			case INTERFACE_TYPE_LIST:
				if (parseTree.children.size() > 1) {
					ParseTreeNode current = parseTree.children.get(0);
					while (current.children.size() > 1) {
						parseTree.children.add(0, current.children.get(2));
						current = current.children.get(0);
					}
					parseTree.children.add(0, current.children.get(0));
				}
				break;
			case CLASS_BODY_DECLARATIONS:
				if (parseTree.children.size() > 1) {
					ParseTreeNode current = parseTree.children.get(0);
					while (current.children.size() > 1) {
						parseTree.children.add(0, current.children.get(1));
						current = current.children.get(0);
					}
					parseTree.children.add(0, current.children.get(0));
				}
				break;
			case CLASS_TYPE_LIST:
				if (parseTree.children.size() > 1) {
					ParseTreeNode current = parseTree.children.get(0);
					while (current.children.size() > 1) {
						parseTree.children.add(0, current.children.get(2));
						current = current.children.get(0);
					}
					parseTree.children.add(0, current.children.get(0));
				}
				break;
			case INTERFACE_MEMBER_DECLARATIONS:
				if (parseTree.children.size() > 1) {
					ParseTreeNode current = parseTree.children.get(0);
					while (current.children.size() > 1) {
						parseTree.children.add(0, current.children.get(1));
						current = current.children.get(0);
					}
					parseTree.children.add(0, current.children.get(0));
				}
				break;
			case VARIABLE_INITIALIZERS:
				if (parseTree.children.size() > 1) {
					ParseTreeNode current = parseTree.children.get(0);
					while (current.children.size() > 1) {
						parseTree.children.add(0, current.children.get(2));
						current = current.children.get(0);
					}
					parseTree.children.add(0, current.children.get(0));
				}
				break;
			case BLOCK_STATEMENTS:
				if (parseTree.children.size() > 1) {
					ParseTreeNode current = parseTree.children.get(0);
					while (current.children.size() > 1) {
						parseTree.children.add(0, current.children.get(1));
						current = current.children.get(0);

					}
					parseTree.children.add(0, current.children.get(0));
				}
				break;
			case STATEMENT_EXPRESSION_LIST:
				if (parseTree.children.size() > 1) {
					ParseTreeNode current = parseTree.children.get(0);
					while (current.children.size() > 1) {
						parseTree.children.add(0, current.children.get(2));
						current = current.children.get(0);
					}
					parseTree.children.add(0, current.children.get(0));
				}
				break;
			default:
		}
		if (parseTree.children != null) {
			for (ParseTreeNode child : parseTree.children) {
				convert(child);
			}
		}
		return parseTree;
	}
}