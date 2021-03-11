package app.presentation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.data.Sequence;
import app.model.NucleicAcid;
import app.model.Polymer;
import app.service.PolymerAnalyzer;
import app.service.PolymerFactory;
import app.service.SequenceDataHandler;

@RestController
@RequestMapping("polymers/{type}/analyzes")
public class AnalysisController extends BaseController {

	@Autowired
	private PolymerFactory polymerFactory;

	@Autowired
	private SequenceDataHandler sequenceDataHandler;

	@Autowired
	private PolymerAnalyzer polymerAnalyzer;

	@GetMapping("monomer-count")
	Map<String, Object> getMonomerCounts(
			@PathVariable String type, 
			@RequestParam(defaultValue = "") List<String> tags, 
			@RequestParam(defaultValue = "") List<Long> ids, 
			@RequestParam int page, 
			@RequestParam int size) {

		Pageable pageable = PageRequest.of(page, size, Sort.by("tag", "id"));
		Page<Sequence> sequences = sequenceDataHandler.findSequences(type, tags, ids, pageable);

		Page<Object> counts = sequences.map(sequence -> {
			
			Polymer polymer = polymerFactory.getPolymer(sequence.getType(), sequence.getValue());
			
			var count = new HashMap<>();
			count.put(sequence.getId(), polymer.getMonomerCount());
			
			return count;
			
		});

		return formatPage("monomer-counts", counts);

	}

	@GetMapping("clump-forming-patterns")
	Map<String, Object> getClumpFormingPatterns(
			@PathVariable String type, 
			@RequestParam(defaultValue = "") List<String> tags, 
			@RequestParam(defaultValue = "") List<Long> ids, 
			@RequestParam int patternSize, 
			@RequestParam int patternTimes, 
			@RequestParam int clumpSize, 
			@RequestParam int page,
			@RequestParam int size) {

		Pageable pageable = PageRequest.of(page, size, Sort.by("tag", "id"));
		Page<Sequence> sequences = sequenceDataHandler.findSequences(type, tags, ids, pageable);

		Page<Object> sequencesPatterns = sequences.map(sequence -> {

			Polymer polymer = polymerFactory.getPolymer(sequence.getType(), sequence.getValue());

			var sequencePatterns = new HashMap<>();
			sequencePatterns.put(sequence.getId(), polymer.getClumpFormingPatterns(patternSize, patternTimes, clumpSize));

			return sequencePatterns;

		});

		return formatPage("patterns", sequencesPatterns);

	}

	@GetMapping("longest-common-subsequence")
	Optional<String> calculateLongestCommonSubsequence(
			@PathVariable String type, 
			@RequestParam(defaultValue = "") List<String> tags, 
			@RequestParam(defaultValue = "") List<Long> ids) {

		List<Sequence> sequences = sequenceDataHandler.findSequences(type, tags, ids);
		List<Polymer> polymers = polymerFactory.getPolymers(type, sequences.stream().map(s -> s.getValue()).collect(Collectors.toList()));

		return polymerAnalyzer.calculateLongestCommonSubsequence(polymers);

	}
	
	@GetMapping("reverse-complement")
	Map<String, Object> getReverseComplement(
			@PathVariable String type, 
			@RequestParam(defaultValue = "") List<String> tags, 
			@RequestParam(defaultValue = "") List<Long> ids, 
			@RequestParam int page, 
			@RequestParam int size) {

		Pageable pageable = PageRequest.of(page, size, Sort.by("tag", "id"));
		Page<Sequence> sequences = sequenceDataHandler.findSequences(type, tags, ids, pageable);

		Page<Object> reverseComplements = sequences.map(sequence -> {

			NucleicAcid nucleicAcid = polymerFactory.getNucleicAcid(sequence.getType(), sequence.getValue());

			var reverseComplement = new HashMap<>();
			reverseComplement.put(sequence.getId(), nucleicAcid.getReverseComplement());

			return reverseComplement;

		});

		return formatPage("reverse-complements", reverseComplements);

	}

}
