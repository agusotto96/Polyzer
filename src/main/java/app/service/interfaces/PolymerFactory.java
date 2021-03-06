package app.service.interfaces;

import java.util.List;
import java.util.Set;

import app.model.NucleicAcid;
import app.model.Polymer;

public interface PolymerFactory {

	public static final String DNA = "DNA";
	public static final String RNA = "RNA";
	public static final String PROTEIN = "protein";

	public static final Set<String> TYPES = Set.of(DNA, RNA, PROTEIN);

	Polymer getPolymer(String type, String sequence);

	List<Polymer> getPolymers(String type, List<String> sequences);

	NucleicAcid getNucleicAcid(String type, String sequence);

	public class InvalidTypeException extends RuntimeException {

		private static final long serialVersionUID = -6970237712012706976L;

		public InvalidTypeException(String errorMessage) {
			super(errorMessage);
		}

	}

}