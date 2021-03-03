package app.service.interfaces;

import java.util.List;
import java.util.Optional;

import app.model.Polymer;

public interface PolymerAnalyzer {

	int calculateHammingDistance(Polymer firstPolymer, Polymer secondPolymer);

	List<Integer> calculateSubsequenceLocations(Polymer polymer, Polymer subpolymer);

	Optional<String> calculateLongestCommonSubsequence(List<Polymer> polymers);

}