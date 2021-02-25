package main.model.entity;

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

	public void setSequence(String sequence) {
		this.sequence = sequence;
		this.validatePolymer();
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

		for (int index = 0; index <= this.sequence.length() - patternSize; index++) {

			String pattern = this.sequence.substring(index, index + patternSize);

			if (patternsLocations.get(pattern) == null) {
				patternsLocations.put(pattern, new LinkedList<Integer>());
			}

			while (!patternsLocations.get(pattern).isEmpty() && index + patternSize - patternsLocations.get(pattern).peek() > clumpSize) {
				patternsLocations.get(pattern).pop();
			}

			patternsLocations.get(pattern).add(index);

			if (patternsLocations.get(pattern).size() == patternTimes) {
				patterns.add(pattern);
			}

		}

		return patterns;

	}

	private void validatePolymer() {

		if (this.sequence == null || this.sequence.isBlank()) {
			throw new IllegalArgumentException("sequence cannot be empty");
		}

		for (char monomer : this.sequence.toCharArray()) {
			if (!getValidMonomers().contains(monomer)) {
				throw new IllegalArgumentException("sequence contains invalid monomer");
			}
		}

	}

	abstract Set<Character> getValidMonomers();

}
