package joos.commons;

import java.util.List;
import java.util.Arrays;
import joos.commons.TokenType;

public class Token {
	public final TokenType mType;
	public String mRawValue;

	public Token(TokenType type, String rawValue) {
		mType = type;
		mRawValue = rawValue;
	}

	public void setRawValue(String value) {
		mRawValue = value;
	}

	public String getRawValue() {
		return mRawValue;
	}

	public static List<Token> getTokens() {
		List<Token> tokens = Arrays.asList(
			// Identifier
			new Token(TokenType.IDENTIFIER, ""),
			// Keywords
			new Token(TokenType.ABSTRACT, "abstract"),
			new Token(TokenType.BOOLEAN, "boolean"),
			new Token(TokenType.BYTE, "byte"),
			new Token(TokenType.CHAR, "char"),
			new Token(TokenType.CLASS, "class"),
			new Token(TokenType.ELSE, "else"),
			new Token(TokenType.EXTENDS, "extends"),
			new Token(TokenType.FINAL, "final"),
			new Token(TokenType.FOR, "for"),
			new Token(TokenType.IF, "if"),
			new Token(TokenType.IMPLEMENTS, "implements"),
			new Token(TokenType.IMPORT, "import"),
			new Token(TokenType.INSTANCEOF, "instanceof"),
			new Token(TokenType.INT, "int"),
			new Token(TokenType.INTERFACE, "interface"),
			new Token(TokenType.NATIVE, "native"),
			new Token(TokenType.NEW, "new"),
			new Token(TokenType.NULL, "null"),
			new Token(TokenType.PACKAGE, "package"),
			new Token(TokenType.PRIVATE, "private"),
			new Token(TokenType.PROTECTED, "protected"),
			new Token(TokenType.PUBLIC, "public"),
			new Token(TokenType.RETURN, "return"),
			new Token(TokenType.SHORT, "short"),
			new Token(TokenType.STATIC, "static"),
			new Token(TokenType.SUPER, "super"),
			new Token(TokenType.THIS, "this"),
			new Token(TokenType.VOID, "void"),
			new Token(TokenType.WHILE, "while"),
			// Symbols
			new Token(TokenType.ASSIGN, "="),
			new Token(TokenType.BITWISE_AND, "&"),
			new Token(TokenType.BITWISE_NOT, "!"),
			new Token(TokenType.BITWISE_OR, "|"),
			new Token(TokenType.BOOL_OP_AND, "&&"),
			new Token(TokenType.BOOL_OP_OR, "||"),
			new Token(TokenType.CHARACTER_ESCAPE, "\\"),
			new Token(TokenType.CLOSE_BR, ")"),
			new Token(TokenType.CLOSE_CBR, "}"),
			new Token(TokenType.CLOSE_COMMENT, "*/"),
			new Token(TokenType.CLOSE_COMMENT_DOUBLE, "**/"),
			new Token(TokenType.CLOSE_SBR, "]"),
			new Token(TokenType.COMMENT, "//"),
			new Token(TokenType.COMP_EQ, "=="),
			new Token(TokenType.COMP_GREATER_THAN, ">"),
			new Token(TokenType.COMP_GREATER_THAN_EQ, ">="),
			new Token(TokenType.COMP_LESS_THAN, "<"),
			new Token(TokenType.COMP_LESS_THAN_EQ, "<="),
			new Token(TokenType.COMP_NOT_EQ, "!="),
			new Token(TokenType.DOT, "."),
			new Token(TokenType.OP_DIV, "/"),
			new Token(TokenType.OP_MINUS, "-"),
			new Token(TokenType.OP_PLUS, "+"),
			new Token(TokenType.OP_REMAINDER, "%"),
			new Token(TokenType.OPEN_BR, "("),
			new Token(TokenType.OPEN_CBR, "{"),
			new Token(TokenType.OPEN_COMMENT, "/*"),
			new Token(TokenType.OPEN_COMMENT_DOUBLE, "/**"),
			new Token(TokenType.OPEN_SBR, "["),
			new Token(TokenType.SEMICOLON, ";"),
			new Token(TokenType.STAR, "*"),
			// Literals
			new Token(TokenType.BOOLEAN_LITERAL_TRUE, "true"),
			new Token(TokenType.BOOLEAN_LITERAL_FALSE, "false"),
			new Token(TokenType.SINGLE_QUOTE, "'"),
			new Token(TokenType.CHAR_LITERAL, ""), // consists of a single char surrounded by single quotes
			new Token(TokenType.INTEGER_LITERAL, ""),
			new Token(TokenType.DOUBLE_QUOTE, "\""),
			new Token(TokenType.STRING_LITERAL, "") // consists of a string of characters surrounded by double quotes
			);
		return tokens;
	}
}