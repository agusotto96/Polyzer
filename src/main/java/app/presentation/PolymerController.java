package app.presentation;

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
import app.service.PolymerFactory;
import app.service.SequenceDataHandler;

@RestController
@RequestMapping("polymers")
class typeController extends BaseController {

	@Autowired
	private SequenceDataHandler sequenceDataHandler;

	@GetMapping()
	Set<String> findtypes() {
		return Set.of(PolymerFactory.DNA, PolymerFactory.RNA, PolymerFactory.PROTEIN);
	}

	@GetMapping("{type}/tags")
	Map<String, Object> findTags(@PathVariable String type, @RequestParam int page, @RequestParam int size) {

		Pageable pageable = PageRequest.of(page, size, Sort.by("tag"));
		Page<String> tags = sequenceDataHandler.findTags(type, pageable);

		if (tags.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "tags not found");
		}

		return formatPage("tags", tags);

	}

	@GetMapping("{type}/tags/{tag}/sequences")
	Map<String, Object> findSequences(@PathVariable String type, @PathVariable String tag, @RequestParam int page, @RequestParam int size) {

		Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
		Page<Sequence> sequences = sequenceDataHandler.findSequences(type, tag, pageable);

		if (sequences.getContent().isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "sequences not found");
		}

		return formatPage("sequences", sequences);

	}

	@PostMapping("{type}/tags/{tag}/sequences")
	void saveSequences(@PathVariable String type, @PathVariable String tag, @RequestBody List<String> sequences) {

		if (tag.isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid tag");
		}

		sequenceDataHandler.saveSequences(type, tag, sequences);

	}

	@PutMapping("{type}/tags/{tag}/sequences")
	void updateTag(@PathVariable String type, @PathVariable String tag, @RequestBody Map<String, String> newTag) {

		if (!newTag.containsKey("tag")) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "missing tag");
		}

		if (newTag.get("tag").isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid tag");
		}

		sequenceDataHandler.updateTag(type, tag, newTag.get("tag"));

	}

	@DeleteMapping("{type}/tags")
	void deleteSequences(@PathVariable String type) {
		sequenceDataHandler.deleteSequences(type);
	}

	@DeleteMapping("{type}/tags/{tag}/sequences")
	void deleteSequences(@PathVariable String type, @PathVariable String tag) {
		sequenceDataHandler.deleteSequences(type, tag);

	}

	@DeleteMapping("{type}/tags/{tag}/sequences/{id}")
	void deleteSequences(@PathVariable String type, @PathVariable String tag, @PathVariable long id) {
		sequenceDataHandler.deleteSequence(type, tag, id);
	}

}
