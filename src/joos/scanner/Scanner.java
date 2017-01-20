package joos.scanner;

import java.util.List;
import joos.commons.TerminalToken;
import joos.exceptions.InvalidSyntaxException;
import java.util.Iterator;
import java.util.ArrayList;
import java.lang.Exception;

public class Scanner {

	private final List<NFA> mNFAs;

	private TerminalToken[] mLastAcceptedTokens = null;
	private int mLastAcceptedEndIndex = -1;
	/**
	 * Initialize NFAs
	 */
	public Scanner() {
		mNFAs = new ArrayList<>();
		for (TerminalToken token : TerminalToken.values()) {
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
				token != TerminalToken.SINGLE_QUOTE &&
				token != TerminalToken.DOUBLE_QUOTE) {
				try {
					nfa = new KeywordNFA(token);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
			mNFAs.add(nfa);
		}
	}

	private void consumeChar(List<NFA> nfas, char toConsume, int currentCharIndex) {
		Iterator<NFA> it = nfas.iterator();
		NFA nfa;
		while(it.hasNext()) {
			nfa = it.next();
			if (nfa != null) {
				if(nfa.consume(toConsume)) {
					if (nfa.isAccepting()) {
						mLastAcceptedTokens = nfa.getTokens();
						mLastAcceptedEndIndex = currentCharIndex;
					}
				} else {
					it.remove();
				}
			}
		}
	}

	private void reset() {
		mLastAcceptedTokens = null;
		mLastAcceptedEndIndex = -1;
		for (NFA nfa : mNFAs) {
			nfa.reset();
		}
	}
	/**
	 * Given a string of input consisting of valid ASCII characters outputs
	 * a list of valid Tokens. If the string does not produce valid tokens
	 * an exception is thrown.
	 */
	public List<TerminalToken> scan(String input) throws InvalidSyntaxException {
		List<TerminalToken> tokens = new ArrayList();
		List<NFA> nfas = mNFAs;
		for (int currentCharIndex = 0 ; currentCharIndex < input.length() ; currentCharIndex++) {
			char toConsume = input.charAt(currentCharIndex);
			if (toConsume != ' ') {
				consumeChar(nfas, toConsume, currentCharIndex);
				if (nfas.size() == 0) {
					if (mLastAcceptedTokens != null) {
						currentCharIndex = mLastAcceptedEndIndex + 1;
					}
					reset();
					nfas = mNFAs;
				}
			}
		}
		return tokens;
	}
}
