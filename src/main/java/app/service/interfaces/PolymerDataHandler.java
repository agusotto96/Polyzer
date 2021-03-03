package app.service.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import app.model.NucleicAcid;
import app.model.Polymer;

public interface PolymerDataHandler {

	Page<String> findTags(String type, Pageable pageable);

	Page<Long> findIds(String type, String tag, Pageable pageable);

	Optional<Polymer> findPolymer(String type, String tag, long id);

	List<Polymer> findPolymers(String type, List<String> tags, List<Long> ids);

	Optional<NucleicAcid> findNucleicAcid(String type, String tag, long id);

	void savePolymers(String type, String tag, List<String> sequences);

	void updateTag(String type, String tag, String newTag);

	void updatePolymer(String type, String tag, long id, String newSequence);

	void deletePolymers();

	void deletePolymers(String type);

	void deletePolymers(String type, String tag);

	void deletePolymer(String type, String tag, long id);

}