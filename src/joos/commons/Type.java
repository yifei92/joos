package joos.commons;

import joos.commons.ParseTreeNode;

/**
 * Created by yifei on 09/03/17.
 */
public class Type {
	public String name;
	public ParseTreeNode decl;
	public Type(String s){
		name=s;
	}
	public Boolean equals(String s){
		return this.name.equals(s);
	}

	public Boolean equals(Type t){
		return this.name.equals(t.name);
	}


}
