package app.data;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RNADataHandler {

	private final String RNA = "RNA";

	private PolymerRepository polymerRepository;

	RNADataHandler(PolymerRepository polymerRepository) {
		super();
		this.polymerRepository = polymerRepository;
	}

	public Page<Polymer> findRNAs(List<String> tags, List<Long> ids, Pageable pageable) {
		return polymerRepository.findPolymers(RNA, tags, ids, pageable);
	}

	public void saveRNAs(String tag, List<String> sequences) {
		Set<Character> validMonomers = Set.of('A', 'C', 'G', 'U');
		savePolymers(RNA, tag, sequences, validMonomers);
	}

	private void savePolymers(String type, String tag, List<String> sequences, Set<Character> validMonomers) {

		sequences.forEach(sequence -> validateSequence(sequence, validMonomers));

		List<Polymer> polymers = sequences.stream().map(sequence -> new Polymer(tag, type, sequence)).collect(Collectors.toList());

		polymerRepository.saveAll(polymers);

	}

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

}
