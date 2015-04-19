package mastermind_ai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ListIterator;

public class Computer extends Player {

	private static int secretLength = 4;
	private static String mySecret, opponentSecret;
	private static ArrayList<String> solutions;
	private static Computer instance;

	private Computer() {
	}

	public static Computer getInstance() {
		if (instance == null) {
			instance = new Computer();
			instance.init();
		}
		return instance;
	}

	// initialization code (in case multiple games are played in one session)
	public void init() {
		mySecret = instance.generateSecret();
		solutions = instance.generateSolutions();
		opponentSecret = null;
	}

	// sets Opponent secret
	public void setOpponentSecret(String secret) {
		opponentSecret = secret;
	}

	// returns Computer secret
	public String getSecret() {
		return mySecret;
	}

	// get 4 raandom non-repeating digits from 0..9 as a string
	public String generateSecret() {
		ArrayList<String> numbers = new ArrayList<String>();
		String secret = "";

		for (int i = 0; i < 10; i++)
			numbers.add(0, String.format("%d", i));

		Collections.shuffle(numbers);
		numbers = new ArrayList<String>(numbers.subList(0, secretLength));

		for (String c : numbers)
			secret += c;

		return secret;
	}

	// construct the initial solution set
	private ArrayList<String> generateSolutions() {
		ArrayList<String> numbers = new ArrayList<String>();
		for (int i = 0; i < 10; i++)
			numbers.add(0, String.format("%d", i));
		solutions = getPermutations(secretLength, numbers);
		Collections.shuffle(solutions);
		return solutions;
	}

	// gather all 4-permutations of a string in a list of strings
	private ArrayList<String> getPermutations(int size,
			ArrayList<String> numbers) {
		ArrayList<String> suffixes = new ArrayList<String>();
		if (size > 0) {
			for (String s : getPermutations(size - 1, numbers))
				for (String c : numbers)
					if (!s.contains(c))
						suffixes.add(0, s + c);
		} else
			suffixes.add("");
		return suffixes;
	}

	// evaluate the first guess from solutions against the opponent secret
	// eliminate all solutions that do not evaluate the same against our guess
	// return the guess
	public String makeGuess() {
		String newGuess = solutions.get(0);
		Result newResult = evaluate(newGuess, opponentSecret);
		ListIterator<String> iter = solutions.listIterator();
		while (iter.hasNext()) {
			if (!evaluate(iter.next(), newGuess).equals(newResult))
				iter.remove();
		}
		return newGuess;
	}
}
