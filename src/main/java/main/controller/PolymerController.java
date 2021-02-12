package main.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import main.DTO.PolymerDTO;
import main.model.Polymer;
import main.model.PolymerAnalyzer;
import main.service.PolymerService;

@RestController
@RequestMapping("polymers")
public class PolymerController {

	private static final String POLYMER = "{type:" + PolymerService.DNA + "|" + PolymerService.RNA + "|" + PolymerService.PROTEIN + "}";
	private static final String NUCLEIC_ACID = "{type:" + PolymerService.DNA + "|" + PolymerService.RNA + "}";

	@Autowired
	PolymerService polymerService;

	@PostMapping()
	public List<PolymerDTO> savePolymers(@RequestBody List<PolymerDTO> DTOs) {

		try {
			return polymerService.savePolymers(DTOs);

		} catch (IllegalArgumentException exception) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
		}

	}
	
	@GetMapping("types")
	public Set<String> findTypes() {

		return Set.of(PolymerService.DNA, PolymerService.RNA, PolymerService.PROTEIN);

	}

	@GetMapping(POLYMER)
	public Map<String, Object> findPolymers(
			@PathVariable String type, 
			@RequestParam(defaultValue = "") List<String> tags,
			@RequestParam(defaultValue = "") List<Long> ids, 
			@RequestParam(defaultValue = "0") int page) {

		return formatPages("polymers", polymerService.findPolymers(type, tags, ids, PageRequest.of(page, 10)));

	}

	@GetMapping(POLYMER + "/tags")
	public Map<String, Object> findTags(@PathVariable String type, @RequestParam(defaultValue = "0") int page) {

		return formatPages("tags", polymerService.findTags(type, PageRequest.of(page, 10, Sort.by("tag"))));

	}

	@GetMapping(POLYMER +"/{tag}/ids")
	public Map<String, Object> findIds(
			@PathVariable String type, 
			@PathVariable String tag, 
			@RequestParam(defaultValue = "0") int page) {

		Page<Long> ids = polymerService.findIds(type, tag, PageRequest.of(page, 10, Sort.by("id")));

		if (ids.getContent().isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

		return formatPages("ids", ids);

	}

	@GetMapping("{id}/sequence")
	public String findPolymer(@PathVariable long id) {

		return polymerService.findPolymer(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)).getSequence();

	}

	@DeleteMapping(POLYMER)
	public void deletePolymers(
			@PathVariable String type,
			@RequestParam(defaultValue = "") List<String> tags, 
			@RequestParam(defaultValue = "") List<Long> ids) {

		polymerService.deletePolymers(type, ids, tags);

	}

	@GetMapping("{id}/monomer-count")
	public Map<Character, Integer> countMonomers(@PathVariable long id) {

		Polymer polymer = polymerService.mapToPolymer(polymerService.findPolymer(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));

		return polymer.countMonomers();

	}

	@GetMapping("{id}/clump-forming-patterns")
	public Set<String> findClumpFormingPatterns(
			@PathVariable long id, 
			@RequestParam int size, 
			@RequestParam int times, 
			@RequestParam int range) {

		Polymer polymer = polymerService.mapToPolymer(polymerService.findPolymer(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));

		return polymer.findClumpFormingPatterns(size, times, range);

	}

	@GetMapping(POLYMER + "/hamming-distance")
	public int calculateHammingDistance(
			@PathVariable String type, 
			@RequestParam(defaultValue = "") List<Long> ids, 
			@RequestParam(defaultValue = "") List<String> tags) {

		List<PolymerDTO> DTOs = polymerService.findPolymers(type, tags, ids);

		if (DTOs.size() != 2) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}

		return PolymerAnalyzer.calculateHammingDistance(polymerService.mapToPolymer(DTOs.get(0)), polymerService.mapToPolymer(DTOs.get(1)));

	}

	@GetMapping(POLYMER + "/subsequence-locations")
	public List<Integer> findSubsequenceLocations(
			@PathVariable String type, 
			@RequestParam(name = "sequence-id") long sequenceId, 
			@RequestParam(name = "subsequence-id") long subsequenceId) {

		PolymerDTO sequence = polymerService.findPolymer(sequenceId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		PolymerDTO subsequence = polymerService.findPolymer(subsequenceId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

		return PolymerAnalyzer.findSubsequenceLocations(polymerService.mapToPolymer(sequence), polymerService.mapToPolymer(subsequence));

	}

	@GetMapping(POLYMER + "/longest-common-subsequence")
	public Optional<String> findLongestCommonSubsequence(
			@PathVariable String type, 
			@RequestParam(defaultValue = "") List<Long> ids, 
			@RequestParam(defaultValue = "") List<String> tags) {

		List<PolymerDTO> DTOs = polymerService.findPolymers(type, tags, ids);

		List<Polymer> polymers = DTOs.stream().map(DTO -> polymerService.mapToPolymer(DTO)).collect(Collectors.toList());

		try {
			return PolymerAnalyzer.findLongestCommonSubsequence(polymers);
		} catch (IllegalArgumentException exception) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
		}

	}

	@GetMapping(NUCLEIC_ACID + "/{id}/reverse-complement")
	public String calculateReverseComplement(@PathVariable String type, @PathVariable long id) {

		PolymerDTO DTO = polymerService.findPolymer(type, id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

		return polymerService.mapToNucleicAcid(DTO).calculateReverseComplement();

	}

	private <Type extends Object> Map<String, Object> formatPages(String content, Page<Type> pages) {

		Map<String, Object> result = new HashMap<>(3);
		result.put("totalPages", pages.getTotalPages());
		result.put("number", pages.getNumber());
		result.put(content, pages.getContent());

		return result;

	}

}
