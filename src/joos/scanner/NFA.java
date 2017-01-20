package joos.scanner;

import joos.commons.TerminalToken;

public interface NFA {

	/**
	 * Consumes one character from the input stream.
	 * Returns true if the NFA made a transition as a result of this new
	 * char. False otherwise.
	 */
	public boolean consume(char newChar);

	/**
	 * Returns true if the NFA is in an accepting state.
	 */
	public boolean isAccepting();

	/**
	 * Resets the NFA.
	 */
	public void reset();

	/**
	 * Returns the token or tokens that this NFA scans for.
	 */
	public TerminalToken[] getTokens();
}
