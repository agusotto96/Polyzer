package app.domain;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class PolymerFactory {

	public Polymer getPolymer(PolymerType type, String sequence) {

		Polymer polymer = switch (type) {

		case DNA -> new DNA(sequence);
		case RNA -> new RNA(sequence);
		case PROTEIN -> new Protein(sequence);
		};

		return polymer;

	}

	public List<Polymer> getPolymers(PolymerType type, List<String> sequences) {

		Function<String, Polymer> mapper = switch (type) {

		case DNA -> sequence -> new DNA(sequence);
		case RNA -> sequence -> new RNA(sequence);
		case PROTEIN -> sequence -> new Protein(sequence);
		};

		return sequences.stream().map(mapper).collect(Collectors.toList());

	}

	public NucleicAcid getNucleicAcid(NucleicAcidType type, String sequence) {

		NucleicAcid nucleicAcid = switch (type) {

		case DNA -> new DNA(sequence);
		case RNA -> new RNA(sequence);
		};

		return nucleicAcid;

	}

}
