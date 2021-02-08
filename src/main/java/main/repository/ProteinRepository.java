package main.repository;

import org.springframework.stereotype.Repository;

import main.model.Protein;

@Repository
public interface ProteinRepository extends PolymerRepository<Protein> {

}
