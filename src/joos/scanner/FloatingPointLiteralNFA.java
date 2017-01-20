package joos.scanner;

import java.lang.Exception;
import joos.scanner.NFA;
import java.lang.Character;
import joos.commons.TerminalToken;
import joos.commons.TokenType;

/**
 * NFA for float literals
 * Valid format is either
 * 1
 * or
 * 1.1f
 */
public class FloatingPointLiteralNFA implements NFA {

	// Specifies what has already been consumed by this NFA
	private enum State { START, WHOLE_INTEGER, DOT,  END };
	private State mState = State.START;
	private String mInteger = "";

	public boolean consume(char newChar) {
		boolean didTransition = false;
		switch (mState) {
			case START:
				if (Character.isDigit(newChar)) {
					mInteger += newChar;
					mState = State.INTEGER;
					return true;
				}
				break;
			case INTEGER:
				// We can have any character within a char literal except for the escape char
				if (Character.isDigit(newChar)) {
					mInteger += newChar;
					return true;
				} else {
					mState = State.END;
					return true;
				}
			case END:
			default:
				return false;
		}
		return false;
	}

	public boolean isAccepting() {
		// If we've accepted the last char in this literal then this NFA is in the accepting state.
		return mState == State.INTEGER;
	}

	public void reset() {
		mState = State.START;
		mInteger = "";
	}

	public TerminalToken[] getTokens() {
		TerminalToken[] tokens = new TerminalToken[1];
		TerminalToken integerLiteral = TerminalToken.getToken(TokenType.INTEGER_LITERAL);
		integerLiteral.setRawValue(mInteger);
		tokens[0] = integerLiteral;
		return tokens;
	}
}
