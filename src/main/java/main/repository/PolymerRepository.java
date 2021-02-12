package main.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@NoRepositoryBean
public interface PolymerRepository<Polymer> extends JpaRepository<Polymer, Long> {

	Page<Polymer> findByTagIn(List<String> tags, Pageable pageable);

	@Query(value = "select distinct seq.tag from #{#entityName} seq")
	Page<String> findAllTags(Pageable pageable);

	@Query(value = "select seq.id from #{#entityName} seq where seq.tag = :tag")
	Page<Long> findIdsByTag(String tag, Pageable pageable);

	void deleteByTagIn(List<String> tags);

	void deleteByIdIn(List<Long> ids);

	void deleteByIdInOrTagIn(List<Long> ids, List<String> tags);

	Page<Polymer> findByIdInOrTagIn(List<Long> ids, List<String> tags, Pageable pageable);

	List<Polymer> findByIdInOrTagIn(List<Long> ids, List<String> tags);

}
