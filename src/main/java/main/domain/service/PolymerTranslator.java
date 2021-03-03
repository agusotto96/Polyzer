package main.domain.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import main.domain.constant.Aminoacid;
import main.domain.constant.Nucleotide;
import main.domain.entity.Protein;
import main.domain.entity.RNA;

public class PolymerTranslator {

	public static final Map<String, Optional<Character>> CODON;

	static {

		CODON = new HashMap<>();

		// START CODON
		CODON.put("" + Nucleotide.ADENINE + Nucleotide.URACIL + Nucleotide.GUANINE, Optional.of(Aminoacid.METHIONINE));

		// BODY CODON
		CODON.put("" + Nucleotide.ADENINE + Nucleotide.ADENINE + Nucleotide.ADENINE, Optional.of(Aminoacid.ALANINE));
		CODON.put("" + Nucleotide.URACIL + Nucleotide.URACIL + Nucleotide.URACIL, Optional.of(Aminoacid.PHENYLALANINE));
		CODON.put("" + Nucleotide.CYTOSINE + Nucleotide.URACIL + Nucleotide.URACIL, Optional.of(Aminoacid.LEUCINE));
		CODON.put("" + Nucleotide.ADENINE + Nucleotide.URACIL + Nucleotide.URACIL, Optional.of(Aminoacid.ISOLEUCINE));
		CODON.put("" + Nucleotide.GUANINE + Nucleotide.URACIL + Nucleotide.URACIL, Optional.of(Aminoacid.VALINE));
		CODON.put("" + Nucleotide.URACIL + Nucleotide.URACIL + Nucleotide.CYTOSINE, Optional.of(Aminoacid.PHENYLALANINE));
		CODON.put("" + Nucleotide.CYTOSINE + Nucleotide.URACIL + Nucleotide.CYTOSINE, Optional.of(Aminoacid.LEUCINE));
		CODON.put("" + Nucleotide.ADENINE + Nucleotide.URACIL + Nucleotide.CYTOSINE, Optional.of(Aminoacid.ISOLEUCINE));
		CODON.put("" + Nucleotide.GUANINE + Nucleotide.URACIL + Nucleotide.CYTOSINE, Optional.of(Aminoacid.VALINE));
		CODON.put("" + Nucleotide.URACIL + Nucleotide.URACIL + Nucleotide.ADENINE, Optional.of(Aminoacid.LEUCINE));
		CODON.put("" + Nucleotide.CYTOSINE + Nucleotide.URACIL + Nucleotide.ADENINE, Optional.of(Aminoacid.LEUCINE));
		CODON.put("" + Nucleotide.ADENINE + Nucleotide.URACIL + Nucleotide.ADENINE, Optional.of(Aminoacid.ISOLEUCINE));
		CODON.put("" + Nucleotide.GUANINE + Nucleotide.URACIL + Nucleotide.ADENINE, Optional.of(Aminoacid.VALINE));
		CODON.put("" + Nucleotide.URACIL + Nucleotide.URACIL + Nucleotide.GUANINE, Optional.of(Aminoacid.LEUCINE));
		CODON.put("" + Nucleotide.CYTOSINE + Nucleotide.URACIL + Nucleotide.GUANINE, Optional.of(Aminoacid.LEUCINE));
		CODON.put("" + Nucleotide.GUANINE + Nucleotide.URACIL + Nucleotide.GUANINE, Optional.of(Aminoacid.VALINE));
		CODON.put("" + Nucleotide.URACIL + Nucleotide.CYTOSINE + Nucleotide.URACIL, Optional.of(Aminoacid.SERINE));
		CODON.put("" + Nucleotide.CYTOSINE + Nucleotide.CYTOSINE + Nucleotide.URACIL, Optional.of(Aminoacid.PROLINE));
		CODON.put("" + Nucleotide.ADENINE + Nucleotide.CYTOSINE + Nucleotide.URACIL, Optional.of(Aminoacid.THREONINE));
		CODON.put("" + Nucleotide.GUANINE + Nucleotide.CYTOSINE + Nucleotide.URACIL, Optional.of(Aminoacid.ALANINE));
		CODON.put("" + Nucleotide.URACIL + Nucleotide.CYTOSINE + Nucleotide.CYTOSINE, Optional.of(Aminoacid.SERINE));
		CODON.put("" + Nucleotide.CYTOSINE + Nucleotide.CYTOSINE + Nucleotide.CYTOSINE, Optional.of(Aminoacid.PROLINE));
		CODON.put("" + Nucleotide.ADENINE + Nucleotide.CYTOSINE + Nucleotide.CYTOSINE, Optional.of(Aminoacid.THREONINE));
		CODON.put("" + Nucleotide.GUANINE + Nucleotide.CYTOSINE + Nucleotide.CYTOSINE, Optional.of(Aminoacid.ALANINE));
		CODON.put("" + Nucleotide.URACIL + Nucleotide.CYTOSINE + Nucleotide.ADENINE, Optional.of(Aminoacid.SERINE));
		CODON.put("" + Nucleotide.CYTOSINE + Nucleotide.CYTOSINE + Nucleotide.ADENINE, Optional.of(Aminoacid.PROLINE));
		CODON.put("" + Nucleotide.ADENINE + Nucleotide.CYTOSINE + Nucleotide.ADENINE, Optional.of(Aminoacid.THREONINE));
		CODON.put("" + Nucleotide.GUANINE + Nucleotide.CYTOSINE + Nucleotide.ADENINE, Optional.of(Aminoacid.ALANINE));
		CODON.put("" + Nucleotide.URACIL + Nucleotide.CYTOSINE + Nucleotide.GUANINE, Optional.of(Aminoacid.SERINE));
		CODON.put("" + Nucleotide.CYTOSINE + Nucleotide.CYTOSINE + Nucleotide.GUANINE, Optional.of(Aminoacid.PROLINE));
		CODON.put("" + Nucleotide.ADENINE + Nucleotide.CYTOSINE + Nucleotide.GUANINE, Optional.of(Aminoacid.THREONINE));
		CODON.put("" + Nucleotide.GUANINE + Nucleotide.CYTOSINE + Nucleotide.GUANINE, Optional.of(Aminoacid.ALANINE));
		CODON.put("" + Nucleotide.URACIL + Nucleotide.ADENINE + Nucleotide.URACIL, Optional.of(Aminoacid.TYROSINE));
		CODON.put("" + Nucleotide.CYTOSINE + Nucleotide.ADENINE + Nucleotide.URACIL, Optional.of(Aminoacid.HISTIDINE));
		CODON.put("" + Nucleotide.ADENINE + Nucleotide.ADENINE + Nucleotide.URACIL, Optional.of(Aminoacid.ASPARAGINE));
		CODON.put("" + Nucleotide.GUANINE + Nucleotide.ADENINE + Nucleotide.URACIL, Optional.of(Aminoacid.ASPARTIC_ACID));
		CODON.put("" + Nucleotide.URACIL + Nucleotide.ADENINE + Nucleotide.CYTOSINE, Optional.of(Aminoacid.TYROSINE));
		CODON.put("" + Nucleotide.CYTOSINE + Nucleotide.ADENINE + Nucleotide.CYTOSINE, Optional.of(Aminoacid.HISTIDINE));
		CODON.put("" + Nucleotide.ADENINE + Nucleotide.ADENINE + Nucleotide.CYTOSINE, Optional.of(Aminoacid.ASPARAGINE));
		CODON.put("" + Nucleotide.GUANINE + Nucleotide.ADENINE + Nucleotide.CYTOSINE, Optional.of(Aminoacid.ASPARTIC_ACID));
		CODON.put("" + Nucleotide.CYTOSINE + Nucleotide.ADENINE + Nucleotide.ADENINE, Optional.of(Aminoacid.GLUTAMINE));
		CODON.put("" + Nucleotide.ADENINE + Nucleotide.ADENINE + Nucleotide.ADENINE, Optional.of(Aminoacid.LYSINE));
		CODON.put("" + Nucleotide.GUANINE + Nucleotide.ADENINE + Nucleotide.ADENINE, Optional.of(Aminoacid.GLUTAMIC_ACID));
		CODON.put("" + Nucleotide.CYTOSINE + Nucleotide.ADENINE + Nucleotide.GUANINE, Optional.of(Aminoacid.GLUTAMINE));
		CODON.put("" + Nucleotide.ADENINE + Nucleotide.ADENINE + Nucleotide.GUANINE, Optional.of(Aminoacid.LYSINE));
		CODON.put("" + Nucleotide.GUANINE + Nucleotide.ADENINE + Nucleotide.GUANINE, Optional.of(Aminoacid.GLUTAMIC_ACID));
		CODON.put("" + Nucleotide.URACIL + Nucleotide.GUANINE + Nucleotide.URACIL, Optional.of(Aminoacid.CYSTEINE));
		CODON.put("" + Nucleotide.CYTOSINE + Nucleotide.GUANINE + Nucleotide.URACIL, Optional.of(Aminoacid.ARGININE));
		CODON.put("" + Nucleotide.ADENINE + Nucleotide.GUANINE + Nucleotide.URACIL, Optional.of(Aminoacid.SERINE));
		CODON.put("" + Nucleotide.GUANINE + Nucleotide.GUANINE + Nucleotide.URACIL, Optional.of(Aminoacid.GLYCINE));
		CODON.put("" + Nucleotide.URACIL + Nucleotide.GUANINE + Nucleotide.CYTOSINE, Optional.of(Aminoacid.CYSTEINE));
		CODON.put("" + Nucleotide.CYTOSINE + Nucleotide.GUANINE + Nucleotide.CYTOSINE, Optional.of(Aminoacid.ARGININE));
		CODON.put("" + Nucleotide.ADENINE + Nucleotide.GUANINE + Nucleotide.CYTOSINE, Optional.of(Aminoacid.SERINE));
		CODON.put("" + Nucleotide.GUANINE + Nucleotide.GUANINE + Nucleotide.CYTOSINE, Optional.of(Aminoacid.GLYCINE));
		CODON.put("" + Nucleotide.CYTOSINE + Nucleotide.GUANINE + Nucleotide.ADENINE, Optional.of(Aminoacid.ARGININE));
		CODON.put("" + Nucleotide.ADENINE + Nucleotide.GUANINE + Nucleotide.ADENINE, Optional.of(Aminoacid.ARGININE));
		CODON.put("" + Nucleotide.GUANINE + Nucleotide.GUANINE + Nucleotide.ADENINE, Optional.of(Aminoacid.GLYCINE));
		CODON.put("" + Nucleotide.URACIL + Nucleotide.GUANINE + Nucleotide.GUANINE, Optional.of(Aminoacid.TRYPTOPHAN));
		CODON.put("" + Nucleotide.CYTOSINE + Nucleotide.GUANINE + Nucleotide.GUANINE, Optional.of(Aminoacid.ARGININE));
		CODON.put("" + Nucleotide.ADENINE + Nucleotide.GUANINE + Nucleotide.GUANINE, Optional.of(Aminoacid.ARGININE));
		CODON.put("" + Nucleotide.GUANINE + Nucleotide.GUANINE + Nucleotide.GUANINE, Optional.of(Aminoacid.GLYCINE));

		// STOP CODON
		CODON.put("" + Nucleotide.URACIL + Nucleotide.ADENINE + Nucleotide.ADENINE, Optional.empty());
		CODON.put("" + Nucleotide.URACIL + Nucleotide.ADENINE + Nucleotide.GUANINE, Optional.empty());
		CODON.put("" + Nucleotide.URACIL + Nucleotide.GUANINE + Nucleotide.ADENINE, Optional.empty());
	}

	public static Protein translateRNA(RNA rna) {
		String nucleotides = rna.getSequence();
		StringBuilder aminoacids = new StringBuilder();
		for (int index = 0; index < nucleotides.length() - 2; index += 3) {
			String triplet = nucleotides.substring(index, index + 3);

			Optional<Character> aminoacid = CODON.get(triplet);
			if (aminoacid.isPresent()) {
				aminoacids.append(aminoacid.get());
			} else {
				break;
			}
		}
		return new Protein(aminoacids.toString());
	}

}
