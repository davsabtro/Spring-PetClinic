package org.springframework.samples.petclinic.adoption;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.owner.Owner;

public interface AdoptionRepository extends CrudRepository<Adoption, Integer> {

	@Query("SELECT a FROM Adoption a")
	public Collection<Adoption> findPetsForAdoption();

	@Query("SELECT COUNT(a) FROM Adoption a")
	public Integer findNumberOfPetsForAdoption();

	@Query("SELECT a FROM Adoption a WHERE a.id = :adoptionId")
	public Adoption findPetsForAdoptionByAdoptionId(@Param("adoptionId") Integer adoptionId);

	@Query("SELECT a.SuitorsToAdopt FROM Adoption a WHERE a.pet.id = :petId")
	public List<Owner> findSuitorsByPetId(@Param("petId") Integer petId);

}
