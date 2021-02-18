package main.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

	public static int calculateHammingDistance(Polymer firstPolymer, Polymer secondPolymer) {

		if (firstPolymer == null || secondPolymer == null) {
			throw new IllegalArgumentException("polymers cannot be empty");
		}

		if (firstPolymer.getSequence().length() != secondPolymer.getSequence().length()) {
			throw new IllegalArgumentException("the length of the polymers must be equal");
		}

		int hammingDistance = 0;

		for (int index = 0; index < firstPolymer.getSequence().length(); index++) {
			if (firstPolymer.getSequence().charAt(index) != secondPolymer.getSequence().charAt(index)) {
				hammingDistance++;
			}
		}

		return hammingDistance;

	}

	public static List<Integer> findSubsequenceLocations(Polymer polymer, Polymer subpolymer) {

		if (polymer == null || subpolymer == null) {
			throw new IllegalArgumentException("polymers cannot be empty");
		}

		if (polymer.getSequence().length() <= subpolymer.getSequence().length()) {
			throw new IllegalArgumentException("the polymer size must be greater than the subpolymer size");
		}

		List<Integer> locations = new ArrayList<>(polymer.getSequence().length() - subpolymer.getSequence().length());

		for (int index = 0; index <= polymer.getSequence().length() - subpolymer.getSequence().length(); index++) {

			if (polymer.getSequence().substring(index, index + subpolymer.getSequence().length()).equals(subpolymer.getSequence())) {
				locations.add(index);
			}

		}

		return locations;

	}

	public static Optional<String> findLongestCommonSubsequence(List<Polymer> polymers) {

		if (polymers == null || polymers.isEmpty()) {
			throw new IllegalArgumentException("polymers cannot be null nor empty");
		}

		if (polymers.size() < 2) {
			throw new IllegalArgumentException("at least two sequences are needed to compare");
		}

		try {

			String firstSequence = polymers.get(0).getSequence();

			for (int i = firstSequence.length(); i > 0; i--) {

				for (int j = 0; j <= firstSequence.length() - i; j++) {

					String subsequence = firstSequence.substring(j, j + i);
					int counter = 0;

					for (int k = 1; k < polymers.size(); k++) {

						if (polymers.get(k).getSequence().contains(subsequence)) {
							counter++;
						} else {
							counter = 0;
							break;
						}

					}

					if (counter == polymers.size() - 1) {
						return Optional.of(subsequence);
					}

				}

			}

			return Optional.empty();

		} catch (NullPointerException e) {
			throw new IllegalArgumentException("polymers cannot be null");
		}

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
