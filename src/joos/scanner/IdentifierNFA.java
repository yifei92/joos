package joos.scanner;

import java.lang.Exception;
import joos.scanner.NFA;
import joos.commons.TerminalToken;
import joos.commons.TokenType;

/**
 * NFA for string literals.
 */
public class IdentifierNFA implements NFA {

	// Specifies what has already been consumed by this NFA
	private enum State { START, FIRST_ALPHABET_CHAR, ALPHANUMERIC_CHARACTERS, END };
	private State mState = State.START;

	private String mName = "";

	public boolean consume(char newChar) {
		switch (mState) {
			case START:
				if (Character.isLetter(newChar)) {
					mState = State.FIRST_ALPHABET_CHAR;
					mName += newChar;
					return true;
				}
				break;
			case FIRST_ALPHABET_CHAR:
				if (Character.isLetter(newChar) || Character.isDigit(newChar)) {
					mState = State.ALPHANUMERIC_CHARACTERS;
					mName += newChar;
					return true;
				} else {
					mState = State.END;
					return true;
				}
			case ALPHANUMERIC_CHARACTERS:
				if (Character.isLetter(newChar) || Character.isDigit(newChar)) {
					mName += newChar;
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
		return mState == State.FIRST_ALPHABET_CHAR || mState == State.ALPHANUMERIC_CHARACTERS;
	}

	public void reset() {
		mState = State.START;
		mName = "";
	}

	public TerminalToken[] getTokens() {
		TerminalToken[] tokens = new TerminalToken[1];
		TerminalToken identifier = TerminalToken.getToken(TokenType.IDENTIFIER);
		identifier.setRawValue(mName);
		tokens[0] = identifier;
		return tokens;
	}
}
