package app.presentation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import app.data.Polymer;
import app.data.PolymerDataHandler;

@RestController
@RequestMapping()
class PolymerController {

	private final String POLYMERS_PATH = "polymers";
	private final String TAGS_PATH = "polymers/{type}/tags";
	private final String SEQUENCES_PATH = "polymers/{type}/tags/{tag}/sequences";
	private final String DNA_SEQUENCES_PATH = "polymers/DNA/tags/{tag}/sequences";
	private final String RNA_SEQUENCES_PATH = "polymers/RNA/tags/{tag}/sequences";
	private final String PROTEIN_SEQUENCES_PATH = "polymers/protein/tags/{tag}/sequences";
	private final String SEQUENCE_PATH = "polymers/{type}/tags/{tag}/sequences/{id}";

	private PolymerDataHandler polymerDataHandler;
	
	PolymerController(PolymerDataHandler polymerDataHandler) {
		super();
		this.polymerDataHandler = polymerDataHandler;
	}

	@GetMapping(POLYMERS_PATH)
	List<String> findTypes() {
		return polymerDataHandler.getTypes();
	}

	@GetMapping(TAGS_PATH)
	Map<String, Object> findTags(@PathVariable String type, @RequestParam int page, @RequestParam int size) {

		Pageable pageable = PageRequest.of(page, size, Sort.by("tag"));
		Page<String> tags = polymerDataHandler.findTags(type, pageable);

		if (tags.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "tags not found");
		}

		return formatPage(tags);

	}

	@GetMapping(SEQUENCES_PATH)
	Map<String, Object> findSequences(@PathVariable String type, @PathVariable String tag, @RequestParam int page, @RequestParam int size) {

		Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
		Page<Polymer> sequences = polymerDataHandler.findPolymers(type, tag, pageable);

		if (sequences.getContent().isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "sequences not found");
		}

		return formatPage(sequences);

	}

	@PostMapping(DNA_SEQUENCES_PATH)
	void saveDNAs(@PathVariable String tag, @RequestBody List<String> sequences) {

		if (tag.isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid tag");
		}

		if (sequences.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "at least one valid sequence must be provided");
		}

		polymerDataHandler.saveDNAs(tag, sequences);

	}

	@PostMapping(RNA_SEQUENCES_PATH)
	void saveRNAs(@PathVariable String tag, @RequestBody List<String> sequences) {

		if (tag.isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid tag");
		}

		if (sequences.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "at least one valid sequence must be provided");
		}

		polymerDataHandler.saveRNAs(tag, sequences);

	}

	@PostMapping(PROTEIN_SEQUENCES_PATH)
	void saveProteins(@PathVariable String tag, @RequestBody List<String> sequences) {

		if (tag.isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid tag");
		}

		if (sequences.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "at least one valid sequence must be provided");
		}

		polymerDataHandler.saveProteins(tag, sequences);

	}

	@PatchMapping(SEQUENCES_PATH)
	void updateTag(@PathVariable String type, @PathVariable String tag, @RequestBody Map<String, String> newTag) {

		if (!newTag.containsKey("tag")) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "missing tag");
		}

		if (newTag.get("tag").isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid tag");
		}

		polymerDataHandler.updateTag(type, tag, newTag.get("tag"));

	}

	@DeleteMapping(TAGS_PATH)
	void deleteSequences(@PathVariable String type) {
		polymerDataHandler.deletePolymers(type);
	}

	@DeleteMapping(SEQUENCES_PATH)
	void deleteSequences(@PathVariable String type, @PathVariable String tag) {
		polymerDataHandler.deletePolymers(type, tag);
	}

	@DeleteMapping(SEQUENCE_PATH)
	void deleteSequences(@PathVariable String type, @PathVariable String tag, @PathVariable long id) {
		polymerDataHandler.deletePolymers(type, tag, id);
	}

	Map<String, Object> formatPage(Page<?> page) {

		Map<String, Object> formattedPage = new HashMap<>(3);

		formattedPage.put("content", page.getContent());
		formattedPage.put("current-page", page.getNumber());
		formattedPage.put("total-pages", page.getTotalPages());

		return formattedPage;

	}

}
