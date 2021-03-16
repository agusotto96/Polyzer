package app.domain;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import app.constants.Nucleotide;

@Service
public class DNAAnalyzer {

	private AnalyzerHelper analyzerHelper;
	private Nucleotide nucleotide;

	DNAAnalyzer(AnalyzerHelper analyzerHelper, Nucleotide nucleotide) {
		super();
		this.analyzerHelper = analyzerHelper;
		this.nucleotide = nucleotide;
	}

	public String getComplement(String sequence) {

		Map<Character, Character> complementaryMonomers = new HashMap<>(4);

		complementaryMonomers.put(nucleotide.CYTOSINE, nucleotide.GUANINE);
		complementaryMonomers.put(nucleotide.GUANINE, nucleotide.CYTOSINE);
		complementaryMonomers.put(nucleotide.ADENINE, nucleotide.THYMINE);
		complementaryMonomers.put(nucleotide.THYMINE, nucleotide.ADENINE);

		return analyzerHelper.replaceMonomers(sequence, complementaryMonomers).toString();

	}

	public String getReverseComplement(String sequence) {
		return analyzerHelper.reverseSequence(getComplement(sequence));
	}

}
