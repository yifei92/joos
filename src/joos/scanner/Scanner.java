package joos.scanner;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import joos.commons.Token;
import joos.exceptions.InvalidSyntaxException;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.Exception;

public class Scanner {

	private final List<NFA> mNFAs;
	private final Map<NFA, Boolean> mIsNFAActive = new HashMap<NFA, Boolean>();

	private int mActiveNFAsCount = 0;
	private Token[] mLastAcceptedTokens = null;
	private int mLastAcceptedEndIndex = -1;

	/**
	 * Initialize NFAs
	 */
	public Scanner() {
		mNFAs = new ArrayList<>();
		for (Token token : Token.values()) {
			NFA nfa = null;
			switch (token) {
				case STRING_LITERAL:
					nfa = new StringLiteralNFA();
					break;
				case CHAR_LITERAL:
					nfa = new CharLiteralNFA();
					break;
				case INTEGER_LITERAL:
					nfa = new IntegerLiteralNFA();
					break;
				case IDENTIFIER:
					nfa = new IdentifierNFA();
					break;
			}
			if (nfa == null &&
				token != Token.SINGLE_QUOTE &&
				token != Token.DOUBLE_QUOTE) {
				try {
					nfa = new KeywordNFA(token);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			} 
			if (nfa != null) {
				mIsNFAActive.put(nfa, true);
				mNFAs.add(nfa);
			}
		}
	}

	private void consumeChar(List<NFA> nfas, char toConsume, int currentCharIndex) {
		for (NFA nfa : nfas) {
			if(mIsNFAActive.get(nfa) && nfa.consume(toConsume)) {
				if (nfa.isAccepting()) {
					mLastAcceptedTokens = nfa.getTokens();
					mLastAcceptedEndIndex = currentCharIndex;
				}
			} else {
				mIsNFAActive.put(nfa, false);
			}
		}
	}

	private void reset() {
		mLastAcceptedTokens = null;
		mLastAcceptedEndIndex = -1;
		for (NFA nfa : mNFAs) {
			nfa.reset();
			mIsNFAActive.put(nfa, true);
		}
	}

	/**
	 * Returns true if all NFAs are inactive. False otherwise.
	 */
	private boolean allNFAsInActive() {
		for (NFA nfa : mNFAs) {
			if (mIsNFAActive.get(nfa)) return false;
		}
		return true;
	}

	/**
	 * Given a string of input consisting of valid ASCII characters outputs
	 * a list of valid Tokens. If the string does not produce valid tokens
	 * an exception is thrown.
	 */
	public List<Token> scan(String input) throws InvalidSyntaxException {
		List<Token> tokens = new ArrayList();
		for (int currentCharIndex = 0 ; currentCharIndex < input.length() ; currentCharIndex++) {
			char toConsume = input.charAt(currentCharIndex);
			consumeChar(mNFAs, toConsume, currentCharIndex);
			if (allNFAsInActive()) {
				if (mLastAcceptedTokens != null) {
					tokens.addAll(Arrays.asList(mLastAcceptedTokens));
					currentCharIndex = mLastAcceptedEndIndex + 1;
				}
				reset();
			}
		}
		return tokens;
	}
}
