package app.service.implementations;

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
import app.service.interfaces.PolymerFactory;

@Service
public class PolymerDataHandler implements app.service.interfaces.PolymerDataHandler {

	@Autowired
	private SequenceRepository sequenceRepository;

	@Autowired
	private PolymerFactory polymerFactory;

	@Override
	public Page<String> findTags(String type, Pageable pageable) {
		return sequenceRepository.findTags(type, pageable);
	}

	@Override
	public Page<Long> findIds(String type, String tag, Pageable pageable) {
		return sequenceRepository.findIds(type, tag, pageable);
	}

	@Override
	public Optional<Polymer> findPolymer(String type, String tag, long id) {
		return sequenceRepository.findSequence(type, tag, id).map(sequence -> polymerFactory.getPolymer(type, sequence));
	}

	@Override
	public List<Polymer> findPolymers(String type, List<String> tags, List<Long> ids) {

		List<String> sequences = sequenceRepository.findSequences(type, tags, ids);
		List<Polymer> polymers = polymerFactory.getPolymers(type, sequences);

		return polymers;

	}

	@Override
	public Optional<NucleicAcid> findNucleicAcid(String type, String tag, long id) {
		return sequenceRepository.findSequence(type, tag, id).map(sequence -> polymerFactory.getNucleicAcid(type, sequence));
	}

	@Override
	public void savePolymers(String type, String tag, List<String> sequences) {

		List<Polymer> polymers = polymerFactory.getPolymers(type, sequences);
		sequenceRepository.saveAll(polymers.stream().map(polymer -> new Sequence(tag, type, polymer.getSequence())).collect(Collectors.toList()));

	}

	@Override
	public void updateTag(String type, String tag, String newTag) {
		sequenceRepository.updateTag(type, tag, newTag);
	}

	@Override
	public void updatePolymer(String type, String tag, long id, String newSequence) {
		sequenceRepository.updateSequence(type, tag, id, newSequence);
	}

	@Override
	public void deletePolymers() {
		sequenceRepository.deleteAll();
	}

	@Override
	public void deletePolymers(String type) {
		sequenceRepository.deleteSequences(type);
	}

	@Override
	public void deletePolymers(String type, String tag) {
		sequenceRepository.deleteSequences(type, tag);
	}

	@Override
	public void deletePolymer(String type, String tag, long id) {
		sequenceRepository.deleteSequence(type, tag, id);
	}

}
