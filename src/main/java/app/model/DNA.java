package app.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class DNA extends NucleicAcid {

	public DNA(String sequence) {
		super(sequence);
	}

	@Override
	Set<Character> getValidMonomers() {
		return Set.of(Nucleotide.ADENINE, Nucleotide.CYTOSINE, Nucleotide.GUANINE, Nucleotide.THYMINE);
	}

	@Override
	Map<Character, Character> getComplementaryMonomers() {

		Map<Character, Character> complementaryNucleotides = new HashMap<>(4);

		complementaryNucleotides.put(Nucleotide.CYTOSINE, Nucleotide.GUANINE);
		complementaryNucleotides.put(Nucleotide.GUANINE, Nucleotide.CYTOSINE);
		complementaryNucleotides.put(Nucleotide.ADENINE, Nucleotide.THYMINE);
		complementaryNucleotides.put(Nucleotide.THYMINE, Nucleotide.ADENINE);

		return complementaryNucleotides;
	}

}
