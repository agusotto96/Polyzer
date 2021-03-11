package app.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import app.data.Sequence;
import app.data.SequenceRepository;

@Service
public class SequenceDataHandler {

	@Autowired
	private SequenceRepository sequenceRepository;

	@Autowired
	private PolymerFactory polymerFactory;

	public Page<String> findTags(String type, Pageable pageable) {
		return sequenceRepository.findTags(type, pageable);
	}

	public Page<Sequence> findSequences(String type, String tag, Pageable pageable) {
		return sequenceRepository.findSequences(type, tag, pageable);
	}

	public void saveSequences(String polymer, String tag, List<String> sequences) {
		polymerFactory.getPolymers(polymer, sequences);
		sequenceRepository.saveAll(sequences.stream().map(sequence -> new Sequence(tag, polymer, sequence)).collect(Collectors.toList()));
	}

	public void updateTag(String type, String tag, String newTag) {
		sequenceRepository.updateTag(type, tag, newTag);
	}

	public void deleteSequences(String polymer) {
		sequenceRepository.deleteSequences(polymer);
	}

	public void deleteSequences(String polymer, String tag) {
		sequenceRepository.deleteSequences(polymer, tag);
	}

	public void deleteSequence(String polymer, String tag, long id) {
		sequenceRepository.deleteSequence(polymer, tag, id);
	}

}
