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
import app.data.ProteinDataHandler;
import app.domain.ProteinAnalyzer;

@RestController
@RequestMapping("polymers/protein/analyzes")
public class ProteinAnalysisController {

	private ProteinDataHandler proteinDataHandler;
	private ProteinAnalyzer proteinAnalyzer;

	ProteinAnalysisController(ProteinDataHandler polymerDataHandler, ProteinAnalyzer proteinAnalyzer) {
		super();
		this.proteinDataHandler = polymerDataHandler;
		this.proteinAnalyzer = proteinAnalyzer;
	}

	@GetMapping("mass")
	Map<String, Object> calculateProteinMass(
			@RequestParam(defaultValue = "") List<String> tags, 
			@RequestParam(defaultValue = "") List<Long> ids, 
			@RequestParam int page, 
			@RequestParam int size) {

		Pageable pageable = PageRequest.of(page, size, Sort.by("tag", "id"));
		Page<Polymer> proteins = proteinDataHandler.findPolymers(tags, ids, pageable);

		Page<Object> proteinsMass = proteins.map(protein -> {

			var proteinMass = new HashMap<>();
			proteinMass.put(protein.getId(), proteinAnalyzer.calculateMass(protein.getSequence()));

			return proteinMass;

		});

		return formatPage(proteinsMass);

	}

	Map<String, Object> formatPage(Page<?> page) {

		Map<String, Object> formattedPage = new HashMap<>(3);

		formattedPage.put("content", page.getContent());
		formattedPage.put("current-page", page.getNumber());
		formattedPage.put("total-pages", page.getTotalPages());

		return formattedPage;

	}

}
