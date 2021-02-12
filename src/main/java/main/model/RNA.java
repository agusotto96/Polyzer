package main.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class RNA extends NucleicAcid {

	public RNA(String sequence) {
		super(sequence);
	}

	@Override
	Set<Character> getValidMonomers() {
		return Set.of(Monomer.ADENINE, Monomer.CYSTEINE, Monomer.GUANINE, Monomer.URACIL);
	}

	@Override
	Map<Character, Character> getComplementaryMonomers() {

		Map<Character, Character> complementaryNucleotides = new HashMap<>(4);

		complementaryNucleotides.put(Monomer.CYSTEINE, Monomer.GUANINE);
		complementaryNucleotides.put(Monomer.GUANINE, Monomer.CYSTEINE);
		complementaryNucleotides.put(Monomer.ADENINE, Monomer.URACIL);
		complementaryNucleotides.put(Monomer.URACIL, Monomer.ADENINE);

		return complementaryNucleotides;
	}

}
