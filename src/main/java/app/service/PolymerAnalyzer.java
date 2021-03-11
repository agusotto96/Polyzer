package app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import app.model.Polymer;

@Service
public class PolymerAnalyzer {

	public int calculateHammingDistance(Polymer firstPolymer, Polymer secondPolymer) {

		if (firstPolymer.getValue().length() != secondPolymer.getValue().length()) {
			throw new IllegalArgumentException("the length of the polymers must be equal");
		}

		int hammingDistance = 0;

		for (int i = 0; i < firstPolymer.getValue().length(); i++) {
			if (firstPolymer.getValue().charAt(i) != secondPolymer.getValue().charAt(i)) {
				hammingDistance++;
			}
		}

		return hammingDistance;

	}

	public List<Integer> calculateSubsequenceLocations(Polymer polymer, Polymer subpolymer) {

		if (polymer.getValue().length() <= subpolymer.getValue().length()) {
			throw new IllegalArgumentException("the polymer size must be greater than the subpolymer size");
		}

		List<Integer> locations = new ArrayList<>(polymer.getValue().length() - subpolymer.getValue().length());

		for (int i = 0; i <= polymer.getValue().length() - subpolymer.getValue().length(); i++) {

			if (polymer.getValue().substring(i, i + subpolymer.getValue().length()).equals(subpolymer.getValue())) {
				locations.add(i);
			}

		}

		return locations;

	}

	public Optional<String> calculateLongestCommonSubsequence(List<Polymer> polymers) {

		if (polymers.size() < 2) {
			throw new IllegalArgumentException("at least two sequences are needed to compare");
		}

		String firstSequence = polymers.get(0).getValue();

		for (int i = firstSequence.length(); i > 0; i--) {

			for (int j = 0; j <= firstSequence.length() - i; j++) {

				String subsequence = firstSequence.substring(j, j + i);
				int counter = 0;

				for (int k = 1; k < polymers.size(); k++) {

					if (polymers.get(k).getValue().contains(subsequence)) {
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

	}

}
