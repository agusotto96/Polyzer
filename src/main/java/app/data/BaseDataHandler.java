package app.data;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public abstract class BaseDataHandler {

	PolymerRepository polymerRepository;
	PolymerDataHandler polymerDataHandler;

	BaseDataHandler(PolymerRepository polymerRepository, PolymerDataHandler polymerDataHandler) {
		super();
		this.polymerRepository = polymerRepository;
		this.polymerDataHandler = polymerDataHandler;
		this.polymerDataHandler.types.add(getType());
	}

	abstract String getType();

	abstract Set<Character> getValidMonomers();

	public Page<Polymer> findPolymers(List<String> tags, List<Long> ids, Pageable pageable) {
		return polymerRepository.findPolymers(getType(), tags, ids, pageable);
	}

	public void savePolymers(String tag, List<String> sequences) {

		sequences.forEach(sequence -> validateSequence(sequence, getValidMonomers()));

		List<Polymer> polymers = sequences.stream().map(sequence -> new Polymer(tag, getType(), sequence)).collect(Collectors.toList());

		polymerRepository.saveAll(polymers);

	}

	void validateSequence(String sequence, Set<Character> validMonomers) {

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
