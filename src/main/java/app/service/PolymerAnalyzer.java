package app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import app.model.Polymer;

@Service
public class PolymerAnalyzer {

	public int calculateHammingDistance(Polymer firstPolymer, Polymer secondPolymer) {

		if (firstPolymer == null || secondPolymer == null) {
			throw new IllegalArgumentException("polymers cannot be empty");
		}

		if (firstPolymer.getSequence().length() != secondPolymer.getSequence().length()) {
			throw new IllegalArgumentException("the length of the polymers must be equal");
		}

		int hammingDistance = 0;

		for (int i = 0; i < firstPolymer.getSequence().length(); i++) {
			if (firstPolymer.getSequence().charAt(i) != secondPolymer.getSequence().charAt(i)) {
				hammingDistance++;
			}
		}

		return hammingDistance;

	}

	public List<Integer> calculateSubsequenceLocations(Polymer polymer, Polymer subpolymer) {

		if (polymer == null || subpolymer == null) {
			throw new IllegalArgumentException("polymers cannot be empty");
		}

		if (polymer.getSequence().length() <= subpolymer.getSequence().length()) {
			throw new IllegalArgumentException("the polymer size must be greater than the subpolymer size");
		}

		List<Integer> locations = new ArrayList<>(polymer.getSequence().length() - subpolymer.getSequence().length());

		for (int i = 0; i <= polymer.getSequence().length() - subpolymer.getSequence().length(); i++) {

			if (polymer.getSequence().substring(i, i + subpolymer.getSequence().length()).equals(subpolymer.getSequence())) {
				locations.add(i);
			}

		}

		return locations;

	}

	public Optional<String> calculateLongestCommonSubsequence(List<Polymer> polymers) {

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

}
