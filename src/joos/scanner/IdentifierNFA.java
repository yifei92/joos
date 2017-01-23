package joos.scanner;

import java.lang.Exception;
import joos.scanner.NFA;
import joos.commons.TerminalToken;
import joos.commons.TokenType;
import java.lang.String;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

/**
 * NFA for identifiers
 */
public class IdentifierNFA extends NFA {

	private static final int STATE_START = 0;
	private static final int STATE_IDENTIFIER = 1;
	// Specifies what has already been consumed by this NFA
	private String mValue = "";

	protected Transitions getTransitions(int state) {
		Transitions transitions = null;
		Map<Character, Integer> table = new HashMap<>();
		switch (state) {
			case STATE_START:
				TransitionTableUtil.putAllLetters(table, STATE_IDENTIFIER);
				table.put('$', STATE_IDENTIFIER);
				table.put('_', STATE_IDENTIFIER);
				break;
			case STATE_IDENTIFIER:
				// Allow for all letters and digits
				TransitionTableUtil.putAllDigits(table, STATE_IDENTIFIER);
				TransitionTableUtil.putAllLetters(table, STATE_IDENTIFIER);
				table.put('$', STATE_IDENTIFIER);
				table.put('_', STATE_IDENTIFIER);
				break;
		}
		return new Transitions(state, table);
	}

	protected Set<Integer> getStates() {
		return new HashSet<Integer>(
			Arrays.asList(
				STATE_START, 
				STATE_IDENTIFIER));
	}

	protected Set<Integer> getAcceptStates() {
		return new HashSet<Integer>(Arrays.asList(STATE_IDENTIFIER));
	}

	@Override
	protected void onCharAccepted(char newChar) {
		mValue += newChar;
	}

	@Override
	public void reset() {
		super.reset();
		mValue = "";
	}

	public List<TerminalToken> getTokens() {
		List<TerminalToken> tokens = new ArrayList<>();
		TerminalToken identifier = TerminalToken.getToken(TokenType.IDENTIFIER);
		identifier.setRawValue(mValue);
		tokens.add(identifier);
		return tokens;
	}
}
