package app.data;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
interface PolymerRepository extends JpaRepository<Polymer, Long> {

	@Query(value = "select distinct p.tag from Polymer p where p.type = :type")
	Page<String> findTags(String type, Pageable pageable);

	@Query(value = "select p from Polymer p where p.type = :type and p.tag = :tag")
	Page<Polymer> findPolymers(String type, String tag, Pageable pageable);

	@Query(value = "select p from Polymer p where p.type = :type and p.tag in :tags or p.id in :ids")
	List<Polymer> findPolymers(String type, List<String> tags, List<Long> ids);

	@Query(value = "select p from Polymer p where p.type = :type and p.tag in :tags or p.id in :ids")
	Page<Polymer> findPolymers(String type, List<String> tags, List<Long> ids, Pageable pageable);

	@Modifying
	@Query(value = "update Polymer p set p.tag = :newTag where p.type = :type and p.tag = :tag")
	void updateTag(String type, String tag, String newTag);

	@Modifying
	@Query(value = "delete from Polymer p where p.type = :type")
	void deletePolymers(String type);

	@Modifying
	@Query(value = "delete from Polymer p where p.type = :type and p.tag = :tag")
	void deletePolymers(String type, String tag);

	@Modifying
	@Query(value = "delete from Polymer p where p.type = :type and p.tag = :tag and p.id = :id")
	void deletePolymers(String type, String tag, long id);

}
