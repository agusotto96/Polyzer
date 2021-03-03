package app.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import app.data.Sequence;
import app.data.SequenceRepository;
import app.model.NucleicAcid;
import app.model.Polymer;

@Service
public class PolymerHandler {

	@Autowired
	private SequenceRepository sequenceRepository;

	@Autowired
	private PolymerFactory polymerFactory;

	public Page<String> findTags(String type, Pageable pageable) {
		return sequenceRepository.findTags(type, pageable);
	}

	public Page<Long> findIds(String type, String tag, Pageable pageable) {
		return sequenceRepository.findIds(type, tag, pageable);
	}

	public Optional<Polymer> findPolymer(String type, String tag, long id) {
		return sequenceRepository.findSequence(type, tag, id).map(sequence -> polymerFactory.getPolymer(type, sequence));
	}

	public List<Polymer> findPolymers(String type, List<String> tags, List<Long> ids) {

		List<String> sequences = sequenceRepository.findSequences(type, tags, ids);
		List<Polymer> polymers = sequences.stream().map(sequence -> polymerFactory.getPolymer(type, sequence)).collect(Collectors.toList());

		return polymers;

	}

	public Optional<NucleicAcid> findNucleicAcid(String type, String tag, long id) {
		return sequenceRepository.findSequence(type, tag, id).map(sequence -> polymerFactory.getNucleicAcid(type, sequence));
	}

	public void savePolymers(String type, String tag, List<String> sequences) {
		
		List<Polymer> polymers = sequences.stream().map(sequence -> polymerFactory.getPolymer(type, sequence)).collect(Collectors.toList());
		sequenceRepository.saveAll(polymers.stream().map(polymer -> new Sequence(tag, type, polymer.getSequence())).collect(Collectors.toList()));
		
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

}
