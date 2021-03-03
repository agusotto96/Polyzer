package main.domain.service;

import main.domain.entity.DNA;
import main.domain.entity.NucleicAcid;
import main.domain.entity.Polymer;
import main.domain.entity.Protein;
import main.domain.entity.RNA;

public class PolymerFactory {

	public static final String DNA = "dna";
	public static final String RNA = "rna";
	public static final String PROTEIN = "protein";

	public static Polymer getPolymer(String type, String sequence) {

		switch (type) {
		case DNA:
			return new DNA(sequence);
		case RNA:
			return new RNA(sequence);
		case PROTEIN:
			return new Protein(sequence);
		default:
			throw new IllegalArgumentException("invalid type");
		}

	}

	public static NucleicAcid getNucleicAcid(String type, String sequence) {

		switch (type) {
		case DNA:
			return new DNA(sequence);
		case RNA:
			return new RNA(sequence);
		default:
			throw new IllegalArgumentException("invalid type");
		}

	}

}
