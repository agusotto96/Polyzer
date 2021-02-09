package main.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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

import main.model.Protein;
import main.model.PolymerAnalyzer;
import main.repository.ProteinRepository;

@RestController
@RequestMapping("protein")
public class ProteinController {

	@Autowired
	ProteinRepository ProteinRepository;

	@PostMapping()
	public List<Protein> create(@RequestBody List<Map<String, String>> sequences) {

		try {

			List<Protein> Proteins = new ArrayList<>(sequences.size());

			for (Map<String, String> sequence : sequences) {
				Proteins.add(new Protein(sequence.get("tag"), sequence.get("sequence")));
			}

			return ProteinRepository.saveAll(Proteins);

		} catch (IllegalArgumentException exception) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
		}

	}

	@GetMapping()
	public Map<Object, Object> read(
			@RequestParam(defaultValue = "0") int page, 
			@RequestParam(required = false) List<Long> ids, 
			@RequestParam(required = false) List<String> tags) {

		Page<Protein> Proteins;

		if (ids == null && tags == null) {
			Proteins = ProteinRepository.findAll(PageRequest.of(page, 10));
		} else {
			ids = ids == null ? new ArrayList<>() : ids;
			tags = tags == null ? new ArrayList<>() : tags;
			Proteins = ProteinRepository.findByIdInOrTagIn(ids, tags, PageRequest.of(page, 10));
		}

		Map<Object, Object> result = new HashMap<>(3);
		result.put("totalPages", Proteins.getTotalPages());
		result.put("page", Proteins.getNumber());
		result.put("Proteins", Proteins.getContent());

		return result;

	}

	@GetMapping("tags")
	public Map<Object, Object> read(@RequestParam(defaultValue = "0") int page) {

		Page<String> tags = ProteinRepository.findAllTags(PageRequest.of(page, 10, Sort.by("tag")));

		Map<Object, Object> result = new HashMap<>(3);
		result.put("totalPages", tags.getTotalPages());
		result.put("page", tags.getNumber());
		result.put("tags", tags.getContent());

		return result;

	}

	@GetMapping("{tag}/ids")
	public Map<Object, Object> read(
			@PathVariable String tag, 
			@RequestParam(defaultValue = "0") int page) {

		Page<Long> ids = ProteinRepository.findIdsByTag(tag, PageRequest.of(page, 10, Sort.by("id")));

		if (ids.getContent().isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

		Map<Object, Object> result = new HashMap<>(3);
		result.put("totalPages", ids.getTotalPages());
		result.put("page", ids.getNumber());
		result.put("ids", ids.getContent());

		return result;

	}

	@GetMapping("{id}")
	public Protein read(@PathVariable long id) {

		return ProteinRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

	}

	@DeleteMapping()
	public void delete(
			@RequestParam(required = false) List<Long> ids, 
			@RequestParam(required = false) List<String> tags) {

		if (ids == null && tags == null) {
			ProteinRepository.deleteAll();
		} else {
			ids = ids == null ? new ArrayList<>() : ids;
			tags = tags == null ? new ArrayList<>() : tags;
			ProteinRepository.deleteByIdInOrTagIn(ids, tags);
		}

	}

	@GetMapping("{id}/monomer-count")
	public Map<Character, Integer> countMonomers(@PathVariable long id) {

		return ProteinRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)).countMonomers();

	}

	@GetMapping("{id}/clump-forming-patterns")
	public Set<String> findClumpFormingPatterns(
			@PathVariable long id, 
			@RequestParam int size, 
			@RequestParam int times, 
			@RequestParam int range) {

		return ProteinRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)).findClumpFormingPatterns(size, times, range);

	}

	@GetMapping("hamming-distance")
	public int calculateHammingDistance(
			@RequestParam(defaultValue = "") List<Long> ids, 
			@RequestParam(defaultValue = "") List<String> tags) {

		List<Protein> Proteins = ProteinRepository.findByIdInOrTagIn(ids, tags);

		if (Proteins.size() != 2) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}

		return PolymerAnalyzer.calculateHammingDistance(Proteins.get(0), Proteins.get(1));

	}

	@GetMapping("subsequence-locations")
	public List<Integer> findSubsequenceLocations(
			@RequestParam(name = "sequence-id") long sequenceId, 
			@RequestParam(name = "subsequence-id") long subsequenceId) {

		Protein sequence = ProteinRepository.findById(sequenceId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		Protein subsequence = ProteinRepository.findById(subsequenceId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

		return PolymerAnalyzer.findSubsequenceLocations(sequence, subsequence);

	}
	
	@GetMapping("longest-common-subsequence")
	public Optional<String> findLongestCommonSubsequence(
			@RequestParam(defaultValue = "") List<Long> ids, 
			@RequestParam(defaultValue = "") List<String> tags) {

		List<Protein> Proteins = ProteinRepository.findByIdInOrTagIn(ids, tags);
		
		try {
			return PolymerAnalyzer.findLongestCommonSubsequence(Proteins);
		} catch (IllegalArgumentException exception) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
		}

	}

}
