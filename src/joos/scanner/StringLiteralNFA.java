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
 * NFA for string literals
 */
public class StringLiteralNFA extends NFA {

	private static final int STATE_START = 0;
	private static final int STATE_QUOTE_OPEN = 1;
	private static final int STATE_ESCAPE = 2;
	private static final int STATE_STRING = 3;
	private static final int STATE_VALID_ESCAPE = 4;
	private static final int STATE_QUOTE_CLOSE = 5;

	private static final Set<Character> STRING_LITERAL_EXCLUSIONS = new HashSet<Character>(Arrays.asList('\\', System.lineSeparator().charAt(0), '"'));

	// Specifies what has already been consumed by this NFA
	private List<TerminalToken> mTokens = new ArrayList<>();

	protected Transitions getTransitions(int state) {
		Transitions transitions = null;
		Map<Character, Integer> table = new HashMap<>();
		switch (state) {
			case STATE_START:
				table.put('"', STATE_QUOTE_OPEN);
				break;
			case STATE_QUOTE_OPEN:
				// Allow for the escape char
				table.put('\\', STATE_ESCAPE);
				// Allow for empty strings
				table.put('"', STATE_QUOTE_CLOSE);
				// Allow for all chars except the ones above
				TransitionTableUtil.putAllCharExcept(table, STRING_LITERAL_EXCLUSIONS, STATE_STRING);
				break;
			case STATE_ESCAPE:
				TransitionTableUtil.putAllValidEscapeChars(table, STATE_VALID_ESCAPE);
				TransitionTableUtil.putAllOctals(table, STATE_VALID_ESCAPE);
				break;
			case STATE_VALID_ESCAPE:
				table.put('\\', STATE_ESCAPE);
				// Allow for empty strings
				table.put('"', STATE_QUOTE_CLOSE);
				TransitionTableUtil.putAllCharExcept(table, STRING_LITERAL_EXCLUSIONS, STATE_STRING);
				break;
			case STATE_STRING:
				// Allow for end quotes
				table.put('"', STATE_QUOTE_CLOSE);
				// Allow for the escape char
				table.put('\\', STATE_ESCAPE);
				// Allow for all chars except for above
				TransitionTableUtil.putAllCharExcept(table, STRING_LITERAL_EXCLUSIONS, STATE_STRING);
				break;
			case STATE_QUOTE_CLOSE:
				break;
		}
		return new Transitions(state, table);
	}

	protected Set<Integer> getStates() {
		return new HashSet<Integer>(
			Arrays.asList(
				STATE_START, 
				STATE_QUOTE_OPEN,
				STATE_ESCAPE,
				STATE_STRING,
				STATE_VALID_ESCAPE,
				STATE_QUOTE_CLOSE));
	}

	protected Set<Integer> getAcceptStates() {
		return new HashSet<Integer>(Arrays.asList(STATE_QUOTE_CLOSE));
	}

	protected void onCharAccepted(char newChar) {
		if (newChar != '"') { 
			if (mTokens.size() > 0 &&
				mTokens.get(mTokens.size() - 1).getType() == TokenType.ESCAPE &&
				(mTokens.get(mTokens.size() - 1).getRawValue() == null || mTokens.get(mTokens.size() - 1).getRawValue() == "")) {
				mTokens.get(mTokens.size() - 1).setRawValue(Character.toString(newChar));
			} else if (newChar == '\\') {
				mTokens.add(TerminalToken.getToken(TokenType.ESCAPE));
			} else {
				if (mTokens.size() == 0 || 
					mTokens.size() > 0 && mTokens.get(mTokens.size() - 1).mType == TokenType.ESCAPE) {
					mTokens.add(TerminalToken.getToken(TokenType.STRING_LITERAL));
				}
				TerminalToken lastToken = mTokens.get(mTokens.size() - 1);
				lastToken.setRawValue(lastToken.getRawValue() + newChar);
			}
		}
	}

	@Override
	public void reset() {
		super.reset();
		mTokens = new ArrayList<>();
	}

	public List<TerminalToken> getTokens() {
		mTokens.add(0, TerminalToken.getToken(TokenType.DOUBLE_QUOTE));
		mTokens.add(TerminalToken.getToken(TokenType.DOUBLE_QUOTE));
		return mTokens;
	}
}
