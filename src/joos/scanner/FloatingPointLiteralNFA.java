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
 * NFA for float literals
 */
public class FloatingPointLiteralNFA extends NFA {

	private static final int STATE_START = 0;
	private static final int STATE_WHOLE_NUMBER = 1;
	private static final int STATE_DOT = 2;
	private static final int STATE_FRACTIONAL_NUMBER = 3;
	private static final int STATE_F = 4;
	// Specifies what has already been consumed by this NFA
	private String mValue = "";

	protected Transitions getTransitions(int state) {
		Transitions transitions = null;
		Map<Character, Integer> table = new HashMap<>();
		switch (state) {
			case STATE_START:
				TransitionTableUtil.putAllDigits(table, STATE_WHOLE_NUMBER);
				break;
			case STATE_WHOLE_NUMBER:
				// Allow for the dot char
				table.put('.', STATE_DOT);
				// Allow for f
				table.put('f', STATE_F);
				// Allow for all digits
				TransitionTableUtil.putAllDigits(table, STATE_WHOLE_NUMBER);
				break;
			case STATE_DOT:
				// Allow for all digits
				TransitionTableUtil.putAllDigits(table, STATE_FRACTIONAL_NUMBER);
				break;
			case STATE_FRACTIONAL_NUMBER:
				// Allow for all digits
				TransitionTableUtil.putAllDigits(table, STATE_FRACTIONAL_NUMBER);
				// Allow for f
				table.put('f', STATE_F);
				break;
			case STATE_F:
				break;
		}
		return new Transitions(state, table);
	}

	protected Set<Integer> getStates() {
		return new HashSet<Integer>(
			Arrays.asList(
				STATE_START, 
				STATE_WHOLE_NUMBER,
				STATE_DOT,
				STATE_FRACTIONAL_NUMBER,
				STATE_F));
	}

	protected Set<Integer> getAcceptStates() {
		return new HashSet<Integer>(Arrays.asList(STATE_F));
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
		TerminalToken floatLiteral = TerminalToken.getToken(TokenType.FLOATING_POINT_LITERAL);
		floatLiteral.setRawValue(mValue);
		tokens.add(floatLiteral);
		return tokens;
	}
}