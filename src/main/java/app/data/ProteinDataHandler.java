package app.data;

import java.util.Set;

import org.springframework.stereotype.Service;

import app.constants.Aminoacid;

@Service
public class ProteinDataHandler extends BaseDataHandler {

	private Aminoacid aminoacid;

	ProteinDataHandler(PolymerRepository polymerRepository, PolymerDataHandler polymerDataHandler, Aminoacid aminoacid) {
		super(polymerRepository, polymerDataHandler);
		this.aminoacid = aminoacid;
	}

	@Override
	String getType() {
		return "protein";
	}

	@Override
	Set<Character> getValidMonomers() {
		return Set.of(
				aminoacid.ALANINE, 			aminoacid.ARGININE, 		aminoacid.ASPARAGINE,
				aminoacid.ASPARTIC_ACID, 	aminoacid.CYSTEINE, 		aminoacid.GLUTAMIC_ACID,
				aminoacid.GLUTAMINE, 		aminoacid.GLYCINE, 			aminoacid.HISTIDINE,
				aminoacid.ISOLEUCINE, 		aminoacid.LEUCINE, 			aminoacid.LYSINE, 
				aminoacid.METHIONINE, 		aminoacid.PHENYLALANINE, 	aminoacid.PROLINE, 
				aminoacid.SERINE, 			aminoacid.THREONINE, 		aminoacid.TRYPTOPHAN, 
				aminoacid.TYROSINE, 		aminoacid.VALINE);
	}

}