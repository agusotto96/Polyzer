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
@RequestMapping("polymers/RNA/analyzes")
public class RNAAnalysisController {

	private RNADataHandler RNADataHandler;
	private RNAAnalyzer RNAAnalyzer;
	private ControllerHelper controllerHelper;

	RNAAnalysisController(RNADataHandler RNADataHandler, RNAAnalyzer RNAAnalyzer, ControllerHelper controllerHelper) {
		super();
		this.RNADataHandler = RNADataHandler;
		this.RNAAnalyzer = RNAAnalyzer;
		this.controllerHelper = controllerHelper;
	}

	@GetMapping("longest-common-subsequence")
	Map<String, Object> getRNAReverseComplement(
			@RequestParam(defaultValue = "") List<String> tags, 
			@RequestParam(defaultValue = "") List<Long> ids, 
			@RequestParam int page, 
			@RequestParam int size) {

		Pageable pageable = PageRequest.of(page, size, Sort.by("tag", "id"));
		Page<Polymer> polymers = RNADataHandler.findPolymers(tags, ids, pageable);

		Page<Object> reverseComplements = polymers.map(polymer -> {

			var reverseComplement = new HashMap<>();
			reverseComplement.put(polymer.getId(), RNAAnalyzer.getReverseComplement(polymer.getSequence()));

			return reverseComplement;

		});

		return controllerHelper.formatPage(reverseComplements);

	}

	@GetMapping("translate")
	Map<String, Object> getTranslation(
			@RequestParam(defaultValue = "") List<String> tags, 
			@RequestParam(defaultValue = "") List<Long> ids, 
			@RequestParam int page, 
			@RequestParam int size) {

		Pageable pageable = PageRequest.of(page, size, Sort.by("tag", "id"));
		Page<Polymer> RNAs = RNADataHandler.findPolymers(tags, ids, pageable);

		Page<Object> translations = RNAs.map(RNA -> {

			var translation = new HashMap<>();
			translation.put(RNA.getId(), RNAAnalyzer.translateToProteins(RNA.getSequence()));

			return translation;

		});

		return controllerHelper.formatPage(translations);

	}

}