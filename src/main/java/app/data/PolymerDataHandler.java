package app.data;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import app.domain.NucleicAcid;
import app.domain.NucleicAcidType;
import app.domain.Polymer;
import app.domain.PolymerFactory;
import app.domain.PolymerType;

@Service
public class PolymerDataHandler {

	@Autowired
	private SequenceRepository sequenceRepository;

	@Autowired
	private PolymerFactory polymerFactory;

	public Page<String> findTags(PolymerType type, Pageable pageable) {
		return sequenceRepository.findTags(type.name(), pageable);
	}

	public Page<PolymerDTO> findPolymers(PolymerType type, String tag, Pageable pageable) {
		return sequenceRepository.findSequences(type.name(), tag, pageable).map(sequence -> getPolymerDTO(sequence));
	}

	public List<PolymerDTO> findPolymers(PolymerType type, List<String> tags, List<Long> ids) {
		List<Sequence> sequences = sequenceRepository.findSequences(type.name(), tags, ids);
		return sequences.stream().map(sequence -> getPolymerDTO(sequence)).collect(Collectors.toList());
	}

	public Page<PolymerDTO> findPolymers(PolymerType type, List<String> tags, List<Long> ids, Pageable pageable) {
		return sequenceRepository.findSequences(type.name(), tags, ids, pageable).map(sequence -> getPolymerDTO(sequence));
	}

	public Page<NucleicAcidDTO> findNucleicAcids(NucleicAcidType type, List<String> tags, List<Long> ids, Pageable pageable) {
		return sequenceRepository.findSequences(type.name(), tags, ids, pageable).map(sequence -> getNucleicAcidDTO(sequence));
	}

	public void savePolymers(PolymerType type, String tag, List<String> sequences) {
		polymerFactory.getPolymers(type, sequences);
		sequenceRepository.saveAll(sequences.stream().map(sequence -> new Sequence(tag, type.name(), sequence)).collect(Collectors.toList()));
	}

	public void updateTag(PolymerType type, String tag, String newTag) {
		sequenceRepository.updateTag(type.name(), tag, newTag);
	}

	public void deletePolymers(PolymerType type) {
		sequenceRepository.deleteSequences(type.name());
	}

	public void deletePolymers(PolymerType type, String tag) {
		sequenceRepository.deleteSequences(type.name(), tag);
	}

	public void deletePolymers(PolymerType type, String tag, long id) {
		sequenceRepository.deleteSequence(type.name(), tag, id);
	}

	private PolymerDTO getPolymerDTO(Sequence sequence) {
		return new PolymerDTO(sequence.getId(), getPolymer(sequence));
	}

	private Polymer getPolymer(Sequence sequence) {
		return polymerFactory.getPolymer(PolymerType.valueOf(sequence.getType()), sequence.getValue());
	}

	private NucleicAcidDTO getNucleicAcidDTO(Sequence sequence) {
		return new NucleicAcidDTO(sequence.getId(), getNucleicAcid(sequence));
	}

	private NucleicAcid getNucleicAcid(Sequence sequence) {
		return polymerFactory.getNucleicAcid(NucleicAcidType.valueOf(sequence.getType()), sequence.getValue());
	}

}
