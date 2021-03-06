package app.service.implementations;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import app.model.DNA;
import app.model.NucleicAcid;
import app.model.Polymer;
import app.model.Protein;
import app.model.RNA;

@Service
public class PolymerFactory implements app.service.interfaces.PolymerFactory {

	@Override
	public Polymer getPolymer(Type type, String sequence) {

		Polymer polymer = switch (type) {

		case DNA -> new DNA(sequence);
		case RNA -> new RNA(sequence);
		case PROTEIN -> new Protein(sequence);
		};

		return polymer;

	}

	@Override
	public List<Polymer> getPolymers(Type type, List<String> sequences) {

		Function<String, Polymer> mapper = switch (type) {

		case DNA -> sequence -> new DNA(sequence);
		case RNA -> sequence -> new RNA(sequence);
		case PROTEIN -> sequence -> new Protein(sequence);
		};

		return sequences.stream().map(mapper).collect(Collectors.toList());

	}

	@Override
	public NucleicAcid getNucleicAcid(Type type, String sequence) {

		NucleicAcid nucleicAcid = switch (type) {

		case DNA -> new DNA(sequence);
		case RNA -> new RNA(sequence);
		default -> throw new IllegalArgumentException("invalid type");
		};

		return nucleicAcid;

	}

}
