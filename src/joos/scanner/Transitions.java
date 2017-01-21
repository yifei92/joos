package joos.scanner;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import joos.exceptions.InvalidTransitionException;

public class Transitions {

	private final int mState;
	private final Map<Character, Integer> mTransitionTable;

	public Transitions(int state, Map<Character, Integer> transitionTable) {
		mState = state;
		mTransitionTable = transitionTable;
	}

	/**
	 * Given a char returns the next state for the NFA
	 */
	public int transition(char nextChar) throws InvalidTransitionException {
		Integer nextState = mTransitionTable.get(nextChar);
		if (nextState == null) {
			throw new InvalidTransitionException(
				"State " + mState + " has no transition for " + nextChar);
		}
		return nextState;
	}
}