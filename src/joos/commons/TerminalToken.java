package joos.commons;

import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import joos.commons.TokenType;
import joos.commons.Token;

public class TerminalToken implements Token {
	public final TokenType mType;
	public String mRawValue;

	private TerminalToken(TokenType type, String rawValue) {
		mType = type;
		mRawValue = rawValue;
	}

	public void setRawValue(String value) {
		mRawValue = value;
	}

	public String getRawValue() {
		return mRawValue;
	}

	public TokenType getType() {
		return mType;
	}

	private static Map<TokenType, TerminalToken> mTokens = null;

	public static boolean isTerminalTokenType(TokenType type) {
		if (mTokens == null) {
			generateTokens();
		}
		return mTokens.get(type) != null;
	}

	public static Set<TokenType> getAllTokens() {
		if (mTokens == null) {
			generateTokens();
		}
		return mTokens.keySet();
	}

	public static TerminalToken getToken(TokenType tokenType) {
		if (mTokens == null) {
			generateTokens();
		}
		TerminalToken token = mTokens.get(tokenType);
		if (token == null) {
			System.out.println("Token " + tokenType + " is not a terminal token!");
			return null;
		}
		return new TerminalToken(token.mType, token.mRawValue);
	}

	private static void generateTokens() {
		mTokens = new HashMap<TokenType,TerminalToken>();
		// Identifier
		mTokens.put(TokenType.IDENTIFIER, new TerminalToken(TokenType.IDENTIFIER, ""));
		// Keywords
		mTokens.put(TokenType.ABSTRACT , new TerminalToken(TokenType.ABSTRACT, "abstract"));
		mTokens.put(TokenType.BOOLEAN , new TerminalToken(TokenType.BOOLEAN, "boolean"));
		mTokens.put(TokenType.BYTE , new TerminalToken(TokenType.BYTE, "byte"));
		mTokens.put(TokenType.CHAR , new TerminalToken(TokenType.CHAR, "char"));
		mTokens.put(TokenType.CLASS , new TerminalToken(TokenType.CLASS, "class"));
		mTokens.put(TokenType.ELSE , new TerminalToken(TokenType.ELSE, "else"));
		mTokens.put(TokenType.EXTENDS , new TerminalToken(TokenType.EXTENDS, "extends"));
		mTokens.put(TokenType.FINAL , new TerminalToken(TokenType.FINAL, "final"));
		mTokens.put(TokenType.FOR , new TerminalToken(TokenType.FOR, "for"));
		mTokens.put(TokenType.IF , new TerminalToken(TokenType.IF, "if"));
		mTokens.put(TokenType.IMPLEMENTS , new TerminalToken(TokenType.IMPLEMENTS, "implements"));
		mTokens.put(TokenType.IMPORT , new TerminalToken(TokenType.IMPORT, "import"));
		mTokens.put(TokenType.INSTANCEOF , new TerminalToken(TokenType.INSTANCEOF, "instanceof"));
		mTokens.put(TokenType.INT , new TerminalToken(TokenType.INT, "int"));
		mTokens.put(TokenType.INTERFACE , new TerminalToken(TokenType.INTERFACE, "interface"));
		mTokens.put(TokenType.NATIVE , new TerminalToken(TokenType.NATIVE, "native"));
		mTokens.put(TokenType.NEW , new TerminalToken(TokenType.NEW, "new"));
		mTokens.put(TokenType.NULL , new TerminalToken(TokenType.NULL, "null"));
		mTokens.put(TokenType.PACKAGE , new TerminalToken(TokenType.PACKAGE, "package"));
		// mTokens.put(TokenType.PRIVATE , new TerminalToken(TokenType.PRIVATE, "private"));
		mTokens.put(TokenType.PROTECTED , new TerminalToken(TokenType.PROTECTED, "protected"));
		mTokens.put(TokenType.PUBLIC , new TerminalToken(TokenType.PUBLIC, "public"));
		mTokens.put(TokenType.RETURN , new TerminalToken(TokenType.RETURN, "return"));
		mTokens.put(TokenType.SHORT , new TerminalToken(TokenType.SHORT, "short"));
		mTokens.put(TokenType.STATIC , new TerminalToken(TokenType.STATIC, "static"));
		// mTokens.put(TokenType.SUPER , new TerminalToken(TokenType.SUPER, "super"));
		mTokens.put(TokenType.THIS , new TerminalToken(TokenType.THIS, "this"));
		mTokens.put(TokenType.VOID , new TerminalToken(TokenType.VOID, "void"));
		mTokens.put(TokenType.WHILE , new TerminalToken(TokenType.WHILE, "while"));
		// mTokens.put(TokenType.CATCH , new TerminalToken(TokenType.CATCH, "catch"));
		// mTokens.put(TokenType.TRY , new TerminalToken(TokenType.TRY, "try"));
		// mTokens.put(TokenType.FINALLY , new TerminalToken(TokenType.FINALLY, "finally"));
		// mTokens.put(TokenType.THROW , new TerminalToken(TokenType.THROW, "throw"));
		// mTokens.put(TokenType.THROWS , new TerminalToken(TokenType.THROWS, "throws"));
		// mTokens.put(TokenType.CONTINUE , new TerminalToken(TokenType.CONTINUE, "continue"));
		// mTokens.put(TokenType.BREAK , new TerminalToken(TokenType.BREAK, "break"));
		// mTokens.put(TokenType.DO , new TerminalToken(TokenType.DO, "do"));
		// mTokens.put(TokenType.CASE , new TerminalToken(TokenType.CASE, "case"));
		// mTokens.put(TokenType.DEFAULT , new TerminalToken(TokenType.DEFAULT, "default"));
		// mTokens.put(TokenType.SWITCH , new TerminalToken(TokenType.SWITCH, "switch"));
		// mTokens.put(TokenType.SYNCHRONIZED , new TerminalToken(TokenType.SYNCHRONIZED, "synchronized"));
		// mTokens.put(TokenType.TRANSIENT , new TerminalToken(TokenType.TRANSIENT, "transient"));
		// mTokens.put(TokenType.VOLATILE , new TerminalToken(TokenType.VOLATILE, "volatile"));
		// mTokens.put(TokenType.FLOAT , new TerminalToken(TokenType.FLOAT, "float"));
		// mTokens.put(TokenType.DOUBLE , new TerminalToken(TokenType.DOUBLE, "double"));
		// mTokens.put(TokenType.LONG , new TerminalToken(TokenType.LONG, "long"));
		// Symbols
		mTokens.put(TokenType.ASSIGN , new TerminalToken(TokenType.ASSIGN, "="));
		// mTokens.put(TokenType.BITWISE_AND , new TerminalToken(TokenType.BITWISE_AND, "&"));
		// mTokens.put(TokenType.BITWISE_NOT , new TerminalToken(TokenType.BITWISE_NOT, "!"));
		// mTokens.put(TokenType.BITWISE_OR , new TerminalToken(TokenType.BITWISE_OR, "|"));
		mTokens.put(TokenType.BOOL_OP_AND , new TerminalToken(TokenType.BOOL_OP_AND, "&&"));
		mTokens.put(TokenType.BOOL_OP_OR , new TerminalToken(TokenType.BOOL_OP_OR, "||"));
		mTokens.put(TokenType.CHARACTER_ESCAPE , new TerminalToken(TokenType.CHARACTER_ESCAPE, ""));
		mTokens.put(TokenType.CLOSE_BR , new TerminalToken(TokenType.CLOSE_BR, ")"));
		mTokens.put(TokenType.CLOSE_CBR , new TerminalToken(TokenType.CLOSE_CBR, "}"));
		mTokens.put(TokenType.CLOSE_SBR , new TerminalToken(TokenType.CLOSE_SBR, "]"));
		mTokens.put(TokenType.COMMENT_SINGLE_LINE , new TerminalToken(TokenType.COMMENT_SINGLE_LINE, ""));
		mTokens.put(TokenType.COMMENT_MULTI_LINE , new TerminalToken(TokenType.COMMENT_MULTI_LINE, ""));
		mTokens.put(TokenType.COMMENT_JAVADOC , new TerminalToken(TokenType.COMMENT_JAVADOC, ""));
		mTokens.put(TokenType.COMP_EQ , new TerminalToken(TokenType.COMP_EQ, "=="));
		mTokens.put(TokenType.COMP_GREATER_THAN , new TerminalToken(TokenType.COMP_GREATER_THAN, ">"));
		mTokens.put(TokenType.COMP_GREATER_THAN_EQ , new TerminalToken(TokenType.COMP_GREATER_THAN_EQ, ">="));
		mTokens.put(TokenType.COMP_LESS_THAN , new TerminalToken(TokenType.COMP_LESS_THAN, "<"));
		mTokens.put(TokenType.COMP_LESS_THAN_EQ , new TerminalToken(TokenType.COMP_LESS_THAN_EQ, "<="));
		mTokens.put(TokenType.COMP_NOT_EQ , new TerminalToken(TokenType.COMP_NOT_EQ, "!="));
		mTokens.put(TokenType.DOT , new TerminalToken(TokenType.DOT, "."));
		mTokens.put(TokenType.OP_DIV , new TerminalToken(TokenType.OP_DIV, "/"));
		mTokens.put(TokenType.OP_MINUS , new TerminalToken(TokenType.OP_MINUS, "-"));
		mTokens.put(TokenType.OP_PLUS , new TerminalToken(TokenType.OP_PLUS, "+"));
		mTokens.put(TokenType.OP_REMAINDER , new TerminalToken(TokenType.OP_REMAINDER, "%"));
		mTokens.put(TokenType.OPEN_BR , new TerminalToken(TokenType.OPEN_BR, "("));
		mTokens.put(TokenType.OPEN_CBR , new TerminalToken(TokenType.OPEN_CBR, "{"));
		mTokens.put(TokenType.OPEN_SBR , new TerminalToken(TokenType.OPEN_SBR, "["));
		mTokens.put(TokenType.SEMICOLON , new TerminalToken(TokenType.SEMICOLON, ";"));
		mTokens.put(TokenType.STAR , new TerminalToken(TokenType.STAR, "*"));
		mTokens.put(TokenType.COLON , new TerminalToken(TokenType.COLON, ":"));
		mTokens.put(TokenType.COMMA , new TerminalToken(TokenType.COMMA, ","));
		// mTokens.put(TokenType.QUESTION_MARK , new TerminalToken(TokenType.QUESTION_MARK, "?"));
		// mTokens.put(TokenType.BITWISE_XOR , new TerminalToken(TokenType.BITWISE_XOR, "^"));
		// mTokens.put(TokenType.OP_LEFT_SHIFT , new TerminalToken(TokenType.OP_LEFT_SHIFT, "<<"));
		// mTokens.put(TokenType.OP_RIGHT_SHIFT , new TerminalToken(TokenType.OP_RIGHT_SHIFT, ">>"));
		// mTokens.put(TokenType.OP_UNSIGNED_RIGHT_SHIFT , new TerminalToken(TokenType.OP_UNSIGNED_RIGHT_SHIFT, ">>>"));
		mTokens.put(TokenType.BOOL_OP_NOT , new TerminalToken(TokenType.BOOL_OP_NOT, "!"));
		// mTokens.put(TokenType.ASSIGN_DECREMENT , new TerminalToken(TokenType.ASSIGN_DECREMENT, "--"));
		// mTokens.put(TokenType.ASSIGN_INCREMENT , new TerminalToken(TokenType.ASSIGN_INCREMENT, "++"));
		// Literals
		mTokens.put(TokenType.BOOLEAN_LITERAL_TRUE , new TerminalToken(TokenType.BOOLEAN_LITERAL_TRUE, "true"));
		mTokens.put(TokenType.BOOLEAN_LITERAL_FALSE , new TerminalToken(TokenType.BOOLEAN_LITERAL_FALSE, "false"));
		mTokens.put(TokenType.SINGLE_QUOTE , new TerminalToken(TokenType.SINGLE_QUOTE, "'"));
		mTokens.put(TokenType.CHAR_LITERAL , new TerminalToken(TokenType.CHAR_LITERAL, "")); // consists of a single char surrounded by single quotes
		mTokens.put(TokenType.INTEGER_LITERAL , new TerminalToken(TokenType.INTEGER_LITERAL, ""));
		mTokens.put(TokenType.DOUBLE_QUOTE , new TerminalToken(TokenType.DOUBLE_QUOTE, "\""));
		mTokens.put(TokenType.STRING_LITERAL , new TerminalToken(TokenType.STRING_LITERAL, "")); // consists of a string of characters surrounded by double quotes
		mTokens.put(TokenType.NULL_LITERAL , new TerminalToken(TokenType.NULL_LITERAL, "null"));
		// mTokens.put(TokenType.FLOATING_POINT_LITERAL , new TerminalToken(TokenType.FLOATING_POINT_LITERAL, ""));
		// Space
		mTokens.put(TokenType.SPACE, new TerminalToken(TokenType.SPACE, " "));
		mTokens.put(TokenType.EOF, new TerminalToken(TokenType.EOF, "eof"));
		mTokens.put(TokenType.NEW_LINE, new TerminalToken(TokenType.NEW_LINE, System.lineSeparator()));
		mTokens.put(TokenType.TAB, new TerminalToken(TokenType.TAB, "\t"));
	}
}
