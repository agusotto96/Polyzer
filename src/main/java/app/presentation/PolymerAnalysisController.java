package app.presentation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import app.data.Polymer;
import app.data.PolymerDataHandler;
import app.domain.PolymerAnalyzer;

@RestController
@RequestMapping("polymers/{type}/analyzes")
public class PolymerAnalysisController {

	private PolymerDataHandler polymerDataHandler;
	private PolymerAnalyzer polymerAnalyzer;
	private ControllerHelper controllerHelper;

	PolymerAnalysisController(PolymerDataHandler polymerDataHandler, PolymerAnalyzer sequenceAnalyzer, ControllerHelper controllerHelper) {
		super();
		this.polymerDataHandler = polymerDataHandler;
		this.polymerAnalyzer = sequenceAnalyzer;
		this.controllerHelper = controllerHelper;
	}

	@GetMapping("monomer-count")
	Map<String, Object> getMonomerCounts(
			@PathVariable String type, 
			@RequestParam(defaultValue = "") List<String> tags, 
			@RequestParam(defaultValue = "") List<Long> ids, 
			@RequestParam int page, 
			@RequestParam int size) {

		Pageable pageable = PageRequest.of(page, size, Sort.by("tag", "id"));
		Page<Polymer> polymers = polymerDataHandler.findPolymers(type, tags, ids, pageable);

		Page<Object> counts = polymers.map(polymer -> {

			var count = new HashMap<>();
			count.put(polymer.getId(), polymerAnalyzer.getMonomerCount(polymer.getSequence()));

			return count;

		});

		return controllerHelper.formatPage(counts);

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
		Page<Polymer> polymers = polymerDataHandler.findPolymers(type, tags, ids, pageable);

		Page<Object> sequencesPatterns = polymers.map(polymer -> {

			var sequencePatterns = new HashMap<>();
			sequencePatterns.put(polymer.getId(), polymerAnalyzer.getClumpFormingPatterns(polymer.getSequence(), patternSize, patternTimes, clumpSize));

			return sequencePatterns;

		});

		return controllerHelper.formatPage(sequencesPatterns);

	}

	@GetMapping("longest-common-subsequence")
	Optional<String> calculateLongestCommonSubsequence(
			@PathVariable String type, 
			@RequestParam(defaultValue = "") List<String> tags, 
			@RequestParam(defaultValue = "") List<Long> ids) {

		List<Polymer> polymers = polymerDataHandler.findPolymers(type, tags, ids);

		if (polymers.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "sequences not found");
		}

		List<String> sequences = polymers.stream().map(polymer -> polymer.getSequence()).collect(Collectors.toList());

		try {
			return polymerAnalyzer.calculateLongestCommonSubsequence(sequences);
		} catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}

	}

}
