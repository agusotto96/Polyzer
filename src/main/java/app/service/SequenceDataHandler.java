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

	public List<Sequence> findSequences(String type, List<String> tags, List<Long> ids) {
		return sequenceRepository.findSequences(type, tags, ids);
	}

	public Page<Sequence> findSequences(String type, List<String> tags, List<Long> ids, Pageable pageable) {
		return sequenceRepository.findSequences(type, tags, ids, pageable);
	}

	public void saveSequences(String type, String tag, List<String> sequences) {
		polymerFactory.getPolymers(type, sequences);
		sequenceRepository.saveAll(sequences.stream().map(sequence -> new Sequence(tag, type, sequence)).collect(Collectors.toList()));
	}

	public void updateTag(String type, String tag, String newTag) {
		sequenceRepository.updateTag(type, tag, newTag);
	}

	public void deleteSequences(String type) {
		sequenceRepository.deleteSequences(type);
	}

	public void deleteSequences(String type, String tag) {
		sequenceRepository.deleteSequences(type, tag);
	}

	public void deleteSequence(String type, String tag, long id) {
		sequenceRepository.deleteSequence(type, tag, id);
	}

}
