package main.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import main.model.DNA;
import main.model.NucleicAcid;
import main.model.Polymer;
import main.model.Protein;
import main.model.RNA;
import main.repository.Sequence;
import main.repository.SequenceRepository;

@Service
public class PolymerService {

	public static final String DNA = "dna";
	public static final String RNA = "rna";
	public static final String PROTEIN = "protein";

	@Autowired
	SequenceRepository sequenceRepository;

	public Page<String> findTags(String type, Pageable pageable) {
		return sequenceRepository.findTags(type, pageable);
	}

	public Page<Long> findIds(String type, String tag, Pageable pageable) {
		return sequenceRepository.findIds(type, tag, pageable);
	}

	public Optional<Polymer> findPolymer(String type, String tag, long id) {
		return sequenceRepository.findSequence(type, tag, id).map(sequence -> getPolymer(type, sequence));
	}

	public List<Polymer> findPolymers(String type, List<String> tags, List<Long> ids) {

		List<String> sequences = sequenceRepository.findSequences(type, tags, ids);
		List<Polymer> polymers = sequences.stream().map(sequence -> getPolymer(type, sequence)).collect(Collectors.toList());

		return polymers;

	}

	public Optional<NucleicAcid> findNucleicAcid(String type, String tag, long id) {
		return sequenceRepository.findSequence(type, tag, id).map(sequence -> getNucleicAcid(type, sequence));
	}

	public void savePolymers(String type, String tag, List<String> sequences) {
		sequences.forEach(sequence -> getPolymer(type, sequence));
		sequenceRepository.saveAll(sequences.stream().map(sequence -> new Sequence(tag, type, sequence)).collect(Collectors.toList()));
	}

	public void updateTag(String type, String tag, String newTag) {
		sequenceRepository.updateTag(type, tag, newTag);
	}

	public void updatePolymer(String type, String tag, long id, String newSequence) {
		sequenceRepository.updateSequence(type, tag, id, newSequence);
	}

	public void deletePolymers() {
		sequenceRepository.deleteAll();
	}

	public void deletePolymers(String type) {
		sequenceRepository.deleteSequences(type);
	}

	public void deletePolymers(String type, String tag) {
		sequenceRepository.deleteSequences(type, tag);
	}

	public void deletePolymer(String type, String tag, long id) {
		sequenceRepository.deleteSequence(type, tag, id);
	}

	private Polymer getPolymer(String type, String sequence) {

		switch (type) {
		case DNA:
			return new DNA(sequence);
		case RNA:
			return new RNA(sequence);
		case PROTEIN:
			return new Protein(sequence);
		default:
			throw new IllegalArgumentException("invalid type");
		}

	}

	private NucleicAcid getNucleicAcid(String type, String sequence) {

		switch (type) {
		case DNA:
			return new DNA(sequence);
		case RNA:
			return new RNA(sequence);
		default:
			throw new IllegalArgumentException("invalid type");
		}

	}

}
