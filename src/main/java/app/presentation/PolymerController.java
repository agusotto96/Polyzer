package app.presentation;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import app.model.NucleicAcid;
import app.model.Polymer;
import app.service.PolymerFactory;
import app.service.PolymerHandler;

@RestController
@RequestMapping("polymers")
class PolymerController {

	private static final String POLYMER = "{type:" + PolymerFactory.DNA + "|" + PolymerFactory.RNA + "|" + PolymerFactory.PROTEIN + "}";
	private static final String NUCLEIC_ACID = "{type:" + PolymerFactory.DNA + "|" + PolymerFactory.RNA + "}";

	@Autowired
	private PolymerHandler polymerHandler;

	@GetMapping()
	Set<String> findTypes() {
		return Set.of(PolymerFactory.DNA, PolymerFactory.RNA, PolymerFactory.PROTEIN);
	}

	@GetMapping(POLYMER)
	Map<String, Object> findTags(@PathVariable String type, @RequestParam int page, @RequestParam int size) {

		Pageable pageable = PageRequest.of(page, size, Sort.by("tag"));
		Page<String> tags = polymerHandler.findTags(type, pageable);

		return formatPages("tags", tags);

	}

	@GetMapping(POLYMER + "/{tag}")
	Map<String, Object> findIds(@PathVariable String type, @PathVariable String tag, @RequestParam int page, @RequestParam int size) {

		Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
		Page<Long> ids = polymerHandler.findIds(type, tag, pageable);

		if (ids.getContent().isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

		return formatPages("ids", ids);

	}

	@GetMapping(value = POLYMER + "/{tag}/{id}")
	Optional<String> findPolymer(@PathVariable String type, @PathVariable String tag, @PathVariable long id) {

		Polymer polymer = polymerHandler.findPolymer(type, tag, id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

		return Optional.of(polymer.getSequence());

	}

	@GetMapping(POLYMER + "/{tag}/{id}/monomer-count")
	Map<Character, Integer> getMonomerCount(@PathVariable String type, @PathVariable String tag, @PathVariable long id) {

		Polymer polymer = polymerHandler.findPolymer(type, tag, id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

		return polymer.getMonomerCount();

	}

	@GetMapping(POLYMER + "/{tag}/{id}/clump-forming-patterns")
	Set<String> getClumpFormingPatterns(@PathVariable String type, @PathVariable String tag, @PathVariable long id, 
			@RequestParam int size, @RequestParam int times, @RequestParam int range) {

		Polymer polymer = polymerHandler.findPolymer(type, tag, id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

		return polymer.getClumpFormingPatterns(size, times, range);

	}

	@GetMapping(NUCLEIC_ACID + "/{tag}/{id}/reverse-complement")
	Optional<String> getReverseComplement(@PathVariable String type, @PathVariable String tag, @PathVariable long id) {

		NucleicAcid nucleicAcid = polymerHandler.findNucleicAcid(type, tag, id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

		return Optional.of(nucleicAcid.getReverseComplement());

	}

	@PostMapping(POLYMER + "/{tag}")
	void savePolymers(@PathVariable String type, @PathVariable String tag, @RequestBody List<String> sequences) {

		try {
			polymerHandler.savePolymers(type, tag, sequences);

		} catch (IllegalArgumentException exception) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
		}

	}

	@PutMapping(POLYMER + "/{tag}")
	void updateTag(@PathVariable String type, @PathVariable String tag, @RequestBody String newTag) {
		polymerHandler.updateTag(type, tag, newTag);
	}

	@PutMapping(POLYMER + "/{tag}/{id}")
	void updatePolymer(@PathVariable String type, @PathVariable String tag, @PathVariable long id, @RequestBody String newSequence) {
		polymerHandler.updatePolymer(type, tag, id, newSequence);
	}

	@DeleteMapping()
	void deletePolymers() {
		polymerHandler.deletePolymers();
	}

	@DeleteMapping(POLYMER)
	void deletePolymers(@PathVariable String type) {
		polymerHandler.deletePolymers(type);
	}

	@DeleteMapping(POLYMER + "/{tag}")
	void deletePolymers(@PathVariable String type, @PathVariable String tag) {
		polymerHandler.deletePolymers(type, tag);
	}

	@DeleteMapping(POLYMER + "/{tag}/{id}")
	void deletePolymer(@PathVariable String type, @PathVariable String tag, @PathVariable long id) {
		polymerHandler.deletePolymer(type, tag, id);
	}

	private Map<String, Object> formatPages(String content, Page<?> pages) {

		Map<String, Object> result = new HashMap<>(3);

		result.put("totalPages", pages.getTotalPages());
		result.put("number", pages.getNumber());
		result.put(content, pages.getContent());

		return result;

	}

}
