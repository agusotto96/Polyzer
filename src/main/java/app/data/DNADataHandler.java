package app.data;

import java.util.Set;

import org.springframework.stereotype.Service;

import app.constants.Nucleotide;

@Service
public class DNADataHandler extends BaseDataHandler {

	private Nucleotide nucleotide;

	DNADataHandler(PolymerRepository polymerRepository, PolymerDataHandler polymerDataHandler, Nucleotide nucleotide) {
		super(polymerRepository, polymerDataHandler);
		this.nucleotide = nucleotide;
	}

	@Override
	String getType() {
		return "DNA";
	}

	@Override
	Set<Character> getValidMonomers() {
		return Set.of(nucleotide.ADENINE, nucleotide.CYTOSINE, nucleotide.GUANINE, nucleotide.THYMINE);
	}

}
