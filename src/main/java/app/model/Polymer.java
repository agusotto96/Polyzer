package app.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public abstract class Polymer {

	String sequence;

	public Polymer(String sequence) {
		super();
		this.sequence = sequence;
		this.validatePolymer();
	}

	public String getSequence() {
		return sequence;
	}

	public Map<Character, Integer> getMonomerCount() {

		Map<Character, Integer> monomerCount = new HashMap<>();

		for (char monomer : this.sequence.toCharArray()) {
			monomerCount.put(monomer, monomerCount.getOrDefault(monomer, 0) + 1);
		}

		return monomerCount;

	}

	public Set<String> getClumpFormingPatterns(int patternSize, int patternTimes, int clumpSize) {

		Map<String, LinkedList<Integer>> patternsLocations = new HashMap<String, LinkedList<Integer>>();
		Set<String> patterns = new HashSet<String>();

		for (int i = 0; i <= this.sequence.length() - patternSize; i++) {

			String pattern = this.sequence.substring(i, i + patternSize);

			if (patternsLocations.get(pattern) == null) {
				patternsLocations.put(pattern, new LinkedList<Integer>());
			}

			while (!patternsLocations.get(pattern).isEmpty() && i + patternSize - patternsLocations.get(pattern).peek() > clumpSize) {
				patternsLocations.get(pattern).pop();
			}

			patternsLocations.get(pattern).add(i);

			if (patternsLocations.get(pattern).size() == patternTimes) {
				patterns.add(pattern);
			}

		}

		return patterns;

	}

	private void validatePolymer() {

		if (this.sequence == null || this.sequence.isBlank()) {
			throw new InvalidSequenceException("sequence cannot be empty");
		}

		for (char monomer : this.sequence.toCharArray()) {
			if (!getValidMonomers().contains(monomer)) {
				throw new InvalidSequenceException("sequence contains invalid monomer");
			}
		}

	}

	public class InvalidSequenceException extends RuntimeException {

		private static final long serialVersionUID = -6970237712012706976L;

		public InvalidSequenceException(String errorMessage) {
			super(errorMessage);
		}

	}

	abstract Set<Character> getValidMonomers();

}
