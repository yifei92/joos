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
	public ParseTreeNode convert(ParseTreeNode parseTree)  {
		switch (parseTree.token.getType()) {
			case CONDITIONAL_OR_EXPRESSION:
			case CONDITIONAL_AND_EXPRESSION:
			case INCLUSIVE_OR_EXPRESSION:
			case EXCLUSIVE_OR_EXPRESSION:
			case AND_EXPRESSION:
			case ADDITIVE_EXPRESSION:
			case MULTIPLICATIVE_EXPRESSION:

				if (parseTree.children.size() > 1) {
					ParseTreeNode current = parseTree.children.get(0);
					parseTree.children.remove(0);
					while (current.children!=null&&current.children.size() > 1) {
						parseTree.children.add(0, current.children.get(1));
						parseTree.children.add(0, current.children.get(2));
						current = current.children.get(0);
					}
					parseTree.children.add(0, current.children.get(0));
				}

			case EXPRESSION:
			case ASSIGNMENT_EXPRESSION:
			case CONDITIONAL_EXPRESSION:
			case EQUALITY_EXPRESSION:
			case RELATIONAL_EXPRESSION:
			case SHIFT_EXPRESSION:
			case UNARY_EXPRESSION:
			case UNARY_EXPRESSION_NOT_PLUS_MINUS:
			//case POSTFIX_EXPRESSION:
				if (parseTree.children.size() == 1) {
					ParseTreeNode current = parseTree.children.get(0);
					while ((current.children.size() == 1) && current.children.get(0).children!=null) {
						current=current.children.get(0);
					}
					parseTree.children.clear();
					parseTree.children.add(current);
				}
				break;
			case FIELD_DECLARATION:
			case METHOD_HEADER:
			case CLASS_DECLARATION:
				parseTree.children.set(0,parseTree.children.get(0).children.get(0));
				break;
			case MODIFIERS:
				if (parseTree.children.size() > 1) {
					ParseTreeNode current = parseTree.children.get(0);
					parseTree.children.remove(0);
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
					parseTree.children.remove(0);
					while (current.children.size() > 1) {
						parseTree.children.add(0, current.children.get(1));
						parseTree.children.add(0, current.children.get(2));
						current = current.children.get(0);
					}
					parseTree.children.add(0, current.children.get(0));
				}
				break;
			case IMPORT_DECLARATIONS:
				if (parseTree.children.size() > 1) {
					ParseTreeNode current = parseTree.children.get(0);
					parseTree.children.remove(0);
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
					parseTree.children.remove(0);
					while (current.children.size() > 1) {
						parseTree.children.add(0, current.children.get(1));
						parseTree.children.add(0, current.children.get(2));
						current = current.children.get(0);
					}
					parseTree.children.add(0, current.children.get(0));
				}
				break;
			case CLASS_BODY_DECLARATIONS:
				if (parseTree.children.size() > 1) {
					ParseTreeNode current = parseTree.children.get(0);
					parseTree.children.remove(0);
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
					parseTree.children.remove(0);
					while (current.children.size() > 1) {
						parseTree.children.add(0, current.children.get(1));
						parseTree.children.add(0, current.children.get(2));
						current = current.children.get(0);
					}
					parseTree.children.add(0, current.children.get(0));
				}
				break;
			case INTERFACE_MEMBER_DECLARATIONS:
				if (parseTree.children.size() > 1) {
					ParseTreeNode current = parseTree.children.get(0);
					parseTree.children.remove(0);
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
					parseTree.children.remove(0);
					while (current.children.size() > 1) {
						parseTree.children.add(0, current.children.get(1));
						parseTree.children.add(0, current.children.get(2));
						current = current.children.get(0);
					}
					parseTree.children.add(0, current.children.get(0));
				}
				break;
			case BLOCK_STATEMENTS:
				if (parseTree.children.size() > 1) {
					ParseTreeNode current = parseTree.children.get(0);
					parseTree.children.remove(0);
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
					parseTree.children.remove(0);
					while (current.children.size() > 1) {
						parseTree.children.add(0, current.children.get(1));
						parseTree.children.add(0, current.children.get(2));
						current = current.children.get(0);
					}
					parseTree.children.add(0, current.children.get(0));
				}
				break;
			case NAME:
				if(parseTree.children.get(0).token.getType()==TokenType.SIMPLE_NAME){
					parseTree.children.set(0,parseTree.children.get(0).children.get(0));
				}
				else{
					ParseTreeNode current=parseTree.children.get(0);
					parseTree.children.clear();
					parseTree.children.add(0,current.children.get(2));
					parseTree.children.add(0,current.children.get(1));
					while(current.children.get(0).children.get(0).token.getType()!=TokenType.SIMPLE_NAME){
						parseTree.children.add(0,current.children.get(2));
						parseTree.children.add(0,current.children.get(1));
						current=current.children.get(0);
					}
					parseTree.children.add(0,current.children.get(0).children.get(0));
				}
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