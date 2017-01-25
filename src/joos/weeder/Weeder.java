package joos.weeder;

import joos.commons.ParseTreeNode;
import joos.commons.TerminalToken;
import joos.commons.TokenType;
import joos.exceptions.InvalidSyntaxException;

import java.util.ArrayList;
import java.util.List;

public class Weeder {

	/**
	 * Given a parse tree looks for any last invalid cases.
	 * Throws an InvalidSyntaxException.
	 */
	public void weed(ParseTreeNode parseTree,String filename) throws InvalidSyntaxException {
		switch (parseTree.token.getType()){
			case INTERFACE_DECLARATION:
				String classname=((TerminalToken)parseTree.children.get(2).token).getRawValue();
				if(!filename.equals(classname+".java")){
					//throw new InvalidSyntaxException("A interface must be declared in a .java file with the same base name as the class/interface.");
				}
				break;
			case CLASS_DECLARATION:
				classname=((TerminalToken)parseTree.children.get(2).token).getRawValue();
				if(!filename.equals(classname+".java")){
					//throw new InvalidSyntaxException("A class must be declared in a .java file with the same base name as the class/interface.");
				}
				parseTree=parseTree.children.get(0);
				if(!parseTree.children.isEmpty()) {
					ParseTreeNode current = parseTree.children.get(0);
					List<TokenType> modifiers = new ArrayList();
					while (current.children.size()>1) {
						modifiers.add(current.children.get(1).children.get(0).token.getType());
						current = current.children.get(0);
					}
					modifiers.add(current.children.get(0).token.getType());
					if(modifiers.contains(TokenType.ABSTRACT)&&modifiers.contains(TokenType.FINAL)){
						throw new InvalidSyntaxException("A class cannot be both abstract and final");
					}
				}

				break;
			case CLASS_BODY_DECLARATIONS:
				ParseTreeNode current=parseTree.children.get(0);
				List<TokenType> declaration = new ArrayList();
				while (current.token.getType() !=TokenType.CLASS_BODY_DECLARATION) {
					declaration.add(current.children.get(1).children.get(0).token.getType());
					current = current.children.get(0);
				}
				declaration.add(current.children.get(0).token.getType());
				if(!declaration.contains(TokenType.CONSTRUCTOR_DECLARATION)){
					throw new InvalidSyntaxException("Every class must contain at least one explicit constructor");
				}
				break;
			case METHOD_DECLARATION:
				ParseTreeNode modifieroptional=parseTree.children.get(0);
				if(!modifieroptional.children.isEmpty()) {
					current = modifieroptional.children.get(0);
					List<TokenType> modifiers = new ArrayList();
					while (current.children.size()>1) {
						modifiers.add(current.children.get(1).children.get(0).token.getType());
						current = current.children.get(0);
					}
					modifiers.add(current.children.get(0).token.getType());
					if(modifiers.contains(TokenType.STATIC)&&modifiers.contains(TokenType.FINAL)){
						throw new InvalidSyntaxException("A static method cannot be final");
					}
					if(modifiers.contains(TokenType.ABSTRACT)&&(modifiers.contains(TokenType.STATIC)||modifiers.contains(TokenType.FINAL))){
						throw new InvalidSyntaxException("An abstract method cannot be static or final.");
					}
					if(modifiers.contains(TokenType.ABSTRACT)||modifiers.contains(TokenType.NATIVE)){
						if(parseTree.children.get(1).children.get(0).token.getType()!=TokenType.SEMICOLON){
							throw new InvalidSyntaxException("A method has a body if and only if it is neither abstract nor native.");
						}
					}
					else{
						if (parseTree.children.get(1).children.get(0).token.getType()!=TokenType.SEMICOLON){
							throw new InvalidSyntaxException("A method has a body if and only if it is neither abstract nor native.");
						}
					}
				}
				break;
			case ABSTRACT_METHOD_DECLARATION:
				ParseTreeNode methodheader=parseTree.children.get(0);
				modifieroptional =methodheader.children.get(0);
				if(!modifieroptional.children.isEmpty()) {
					current = modifieroptional.children.get(0);
					List<TokenType> modifiers = new ArrayList();
					while (current.children.size()>1) {
						modifiers.add(current.children.get(1).children.get(0).token.getType());
						current = current.children.get(0);
					}
					modifiers.add(current.children.get(0).token.getType());
					if (modifiers.contains(TokenType.STATIC) || modifiers.contains(TokenType.FINAL) || modifiers.contains(TokenType.NATIVE)) {
						throw new InvalidSyntaxException("An interface method cannot be static, final, or native.");
					}
				}
				break;
			case FIELD_DECLARATION:
				parseTree=parseTree.children.get(0);
				if(!parseTree.children.isEmpty()) {
					current = parseTree.children.get(0);
					List<TokenType> modifiers = new ArrayList();
					while (current.children.size()>1) {
						modifiers.add(current.children.get(1).children.get(0).token.getType());
						current = current.children.get(0);
					}
					modifiers.add(current.children.get(0).token.getType());
					if(modifiers.contains(TokenType.FINAL)){
						throw new InvalidSyntaxException("No field can be final");
					}
				}
				break;
			default:

		}
		if(parseTree.children!=null) {
			for (ParseTreeNode child : parseTree.children) {
				weed(child,filename);
			}
		}
	}
}