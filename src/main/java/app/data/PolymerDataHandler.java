package app.data;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class PolymerDataHandler {

	@Autowired
	private PolymerRepository polymerRepository;

	@Autowired
	private SequenceValidator sequenceValidator;

	public List<String> getTypes() {
		return Arrays.stream(PolymerType.values()).map(type -> type.name()).collect(Collectors.toList());
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
		return polymerRepository.findPolymers(PolymerType.DNA.name(), tags, ids, pageable);
	}

	public Page<Polymer> findRNAs(List<String> tags, List<Long> ids, Pageable pageable) {
		return polymerRepository.findPolymers(PolymerType.RNA.name(), tags, ids, pageable);
	}

	public void saveDNAs(String tag, List<String> sequences) {

		sequences.forEach(sequence -> sequenceValidator.validateDNASequence(sequence));

		List<Polymer> polymers = sequences.stream().map(sequence -> new Polymer(tag, PolymerType.DNA.name(), sequence)).collect(Collectors.toList());

		polymerRepository.saveAll(polymers);

	}

	public void saveRNAs(String tag, List<String> sequences) {

		sequences.forEach(sequence -> sequenceValidator.validateRNASequence(sequence));

		List<Polymer> polymers = sequences.stream().map(sequence -> new Polymer(tag, PolymerType.RNA.name(), sequence)).collect(Collectors.toList());

		polymerRepository.saveAll(polymers);

	}

	public void saveProteins(String tag, List<String> sequences) {

		sequences.forEach(sequence -> sequenceValidator.validateProteinSequence(sequence));

		List<Polymer> polymers = sequences.stream().map(sequence -> new Polymer(tag, PolymerType.PROTEIN.name(), sequence)).collect(Collectors.toList());

		polymerRepository.saveAll(polymers);

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

}

enum PolymerType {
	DNA, RNA, PROTEIN
}
