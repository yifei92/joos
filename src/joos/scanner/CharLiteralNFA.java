package joos.scanner;

import java.lang.Exception;
import joos.scanner.NFA;
import joos.commons.Token;
import java.lang.String;

/**
 * NFA for char literals
 */
public class CharLiteralNFA implements NFA {

	// Specifies what has already been consumed by this NFA
	private enum State { START, FIRST_QUOTE, CHARACTER, END_QUOTE, END };
	private State mState = State.START;
	private char mChar = Character.MIN_VALUE;

	public boolean consume(char newChar) {
		boolean didTransition = false;
		switch (mState) {
			case START:
				if (newChar == '\'') {
					mState = State.FIRST_QUOTE;
					return true;
				}
				break;
			case FIRST_QUOTE:
				// We can have any character within a char literal except for the escape char
				if (newChar != '\\') {
					mChar = newChar;
					mState = State.CHARACTER;
					return true;
				}
				break;
			case CHARACTER:
				if (newChar == '\'') {
					mState = State.END_QUOTE;
					return true; 
				}
				break;
			case END_QUOTE:
				mState = State.END;
				return false;
			default:
				return false;
		}
		return false;
	}

	public boolean isAccepting() {
		// If we've accepted the last char in this literal then this NFA is in the accepting state.
		return mState == State.END_QUOTE;
	}

	public void reset() {
		mState = State.START;
		mChar = Character.MIN_VALUE;
	}

	public Token[] getTokens() {
		Token[] tokens = new Token[3];
		Token charLiteral = Token.CHAR_LITERAL;
		charLiteral.setRawValue(String.valueOf(mChar));
		tokens[0] = Token.SINGLE_QUOTE;
		tokens[1] = charLiteral;
		tokens[2] = Token.SINGLE_QUOTE;
		return tokens;
	}
}