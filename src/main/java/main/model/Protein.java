package main.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "PROTEIN")
public final class Protein extends Polymer {

	Protein() {
		super();
	}

	public Protein(String tag, String sequence) {
		super(tag, sequence);
	}

	@Override
	Set<Character> getValidMonomers() {
		return Set.of(Monomer.ALANINE, Monomer.ARGININE, Monomer.ASPARAGINE, 
				Monomer.ASPARTIC_ACID, Monomer.CYSTEINE, Monomer.GLUTAMIC_ACID, 
				Monomer.GLUTAMINE, Monomer.GLYCINE, Monomer.HISTIDINE, 
				Monomer.ISOLEUCINE, Monomer.LEUCINE, Monomer.LYSINE,
				Monomer.METHIONINE, Monomer.PHENYLALANINE, Monomer.PROLINE, 
				Monomer.SERINE, Monomer.THREONINE, Monomer.TRYPTOPHAN, 
				Monomer.TYROSINE, Monomer.VALINE);
	}

}
