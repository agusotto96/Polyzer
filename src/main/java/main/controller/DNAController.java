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
import org.springframework.data.domain.Pageable;
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

import main.model.DNA;
import main.model.Polymer;
import main.model.PolymerAnalyzer;
import main.repository.DNARepository;
import main.repository.PolymerRepository;
import main.repository.ProteinRepository;
import main.repository.RNARepository;

@RestController
@RequestMapping("{type}")
public class DNAController {

	@Autowired
	DNARepository DNARepository;

	@Autowired
	RNARepository RNARepository;

	@Autowired
	ProteinRepository ProteinRepository;
	
	private static final String DNA = "dna";
	private static final String RNA = "rna";
	private static final String PROTEINS = "proteins";

	@PostMapping
	public List<Polymer> create(
			@PathVariable String type, 
			@RequestBody List<Map<String, String>> sequences) {

		List<Polymer> polymers = new ArrayList<>(sequences.size());

		try {

			switch (type) {
			case DNA:
				polymers.addAll(DNARepository.saveAll(PolymerMapper.mapDNAs(sequences)));
				break;

			case RNA:
				polymers.addAll(RNARepository.saveAll(PolymerMapper.mapRNAs(sequences)));
				break;

			case PROTEINS:
				polymers.addAll(ProteinRepository.saveAll(PolymerMapper.mapProteins(sequences)));
				break;

			default:
				throw new ResponseStatusException(HttpStatus.NOT_FOUND);
			}

			return polymers;

		} catch (IllegalArgumentException exception) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
		}

	}

	@GetMapping()
	public Map<String, Object> read(
			@PathVariable String type, 
			@RequestParam(defaultValue = "0") int page, 
			@RequestParam(required = false) List<Long> ids, 
			@RequestParam(required = false) List<String> tags) {

		Pageable pageable = PageRequest.of(page, 10);

		switch (type) {
		case DNA:
			return formatPages(findByIdOrTag(page, pageable, ids, tags, DNARepository));

		case RNA:
			return formatPages(findByIdOrTag(page, pageable, ids, tags, RNARepository));

		case PROTEINS:
			return formatPages(findByIdOrTag(page, pageable, ids, tags, ProteinRepository));

		default:
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}



	@GetMapping("tags")
	public Map<String, Object> read(
			@PathVariable String type, 
			@RequestParam(defaultValue = "0") int page) {

		Page<String> tags;
		Pageable pageable = PageRequest.of(page, 10, Sort.by("tag"));

		switch (type) {
		case DNA:
			tags = DNARepository.findAllTags(pageable);
			break;

		case RNA:
			tags = RNARepository.findAllTags(pageable);
			break;

		case PROTEINS:
			tags = ProteinRepository.findAllTags(pageable);
			break;

		default:
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

		return formatPages(tags);

	}

	@GetMapping("{tag}/ids")
	public Map<String, Object> read(
			@PathVariable String type, 
			@PathVariable String tag, 
			@RequestParam(defaultValue = "0") int page) {

		Page<Long> ids = DNARepository.findIdsByTag(tag, PageRequest.of(page, 10, Sort.by("id")));

		if (ids.getContent().isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
		

		return formatPages(ids);

	}

	@GetMapping("{id}")
	public DNA read(@PathVariable long id) {

		return DNARepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

	}

	@DeleteMapping()
	public void delete(
			@RequestParam(required = false) List<Long> ids, 
			@RequestParam(required = false) List<String> tags) {

		if (ids == null && tags == null) {
			DNARepository.deleteAll();
		} else {
			ids = ids == null ? new ArrayList<>() : ids;
			tags = tags == null ? new ArrayList<>() : tags;
			DNARepository.deleteByIdInOrTagIn(ids, tags);
		}

	}

	@GetMapping("{id}/monomer-count")
	public Map<Character, Integer> countMonomers(@PathVariable long id) {

		return DNARepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)).countMonomers();

	}

	@GetMapping("{id}/clump-forming-patterns")
	public Set<String> findClumpFormingPatterns(
			@PathVariable long id, 
			@RequestParam int size, 
			@RequestParam int times, 
			@RequestParam int range) {

		return DNARepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)).findClumpFormingPatterns(size, times, range);

	}

	@GetMapping("hamming-distance")
	public int calculateHammingDistance(
			@RequestParam(defaultValue = "") List<Long> ids, 
			@RequestParam(defaultValue = "") List<String> tags) {

		List<DNA> DNAs = DNARepository.findByIdInOrTagIn(ids, tags);

		if (DNAs.size() != 2) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}

		return PolymerAnalyzer.calculateHammingDistance(DNAs.get(0), DNAs.get(1));

	}

	@GetMapping("subsequence-locations")
	public List<Integer> findSubsequenceLocations(
			@RequestParam(name = "sequence-id") long sequenceId, 
			@RequestParam(name = "subsequence-id") long subsequenceId) {

		DNA sequence = DNARepository.findById(sequenceId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		DNA subsequence = DNARepository.findById(subsequenceId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

		return PolymerAnalyzer.findSubsequenceLocations(sequence, subsequence);

	}

	@GetMapping("longest-common-subsequence")
	public Optional<String> findLongestCommonSubsequence(
			@RequestParam(defaultValue = "") List<Long> ids, 
			@RequestParam(defaultValue = "") List<String> tags) {

		List<DNA> DNAs = DNARepository.findByIdInOrTagIn(ids, tags);

		try {
			return PolymerAnalyzer.findLongestCommonSubsequence(DNAs);
		} catch (IllegalArgumentException exception) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
		}

	}

	@GetMapping("{id}/reverse-complement")
	public Optional<String> calculateReverseComplement(@PathVariable long id) {

		return Optional.of(DNARepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)).calculateReverseComplement());

	}

	private <Type extends Object> Map<String, Object> formatPages(Page<Type> pages) {

		Map<String, Object> result = new HashMap<>(3);
		result.put("totalPages", pages.getTotalPages());
		result.put("page", pages.getNumber());
		result.put("tags", pages.getContent());

		return result;

	}

	private <Type extends Polymer> Page<Type> findByIdOrTag(int page, Pageable pageable, List<Long> ids, List<String> tags, PolymerRepository<Type> repository) {

		Page<Type> polymers;

		if (ids == null && tags == null) {
			polymers = repository.findAll(pageable);
		} else {
			ids = ids == null ? new ArrayList<>() : ids;
			tags = tags == null ? new ArrayList<>() : tags;
			polymers = repository.findByIdInOrTagIn(ids, tags, pageable);
		}

		return polymers;

	}

}
