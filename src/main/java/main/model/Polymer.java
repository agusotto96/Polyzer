package main.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Polymer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	String tag;
	String sequence;

	Polymer() {
		super();
	}

	public Polymer(String tag, String sequence) {
		super();
		this.tag = tag;
		this.sequence = sequence;
		this.validatePolymer();
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
		this.validatePolymer();
	}

	public Long getId() {
		return id;
	}

	public String getTag() {
		return tag;
	}

	public Map<Character, Integer> countMonomers() {

		Map<Character, Integer> monomerCount = new HashMap<>();

		for (char monomer : this.sequence.toCharArray()) {
			monomerCount.put(monomer, monomerCount.getOrDefault(monomer, 0) + 1);
		}

		return monomerCount;

	}

	public Set<String> findClumpFormingPatterns(int patternSize, int patternTimes, int clumpSize) {

		Map<String, LinkedList<Integer>> patternsLocations = new HashMap<String, LinkedList<Integer>>();
		Set<String> patterns = new HashSet<String>();

		for (int index = 0; index <= this.sequence.length() - patternSize; index++) {

			String pattern = this.sequence.substring(index, index + patternSize);

			if (patternsLocations.get(pattern) == null) {
				patternsLocations.put(pattern, new LinkedList<Integer>());
			}

			while (!patternsLocations.get(pattern).isEmpty() && index + patternSize - patternsLocations.get(pattern).peek() > clumpSize) {
				patternsLocations.get(pattern).pop();
			}

			patternsLocations.get(pattern).add(index);

			if (patternsLocations.get(pattern).size() == patternTimes) {
				patterns.add(pattern);
			}

		}

		return patterns;

	}

	private void validatePolymer() {

		if (this.sequence == null || this.sequence.isBlank()) {
			throw new IllegalArgumentException("sequence cannot be empty");
		}

		if (this.tag == null || this.tag.isBlank()) {
			throw new IllegalArgumentException("tag cannot be empty");
		}

		for (char monomer : this.sequence.toCharArray()) {
			if (!getValidMonomers().contains(monomer)) {
				throw new IllegalArgumentException("sequence contains invalid monomer");
			}
		}

	}

	abstract Set<Character> getValidMonomers();

}
