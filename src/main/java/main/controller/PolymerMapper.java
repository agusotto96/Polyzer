package main.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import main.model.DNA;
import main.model.Protein;
import main.model.RNA;

public class PolymerMapper {

	public static List<DNA> mapDNAs(List<Map<String, String>> sequences) {

		List<DNA> DNAs = new ArrayList<>(sequences.size());

		for (Map<String, String> sequence : sequences) {
			DNAs.add(new DNA(sequence.get("tag"), sequence.get("sequence")));
		}

		return DNAs;

	}

	public static List<RNA> mapRNAs(List<Map<String, String>> sequences) {

		List<RNA> RNAs = new ArrayList<>(sequences.size());

		for (Map<String, String> sequence : sequences) {
			RNAs.add(new RNA(sequence.get("tag"), sequence.get("sequence")));
		}

		return RNAs;

	}

	public static List<Protein> mapProteins(List<Map<String, String>> sequences) {

		List<Protein> Proteins = new ArrayList<>(sequences.size());

		for (Map<String, String> sequence : sequences) {
			Proteins.add(new Protein(sequence.get("tag"), sequence.get("sequence")));
		}

		return Proteins;

	}

}
