package app.domain;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import app.constants.Aminoacid;

@Service
public class ProteinAnalyzer {

	private AnalyzerHelper analyzerHelper;
	private Aminoacid aminoacid;

	ProteinAnalyzer(AnalyzerHelper analyzerHelper, Aminoacid aminoacid) {
		super();
		this.analyzerHelper = analyzerHelper;
		this.aminoacid = aminoacid;
	}

	public double calculateMass(String protein) {

		Map<Character, Double> aminoacidMass = new HashMap<>();

		aminoacidMass.put(aminoacid.ALANINE, 		071.03711);
		aminoacidMass.put(aminoacid.CYSTEINE, 		103.00919);
		aminoacidMass.put(aminoacid.ASPARTIC_ACID, 	115.02694);
		aminoacidMass.put(aminoacid.GLUTAMIC_ACID, 	129.04259);
		aminoacidMass.put(aminoacid.PHENYLALANINE, 	147.06841);
		aminoacidMass.put(aminoacid.GLYCINE, 		057.02146);
		aminoacidMass.put(aminoacid.HISTIDINE, 		137.05891);
		aminoacidMass.put(aminoacid.ISOLEUCINE, 	113.08406);
		aminoacidMass.put(aminoacid.LYSINE, 		128.09496);
		aminoacidMass.put(aminoacid.LEUCINE, 		113.08406);
		aminoacidMass.put(aminoacid.METHIONINE, 	131.04049);
		aminoacidMass.put(aminoacid.ASPARAGINE, 	114.04293);
		aminoacidMass.put(aminoacid.PROLINE, 		097.05276);
		aminoacidMass.put(aminoacid.GLUTAMINE, 		128.05858);
		aminoacidMass.put(aminoacid.ARGININE, 		156.10111);
		aminoacidMass.put(aminoacid.SERINE, 		087.03203);
		aminoacidMass.put(aminoacid.THREONINE, 		101.04768);
		aminoacidMass.put(aminoacid.VALINE, 		099.06841);
		aminoacidMass.put(aminoacid.TRYPTOPHAN, 	186.07931);
		aminoacidMass.put(aminoacid.TYROSINE, 		163.06333);

		return analyzerHelper.calculatePolymerMass(protein, aminoacidMass);

	}

}
