package joos.scanner;

import java.util.Map;

public class TransitionTableUtil {

	public static void putAllLetters(Map<Character, Integer> table, int toState) {
		// Put upper case
		for (char c = 65 ; c <= 90 ; c++) {
			table.put(c, toState);
		}
		// Put lower case
		for (char c = 97 ; c <= 122 ; c++) {
			table.put(c, toState);
		}
	}

	public static void putAllDigits(Map<Character, Integer> table, int toState) {
		for (char c = 48 ; c <= 57 ; c++) {
			table.put(c, toState);
		}
	}
}