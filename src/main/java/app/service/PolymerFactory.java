package app.service;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import app.model.DNA;
import app.model.NucleicAcid;
import app.model.Polymer;
import app.model.Protein;
import app.model.RNA;

@Service
public class PolymerFactory {

	public static final String DNA = "DNA";
	public static final String RNA = "RNA";
	public static final String PROTEIN = "protein";

	public static final Set<String> POLYMERS = Set.of(DNA, RNA, PROTEIN);
	public static final Set<String> NUCLEIC_ACIDS = Set.of(DNA, RNA);

	public Polymer getPolymer(String type, String sequence) {

		Polymer polymer = switch (type) {

		case DNA -> new DNA(sequence);
		case RNA -> new RNA(sequence);
		case PROTEIN -> new Protein(sequence);
		default -> throw new InvalidTypeException("Unexpected value: " + type);
		};

		return polymer;

	}

	public List<Polymer> getPolymers(String type, List<String> sequences) {

		Function<String, Polymer> mapper = switch (type) {

		case DNA -> sequence -> new DNA(sequence);
		case RNA -> sequence -> new RNA(sequence);
		case PROTEIN -> sequence -> new Protein(sequence);
		default -> throw new InvalidTypeException("Unexpected value: " + type);
		};

		return sequences.stream().map(mapper).collect(Collectors.toList());

	}

	public NucleicAcid getNucleicAcid(String type, String sequence) {

		NucleicAcid nucleicAcid = switch (type) {

		case DNA -> new DNA(sequence);
		case RNA -> new RNA(sequence);
		default -> throw new InvalidTypeException("Unexpected value: " + type);
		};

		return nucleicAcid;

	}

	public class InvalidTypeException extends RuntimeException {

		private static final long serialVersionUID = -6970237712012706976L;

		public InvalidTypeException(String errorMessage) {
			super(errorMessage);
		}

	}

}
