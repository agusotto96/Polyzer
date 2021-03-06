package app.service.interfaces;

import java.util.List;

import app.model.NucleicAcid;
import app.model.Polymer;

public interface PolymerFactory {

	public enum Type {
		DNA, RNA, PROTEIN
	}

	Polymer getPolymer(Type type, String sequence);

	List<Polymer> getPolymers(Type type, List<String> sequences);

	NucleicAcid getNucleicAcid(Type type, String sequence);

}