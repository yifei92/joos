package joos.commons;

public enum Token {
	EXTENDS("extends"),
	IMPLEMENTS("implements"),
	STATIC("static"),
	IMPORT("import"),
	PACKAGE("package"),
	INTERFACE("interface"),
	RETURN("return"),
	NEW("new"),
	CLASS("class"),
	ABSTRACT("abstract"),
	VOID("void"),
	IF("if"),
	ELSE("else"),
	WHILE("while"),
	FOR("for"),
	ASSIGN("="),
	OPEN_BR("("),
	CLOSE_BR(")"),
	OPEN_CBR("{"),
	CLOSE_CBR("}"),
	OPEN_SBR("["),
	CLOSE_SBR("]"),
	OPEN_COMMENT("/*"),
	CLOSE_COMMENT("*/"),
	OPEN_COMMENT_DOUBLE("/**"),
	CLOSE_COMMENT_DOUBLE("**/"),
	COMMENT("//"),
	FINAL("final"),
	NATIVE("native"),
	PUBLIC("public"),
	PRIVATE("private"),
	PROTECTED("protected"),
	IDENTIFIER(""),
	INTEGER_LITERAL(""),
	BOOLEAN_LITERAL(""),
	STRING_LITERAL(""),
	CHAR_LITERAL(""),
	BOOLEAN("boolean"),
	INT("int"),
	CHAR("char"),
	BYTE("byte"),
	SHORT("short"),
	NULL("null"),
	STAR("*"),
	OP_PLUS("+"),
	OP_MINUS("+"),
	OP_DIV("/"),
	OP_REMAINDER("%"),
	COMP_LESS_THAN("<"),
	COMP_GREATER_THAN(">"),
	COMP_LESS_THAN_EQ("<="),
	COMP_GREATER_THAN_EQ(">="),
	COMP_EQ("=="),
	COMP_NOT_EQ("!="),
	BOOL_OP_AND("&&"),
	BOOL_OP_OR("||"),
	BITWISE_AND("&"),
	BITWISE_OR("|"),
	BITWISE_NOT("!"),
	THIS("this"),
	SUPER("super"),
	INSTANCEOF("instanceof"),
	CHARACTER_ESCAPE("\\"),
	DOT("."),
	SEMICOLON(";");

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