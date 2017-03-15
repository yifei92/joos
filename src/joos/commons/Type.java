package joos.commons;

import java.util.Arrays;
import java.util.List;
import joos.commons.ParseTreeNode;
import joos.environment.Environment;
import java.util.Set;

/**
 * Created by yifei on 09/03/17.
 */
public class Type {
	public enum TypeType { PRIMITIVE, TYPE, OBJECT, ARRAY }
	public TypeType type;
	public String name;
	public Environment environment;
	public Type subType;
	public ParseTreeNode decl;
	public Set<TokenType> modifiers;
	public Type(String s){
		name=s;
	}
	private Type(TypeType type, String name, Environment environment, Type subType, ParseTreeNode decl) {
		this.type = type;
		this.name = name;
		this.environment = environment;
		this.subType = subType;
		this.decl = decl;
	}
	public Boolean equals(String s){
		return this.name.equals(s);
	}

	public Boolean equals(Type t){
		return this.name.equals(t.name);
	}

	public static Type newPrimitive(String name, ParseTreeNode decl) {
		return new Type(TypeType.PRIMITIVE, name, null, null, decl);
	}

	public static Type newType(String name, Type subType, ParseTreeNode decl) {
		return new Type(TypeType.TYPE, name, null, subType, decl);
	}

	public static Type newObject(String name, Environment environment, ParseTreeNode decl) {
		return new Type(TypeType.OBJECT, name, environment, null, decl);
	}

	public static Type newArray(String name, Type subType, ParseTreeNode decl) {
		return new Type(TypeType.ARRAY, name, null, subType, decl);
	}
}
