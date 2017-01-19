package joos.scanner;

import java.lang.Exception;
import joos.scanner.NFA;
import joos.commons.Token;
import joos.commons.TokenType;

/**
 * NFA for string literals.
 * TODO: escape chars in the string do not currently work.
 */
public class StringLiteralNFA implements NFA {

	// Specifies what has already been consumed by this NFA
	private enum State { START, FIRST_DQUOTE, CHARACTERS, END_DQUOTE, END };
	private State mState = State.START;

	private String mValue = "";

	public boolean consume(char newChar) {
		switch (mState) {
			case START:
				if (newChar == '\"') {
					mState = State.FIRST_DQUOTE;
					return true;
				}
				break;
			case FIRST_DQUOTE:
				if (newChar == '\"') {
					mState = State.END_DQUOTE;
					return true;
				} else if (newChar != '\\') {
					mState = State.CHARACTERS;
					mValue += newChar;
					return true;
				}
				break;
			case CHARACTERS:
				if (newChar == '\"') {
					mState = State.END_DQUOTE;
					return true; 
				} else if (newChar != '\\'){
					mValue += newChar;
					return true;
				}
				break;
			case END_DQUOTE:
				mState = State.END;
			default:
				return false;
		}
		return false;
	}

	public boolean isAccepting() {
		// If we've accepted the last char in this literal then this NFA is in the accepting state.
		return mState == State.END_DQUOTE;
	}

	public void reset() {
		mState = State.START;
		mValue = "";
	}

	public Token[] getTokens() {
		Token[] tokens = new Token[3];
		tokens[0] = new Token(TokenType.DOUBLE_QUOTE, "\"");
		tokens[1] = new Token(TokenType.STRING_LITERAL, mValue);
		tokens[2] = new Token(TokenType.DOUBLE_QUOTE, "\"");
		return tokens;
	}
}