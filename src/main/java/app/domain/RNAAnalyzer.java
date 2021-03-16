package app.domain;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import app.constants.Codon;
import app.constants.Nucleotide;

@Service
public class RNAAnalyzer {

	private AnalyzerHelper analyzerHelper;
	private Nucleotide nucleotide;
	private Codon codon;

	RNAAnalyzer(AnalyzerHelper analyzerHelper, Nucleotide nucleotide, Codon codon) {
		super();
		this.analyzerHelper = analyzerHelper;
		this.nucleotide = nucleotide;
		this.codon = codon;
	}
	
	public String getReverseComplement(String sequence) {
		return analyzerHelper.reverseSequence(getComplement(sequence));
	}

	public String getComplement(String sequence) {

		Map<Character, Character> complementaryMonomers = new HashMap<>(4);

		complementaryMonomers.put(nucleotide.CYTOSINE, nucleotide.GUANINE);
		complementaryMonomers.put(nucleotide.GUANINE, nucleotide.CYTOSINE);
		complementaryMonomers.put(nucleotide.ADENINE, nucleotide.URACIL);
		complementaryMonomers.put(nucleotide.URACIL, nucleotide.ADENINE);

		return analyzerHelper.replaceMonomers(sequence, complementaryMonomers).toString();

	}

	public Set<String> translateToProteins(String sequence) {

		Set<String> proteins = new HashSet<>();

		for (String readingFrame : getReadingFrames(sequence)) {
			proteins.addAll(translateReadingFrameToProteins(readingFrame));
		}

		return proteins;

	}

	private Set<String> getReadingFrames(String sequence) {

		Set<String> readingFrames = new HashSet<>(6);

		String reverseComplement = getReverseComplement(sequence);
		readingFrames.add(sequence);
		readingFrames.add(reverseComplement);

		if (sequence.length() >= 2) {
			readingFrames.add(sequence.substring(1));
			readingFrames.add(reverseComplement.substring(1));
		}

		if (sequence.length() >= 3) {
			readingFrames.add(sequence.substring(2));
			readingFrames.add(reverseComplement.substring(2));
		}

		return readingFrames;

	}

	private Set<String> translateReadingFrameToProteins(String sequence) {

		Set<String> proteins = new HashSet<>();

		StringBuilder protein = new StringBuilder();

		boolean proteinHasStarted = false;

		for (int i = 0; i <= sequence.length() - 3; i += 3) {

			String triplet = sequence.substring(i, i + 3);

			if (!proteinHasStarted) {

				if (codon.start.containsKey(triplet)) {

					protein.append(codon.start.get(triplet));
					proteinHasStarted = true;

				}

			} else {

				if (codon.start.containsKey(triplet)) {

					protein.append(codon.start.get(triplet));

				} else if (codon.body.containsKey(triplet)) {

					protein.append(codon.body.get(triplet));

				} else if (codon.stop.contains(triplet)) {

					proteins.add(protein.toString());
					protein = new StringBuilder();
					proteinHasStarted = false;

				}

			}

		}

		return proteins;

	}

}
