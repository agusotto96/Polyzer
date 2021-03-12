package app.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class SequenceAnalyzer {

	public Map<Character, Integer> getMonomerCount(String sequence) {

		Map<Character, Integer> monomerCount = new HashMap<>();

		for (char monomer : sequence.toCharArray()) {
			monomerCount.put(monomer, monomerCount.getOrDefault(monomer, 0) + 1);
		}

		return monomerCount;

	}

	public String getDNAReverseComplement(String sequence) {

		Map<Character, Character> complementaryMonomers = new HashMap<>(4);

		complementaryMonomers.put('C', 'G');
		complementaryMonomers.put('G', 'C');
		complementaryMonomers.put('A', 'T');
		complementaryMonomers.put('T', 'A');

		return getReverseComplement(sequence, complementaryMonomers);

	}

	public String getRNAReverseComplement(String sequence) {

		Map<Character, Character> complementaryMonomers = new HashMap<>(4);

		complementaryMonomers.put('C', 'G');
		complementaryMonomers.put('G', 'C');
		complementaryMonomers.put('A', 'U');
		complementaryMonomers.put('U', 'A');

		return getReverseComplement(sequence, complementaryMonomers);

	}

	public Set<String> getClumpFormingPatterns(String sequence, int patternSize, int patternTimes, int clumpSize) {

		Map<String, LinkedList<Integer>> patternsLocations = new HashMap<String, LinkedList<Integer>>();
		Set<String> patterns = new HashSet<String>();

		for (int i = 0; i <= sequence.length() - patternSize; i++) {

			String pattern = sequence.substring(i, i + patternSize);

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

	public int calculateHammingDistance(String sequenceA, String sequenceB) {

		if (sequenceA.length() != sequenceB.length()) {
			throw new IllegalArgumentException("the length of the sequences must be equal");
		}

		int hammingDistance = 0;

		for (int i = 0; i < sequenceA.length(); i++) {
			if (sequenceA.charAt(i) != sequenceB.charAt(i)) {
				hammingDistance++;
			}
		}

		return hammingDistance;

	}

	public List<Integer> calculateSubsequenceLocations(String sequence, String subsequence) {

		if (sequence.length() <= subsequence.length()) {
			throw new IllegalArgumentException("the sequence size must be greater than the subsequence size");
		}

		List<Integer> locations = new ArrayList<>(sequence.length() - subsequence.length());

		for (int i = 0; i <= sequence.length() - subsequence.length(); i++) {

			if (sequence.substring(i, i + subsequence.length()).equals(subsequence)) {
				locations.add(i);
			}

		}

		return locations;

	}

	public Optional<String> calculateLongestCommonSubsequence(List<String> sequences) {

		if (sequences.size() < 2) {
			throw new IllegalArgumentException("at least two sequences are needed");
		}

		String firstSequence = sequences.get(0);

		for (int i = firstSequence.length(); i > 0; i--) {

			for (int j = 0; j <= firstSequence.length() - i; j++) {

				String subsequence = firstSequence.substring(j, j + i);
				int counter = 0;

				for (int k = 1; k < sequences.size(); k++) {

					if (sequences.get(k).contains(subsequence)) {
						counter++;
					} else {
						counter = 0;
						break;
					}

				}

				if (counter == sequences.size() - 1) {
					return Optional.of(subsequence);
				}

			}

		}

		return Optional.empty();

	}

	private String getReverseComplement(String sequence, Map<Character, Character> complementaryMonomers) {

		StringBuilder builder = new StringBuilder(sequence.length());

		for (char nucleotide : sequence.toCharArray()) {
			builder.append(complementaryMonomers.get(nucleotide));
		}

		return builder.reverse().toString();

	}

}
