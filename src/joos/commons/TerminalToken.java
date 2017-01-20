package joos.commons;

import joos.commons.Token;

public enum TerminalToken implements Token {
	// Full words
	ABSTRACT("abstract"),
	BOOLEAN("boolean"),
	BYTE("byte"),
	CHAR("char"),
	CLASS("class"),
	ELSE("else"),
	EXTENDS("extends"),
	FINAL("final"),
	FOR("for"),
	IF("if"),
	IMPLEMENTS("implements"),
	IMPORT("import"),
	INSTANCEOF("instanceof"),
	INT("int"),
	INTERFACE("interface"),
	NATIVE("native"),
	NEW("new"),
	NULL("null"),
	PACKAGE("package"),
	PRIVATE("private"),
	PROTECTED("protected"),
	PUBLIC("public"),
	RETURN("return"),
	SHORT("short"),
	STATIC("static"),
	SUPER("super"),
	THIS("this"),
	VOID("void"),
	WHILE("while"),
	TRY("try"),
	DO("do"),
	THROW("throw"),
	THROWS("throws"),
	SYNCHRONIZED("synchronized"),
	CATCH("catch"),
	FINALLY("finally"),
	BREAK("break"),
	CONTINUE("continue"),
	CASE("case"),
	SWITCH("switch"),
	DEFAULT("default"),
	TRANSIENT("transient"),
	VOLATILE("volatile"),
	FLOAT("float"),
	DOUBLE("double"),
	LONG("long"),
	// Symbols
	ASSIGN("="),
	ASSIGN_INCREMENT("++"),
	ASSIGN_DECREMENT("--"),
	ASSIGN_PLUS("+="),
	ASSIGN_MINUS("-="),
	ASSIGN_STAR("*="),
	ASSIGN_DIVIDE("/="),
	ASSIGN_REMAINDER("%="),
	ASSIGN_LEFT_SHIFT("<<="),
	ASSIGN_RIGHT_SHIFT(">>="),
	ASSIGN_UNSIGNED_LEFT_SHIFT(">>>="),
	ASSIGN_BITWISE_AND("&="),
	ASSIGN_BITWISE_OR("|="),
	ASSIGN_BITWISE_XOR("^="),
	BITWISE_AND("&"),
	BITWISE_NOT("~"),
	BITWISE_OR("|"),
	BITWISE_XOR("^"),
	BOOL_OP_NOT("!"),
	BOOL_OP_AND("&&"),
	BOOL_OP_OR("||"),
	CHARACTER_ESCAPE("\\"),
	CLOSE_BR(")"),
	CLOSE_CBR("}"),
	CLOSE_COMMENT("*/"),
	CLOSE_COMMENT_DOUBLE("**/"),
	CLOSE_SBR("]"),
	COMMENT("//"),
	COMP_EQ("=="),
	COMP_GREATER_THAN(">"),
	COMP_GREATER_THAN_EQ(">="),
	COMP_LESS_THAN("<"),
	COMP_LESS_THAN_EQ("<="),
	COMP_NOT_EQ("!="),
	DOT("."),
	OP_DIV("/"),
	OP_MINUS("+"),
	OP_PLUS("+"),
	OP_REMAINDER("%"),
	OP_LEFT_SHIFT("<<"),
	OP_RIGHT_SHIFT(">>"),
	OP_UNSIGNED_RIGHT_SHIFT(">>>"),
	OPEN_BR("("),
	OPEN_CBR("{"),
	OPEN_COMMENT("/*"),
	OPEN_COMMENT_DOUBLE("/**"),
	OPEN_SBR("["),
	SEMICOLON(";"),
	COLON(":"),
	COMMA(","),
	QUESTION_MARK("?"),
	STAR("*"),
	// Literals
	IDENTIFIER(""),
	NULL_LITERAL("null"),
	BOOLEAN_LITERAL_TRUE("true"),
	BOOLEAN_LITERAL_FALSE("false"),
	SINGLE_QUOTE("'"),
	CHAR_LITERAL(""), // consists of a single char surrounded by single quotes
	INTEGER_LITERAL(""),
	FLOATING_POINT_LITERAL(""),
	DOUBLE_QUOTE("\""),
	STRING_LITERAL(""), // consists of a string of characters surrounded by double quotes
	EOF("");

	private String mRawValue;

	private TerminalToken (String rawValue) {
		mRawValue = rawValue;
	}

	public String getRawValue() {
		return mRawValue;
	}

	public void setRawValue(String rawValue) {
		mRawValue = rawValue;
	}
}
