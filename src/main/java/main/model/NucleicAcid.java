package main.model;

import java.util.Map;

public abstract class NucleicAcid extends Polymer {

	NucleicAcid() {
		super();
	}

	NucleicAcid(String tag, String sequence) {
		super(tag, sequence);
	}

	public String calculateReverseComplement() {

		StringBuilder builder = new StringBuilder(this.sequence.length());

		for (char nucleotide : this.sequence.toCharArray()) {
			builder.append(getComplementaryMonomers().get(nucleotide));
		}

		return builder.reverse().toString();

	}

	abstract Map<Character, Character> getComplementaryMonomers();

}
