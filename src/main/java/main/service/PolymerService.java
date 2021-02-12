package main.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import main.DTO.PolymerDTO;
import main.model.DNA;
import main.model.NucleicAcid;
import main.model.Polymer;
import main.model.Protein;
import main.model.RNA;
import main.repository.PolymerRepository;

@Service
public class PolymerService {

	public static final String DNA = "dna";
	public static final String RNA = "rna";
	public static final String PROTEIN = "protein";

	@Autowired
	PolymerRepository polymerRepository;

	public void validateDTO(PolymerDTO DTO) {

		if (DTO.getTag() == null || DTO.getTag().isBlank()) {
			throw new IllegalArgumentException("tag cannot be empty");
		}

		if (DTO.getType() == null || DTO.getType().isBlank()) {
			throw new IllegalArgumentException("type cannot be empty");
		}

		if (DTO.getSequence() == null || DTO.getSequence().isBlank()) {
			throw new IllegalArgumentException("sequence cannot be empty");
		}

		switch (DTO.getType()) {
		case DNA:
			new DNA(DTO.getSequence());
			break;
		case RNA:
			new RNA(DTO.getSequence());
			break;
		case PROTEIN:
			new Protein(DTO.getSequence());
			break;
		default:
			throw new IllegalArgumentException("invalid type");
		}

	}

	public Polymer mapToPolymer(PolymerDTO DTO) {

		switch (DTO.getType()) {
		case DNA:
			return new DNA(DTO.getSequence());
		case RNA:
			return new RNA(DTO.getSequence());
		case PROTEIN:
			return new Protein(DTO.getSequence());
		default:
			throw new IllegalArgumentException("invalid type");
		}

	}

	public NucleicAcid mapToNucleicAcid(PolymerDTO DTO) {

		switch (DTO.getType()) {
		case DNA:
			return new DNA(DTO.getSequence());
		case RNA:
			return new RNA(DTO.getSequence());
		default:
			throw new IllegalArgumentException("invalid type");
		}

	}

	public List<PolymerDTO> savePolymers(List<PolymerDTO> DTOs) {

		for (PolymerDTO DTO : DTOs) {
			validateDTO(DTO);
		}

		return polymerRepository.saveAll(DTOs);

	};

	public Page<PolymerDTO> findPolymers(String type, List<String> tags, List<Long> ids, Pageable pageable) {

		if (type == null || tags == null || ids == null || pageable == null) {
			throw new IllegalArgumentException();
		}

		return polymerRepository.findPolymers(type, tags, ids, pageable);

	}

	public List<PolymerDTO> findPolymers(String type, List<String> tags, List<Long> ids) {

		if (type == null || tags == null || ids == null) {
			throw new IllegalArgumentException();
		}

		return polymerRepository.findPolymers(type, tags, ids);

	}

	public Page<String> findTags(String type, Pageable pageable) {

		if (type == null || pageable == null) {
			throw new IllegalArgumentException();
		}

		return polymerRepository.findTags(type, pageable);

	}

	public Page<Long> findIds(String type, String tag, Pageable pageable) {

		if (type == null || tag == null || pageable == null) {
			throw new IllegalArgumentException();
		}

		return polymerRepository.findIds(type, tag, pageable);

	}

	public Optional<PolymerDTO> findPolymer(long id) {

		return polymerRepository.findById(id);

	}

	public void deletePolymers(String type, List<Long> ids, List<String> tags) {

		if (type == null || ids == null || tags == null) {
			throw new IllegalArgumentException();
		}

		polymerRepository.deletePolymers(type, ids, tags);

	}

	public Optional<PolymerDTO> findPolymer(String type, long id) {

		if (type == null) {
			throw new IllegalArgumentException();
		}

		return polymerRepository.findPolymer(type, id);

	}

}
