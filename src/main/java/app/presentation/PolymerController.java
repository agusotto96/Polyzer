package app.presentation;

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

import app.data.Sequence;
import app.service.SequenceDataHandler;
import app.service.PolymerFactory;

@RestController
@RequestMapping("polymers")
class PolymerController {

	@Autowired
	private SequenceDataHandler sequenceDataHandler;

	@GetMapping()
	Set<String> findPolymers() {
		return PolymerFactory.POLYMERS;
	}

	@GetMapping("{polymer}/tags")
	Map<String, Object> findTags(@PathVariable String polymer, @RequestParam int page, @RequestParam int size) {

		Pageable pageable = PageRequest.of(page, size, Sort.by("tag"));
		Page<String> tags = sequenceDataHandler.findTags(polymer, pageable);

		if (tags.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "tags not found");
		}

		return formatPage("tags", tags);

	}

	@GetMapping("{polymer}/tags/{tag}/sequences")
	Map<String, Object> findSequences(@PathVariable String polymer, @PathVariable String tag, @RequestParam int page, @RequestParam int size) {

		Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
		Page<Sequence> sequences = sequenceDataHandler.findSequences(polymer, tag, pageable);

		if (sequences.getContent().isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "sequences not found");
		}

		return formatPage("ids", sequences);

	}

	@PostMapping("{polymer}/tags/{tag}/sequences")
	void saveSequences(@PathVariable String polymer, @PathVariable String tag, @RequestBody List<String> sequences) {

		if (tag.isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid tag");
		}

		sequenceDataHandler.saveSequences(polymer, tag, sequences);

	}

	@PutMapping("{polymer}/tags/{tag}/sequences")
	void updateTag(@PathVariable String polymer, @PathVariable String tag, @RequestBody Map<String, String> newTag) {

		if (!newTag.containsKey("tag")) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "missing tag");
		}

		if (newTag.get("tag").isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid tag");
		}

		sequenceDataHandler.updateTag(polymer, tag, newTag.get("tag"));

	}

	@DeleteMapping("{polymer}/tags")
	void deleteSequences(@PathVariable String type) {

		sequenceDataHandler.deleteSequences(type);

	}

	@DeleteMapping("{polymer}/tags/{tag}/sequences")
	void deleteSequences(@PathVariable String polymer, @PathVariable String tag) {

		sequenceDataHandler.deleteSequences(polymer, tag);

	}

	@DeleteMapping("{polymer}/tags/{tag}/sequences/{sequence}")
	void deleteSequences(@PathVariable String polymer, @PathVariable String tag, @PathVariable long id) {

		sequenceDataHandler.deleteSequence(polymer, tag, id);

	}

	private Map<String, Object> formatPage(String content, Page<?> page) {

		Map<String, Object> formattedPage = new HashMap<>(3);

		formattedPage.put(content, page.getContent());
		formattedPage.put("current-page", page.getNumber());
		formattedPage.put("total-pages", page.getTotalPages());

		return formattedPage;

	}

}
