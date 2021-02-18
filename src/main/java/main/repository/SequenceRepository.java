package main.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface SequenceRepository extends JpaRepository<Sequence, Long> {

	@Query(value = "select distinct sequence.tag from Sequence sequence where sequence.type = :type")
	Page<String> findTags(String type, Pageable pageable);

	@Query(value = "select distinct sequence.id from Sequence sequence where sequence.type = :type and sequence.tag = :tag")
	Page<Long> findIds(String type, String tag, Pageable pageable);

	@Query(value = "select sequence.value from Sequence sequence where sequence.type = :type and sequence.tag = :tag and sequence.id = :id")
	Optional<String> findSequence(String type, String tag, long id);

	@Query(value = "select sequence.value from Sequence sequence where sequence.type = :type and sequence.tag in :tag or sequence.id in :id")
	List<String> findSequences(String type, List<String> tags, List<Long> ids);

	@Modifying
	@Query(value = "update Sequence sequence set sequence.tag = newTag where sequence.tag = tag")
	void updateTag(String type, String tag, String newTag);

	@Modifying
	@Query(value = "update Sequence sequence set sequence.value = :newSequence where sequence.type = :type and sequence.tag = :tag and sequence.id = :id")
	void updateSequence(String type, String tag, long id, String newSequence);

	@Modifying
	@Query(value = "delete from Sequence sequence where sequence.type = :type")
	void deleteSequences(String type);

	@Modifying
	@Query(value = "delete from Sequence sequence where sequence.type = :type and sequence.tag = :tag")
	void deleteSequences(String type, String tag);

	@Modifying
	@Query(value = "delete from Sequence sequence where sequence.type = :type and sequence.tag = :tag and sequence.id = :id")
	void deleteSequence(String type, String tag, long id);

}
