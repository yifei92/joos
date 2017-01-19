package joos.commons;

import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import joos.commons.TokenType;

public class Token {
	public final TokenType mType;
	public String mRawValue;

	private Token(TokenType type, String rawValue) {
		mType = type;
		mRawValue = rawValue;
	}

	public void setRawValue(String value) {
		mRawValue = value;
	}

	public String getRawValue() {
		return mRawValue;
	}

	private static Map<TokenType, Token> mTokens = null;

	public static Token getToken(TokenType tokenType) {
		if (mTokens == null) {
			mTokens = new HashMap<TokenType,Token>();
			// Identifier
			mTokens.put(TokenType.IDENTIFIER, new Token(TokenType.IDENTIFIER, ""));
			// Keywords
			mTokens.put(TokenType.ABSTRACT , new Token(TokenType.ABSTRACT, "abstract"));
			mTokens.put(TokenType.BOOLEAN , new Token(TokenType.BOOLEAN, "boolean"));
			mTokens.put(TokenType.BYTE , new Token(TokenType.BYTE, "byte"));
			mTokens.put(TokenType.CHAR , new Token(TokenType.CHAR, "char"));
			mTokens.put(TokenType.CLASS , new Token(TokenType.CLASS, "class"));
			mTokens.put(TokenType.ELSE , new Token(TokenType.ELSE, "else"));
			mTokens.put(TokenType.EXTENDS , new Token(TokenType.EXTENDS, "extends"));
			mTokens.put(TokenType.FINAL , new Token(TokenType.FINAL, "final"));
			mTokens.put(TokenType.FOR , new Token(TokenType.FOR, "for"));
			mTokens.put(TokenType.IF , new Token(TokenType.IF, "if"));
			mTokens.put(TokenType.IMPLEMENTS , new Token(TokenType.IMPLEMENTS, "implements"));
			mTokens.put(TokenType.IMPORT , new Token(TokenType.IMPORT, "import"));
			mTokens.put(TokenType.INSTANCEOF , new Token(TokenType.INSTANCEOF, "instanceof"));
			mTokens.put(TokenType.INT , new Token(TokenType.INT, "int"));
			mTokens.put(TokenType.INTERFACE , new Token(TokenType.INTERFACE, "interface"));
			mTokens.put(TokenType.NATIVE , new Token(TokenType.NATIVE, "native"));
			mTokens.put(TokenType.NEW , new Token(TokenType.NEW, "new"));
			mTokens.put(TokenType.NULL , new Token(TokenType.NULL, "null"));
			mTokens.put(TokenType.PACKAGE , new Token(TokenType.PACKAGE, "package"));
			mTokens.put(TokenType.PRIVATE , new Token(TokenType.PRIVATE, "private"));
			mTokens.put(TokenType.PROTECTED , new Token(TokenType.PROTECTED, "protected"));
			mTokens.put(TokenType.PUBLIC , new Token(TokenType.PUBLIC, "public"));
			mTokens.put(TokenType.RETURN , new Token(TokenType.RETURN, "return"));
			mTokens.put(TokenType.SHORT , new Token(TokenType.SHORT, "short"));
			mTokens.put(TokenType.STATIC , new Token(TokenType.STATIC, "static"));
			mTokens.put(TokenType.SUPER , new Token(TokenType.SUPER, "super"));
			mTokens.put(TokenType.THIS , new Token(TokenType.THIS, "this"));
			mTokens.put(TokenType.VOID , new Token(TokenType.VOID, "void"));
			mTokens.put(TokenType.WHILE , new Token(TokenType.WHILE, "while"));
			// Symbols
			mTokens.put(TokenType.ASSIGN , new Token(TokenType.ASSIGN, "="));
			mTokens.put(TokenType.BITWISE_AND , new Token(TokenType.BITWISE_AND, "&"));
			mTokens.put(TokenType.BITWISE_NOT , new Token(TokenType.BITWISE_NOT, "!"));
			mTokens.put(TokenType.BITWISE_OR , new Token(TokenType.BITWISE_OR, "|"));
			mTokens.put(TokenType.BOOL_OP_AND , new Token(TokenType.BOOL_OP_AND, "&&"));
			mTokens.put(TokenType.BOOL_OP_OR , new Token(TokenType.BOOL_OP_OR, "||"));
			mTokens.put(TokenType.CHARACTER_ESCAPE , new Token(TokenType.CHARACTER_ESCAPE, "\\"));
			mTokens.put(TokenType.CLOSE_BR , new Token(TokenType.CLOSE_BR, ")"));
			mTokens.put(TokenType.CLOSE_CBR , new Token(TokenType.CLOSE_CBR, "}"));
			mTokens.put(TokenType.CLOSE_COMMENT , new Token(TokenType.CLOSE_COMMENT, "*/"));
			mTokens.put(TokenType.CLOSE_COMMENT_DOUBLE , new Token(TokenType.CLOSE_COMMENT_DOUBLE, "**/"));
			mTokens.put(TokenType.CLOSE_SBR , new Token(TokenType.CLOSE_SBR, "]"));
			mTokens.put(TokenType.COMMENT , new Token(TokenType.COMMENT, "//"));
			mTokens.put(TokenType.COMP_EQ , new Token(TokenType.COMP_EQ, "=="));
			mTokens.put(TokenType.COMP_GREATER_THAN , new Token(TokenType.COMP_GREATER_THAN, ">"));
			mTokens.put(TokenType.COMP_GREATER_THAN_EQ , new Token(TokenType.COMP_GREATER_THAN_EQ, ">="));
			mTokens.put(TokenType.COMP_LESS_THAN , new Token(TokenType.COMP_LESS_THAN, "<"));
			mTokens.put(TokenType.COMP_LESS_THAN_EQ , new Token(TokenType.COMP_LESS_THAN_EQ, "<="));
			mTokens.put(TokenType.COMP_NOT_EQ , new Token(TokenType.COMP_NOT_EQ, "!="));
			mTokens.put(TokenType.DOT , new Token(TokenType.DOT, "."));
			mTokens.put(TokenType.OP_DIV , new Token(TokenType.OP_DIV, "/"));
			mTokens.put(TokenType.OP_MINUS , new Token(TokenType.OP_MINUS, "-"));
			mTokens.put(TokenType.OP_PLUS , new Token(TokenType.OP_PLUS, "+"));
			mTokens.put(TokenType.OP_REMAINDER , new Token(TokenType.OP_REMAINDER, "%"));
			mTokens.put(TokenType.OPEN_BR , new Token(TokenType.OPEN_BR, "("));
			mTokens.put(TokenType.OPEN_CBR , new Token(TokenType.OPEN_CBR, "{"));
			mTokens.put(TokenType.OPEN_COMMENT , new Token(TokenType.OPEN_COMMENT, "/*"));
			mTokens.put(TokenType.OPEN_COMMENT_DOUBLE , new Token(TokenType.OPEN_COMMENT_DOUBLE, "/**"));
			mTokens.put(TokenType.OPEN_SBR , new Token(TokenType.OPEN_SBR, "["));
			mTokens.put(TokenType.SEMICOLON , new Token(TokenType.SEMICOLON, ";"));
			mTokens.put(TokenType.STAR , new Token(TokenType.STAR, "*"));
			// Literals
			mTokens.put(TokenType.BOOLEAN_LITERAL_TRUE , new Token(TokenType.BOOLEAN_LITERAL_TRUE, "true"));
			mTokens.put(TokenType.BOOLEAN_LITERAL_FALSE , new Token(TokenType.BOOLEAN_LITERAL_FALSE, "false"));
			mTokens.put(TokenType.SINGLE_QUOTE , new Token(TokenType.SINGLE_QUOTE, "'"));
			mTokens.put(TokenType.CHAR_LITERAL , new Token(TokenType.CHAR_LITERAL, "")); // consists of a single char surrounded by single quotes
			mTokens.put(TokenType.INTEGER_LITERAL , new Token(TokenType.INTEGER_LITERAL, ""));
			mTokens.put(TokenType.DOUBLE_QUOTE , new Token(TokenType.DOUBLE_QUOTE, "\""));
			mTokens.put(TokenType.STRING_LITERAL , new Token(TokenType.STRING_LITERAL, "")); // consists of a string of characters surrounded by double quotes
			// Space
			mTokens.put(TokenType.SPACE, new Token(TokenType.SPACE, " "));
		}
		Token token = mTokens.get(tokenType);
		return new Token(token.mType, token.mRawValue);
	}
}