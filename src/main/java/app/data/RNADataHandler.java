package app.data;

import java.util.Set;

import org.springframework.stereotype.Service;

import app.constants.Nucleotide;

@Service
public class RNADataHandler extends BaseDataHandler {

	private Nucleotide nucleotide;

	RNADataHandler(PolymerRepository polymerRepository, PolymerDataHandler polymerDataHandler, Nucleotide nucleotide) {
		super(polymerRepository, polymerDataHandler);
		this.nucleotide = nucleotide;
	}

	@Override
	String getType() {
		return "RNA";
	}

	@Override
	Set<Character> getValidMonomers() {
		return Set.of(nucleotide.ADENINE, nucleotide.CYTOSINE, nucleotide.GUANINE, nucleotide.URACIL);
	}

}