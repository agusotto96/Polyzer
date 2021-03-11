package app.data;

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

	@Query(value = "select distinct sequence.tag from Sequence sequence where sequence.polymer = :polymer")
	public Page<String> findTags(String polymer, Pageable pageable);

	@Query(value = "select sequence from Sequence sequence where sequence.polymer = :polymer and sequence.tag = :tag")
	public Page<Sequence> findSequences(String polymer, String tag, Pageable pageable);

	@Modifying
	@Query(value = "update Sequence sequence set sequence.tag = :newTag where sequence.polymer = :polymer and sequence.tag = :tag")
	public void updateTag(String polymer, String tag, String newTag);

	@Modifying
	@Query(value = "delete from Sequence sequence where sequence.polymer = :polymer")
	public void deleteSequences(String polymer);

	@Modifying
	@Query(value = "delete from Sequence sequence where sequence.polymer = :polymer and sequence.tag = :tag")
	public void deleteSequences(String polymer, String tag);

	@Modifying
	@Query(value = "delete from Sequence sequence where sequence.polymer = :polymer and sequence.tag = :tag and sequence.id = :id")
	public void deleteSequence(String polymer, String tag, long id);

}
