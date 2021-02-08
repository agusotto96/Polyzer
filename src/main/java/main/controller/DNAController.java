package main.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

import main.model.DNA;
import main.repository.DNARepository;

@RestController
@RequestMapping("dna")
public class DNAController {

	@Autowired
	DNARepository DNARepository;

	@PostMapping()
	public List<DNA> create(@RequestBody List<Map<String, String>> sequences) {

		try {

			List<DNA> DNAs = new ArrayList<>(sequences.size());

			for (Map<String, String> sequence : sequences) {
				DNAs.add(new DNA(sequence.get("tag"), sequence.get("sequence")));
			}

			return DNARepository.saveAll(DNAs);

		} catch (IllegalArgumentException exception) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
		}

	}

	@GetMapping()
	public Map<Object, Object> read(@RequestParam(defaultValue = "0") int page, @RequestParam(required = false) List<Long> ids, @RequestParam(required = false) List<String> tags) {

		Page<DNA> DNAs;

		if (ids == null && tags == null) {
			DNAs = DNARepository.findAll(PageRequest.of(page, 10));
		} else {
			ids = ids == null ? new ArrayList<>() : ids;
			tags = tags == null ? new ArrayList<>() : tags;
			DNAs = DNARepository.findByIdInOrTagIn(ids, tags, PageRequest.of(page, 10));
		}

		Map<Object, Object> result = new HashMap<>(3);
		result.put("totalPages", DNAs.getTotalPages());
		result.put("page", DNAs.getNumber());
		result.put("DNAs", DNAs.getContent());

		return result;

	}

	@GetMapping("tags")
	public Map<Object, Object> read(@RequestParam(defaultValue = "0") int page) {

		Page<String> tags = DNARepository.findAllTags(PageRequest.of(page, 10, Sort.by("tag")));

		Map<Object, Object> result = new HashMap<>(3);
		result.put("totalPages", tags.getTotalPages());
		result.put("page", tags.getNumber());
		result.put("tags", tags.getContent());

		return result;

	}

	@GetMapping("{tag}/ids")
	public Map<Object, Object> read(@PathVariable String tag, @RequestParam(defaultValue = "0") int page) {

		Page<Long> ids = DNARepository.findIdsByTag(tag, PageRequest.of(page, 10, Sort.by("id")));

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
	public DNA read(@PathVariable long id) {

		return DNARepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

	}

	@DeleteMapping()
	public void delete(@RequestParam(required = false) List<Long> ids, @RequestParam(required = false) List<String> tags) {

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
	public Set<String> findClumpFormingPatterns(@PathVariable long id, @RequestParam int size, @RequestParam int times, @RequestParam int range) {

		return DNARepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)).findClumpFormingPatterns(size, times, range);

	}

}
