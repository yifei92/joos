package joos.scanner;

import java.lang.Exception;
import joos.scanner.NFA;
import joos.commons.Token;

/**
 * NFA for non literal tokens. 
 * These are tokens that have keywords or are symbols.
 */
public class KeywordNFA implements NFA {

	private final String mLiteral;
	private final Token mToken;

	private int mNextCharIndex = 0;

	public KeywordNFA(Token token) throws Exception {
		mToken = token;
		mLiteral = mToken.getRawValue();
		if (mLiteral == null || mLiteral.length() == 0) {
			throw new Exception("Invalid token " + mToken + " cannot create an NFA.");
		}
	}

	public boolean consume(char newChar) {
		// If the new char is the next char of the literal then we can accept it and make a transition.
		// Else we kill the NFA.
		if (mNextCharIndex < mLiteral.length() &&
			newChar == mLiteral.charAt(mNextCharIndex)) {
			mNextCharIndex++;
			return true;
		} else {
			return false;
		}
	}

	public boolean isAccepting() {
		// If we've accepted the last char in this literal then this NFA is in the accepting state.
		return mLiteral.length() == mNextCharIndex;
	}

	public void reset() {
		mNextCharIndex = 0;
	}

	public Token[] getTokens() {
		Token[] tokens = new Token[1];
		tokens[0] = mToken;
		return tokens;
	}
}