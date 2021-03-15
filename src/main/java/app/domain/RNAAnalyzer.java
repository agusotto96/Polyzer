package app.domain;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class RNAAnalyzer {

	public String getRNAReverseComplement(String sequence) {

		Map<Character, Character> complementaryMonomers = new HashMap<>(4);

		complementaryMonomers.put('C', 'G');
		complementaryMonomers.put('G', 'C');
		complementaryMonomers.put('A', 'U');
		complementaryMonomers.put('U', 'A');

		return getReverseComplement(sequence, complementaryMonomers);

	}

	private String getReverseComplement(String sequence, Map<Character, Character> complementaryMonomers) {

		StringBuilder builder = new StringBuilder(sequence.length());

		for (char nucleotide : sequence.toCharArray()) {
			builder.append(complementaryMonomers.get(nucleotide));
		}

		return builder.reverse().toString();

	}

}
