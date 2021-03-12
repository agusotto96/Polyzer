package app.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RNA extends NucleicAcid {

	RNA(String sequence) {
		super(sequence);
	}

	@Override
	Set<Character> getValidMonomers() {
		return Set.of(Nucleotide.ADENINE, Nucleotide.CYTOSINE, Nucleotide.GUANINE, Nucleotide.URACIL);
	}

	@Override
	Map<Character, Character> getComplementaryMonomers() {

		Map<Character, Character> complementaryNucleotides = new HashMap<>(4);

		complementaryNucleotides.put(Nucleotide.CYTOSINE, Nucleotide.GUANINE);
		complementaryNucleotides.put(Nucleotide.GUANINE, Nucleotide.CYTOSINE);
		complementaryNucleotides.put(Nucleotide.ADENINE, Nucleotide.URACIL);
		complementaryNucleotides.put(Nucleotide.URACIL, Nucleotide.ADENINE);

		return complementaryNucleotides;

	}

}
