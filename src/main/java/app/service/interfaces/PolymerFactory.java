package app.service.interfaces;

import java.util.List;

import app.model.NucleicAcid;
import app.model.Polymer;

public interface PolymerFactory {

	String DNA = "dna";
	String RNA = "rna";
	String PROTEIN = "protein";

	Polymer getPolymer(String type, String sequence);
	
	List<Polymer> getPolymers(String type, List<String> sequences);

	NucleicAcid getNucleicAcid(String type, String sequence);

}