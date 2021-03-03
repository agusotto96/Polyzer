package app.model;

import java.util.Map;

public abstract class NucleicAcid extends Polymer {

	NucleicAcid(String sequence) {
		super(sequence);
	}

	public String getReverseComplement() {

		StringBuilder builder = new StringBuilder(this.sequence.length());

		for (char nucleotide : this.sequence.toCharArray()) {
			builder.append(getComplementaryMonomers().get(nucleotide));
		}

		return builder.reverse().toString();

	}

	abstract Map<Character, Character> getComplementaryMonomers();

}
