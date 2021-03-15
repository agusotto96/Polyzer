package app.domain;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class ProteinAnalyzer {

	public double calculateProteinMass(String protein) {

		Map<Character, Double> aminoacidMass = new HashMap<>();

		aminoacidMass.put('A', 071.03711);
		aminoacidMass.put('C', 103.00919);
		aminoacidMass.put('D', 115.02694);
		aminoacidMass.put('E', 129.04259);
		aminoacidMass.put('F', 147.06841);
		aminoacidMass.put('G', 057.02146);
		aminoacidMass.put('H', 137.05891);
		aminoacidMass.put('I', 113.08406);
		aminoacidMass.put('K', 128.09496);
		aminoacidMass.put('L', 113.08406);
		aminoacidMass.put('M', 131.04049);
		aminoacidMass.put('N', 114.04293);
		aminoacidMass.put('P', 097.05276);
		aminoacidMass.put('Q', 128.05858);
		aminoacidMass.put('R', 156.10111);
		aminoacidMass.put('S', 087.03203);
		aminoacidMass.put('T', 101.04768);
		aminoacidMass.put('V', 099.06841);
		aminoacidMass.put('W', 186.07931);
		aminoacidMass.put('Y', 163.06333);

		return calculatePolymerMass(protein, aminoacidMass);

	}

	private double calculatePolymerMass(String sequence, Map<Character, Double> monomerMass) {

		double mass = 0;

		for (char monomer : sequence.toCharArray()) {
			mass += monomerMass.get(monomer);
		}

		return mass;

	}

}
