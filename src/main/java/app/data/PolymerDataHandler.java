package app.data;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PolymerDataHandler {

	private final String DNA = "DNA";
	private final String RNA = "RNA";
	private final String PROTEIN = "protein";

	private PolymerRepository polymerRepository;

	PolymerDataHandler(PolymerRepository polymerRepository) {
		super();
		this.polymerRepository = polymerRepository;
	}

	public List<String> getTypes() {
		return List.of(DNA, RNA, PROTEIN);
	}

	public Page<String> findTags(String type, Pageable pageable) {
		return polymerRepository.findTags(type, pageable);
	}

	public Page<Polymer> findPolymers(String type, String tag, Pageable pageable) {
		return polymerRepository.findPolymers(type, tag, pageable);
	}

	public List<Polymer> findPolymers(String type, List<String> tags, List<Long> ids) {
		return polymerRepository.findPolymers(type, tags, ids);
	}

	public Page<Polymer> findPolymers(String type, List<String> tags, List<Long> ids, Pageable pageable) {
		return polymerRepository.findPolymers(type, tags, ids, pageable);
	}

	public Page<Polymer> findDNAs(List<String> tags, List<Long> ids, Pageable pageable) {
		return polymerRepository.findPolymers(DNA, tags, ids, pageable);
	}

	public Page<Polymer> findRNAs(List<String> tags, List<Long> ids, Pageable pageable) {
		return polymerRepository.findPolymers(RNA, tags, ids, pageable);
	}

	public void saveDNAs(String tag, List<String> sequences) {
		Set<Character> validMonomers = Set.of('A', 'C', 'G', 'T');
		savePolymers(DNA, tag, sequences, validMonomers);
	}

	public void saveRNAs(String tag, List<String> sequences) {
		Set<Character> validMonomers = Set.of('A', 'C', 'G', 'U');
		savePolymers(RNA, tag, sequences, validMonomers);
	}

	public void saveProteins(String tag, List<String> sequences) {
		Set<Character> validMonomers = Set.of('A', 'R', 'N', 'D', 'C', 'E', 'Q', 'G', 'H', 'I', 'L', 'K', 'M', 'F', 'P', 'S', 'T', 'W', 'Y', 'V');
		savePolymers(PROTEIN, tag, sequences, validMonomers);
	}

	public void updateTag(String type, String tag, String string) {
		polymerRepository.updateTag(type, tag, tag);
	}

	public void deletePolymers(String type) {
		polymerRepository.deletePolymers(type);
	}

	public void deletePolymers(String type, String tag) {
		polymerRepository.deletePolymers(type, tag);
	}

	public void deletePolymers(String type, String tag, long id) {
		polymerRepository.deletePolymers(type, tag, id);
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
