package app.model;

import java.util.Set;

public final class Protein extends Polymer {

	public Protein(String sequence) {
		super(sequence);
	}

	@Override
	Set<Character> getValidMonomers() {
		return Set.of(
				Aminoacid.ALANINE, 			Aminoacid.ARGININE, 		Aminoacid.ASPARAGINE,
				Aminoacid.ASPARTIC_ACID, 	Aminoacid.CYSTEINE, 		Aminoacid.GLUTAMIC_ACID,
				Aminoacid.GLUTAMINE, 		Aminoacid.GLYCINE, 			Aminoacid.HISTIDINE,
				Aminoacid.ISOLEUCINE, 		Aminoacid.LEUCINE, 			Aminoacid.LYSINE, 
				Aminoacid.METHIONINE, 		Aminoacid.PHENYLALANINE, 	Aminoacid.PROLINE, 
				Aminoacid.SERINE, 			Aminoacid.THREONINE, 		Aminoacid.TRYPTOPHAN, 
				Aminoacid.TYROSINE, 		Aminoacid.VALINE);
	}

}
