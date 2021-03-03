package app.presentation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import app.model.Polymer;
import app.service.PolymerAnalyzer;
import app.service.PolymerHandler;

@RestController
@RequestMapping("analysis")
class AnalysisController {

	@Autowired
	PolymerHandler polymerHandler;
	
	@Autowired
	PolymerAnalyzer polymerAnalyzer;

	@GetMapping("hamming-distance")
	int calculateHammingDistance(@RequestParam String type, @RequestParam(defaultValue = "") List<String> tags, @RequestParam(defaultValue = "") List<Long> ids) {

		List<Polymer> polymers = polymerHandler.findPolymers(type, tags, ids);

		if (polymers.size() != 2) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "two and only two polymers must be selected");
		}

		return polymerAnalyzer.calculateHammingDistance(polymers.get(0), polymers.get(1));

	}

	@GetMapping("subsequence-locations")
	List<Integer> findSubsequenceLocations(@RequestParam String type, @RequestParam String polymerTag, @RequestParam String subpolymerTag, @RequestParam long polymerId, 
			@RequestParam long subpolymerId) {

		Polymer polymer = polymerHandler.findPolymer(type, polymerTag, polymerId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

		Polymer subpolymer = polymerHandler.findPolymer(type, subpolymerTag, subpolymerId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

		return polymerAnalyzer.calculateSubsequenceLocations(polymer, subpolymer);

	}

	@GetMapping("longest-common-subsequence")
	Optional<String> calculateLongestCommonSubsequence(@RequestParam String type, @RequestParam(defaultValue = "") List<String> tags, 
			@RequestParam(defaultValue = "") List<Long> ids) {

		List<Polymer> polymers = polymerHandler.findPolymers(type, tags, ids);

		if (polymers.size() < 2) {
			throw new IllegalArgumentException("at least two polymers must be selected");
		}

		return polymerAnalyzer.calculateLongestCommonSubsequence(polymers);

	}

}
