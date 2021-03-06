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
import app.service.interfaces.PolymerDataHandler;
import app.service.interfaces.PolymerFactory;

@RestController
@RequestMapping("polymers")
class PolymerController {

	@Autowired
	private PolymerDataHandler polymerHandler;

	@GetMapping()
	Set<String> findTypes() {
		return PolymerFactory.TYPES;
	}

	@GetMapping("{type}")
	Map<String, Object> findTags(@PathVariable String type, @RequestParam int page, @RequestParam int size) {

		Pageable pageable = PageRequest.of(page, size, Sort.by("tag"));
		Page<String> tags = polymerHandler.findTags(type, pageable);

		if (tags.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "tags not found");
		}

		return formatPages("tags", tags);

	}

	@GetMapping("{type}/{tag}")
	Map<String, Object> findIds(@PathVariable String type, @PathVariable String tag, @RequestParam int page, @RequestParam int size) {

		Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
		Page<Long> ids = polymerHandler.findIds(type, tag, pageable);

		if (ids.getContent().isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "polymer not found");
		}

		return formatPages("ids", ids);

	}

	@GetMapping("{type}/{tag}/{id}")
	Optional<String> findPolymer(@PathVariable String type, @PathVariable String tag, @PathVariable long id) {

		Polymer polymer = polymerHandler.findPolymer(type, tag, id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "polymer not found"));

		return Optional.of(polymer.getSequence());

	}

	@GetMapping("{type}/{tag}/{id}/monomer-count")
	Map<Character, Integer> getMonomerCount(@PathVariable String type, @PathVariable String tag, @PathVariable long id) {

		Polymer polymer = polymerHandler.findPolymer(type, tag, id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "polymer not found"));

		return polymer.getMonomerCount();

	}

	@GetMapping("{type}/{tag}/{id}/clump-forming-patterns")
	Set<String> getClumpFormingPatterns(@PathVariable String type, @PathVariable String tag, @PathVariable long id, 
			@RequestParam int size, @RequestParam int times, @RequestParam int range) {

		Polymer polymer = polymerHandler.findPolymer(type, tag, id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "polymer not found"));

		return polymer.getClumpFormingPatterns(size, times, range);

	}

	@GetMapping("{type}/{tag}/{id}/reverse-complement")
	Optional<String> getReverseComplement(@PathVariable String type, @PathVariable String tag, @PathVariable long id) {

		NucleicAcid nucleicAcid = polymerHandler.findNucleicAcid(type, tag, id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "polymer not found"));

		return Optional.of(nucleicAcid.getReverseComplement());

	}

	@PostMapping("{type}/{tag}")
	void savePolymers(@PathVariable String type, @PathVariable String tag, @RequestBody List<String> sequences) {

		polymerHandler.savePolymers(type, tag, sequences);

	}

	@PutMapping("{type}/{tag}")
	void updateTag(@PathVariable String type, @PathVariable String tag, @RequestBody String newTag) {
		polymerHandler.updateTag(type, tag, newTag);
	}

	@PutMapping("{type}/{tag}/{id}")
	void updatePolymer(@PathVariable String type, @PathVariable String tag, @PathVariable long id, @RequestBody String newSequence) {
		polymerHandler.updatePolymer(type, tag, id, newSequence);
	}

	@DeleteMapping()
	void deletePolymers() {
		polymerHandler.deletePolymers();
	}

	@DeleteMapping("{type}")
	void deletePolymers(@PathVariable String type) {
		polymerHandler.deletePolymers(type);
	}

	@DeleteMapping("{type}/{tag}")
	void deletePolymers(@PathVariable String type, @PathVariable String tag) {
		polymerHandler.deletePolymers(type, tag);
	}

	@DeleteMapping("{type}/{tag}/{id}")
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
