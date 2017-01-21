package joos.scanner;

import joos.commons.TerminalToken;
import joos.commons.TokenType;
import joos.exceptions.InvalidTransitionException;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.Iterator;

/**
 * Consists of a state machine where each state is an int.
 * 0 is always the starting state id.
 */
public abstract class NFA {

	private int mCurrentState = 0;
	private Map<Integer, Transitions> mTransitions;
	private Set<Integer> mStates;
	private Set<Integer> mAcceptStates;

	protected void init() {
		if (mStates == null) { 
			mStates = getStates();
		}
		if (mAcceptStates == null) {
			mAcceptStates = getAcceptStates();
		}
		if (mTransitions == null) {
			mTransitions = new HashMap<>();
			for (int state : mStates) {
				mTransitions.put(state, getTransitions(state));
			}
		}
	}

	/**
	 * Given a state returns all transitions out of it.
	 */
	protected abstract Transitions getTransitions(int state);

	/**
	 *	Returns all states. 
	 */
	protected abstract Set<Integer> getStates();
	
	/**
	 * Returns a set of state ids that are accepting states.
	 */
	protected abstract Set<Integer> getAcceptStates();

	protected abstract void onCharAccepted(char newChar);

	/**
	 * Returns the token or tokens that this NFA scans for.
	 */
	public abstract List<TerminalToken> getTokens();

	/**
	 * Consumes one character from the input stream.
	 * Returns true if the NFA made a transition as a result of this new
	 * char. False otherwise.
	 */
	public boolean consume(char newChar) {
		init();
		Transitions currentTransitions = mTransitions.get(mCurrentState); 
		try {
			mCurrentState = currentTransitions.transition(newChar);
			onCharAccepted(newChar);
			return true;
		} catch (InvalidTransitionException e) {
			//System.out.println(e.getMessage());
			return false;
		}
	}

	/**
	 * Returns true if the NFA is in an accepting state.
	 */
	public boolean isAccepting() {
		init();
		return mAcceptStates.contains(mCurrentState);
	}

	/**
	 * Resets the NFA.
	 */
	public void reset() {
		init();
		mCurrentState = 0;
	}
}
