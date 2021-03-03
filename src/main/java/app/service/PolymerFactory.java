package app.service;

import org.springframework.stereotype.Service;

import app.model.DNA;
import app.model.NucleicAcid;
import app.model.Polymer;
import app.model.Protein;
import app.model.RNA;

@Service
public class PolymerFactory {

	public static final String DNA = "dna";
	public static final String RNA = "rna";
	public static final String PROTEIN = "protein";

	public Polymer getPolymer(String type, String sequence) {

		Polymer polymer = switch (type) {

		case DNA -> new DNA(sequence);
		case RNA -> new RNA(sequence);
		case PROTEIN -> new Protein(sequence);
		default -> throw new IllegalArgumentException("invalid type");

		};

		return polymer;

	}

	public NucleicAcid getNucleicAcid(String type, String sequence) {

		NucleicAcid nucleicAcid = switch (type) {

		case DNA -> new DNA(sequence);
		case RNA -> new RNA(sequence);
		default -> throw new IllegalArgumentException("invalid type");

		};

		return nucleicAcid;

	}

}
