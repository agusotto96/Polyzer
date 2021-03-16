package app.constants;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class Codon {

	private Aminoacid aminoacid;
	private Nucleotide nucleotide;
	public Map<String, Character> start;
	public Map<String, Character> body;
	public Set<String> stop;

	Codon(Aminoacid aminoacid, Nucleotide nucleotide) {
		super();
		this.aminoacid = aminoacid;
		this.nucleotide = nucleotide;
		start();
		body();
		stop();
	}

	private void start() {
		start = new HashMap<>();
		start.put("" + nucleotide.ADENINE + nucleotide.URACIL + nucleotide.GUANINE, aminoacid.METHIONINE);
	}

	private void body() {
		body = new HashMap<>();
		body.put("" + nucleotide.ADENINE + nucleotide.ADENINE + nucleotide.ADENINE, aminoacid.ALANINE);
		body.put("" + nucleotide.URACIL + nucleotide.URACIL + nucleotide.URACIL, aminoacid.PHENYLALANINE);
		body.put("" + nucleotide.CYTOSINE + nucleotide.URACIL + nucleotide.URACIL, aminoacid.LEUCINE);
		body.put("" + nucleotide.ADENINE + nucleotide.URACIL + nucleotide.URACIL, aminoacid.ISOLEUCINE);
		body.put("" + nucleotide.GUANINE + nucleotide.URACIL + nucleotide.URACIL, aminoacid.VALINE);
		body.put("" + nucleotide.URACIL + nucleotide.URACIL + nucleotide.CYTOSINE, aminoacid.PHENYLALANINE);
		body.put("" + nucleotide.CYTOSINE + nucleotide.URACIL + nucleotide.CYTOSINE, aminoacid.LEUCINE);
		body.put("" + nucleotide.ADENINE + nucleotide.URACIL + nucleotide.CYTOSINE, aminoacid.ISOLEUCINE);
		body.put("" + nucleotide.GUANINE + nucleotide.URACIL + nucleotide.CYTOSINE, aminoacid.VALINE);
		body.put("" + nucleotide.URACIL + nucleotide.URACIL + nucleotide.ADENINE, aminoacid.LEUCINE);
		body.put("" + nucleotide.CYTOSINE + nucleotide.URACIL + nucleotide.ADENINE, aminoacid.LEUCINE);
		body.put("" + nucleotide.ADENINE + nucleotide.URACIL + nucleotide.ADENINE, aminoacid.ISOLEUCINE);
		body.put("" + nucleotide.GUANINE + nucleotide.URACIL + nucleotide.ADENINE, aminoacid.VALINE);
		body.put("" + nucleotide.URACIL + nucleotide.URACIL + nucleotide.GUANINE, aminoacid.LEUCINE);
		body.put("" + nucleotide.CYTOSINE + nucleotide.URACIL + nucleotide.GUANINE, aminoacid.LEUCINE);
		body.put("" + nucleotide.GUANINE + nucleotide.URACIL + nucleotide.GUANINE, aminoacid.VALINE);
		body.put("" + nucleotide.URACIL + nucleotide.CYTOSINE + nucleotide.URACIL, aminoacid.SERINE);
		body.put("" + nucleotide.CYTOSINE + nucleotide.CYTOSINE + nucleotide.URACIL, aminoacid.PROLINE);
		body.put("" + nucleotide.ADENINE + nucleotide.CYTOSINE + nucleotide.URACIL, aminoacid.THREONINE);
		body.put("" + nucleotide.GUANINE + nucleotide.CYTOSINE + nucleotide.URACIL, aminoacid.ALANINE);
		body.put("" + nucleotide.URACIL + nucleotide.CYTOSINE + nucleotide.CYTOSINE, aminoacid.SERINE);
		body.put("" + nucleotide.CYTOSINE + nucleotide.CYTOSINE + nucleotide.CYTOSINE, aminoacid.PROLINE);
		body.put("" + nucleotide.ADENINE + nucleotide.CYTOSINE + nucleotide.CYTOSINE, aminoacid.THREONINE);
		body.put("" + nucleotide.GUANINE + nucleotide.CYTOSINE + nucleotide.CYTOSINE, aminoacid.ALANINE);
		body.put("" + nucleotide.URACIL + nucleotide.CYTOSINE + nucleotide.ADENINE, aminoacid.SERINE);
		body.put("" + nucleotide.CYTOSINE + nucleotide.CYTOSINE + nucleotide.ADENINE, aminoacid.PROLINE);
		body.put("" + nucleotide.ADENINE + nucleotide.CYTOSINE + nucleotide.ADENINE, aminoacid.THREONINE);
		body.put("" + nucleotide.GUANINE + nucleotide.CYTOSINE + nucleotide.ADENINE, aminoacid.ALANINE);
		body.put("" + nucleotide.URACIL + nucleotide.CYTOSINE + nucleotide.GUANINE, aminoacid.SERINE);
		body.put("" + nucleotide.CYTOSINE + nucleotide.CYTOSINE + nucleotide.GUANINE, aminoacid.PROLINE);
		body.put("" + nucleotide.ADENINE + nucleotide.CYTOSINE + nucleotide.GUANINE, aminoacid.THREONINE);
		body.put("" + nucleotide.GUANINE + nucleotide.CYTOSINE + nucleotide.GUANINE, aminoacid.ALANINE);
		body.put("" + nucleotide.URACIL + nucleotide.ADENINE + nucleotide.URACIL, aminoacid.TYROSINE);
		body.put("" + nucleotide.CYTOSINE + nucleotide.ADENINE + nucleotide.URACIL, aminoacid.HISTIDINE);
		body.put("" + nucleotide.ADENINE + nucleotide.ADENINE + nucleotide.URACIL, aminoacid.ASPARAGINE);
		body.put("" + nucleotide.GUANINE + nucleotide.ADENINE + nucleotide.URACIL, aminoacid.ASPARTIC_ACID);
		body.put("" + nucleotide.URACIL + nucleotide.ADENINE + nucleotide.CYTOSINE, aminoacid.TYROSINE);
		body.put("" + nucleotide.CYTOSINE + nucleotide.ADENINE + nucleotide.CYTOSINE, aminoacid.HISTIDINE);
		body.put("" + nucleotide.ADENINE + nucleotide.ADENINE + nucleotide.CYTOSINE, aminoacid.ASPARAGINE);
		body.put("" + nucleotide.GUANINE + nucleotide.ADENINE + nucleotide.CYTOSINE, aminoacid.ASPARTIC_ACID);
		body.put("" + nucleotide.CYTOSINE + nucleotide.ADENINE + nucleotide.ADENINE, aminoacid.GLUTAMINE);
		body.put("" + nucleotide.ADENINE + nucleotide.ADENINE + nucleotide.ADENINE, aminoacid.LYSINE);
		body.put("" + nucleotide.GUANINE + nucleotide.ADENINE + nucleotide.ADENINE, aminoacid.GLUTAMIC_ACID);
		body.put("" + nucleotide.CYTOSINE + nucleotide.ADENINE + nucleotide.GUANINE, aminoacid.GLUTAMINE);
		body.put("" + nucleotide.ADENINE + nucleotide.ADENINE + nucleotide.GUANINE, aminoacid.LYSINE);
		body.put("" + nucleotide.GUANINE + nucleotide.ADENINE + nucleotide.GUANINE, aminoacid.GLUTAMIC_ACID);
		body.put("" + nucleotide.URACIL + nucleotide.GUANINE + nucleotide.URACIL, aminoacid.CYSTEINE);
		body.put("" + nucleotide.CYTOSINE + nucleotide.GUANINE + nucleotide.URACIL, aminoacid.ARGININE);
		body.put("" + nucleotide.ADENINE + nucleotide.GUANINE + nucleotide.URACIL, aminoacid.SERINE);
		body.put("" + nucleotide.GUANINE + nucleotide.GUANINE + nucleotide.URACIL, aminoacid.GLYCINE);
		body.put("" + nucleotide.URACIL + nucleotide.GUANINE + nucleotide.CYTOSINE, aminoacid.CYSTEINE);
		body.put("" + nucleotide.CYTOSINE + nucleotide.GUANINE + nucleotide.CYTOSINE, aminoacid.ARGININE);
		body.put("" + nucleotide.ADENINE + nucleotide.GUANINE + nucleotide.CYTOSINE, aminoacid.SERINE);
		body.put("" + nucleotide.GUANINE + nucleotide.GUANINE + nucleotide.CYTOSINE, aminoacid.GLYCINE);
		body.put("" + nucleotide.CYTOSINE + nucleotide.GUANINE + nucleotide.ADENINE, aminoacid.ARGININE);
		body.put("" + nucleotide.ADENINE + nucleotide.GUANINE + nucleotide.ADENINE, aminoacid.ARGININE);
		body.put("" + nucleotide.GUANINE + nucleotide.GUANINE + nucleotide.ADENINE, aminoacid.GLYCINE);
		body.put("" + nucleotide.URACIL + nucleotide.GUANINE + nucleotide.GUANINE, aminoacid.TRYPTOPHAN);
		body.put("" + nucleotide.CYTOSINE + nucleotide.GUANINE + nucleotide.GUANINE, aminoacid.ARGININE);
		body.put("" + nucleotide.ADENINE + nucleotide.GUANINE + nucleotide.GUANINE, aminoacid.ARGININE);
		body.put("" + nucleotide.GUANINE + nucleotide.GUANINE + nucleotide.GUANINE, aminoacid.GLYCINE);
	}

	private void stop() {
		stop = new HashSet<>();
		stop.add("" + nucleotide.URACIL + nucleotide.ADENINE + nucleotide.ADENINE);
		stop.add("" + nucleotide.URACIL + nucleotide.ADENINE + nucleotide.GUANINE);
		stop.add("" + nucleotide.URACIL + nucleotide.GUANINE + nucleotide.ADENINE);
	}

}
