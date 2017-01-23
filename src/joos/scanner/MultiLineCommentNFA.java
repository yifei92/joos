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
 * NFA for multi line comments /* ... */
public class MultiLineCommentNFA extends NFA {

	private static final int STATE_START = 0;
	private static final int STATE_OPEN_SLASH = 1;
	private static final int STATE_OPEN_STAR = 2;
	private static final int STATE_CHARS = 3;
	private static final int STATE_CLOSE_STAR = 4;
	private static final int STATE_CLOSE_SLASH = 5;

	private static final Set<Character> STAR_EXCLUSIONS = new HashSet<Character>(Arrays.asList('*'));
	private static final Set<Character> STAR_SLASH_EXCLUSIONS = new HashSet<Character>(Arrays.asList('*','/'));
	
	protected Transitions getTransitions(int state) {
		Transitions transitions = null;
		Map<Character, Integer> table = new HashMap<>();
		switch (state) {
			case STATE_START:
				table.put('/', STATE_OPEN_SLASH);
				break;
			case STATE_OPEN_SLASH:
				table.put('*', STATE_OPEN_STAR);
				break;
			case STATE_OPEN_STAR:
				table.put('*', STATE_CLOSE_STAR);
				// Allow for all chars except the * char else this will be a javadoc comment
				TransitionTableUtil.putAllCharExcept(table, STAR_EXCLUSIONS, STATE_CHARS);
				break;
			case STATE_CHARS:
				// Allow for all chars except the * char
				TransitionTableUtil.putAllCharExcept(table, STAR_EXCLUSIONS, STATE_CHARS);
				table.put('*', STATE_CLOSE_STAR);
				break;
			case STATE_CLOSE_STAR:
				// Allow for all chars except the / char
				TransitionTableUtil.putAllCharExcept(table, STAR_SLASH_EXCLUSIONS, STATE_CHARS);
				table.put('/', STATE_CLOSE_SLASH);
				table.put('*', STATE_CLOSE_STAR);
				break;
			case STATE_CLOSE_SLASH:
				break;
		}
		return new Transitions(state, table);
	}

	protected Set<Integer> getStates() {
		return new HashSet<Integer>(
			Arrays.asList(
				STATE_START, 
				STATE_OPEN_SLASH,
				STATE_OPEN_STAR,
				STATE_CHARS,
				STATE_CLOSE_STAR,
				STATE_CLOSE_SLASH));
	}

	protected Set<Integer> getAcceptStates() {
		return new HashSet<Integer>(Arrays.asList(STATE_CLOSE_SLASH));
	}

	protected void onCharAccepted(char newChar) {
		/** noop */
	}

	public List<TerminalToken> getTokens() {
		List<TerminalToken> tokens = new ArrayList<>();
		tokens.add(TerminalToken.getToken(TokenType.COMMENT_MULTI_LINE));
		return tokens;
	}
}
