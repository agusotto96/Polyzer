package app.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;

import app.constants.Aminoacid;
import app.constants.Codon;

@Service
public class ProteinAnalyzer {

	private AnalyzerHelper analyzerHelper;
	private Aminoacid aminoacid;
	private Codon codon;
	private Map<Character, List<String>> invertedCodon;

	ProteinAnalyzer(AnalyzerHelper analyzerHelper, Aminoacid aminoacid, Codon codon) {
		super();
		this.analyzerHelper = analyzerHelper;
		this.aminoacid = aminoacid;
		this.codon = codon;
		this.invertedCodon = invertCodon(this.codon);
	}

	public double calculateMass(String protein) {

		Map<Character, Double> aminoacidMass = new HashMap<>();

		aminoacidMass.put(aminoacid.ALANINE, 71.03711);
		aminoacidMass.put(aminoacid.CYSTEINE, 103.00919);
		aminoacidMass.put(aminoacid.ASPARTIC_ACID, 115.02694);
		aminoacidMass.put(aminoacid.GLUTAMIC_ACID, 129.04259);
		aminoacidMass.put(aminoacid.PHENYLALANINE, 147.06841);
		aminoacidMass.put(aminoacid.GLYCINE, 57.02146);
		aminoacidMass.put(aminoacid.HISTIDINE, 137.05891);
		aminoacidMass.put(aminoacid.ISOLEUCINE, 113.08406);
		aminoacidMass.put(aminoacid.LYSINE, 128.09496);
		aminoacidMass.put(aminoacid.LEUCINE, 113.08406);
		aminoacidMass.put(aminoacid.METHIONINE, 131.04049);
		aminoacidMass.put(aminoacid.ASPARAGINE, 114.04293);
		aminoacidMass.put(aminoacid.PROLINE, 97.05276);
		aminoacidMass.put(aminoacid.GLUTAMINE, 128.05858);
		aminoacidMass.put(aminoacid.ARGININE, 156.10111);
		aminoacidMass.put(aminoacid.SERINE, 87.03203);
		aminoacidMass.put(aminoacid.THREONINE, 101.04768);
		aminoacidMass.put(aminoacid.VALINE, 99.06841);
		aminoacidMass.put(aminoacid.TRYPTOPHAN, 186.07931);
		aminoacidMass.put(aminoacid.TYROSINE, 163.06333);

		return analyzerHelper.calculatePolymerMass(protein, aminoacidMass);

	}

	public List<List<String>> reverseTranslation(String protein) {

		List<List<String>> RNAs = new ArrayList<>();

		for (char aminoacid : protein.toCharArray()) {
			RNAs.add(invertedCodon.get(aminoacid));
		}

		RNAs.add(new ArrayList<>(codon.stop));

		return RNAs;

	}

	private Map<Character, List<String>> invertCodon(Codon codon) {

		Map<Character, List<String>> invertedCodon = new HashMap<Character, List<String>>();

		invertedCodon.putAll(invertMap(codon.start));
		invertedCodon.putAll(invertMap(codon.body));

		return invertedCodon;

	}

	private Map<Character, List<String>> invertMap(Map<String, Character> map) {

		Map<Character, List<String>> invertedMap = new HashMap<Character, List<String>>();

		for (Entry<String, Character> entry : map.entrySet()) {

			List<String> aux = invertedMap.getOrDefault(entry.getValue(), new ArrayList<>());
			aux.add(entry.getKey());
			invertedMap.put(entry.getValue(), aux);

		}

		return invertedMap;

	}

}
