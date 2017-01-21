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
 * NFA for char literals
 */
public class CharLiteralNFA extends NFA {

	private static final int STATE_START = 0;
	private static final int STATE_COMMA_OPEN = 1;
	private static final int STATE_ESCAPE = 2;
	private static final int STATE_CHAR = 3;
	private static final int STATE_COMMA_CLOSE = 4;
	// Specifies what has already been consumed by this NFA
	private String mValue = "";
	private boolean mContainsEscape = false;

	protected Transitions getTransitions(int state) {
		Transitions transitions = null;
		Map<Character, Integer> table = new HashMap<>();
		switch (state) {
			case STATE_START:
				table.put('\'', STATE_COMMA_OPEN);
				break;
			case STATE_COMMA_OPEN:
				// Allow for the escape char
				table.put('\\', STATE_ESCAPE);
				// Allow for all letters and digits
				TransitionTableUtil.putAllLetters(table, STATE_CHAR);
				TransitionTableUtil.putAllDigits(table, STATE_CHAR);
				break;
			case STATE_ESCAPE:
				// Allow for all letters and digits
				TransitionTableUtil.putAllLetters(table, STATE_CHAR);
				TransitionTableUtil.putAllDigits(table, STATE_CHAR);
				break;
			case STATE_CHAR:
				table.put('\'', STATE_COMMA_CLOSE);
				break;
			case STATE_COMMA_CLOSE:
				break;
		}
		return new Transitions(state, table);
	}

	protected Set<Integer> getStates() {
		return new HashSet<Integer>(
			Arrays.asList(
				STATE_START, 
				STATE_COMMA_OPEN,
				STATE_ESCAPE,
				STATE_CHAR,
				STATE_COMMA_CLOSE));
	}

	protected Set<Integer> getAcceptStates() {
		return new HashSet<Integer>(Arrays.asList(STATE_COMMA_CLOSE));
	}

	protected void onCharAccepted(char newChar) {
		if (newChar == '\\') {
			mContainsEscape = true;
		} else if (newChar != '\'') {
			mValue += newChar;
		}
	}

	@Override
	public void reset() {
		super.reset();
		mValue = "";
		mContainsEscape = false;
	}

	public List<TerminalToken> getTokens() {
		List<TerminalToken> tokens = new ArrayList<>();
		TerminalToken charLiteral = TerminalToken.getToken(TokenType.CHAR_LITERAL);
		charLiteral.setRawValue(mValue);
		tokens.add(TerminalToken.getToken(TokenType.SINGLE_QUOTE));
		if (mContainsEscape) {
			tokens.add(TerminalToken.getToken(TokenType.CHARACTER_ESCAPE));
		}
		tokens.add(charLiteral);
		tokens.add(TerminalToken.getToken(TokenType.SINGLE_QUOTE));
		return tokens;
	}
}
