package joos.scanner;

import java.util.Map;
import java.util.Set;
import java.util.HashSet;

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

	public static void putAllChars(Map<Character, Integer> table, int toState) {
		putAllCharExcept(table, new HashSet<Character>(), toState);
	}

	public static void putAllCharExcept(Map<Character, Integer> table, Set<Character> exclusions, int toState) {
		for (char c = 0 ; c < 127 ; c++) {
			if (!exclusions.contains(c)) {
				table.put(c, toState);
			}
		}
	}

	public static void putAllValidEscapeChars(Map<Character, Integer> table, int toState) {
		table.put('t', toState);
		table.put('b', toState);
		table.put('n', toState);
		table.put('r', toState);
		table.put('f', toState);
		table.put('\'', toState);
		table.put('\"', toState);
		table.put('\\', toState);
	}

	public static void putAllOctals(Map<Character, Integer> table, int toState) {
		for (char c = 48 ; c <= 55 ; c++) {
			table.put(c, toState);
		}
	}
}