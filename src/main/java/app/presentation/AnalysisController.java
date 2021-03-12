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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.data.NucleicAcidDTO;
import app.data.PolymerDTO;
import app.data.PolymerDataHandler;
import app.domain.NucleicAcidType;
import app.domain.Polymer;
import app.domain.PolymerAnalyzer;
import app.domain.PolymerType;

@RestController
@RequestMapping("analyzes")
public class AnalysisController extends BaseController {

	@Autowired
	private PolymerDataHandler polymerDataHandler;

	@Autowired
	private PolymerAnalyzer polymerAnalyzer;

	@GetMapping("monomer-count")
	Map<String, Object> getMonomerCounts(
			@RequestParam PolymerType type, 
			@RequestParam(defaultValue = "") List<String> tags, 
			@RequestParam(defaultValue = "") List<Long> ids, 
			@RequestParam int page, 
			@RequestParam int size) {

		Pageable pageable = PageRequest.of(page, size, Sort.by("tag", "id"));
		Page<PolymerDTO> sequences = polymerDataHandler.findPolymers(type, tags, ids, pageable);

		Page<Object> counts = sequences.map(sequence -> {

			var count = new HashMap<>();
			count.put(sequence.getId(), sequence.getPolymer().getMonomerCount());

			return count;

		});

		return formatPage("monomer-counts", counts);

	}

	@GetMapping("clump-forming-patterns")
	Map<String, Object> getClumpFormingPatterns(
			@RequestParam PolymerType type, 
			@RequestParam(defaultValue = "") List<String> tags, 
			@RequestParam(defaultValue = "") List<Long> ids, 
			@RequestParam int patternSize, 
			@RequestParam int patternTimes,
			@RequestParam int clumpSize, 
			@RequestParam int page, 
			@RequestParam int size) {

		Pageable pageable = PageRequest.of(page, size, Sort.by("tag", "id"));
		Page<PolymerDTO> sequences = polymerDataHandler.findPolymers(type, tags, ids, pageable);

		Page<Object> sequencesPatterns = sequences.map(sequence -> {

			var sequencePatterns = new HashMap<>();
			sequencePatterns.put(sequence.getId(), sequence.getPolymer().getClumpFormingPatterns(patternSize, patternTimes, clumpSize));

			return sequencePatterns;

		});

		return formatPage("patterns", sequencesPatterns);

	}

	@GetMapping("longest-common-subsequence")
	Optional<String> calculateLongestCommonSubsequence(
			@RequestParam PolymerType type, 
			@RequestParam(defaultValue = "") List<String> tags, 
			@RequestParam(defaultValue = "") List<Long> ids) {

		List<PolymerDTO> sequences = polymerDataHandler.findPolymers(type, tags, ids);
		List<Polymer> polymers = sequences.stream().map(sequence -> sequence.getPolymer()).collect(Collectors.toList());

		return polymerAnalyzer.calculateLongestCommonSubsequence(polymers);

	}

	@GetMapping("reverse-complement")
	Map<String, Object> getReverseComplement(
			@RequestParam NucleicAcidType type, 
			@RequestParam(defaultValue = "") List<String> tags, 
			@RequestParam(defaultValue = "") List<Long> ids, 
			@RequestParam int page, 
			@RequestParam int size) {

		Pageable pageable = PageRequest.of(page, size, Sort.by("tag", "id"));
		Page<NucleicAcidDTO> sequences = polymerDataHandler.findNucleicAcids(type, tags, ids, pageable);

		Page<Object> reverseComplements = sequences.map(sequence -> {

			var reverseComplement = new HashMap<>();
			reverseComplement.put(sequence.getId(), sequence.getNucleicAcid().getReverseComplement());

			return reverseComplement;

		});

		return formatPage("reverse-complements", reverseComplements);

	}

}
