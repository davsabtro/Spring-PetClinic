package org.springframework.samples.petclinic.adoption;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
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

	@Query("SELECT a FROM Adoption a WHERE a.pet.id = :petId")
	public List<Adoption> findAdoptionDataByPetId(@Param("petId") Integer petId);

	@Modifying
	@Query("DELETE from Adoption a WHERE a.id = :adoptionId")
	public void deleteAdoptionRequests(@Param("adoptionId") Integer adoptionId);

}
