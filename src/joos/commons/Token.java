package joos.commons;

public enum Token {
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
	// Symbols
	ASSIGN("="),
	BITWISE_AND("&"),
	BITWISE_NOT("!"),
	BITWISE_OR("|"),
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
	OPEN_BR("("),
	OPEN_CBR("{"),
	OPEN_COMMENT("/*"),
	OPEN_COMMENT_DOUBLE("/**"),
	OPEN_SBR("["),
	SEMICOLON(";"),
	STAR("*"),
	// Literals
	IDENTIFIER(""),
	BOOLEAN_LITERAL_TRUE("true"),
	BOOLEAN_LITERAL_FALSE("false"),
	SINGLE_QUOTE("'"),
	CHAR_LITERAL(""), // consists of a single char surrounded by single quotes
	INTEGER_LITERAL(""),
	DOUBLE_QUOTE("\""),
	STRING_LITERAL(""); // consists of a string of characters surrounded by double quotes

	private String mRawValue;

	private Token (String rawValue) {
		mRawValue = rawValue;
	}

	public String getRawValue() {
		return mRawValue;
	}

	public void setRawValue(String rawValue) {
		mRawValue = rawValue;
	}
}