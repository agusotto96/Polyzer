package app.presentation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import app.data.PolymerDTO;
import app.data.PolymerDataHandler;
import app.domain.PolymerType;

@RestController
@RequestMapping()
class PolymerController extends BaseController {

	private final String POLYMERS_PATH = "polymers";
	private final String TAGS_PATH = "polymers/{type}/tags";
	private final String SEQUENCES_PATH = "polymers/{type}/tags/{tag}/sequences";
	private final String SEQUENCE_PATH = "polymers/{type}/tags/{tag}/sequences/{id}";

	@Autowired
	private PolymerDataHandler polymerDataHandler;

	@GetMapping(POLYMERS_PATH)
	PolymerType[] findtypes() {
		return PolymerType.values();
	}

	@GetMapping(TAGS_PATH)
	Map<String, Object> findTags(@PathVariable PolymerType type, @RequestParam int page, @RequestParam int size) {

		Pageable pageable = PageRequest.of(page, size, Sort.by("tag"));
		Page<String> tags = polymerDataHandler.findTags(type, pageable);

		if (tags.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "tags not found");
		}

		return formatPage("tags", tags);

	}

	@GetMapping(SEQUENCES_PATH)
	Map<String, Object> findSequences(@PathVariable PolymerType type, @PathVariable String tag, @RequestParam int page, @RequestParam int size) {

		Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
		Page<PolymerDTO> sequences = polymerDataHandler.findPolymers(type, tag, pageable);

		if (sequences.getContent().isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "sequences not found");
		}

		return formatPage("sequences", sequences.map(sequence -> formatDTO(sequence)));

	}

	@PostMapping(SEQUENCES_PATH)
	void saveSequences(@PathVariable PolymerType type, @PathVariable String tag, @RequestBody List<String> sequences) {

		if (tag.isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid tag");
		}

		if (sequences.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "at least one valid sequence must be provided");
		}

		try {
			polymerDataHandler.savePolymers(type, tag, sequences);
		} catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}

	}

	@PutMapping(SEQUENCES_PATH)
	void updateTag(@PathVariable PolymerType type, @PathVariable String tag, @RequestBody Map<String, String> newTag) {

		if (!newTag.containsKey("tag")) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "missing tag");
		}

		if (newTag.get("tag").isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid tag");
		}

		polymerDataHandler.updateTag(type, tag, newTag.get("tag"));

	}

	@DeleteMapping(TAGS_PATH)
	void deleteSequences(@PathVariable PolymerType type) {
		polymerDataHandler.deletePolymers(type);
	}

	@DeleteMapping(SEQUENCES_PATH)
	void deleteSequences(@PathVariable PolymerType type, @PathVariable String tag) {
		polymerDataHandler.deletePolymers(type, tag);

	}

	@DeleteMapping(SEQUENCE_PATH)
	void deleteSequences(@PathVariable PolymerType type, @PathVariable String tag, @PathVariable long id) {
		polymerDataHandler.deletePolymers(type, tag, id);
	}

	private Map<Long, String> formatDTO(PolymerDTO DTO) {
		Map<Long, String> formattedDTO = new HashMap<>();
		formattedDTO.put(DTO.getId(), DTO.getPolymer().getValue());
		return formattedDTO;
	}

}
