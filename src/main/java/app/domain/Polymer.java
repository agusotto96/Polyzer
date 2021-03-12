package app.domain;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public abstract class Polymer {

	String value;

	Polymer(String value) {
		super();
		this.value = value;
		this.validatePolymer();
	}

	public String getValue() {
		return value;
	}

	public Map<Character, Integer> getMonomerCount() {

		Map<Character, Integer> monomerCount = new HashMap<>();

		for (char monomer : this.value.toCharArray()) {
			monomerCount.put(monomer, monomerCount.getOrDefault(monomer, 0) + 1);
		}

		return monomerCount;

	}

	public Set<String> getClumpFormingPatterns(int patternSize, int patternTimes, int clumpSize) {

		Map<String, LinkedList<Integer>> patternsLocations = new HashMap<String, LinkedList<Integer>>();
		Set<String> patterns = new HashSet<String>();

		for (int i = 0; i <= this.value.length() - patternSize; i++) {

			String pattern = this.value.substring(i, i + patternSize);

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

		if (this.value == null || this.value.isBlank()) {
			throw new IllegalArgumentException("sequence cannot be empty");
		}

		for (char monomer : this.value.toCharArray()) {
			if (!getValidMonomers().contains(monomer)) {
				throw new IllegalArgumentException("sequence contains invalid monomer");
			}
		}

	}

	abstract Set<Character> getValidMonomers();

}
