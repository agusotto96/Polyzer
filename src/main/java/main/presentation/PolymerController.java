package main.presentation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import main.model.entity.NucleicAcid;
import main.model.entity.Polymer;
import main.service.PolymerService;

@RestController
@RequestMapping("polymers")
public class PolymerController {

	private static final String POLYMER      = 	"{type:" + PolymerService.DNA + "|" + PolymerService.RNA + "|" + PolymerService.PROTEIN + "}";
	private static final String NUCLEIC_ACID = 	"{type:" + PolymerService.DNA + "|" + PolymerService.RNA + "}";

	@Autowired
	PolymerService polymerService;

	@GetMapping()
	public Set<String> findTypes() {
		return Set.of(PolymerService.DNA, PolymerService.RNA, PolymerService.PROTEIN);
	}

	@GetMapping(POLYMER)
	public Map<String, Object> findTags(@PathVariable String type, @RequestParam int page, @RequestParam int size) {

		Pageable pageable = PageRequest.of(page, size, Sort.by("tag"));
		Page<String> tags = polymerService.findTags(type, pageable);

		return formatPages("tags", tags);

	}

	@GetMapping(POLYMER + "/{tag}")
	public Map<String, Object> findIds(@PathVariable String type, @PathVariable String tag, @RequestParam int page, @RequestParam int size) {

		Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
		Page<Long> ids = polymerService.findIds(type, tag, pageable);

		if (ids.getContent().isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

		return formatPages("ids", ids);

	}

	@GetMapping(POLYMER + "/{tag}/{id}")
	public String findPolymer(@PathVariable String type, @PathVariable String tag, @PathVariable long id) {

		Polymer polymer = polymerService.findPolymer(type, tag, id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

		return polymer.getSequence();

	}

	@GetMapping(POLYMER + "/{tag}/{id}/monomer-count")
	public Map<Character, Integer> getMonomerCount(@PathVariable String type, @PathVariable String tag, @PathVariable long id) {

		Polymer polymer = polymerService.findPolymer(type, tag, id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

		return polymer.getMonomerCount();

	}

	@GetMapping(POLYMER + "/{tag}/{id}/clump-forming-patterns")
	public Set<String> getClumpFormingPatterns(
			@PathVariable String type, 	@PathVariable String tag, 	@PathVariable long id, 
			@RequestParam int size, 	@RequestParam int times, 	@RequestParam int range) {

		Polymer polymer = polymerService.findPolymer(type, tag, id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

		return polymer.getClumpFormingPatterns(size, times, range);

	}

	@GetMapping(NUCLEIC_ACID + "/{tag}/{id}/reverse-complement")
	public String getReverseComplement(@PathVariable String type, @PathVariable String tag, @PathVariable long id) {

		NucleicAcid nucleicAcid = polymerService.findNucleicAcid(type, tag, id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

		return nucleicAcid.getReverseComplement();

	}

	@PostMapping(POLYMER + "/{tag}")
	public void savePolymers(@PathVariable String type, @PathVariable String tag, @RequestBody List<String> sequences) {

		try {
			polymerService.savePolymers(type, tag, sequences);

		} catch (IllegalArgumentException exception) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
		}

	}

	@PutMapping(POLYMER + "/{tag}")
	public void updateTag(@PathVariable String type, @PathVariable String tag, @RequestBody String newTag) {
		polymerService.updateTag(type, tag, newTag);
	}

	@PutMapping(POLYMER + "/{tag}/{id}")
	public void updatePolymer(@PathVariable String type, @PathVariable String tag, @PathVariable long id, @RequestBody String newSequence) {
		polymerService.updatePolymer(type, tag, id, newSequence);
	}

	@DeleteMapping()
	public void deletePolymers() {
		polymerService.deletePolymers();
	}

	@DeleteMapping(POLYMER)
	public void deletePolymers(@PathVariable String type) {
		polymerService.deletePolymers(type);
	}

	@DeleteMapping(POLYMER + "/{tag}")
	public void deletePolymers(@PathVariable String type, @PathVariable String tag) {
		polymerService.deletePolymers(type, tag);
	}

	@DeleteMapping(POLYMER + "/{tag}/{id}")
	public void deletePolymer(@PathVariable String type, @PathVariable String tag, @PathVariable long id) {
		polymerService.deletePolymer(type, tag, id);
	}

	private Map<String, Object> formatPages(String content, Page<?> pages) {

		Map<String, Object> result = new HashMap<>(3);

		result.put("totalPages", pages.getTotalPages());
		result.put("number", pages.getNumber());
		result.put(content, pages.getContent());

		return result;

	}

}
