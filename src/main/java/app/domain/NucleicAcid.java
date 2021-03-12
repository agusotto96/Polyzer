package app.domain;

import java.util.Map;

public abstract class NucleicAcid extends Polymer {

	NucleicAcid(String sequence) {
		super(sequence);
	}

	public String getReverseComplement() {

		StringBuilder builder = new StringBuilder(this.value.length());

		for (char nucleotide : this.value.toCharArray()) {
			builder.append(getComplementaryMonomers().get(nucleotide));
		}

		return builder.reverse().toString();

	}

	abstract Map<Character, Character> getComplementaryMonomers();

}
