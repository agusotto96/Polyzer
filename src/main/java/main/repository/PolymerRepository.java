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

import main.DTO.PolymerDTO;

@Transactional
@Repository
public interface PolymerRepository extends JpaRepository<PolymerDTO, Long> {

	@Query(value = "select distinct polymer.tag from #{#entityName} polymer where polymer.type = :type")
	Page<String> findTags(String type, Pageable pageable);

	@Query(value = "select seq.id from #{#entityName} seq where seq.type = :type and seq.tag = :tag")
	Page<Long> findIds(String type, String tag, Pageable pageable);

	@Modifying
	@Query(value = "delete from #{#entityName} polymer where polymer.type = :type and (polymer.tag in :tags or polymer.id in :ids)")
	void deletePolymers(String type, List<Long> ids, List<String> tags);

	@Query(value = "select polymer from #{#entityName} polymer where polymer.type = :type and (polymer.tag in :tags or polymer.id in :ids)")
	Page<PolymerDTO> findPolymers(String type, List<String> tags, List<Long> ids, Pageable pageable);

	@Query(value = "select polymer from #{#entityName} polymer where polymer.type = :type and (polymer.tag in :tags or polymer.id in :ids)")
	List<PolymerDTO> findPolymers(String type, List<String> tags, List<Long> ids);

	@Query(value = "select polymer from #{#entityName} polymer where polymer.type = :type and polymer.id = :id")
	Optional<PolymerDTO> findPolymer(String type, long id);

}
