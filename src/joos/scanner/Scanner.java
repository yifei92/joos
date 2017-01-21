package joos.scanner;

import java.util.List;
import joos.commons.TerminalToken;
import java.util.Map;
import java.util.HashMap;
import joos.commons.TokenType;
import joos.exceptions.InvalidSyntaxException;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.Exception;
import java.lang.Math;

public class Scanner {

	private final List<NFA> mNFAs;
	private final Map<NFA, Boolean> mIsNFAActive = new HashMap<NFA, Boolean>();

	private List<TerminalToken> mLastAcceptedTokens = new ArrayList<>();
	private int mLastAcceptedEndIndex = -1;

	/**
	 * Initialize NFAs
	 */
	public Scanner() {
		mNFAs = new ArrayList<>();
		for (TokenType tokenType : TokenType.values()) {
			if (TerminalToken.isTerminalTokenType(tokenType)) { 
				NFA nfa = null;
				switch (tokenType) {
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
					case FLOATING_POINT_LITERAL:
						nfa = new FloatingPointLiteralNFA();
						break;
				}
				if (nfa == null &&
					tokenType != TokenType.SINGLE_QUOTE &&
					tokenType != TokenType.DOUBLE_QUOTE) {
					try {
						nfa = new KeywordNFA(tokenType);
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println(e.getMessage());
					}
				}
				if (nfa != null) {
					mIsNFAActive.put(nfa, true);
					mNFAs.add(nfa);
				}
			}
		}
	}

	private void consumeChar(List<NFA> nfas, char toConsume, int currentCharIndex) {
		for (NFA nfa : nfas) {
			boolean madeTransition = false;
			if(mIsNFAActive.get(nfa)) {
				madeTransition = nfa.consume(toConsume);
				if (madeTransition && nfa.isAccepting()) {
					mLastAcceptedTokens = nfa.getTokens();
					mLastAcceptedEndIndex = currentCharIndex;
				}
			}
			mIsNFAActive.put(nfa, madeTransition);
		}
	}

	private void reset() {
		mLastAcceptedTokens = new ArrayList<>();
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

	private int numNFAsActive() {
		int s = 0;
		for (NFA nfa : mNFAs) {
			if (mIsNFAActive.get(nfa)) {
				s++;
			}
		}
		return s;
	}

	private void printActiveNFATokenType() {
		if (numNFAsActive() == 1) {
			for (NFA nfa : mNFAs) {
				if (mIsNFAActive.get(nfa)) {
					System.out.println("active type " + nfa.getTokens().get(0).getType());
				}
			}
		}
	}

	/**
	 * Given a string of input consisting of valid ASCII characters outputs
	 * a list of valid Tokens. If the string does not produce valid tokens
	 * an exception is thrown.
	 */
	public List<TerminalToken> scan(String input) throws InvalidSyntaxException {
		input += " ";
		List<TerminalToken> tokens = new ArrayList();
		int currentCharIndex = 0;
		while (currentCharIndex < input.length()) {
			char toConsume = input.charAt(currentCharIndex);
			consumeChar(mNFAs, toConsume, currentCharIndex);
			if (allNFAsInActive()) {
				if (mLastAcceptedTokens != null && !mLastAcceptedTokens.isEmpty()) {
					for (TerminalToken acceptedToken : mLastAcceptedTokens) {
						if (acceptedToken.mType != TokenType.SPACE &&
							acceptedToken.mType != TokenType.TAB) {
							tokens.add(acceptedToken);
						}
					}
					currentCharIndex = mLastAcceptedEndIndex + 1;
				} else {
					String errorCodeChunk =
						input.substring(
							currentCharIndex,
							Math.min(input.length(), currentCharIndex + 5));
					throw new InvalidSyntaxException("Error at char " + currentCharIndex + " " + errorCodeChunk);
				}
				reset();
			} else {
				currentCharIndex++;
			}
		}
		tokens.add(TerminalToken.getToken(TokenType.EOF));
		return tokens;
	}
}
