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
 * NFA for javadoc line comments that start with  /** and have a * at the beginning of every line
 */
public class JavadocCommentNFA extends NFA {

	private static final int STATE_START = 0;
	private static final int STATE_OPEN_SLASH = 1;
	private static final int STATE_OPEN_STAR_FIRST = 2;
	private static final int STATE_CHARS = 3;
	private static final int STATE_NEWLINE = 4;
	private static final int STATE_NEWLINE_STAR = 5;
	private static final int STATE_CLOSE_STAR = 6;
	private static final int STATE_CLOSE_SLASH = 7;

	private static final Set<Character> NEWLINE_STAR_EXCLUSIONS =
		new HashSet<Character>(Arrays.asList(System.lineSeparator().charAt(0), '*'));
	private static final Set<Character> NEWLINE_STAR_EXCLUSIONS_SLASH =
		new HashSet<Character>(Arrays.asList(System.lineSeparator().charAt(0), '*', '/'));
		/**
		 *
		 * asdasd
		 */
	protected Transitions getTransitions(int state) {
		Transitions transitions = null;
		Map<Character, Integer> table = new HashMap<>();
		switch (state) {
			case STATE_START:
				table.put('/', STATE_OPEN_SLASH);
				break;
			case STATE_OPEN_SLASH:
				table.put('*', STATE_OPEN_STAR_FIRST);
				break;
			case STATE_OPEN_STAR_FIRST:
				table.put('*', STATE_CHARS);
				break;
			case STATE_CHARS:
				table.put('*', STATE_CLOSE_STAR);
				table.put(System.lineSeparator().charAt(0), STATE_NEWLINE);
				// Allow for all chars except the \n char
				TransitionTableUtil.putAllCharExcept(table, NEWLINE_STAR_EXCLUSIONS, STATE_CHARS);
				break;
			case STATE_NEWLINE:
				table.put('*', STATE_NEWLINE_STAR);
				table.put('\t', STATE_NEWLINE);
				table.put(' ', STATE_NEWLINE);
				break;
			case STATE_NEWLINE_STAR:
				table.put('*', STATE_NEWLINE_STAR);
				table.put(System.lineSeparator().charAt(0), STATE_NEWLINE);
				table.put('/', STATE_CLOSE_SLASH);
				TransitionTableUtil.putAllCharExcept(table, NEWLINE_STAR_EXCLUSIONS_SLASH, STATE_CHARS);
				break;
			case STATE_CLOSE_STAR:
				table.put('*', STATE_CLOSE_STAR);
				table.put(System.lineSeparator().charAt(0), STATE_NEWLINE);
				table.put('/', STATE_CLOSE_SLASH);
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
		tokens.add(TerminalToken.getToken(TokenType.COMMENT_JAVADOC));
		return tokens;
	}
}
