package app.data;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PolymerDataHandler {

	private PolymerRepository polymerRepository;

	PolymerDataHandler(PolymerRepository polymerRepository) {
		super();
		this.polymerRepository = polymerRepository;
	}

	public Page<String> findTags(String type, Pageable pageable) {
		return polymerRepository.findTags(type, pageable);
	}

	public Page<Polymer> findPolymers(String type, String tag, Pageable pageable) {
		return polymerRepository.findPolymers(type, tag, pageable);
	}

	public List<Polymer> findPolymers(String type, List<String> tags, List<Long> ids) {
		return polymerRepository.findPolymers(type, tags, ids);
	}

	public Page<Polymer> findPolymers(String type, List<String> tags, List<Long> ids, Pageable pageable) {
		return polymerRepository.findPolymers(type, tags, ids, pageable);
	}

	public void updateTag(String type, String tag, String string) {
		polymerRepository.updateTag(type, tag, tag);
	}

	public void deletePolymers(String type) {
		polymerRepository.deletePolymers(type);
	}

	public void deletePolymers(String type, String tag) {
		polymerRepository.deletePolymers(type, tag);
	}

	public void deletePolymers(String type, String tag, long id) {
		polymerRepository.deletePolymers(type, tag, id);
	}

}
