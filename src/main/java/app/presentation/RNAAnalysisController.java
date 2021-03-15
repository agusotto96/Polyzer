package app.presentation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.data.Polymer;
import app.data.RNADataHandler;
import app.domain.RNAAnalyzer;

@RestController
@RequestMapping()
public class RNAAnalysisController {

	private final String RNA_REVERSE_COMPLEMENT_PATH = "polymers/RNA/analyzes/longest-common-subsequence";

	private RNADataHandler RNADataHandler;
	private RNAAnalyzer RNAAnalyzer;

	RNAAnalysisController(RNADataHandler RNADataHandler, RNAAnalyzer RNAAnalyzer) {
		super();
		this.RNADataHandler = RNADataHandler;
		this.RNAAnalyzer = RNAAnalyzer;
	}

	@GetMapping(RNA_REVERSE_COMPLEMENT_PATH)
	Map<String, Object> getRNAReverseComplement(
			@RequestParam(defaultValue = "") List<String> tags, 
			@RequestParam(defaultValue = "") List<Long> ids, 
			@RequestParam int page, 
			@RequestParam int size) {

		Pageable pageable = PageRequest.of(page, size, Sort.by("tag", "id"));
		Page<Polymer> polymers = RNADataHandler.findRNAs(tags, ids, pageable);

		Page<Object> reverseComplements = polymers.map(polymer -> {

			var reverseComplement = new HashMap<>();
			reverseComplement.put(polymer.getId(), RNAAnalyzer.getRNAReverseComplement(polymer.getSequence()));

			return reverseComplement;

		});

		return formatPage(reverseComplements);

	}

	Map<String, Object> formatPage(Page<?> page) {

		Map<String, Object> formattedPage = new HashMap<>(3);

		formattedPage.put("content", page.getContent());
		formattedPage.put("current-page", page.getNumber());
		formattedPage.put("total-pages", page.getTotalPages());

		return formattedPage;

	}

}
