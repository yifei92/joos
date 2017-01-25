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
	private static final int STATE_VALID_ESCAPE_OCTAL_1 = 3;
	private static final int STATE_VALID_ESCAPE_OCTAL_2 = 4;
	private static final int STATE_CHAR = 5;
	private static final int STATE_COMMA_CLOSE = 6;

	private static final Set<Character> ESCAPE_EXCLUSIONS = new HashSet<Character>(Arrays.asList('\\', System.lineSeparator().charAt(0)));
	private static final Set<Character> OCTAL_EXCLUSIONS = new HashSet<Character>(Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7'));
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
				// Allow for all chars except \
				TransitionTableUtil.putAllCharExcept(table, ESCAPE_EXCLUSIONS, STATE_CHAR);
				break;
			case STATE_ESCAPE:
				TransitionTableUtil.putAllValidEscapeChars(table, STATE_CHAR);
				TransitionTableUtil.putAllOctals(table, STATE_VALID_ESCAPE_OCTAL_1);
				break;
			case STATE_VALID_ESCAPE_OCTAL_1:
				TransitionTableUtil.putAllOctals(table, STATE_VALID_ESCAPE_OCTAL_2);
				table.put('\'', STATE_COMMA_CLOSE);
				break;
			case STATE_VALID_ESCAPE_OCTAL_2:
				TransitionTableUtil.putAllOctals(table, STATE_CHAR);
				table.put('\'', STATE_COMMA_CLOSE);
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
				STATE_VALID_ESCAPE_OCTAL_1,
				STATE_VALID_ESCAPE_OCTAL_2,
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
		tokens.add(TerminalToken.getToken(TokenType.SINGLE_QUOTE));
		if (mContainsEscape) {
			TerminalToken charEscape = TerminalToken.getToken(TokenType.ESCAPE);
			charEscape.setRawValue(mValue);
			tokens.add(charEscape);
		} else {
			TerminalToken charLiteral = TerminalToken.getToken(TokenType.CHAR_LITERAL);
			charLiteral.setRawValue(mValue);
			tokens.add(charLiteral);
		}
		tokens.add(TerminalToken.getToken(TokenType.SINGLE_QUOTE));
		return tokens;
	}
}
