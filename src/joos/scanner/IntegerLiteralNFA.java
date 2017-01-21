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
 * NFA for int literals
 */
public class IntegerLiteralNFA extends NFA {

	private static final int STATE_START = 0;
	private static final int STATE_INTEGER = 1;
	// Specifies what has already been consumed by this NFA
	private String mValue = "";

	protected Transitions getTransitions(int state) {
		Transitions transitions = null;
		Map<Character, Integer> table = new HashMap<>();
		switch (state) {
			case STATE_START:
				TransitionTableUtil.putAllDigits(table, STATE_INTEGER);
				break;
			case STATE_INTEGER:
				// Allow for all digits
				TransitionTableUtil.putAllDigits(table, STATE_INTEGER);
				break;
		}
		return new Transitions(state, table);
	}

	protected Set<Integer> getStates() {
		return new HashSet<Integer>(
			Arrays.asList(
				STATE_START, 
				STATE_INTEGER));
	}

	protected Set<Integer> getAcceptStates() {
		return new HashSet<Integer>(Arrays.asList(STATE_INTEGER));
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
		TerminalToken integerLiteral = TerminalToken.getToken(TokenType.INTEGER_LITERAL);
		integerLiteral.setRawValue(mValue);
		tokens.add(integerLiteral);
		return tokens;
	}
}
