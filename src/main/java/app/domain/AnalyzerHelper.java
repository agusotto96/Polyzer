package app.domain;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class AnalyzerHelper {
	
	String reverseSequence(String sequence) {
		return new StringBuilder(sequence).reverse().toString();
	}

	StringBuilder replaceMonomers(String sequence, Map<Character, Character> complementaryMonomers) {

		StringBuilder replacedSequence = new StringBuilder(sequence.length());

		for (char nucleotide : sequence.toCharArray()) {
			if (complementaryMonomers.containsKey(nucleotide)) {
				replacedSequence.append(complementaryMonomers.get(nucleotide));
			} else {
				replacedSequence.append(nucleotide);
			}
		}
	
		return replacedSequence;

	}

	double calculatePolymerMass(String sequence, Map<Character, Double> monomerMass) {

		double mass = 0;

		for (char monomer : sequence.toCharArray()) {
			mass += monomerMass.get(monomer);
		}

		return mass;

	}

}
