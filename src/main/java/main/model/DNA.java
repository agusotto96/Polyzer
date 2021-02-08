package main.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.persistence.Entity;

@Entity
public final class DNA extends NucleicAcid {

	DNA() {
		super();
	}

	public DNA(String tag, String sequence) {
		super(tag, sequence);
	}

	@Override
	Set<Character> getValidMonomers() {
		return Set.of(Monomer.ADENINE, Monomer.CYSTEINE, Monomer.GUANINE, Monomer.THYMINE);
	}

	@Override
	Map<Character, Character> getComplementaryMonomers() {

		Map<Character, Character> complementaryNucleotides = new HashMap<>(4);

		complementaryNucleotides.put(Monomer.CYSTEINE, Monomer.GUANINE);
		complementaryNucleotides.put(Monomer.GUANINE, Monomer.CYSTEINE);
		complementaryNucleotides.put(Monomer.ADENINE, Monomer.THYMINE);
		complementaryNucleotides.put(Monomer.THYMINE, Monomer.ADENINE);

		return complementaryNucleotides;
	}

}
