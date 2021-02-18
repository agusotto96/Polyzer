package main.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import main.model.Polymer;
import main.service.PolymerService;

@RestController
@RequestMapping("analysis")
public class AnalysisController {

	@Autowired
	PolymerService polymerService;

	@GetMapping("hamming-distance")
	public int calculateHammingDistance(@RequestParam String type, @RequestParam List<String> tags, @RequestParam List<Long> ids) {

		List<Polymer> polymers = polymerService.findPolymers(type, tags, ids);

		if (polymers.size() != 2) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "two and only two polymers must be selected");
		}

		return Polymer.calculateHammingDistance(polymers.get(0), polymers.get(1));

	}

	@GetMapping("subsequence-locations")
	public List<Integer> findSubsequenceLocations(
			@RequestParam String type, 
			@RequestParam String polymerTag, 	@RequestParam String subpolymerTag, 
			@RequestParam long polymerId, 		@RequestParam long subpolymerId) {

		Polymer polymer =  polymerService.findPolymer(type, polymerTag, polymerId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		
		Polymer subpolymer = polymerService.findPolymer(type, subpolymerTag, subpolymerId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

		return Polymer.calculateSubsequenceLocations(polymer, subpolymer);

	}

	@GetMapping("longest-common-subsequence")
	public Optional<String> calculateLongestCommonSubsequence(@RequestParam String type, @RequestParam List<String> tags, @RequestParam List<Long> ids) {

		List<Polymer> polymers = polymerService.findPolymers(type, tags, ids);

		if (polymers.size() < 2) {
			throw new IllegalArgumentException("at least two polymers must be selected");
		}

		return Polymer.calculateLongestCommonSubsequence(polymers);

	}

}
