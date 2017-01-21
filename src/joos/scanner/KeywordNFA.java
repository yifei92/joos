package joos.scanner;

import java.lang.Exception;
import joos.scanner.NFA;
import joos.commons.TerminalToken;
import joos.commons.TokenType;
import java.lang.String;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * NFA for Keywords.
 * Each state is the index of the letter that was just accepted.
 */
public class KeywordNFA extends NFA {

	private final TerminalToken mToken;
	private final String mLiteral;

	public KeywordNFA(TokenType tokenType) throws Exception {
		mToken = TerminalToken.getToken(tokenType);
		mLiteral = mToken.getRawValue();
		if (mLiteral == null || mLiteral.length() == 0) {
			throw new Exception("Invalid token " + mToken.mType + " cannot create an NFA.");
		}
	}

	protected Transitions getTransitions(int state) {
		Map<Character, Integer> table = new HashMap<>();
		if (state != mLiteral.length()) {
			table.put(mLiteral.charAt(state), state+1);
		}
		return new Transitions(state, table);
	}

	protected Set<Integer> getStates() {
		Set<Integer> states = new HashSet<Integer>();
		for (int i = 0 ; i <= mLiteral.length() ; i++) {
			states.add(i);
		}
		return states;
	}

	protected Set<Integer> getAcceptStates() {
		return new HashSet<Integer>(Arrays.asList(mLiteral.length()));
	}

	@Override
	protected void onCharAccepted(char newChar) {
		/** noop **/
	}

	public List<TerminalToken> getTokens() {
		List<TerminalToken> tokens = new ArrayList<>();
		tokens.add(mToken);
		return tokens;
	}
}
