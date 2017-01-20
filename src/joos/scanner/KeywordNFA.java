package joos.scanner;

import java.lang.Exception;
import joos.scanner.NFA;
import joos.commons.TerminalToken;
import joos.commons.TokenType;

/**
 * NFA for non literal tokens.
 * These are tokens that have keywords or are symbols.
 */
public class KeywordNFA implements NFA {

	private final String mLiteral;
	private final TerminalToken mToken;

	private enum State {START, KEYWORD_CHAR, KEYWORD_COMPLETE, END};
	private int mNextCharIndex = 0;
	private State mState = State.START;

	public KeywordNFA(TokenType tokenType) throws Exception {
		mToken = TerminalToken.getToken(tokenType);
		mLiteral = mToken.getRawValue();
		if (mLiteral == null || mLiteral.length() == 0) {
			throw new Exception("Invalid token " + mToken.mType + " cannot create an NFA.");
		}
	}

	public boolean consume(char newChar) {
		// If the new char is the next char of the literal then we can accept it and make a transition.
		switch (mState) {
			case START:
				if (mNextCharIndex < mLiteral.length() &&
					newChar == mLiteral.charAt(mNextCharIndex)) {
					mNextCharIndex ++;
					mState = mLiteral.length() == 1 ? State.KEYWORD_COMPLETE : State.KEYWORD_CHAR;
					return true;
				}
				break;
			case KEYWORD_CHAR:
				if (mNextCharIndex == mLiteral.length() - 1 &&
					newChar == mLiteral.charAt(mNextCharIndex)) {
					mState = State.KEYWORD_COMPLETE;
					return true;
				} else if (mNextCharIndex < mLiteral.length() &&
					newChar == mLiteral.charAt(mNextCharIndex)) {
					mState = State.KEYWORD_CHAR;
					mNextCharIndex++;
					return true;
				} else {
					mState = State.END;
					return true;
				}
			case KEYWORD_COMPLETE:
				mState = State.END;
				break;
			case END:
				return false;
		}
		return false;
	}

	public boolean isAccepting() {
		return mState == State.KEYWORD_COMPLETE;
	}

	public void reset() {
		mNextCharIndex = 0;
		mState = State.START;
	}

	public TerminalToken[] getTokens() {
		TerminalToken[] tokens = new TerminalToken[1];
		tokens[0] = mToken;
		return tokens;
	}
}
