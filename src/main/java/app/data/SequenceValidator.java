package app.data;

import java.util.Set;

import org.springframework.stereotype.Service;

@Service
class SequenceValidator {

	private void validateSequence(String sequence, Set<Character> validMonomers) {

		if (sequence == null || sequence.isBlank()) {
			throw new IllegalArgumentException("sequence cannot be empty");
		}

		for (char monomer : sequence.toCharArray()) {
			if (!validMonomers.contains(monomer)) {
				throw new IllegalArgumentException("sequence contains invalid monomer");
			}
		}

	}

	void validateDNASequence(String sequence) {
		validateSequence(sequence, Set.of('A', 'C', 'G', 'T'));

	}

	void validateRNASequence(String sequence) {
		validateSequence(sequence, Set.of('A', 'C', 'G', 'U'));

	}

	void validateProteinSequence(String sequence) {
		validateSequence(sequence, Set.of('A', 'R', 'N', 'D', 'C', 'E', 'Q', 'G', 'H', 'I', 'L', 'K', 'M', 'F', 'P', 'S', 'T', 'W', 'Y', 'V'));
	}

}
