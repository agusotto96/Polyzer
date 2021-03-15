package app.presentation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import app.data.RNADataHandler;

@RestController
@RequestMapping()
class RNAController {

	private final String RNA_SEQUENCES_PATH = "polymers/RNA/tags/{tag}/sequences";

	private RNADataHandler RNADataHandler;

	RNAController(RNADataHandler RNADataHandler) {
		super();
		this.RNADataHandler = RNADataHandler;
	}

	@PostMapping(RNA_SEQUENCES_PATH)
	void saveRNAs(@PathVariable String tag, @RequestBody List<String> sequences) {

		if (tag.isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid tag");
		}

		if (sequences.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "at least one valid sequence must be provided");
		}

		RNADataHandler.saveRNAs(tag, sequences);

	}

	Map<String, Object> formatPage(Page<?> page) {

		Map<String, Object> formattedPage = new HashMap<>(3);

		formattedPage.put("content", page.getContent());
		formattedPage.put("current-page", page.getNumber());
		formattedPage.put("total-pages", page.getTotalPages());

		return formattedPage;

	}

}
