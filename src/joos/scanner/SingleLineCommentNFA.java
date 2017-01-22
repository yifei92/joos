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
 * NFA for single line comments <// ...>
 */
public class SingleLineCommentNFA extends NFA {

	private static final int STATE_START = 0;
	private static final int STATE_FIRST_SLASH = 1;
	private static final int STATE_SECOND_SLASH = 2;
	private static final int STATE_CHARS= 3;

	private static final Set<Character> NEWLINE_EXCLUSIONS = new HashSet<Character>(Arrays.asList(System.lineSeparator().charAt(0)));

	protected Transitions getTransitions(int state) {
		Transitions transitions = null;
		Map<Character, Integer> table = new HashMap<>();
		switch (state) {
			case STATE_START:
				table.put('/', STATE_FIRST_SLASH);
				break;
			case STATE_FIRST_SLASH:
				table.put('/', STATE_SECOND_SLASH);
				break;
			case STATE_SECOND_SLASH:
				// Allow for all chars except the newline char
				TransitionTableUtil.putAllCharExcept(table, NEWLINE_EXCLUSIONS, STATE_CHARS);
				break;
			case STATE_CHARS:
				// Allow for all chars except the newline char
				TransitionTableUtil.putAllCharExcept(table, NEWLINE_EXCLUSIONS, STATE_CHARS);
				break;
		}
		return new Transitions(state, table);
	}

	protected Set<Integer> getStates() {
		return new HashSet<Integer>(
			Arrays.asList(
				STATE_START, 
				STATE_FIRST_SLASH,
				STATE_SECOND_SLASH,
				STATE_CHARS));
	}

	protected Set<Integer> getAcceptStates() {
		return new HashSet<Integer>(Arrays.asList(STATE_CHARS));
	}

	protected void onCharAccepted(char newChar) {
		/** noop */
	}

	public List<TerminalToken> getTokens() {
		List<TerminalToken> tokens = new ArrayList<>();
		tokens.add(TerminalToken.getToken(TokenType.COMMENT_SINGLE_LINE));
		return tokens;
	}
}
