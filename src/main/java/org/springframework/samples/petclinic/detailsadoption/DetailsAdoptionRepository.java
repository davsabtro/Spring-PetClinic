package org.springframework.samples.petclinic.detailsadoption;

import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.owner.Owner;

public interface DetailsAdoptionRepository extends CrudRepository<DetailsAdoption, Integer> {

	@Query("SELECT da FROM DetailsAdoption da WHERE da.adoption.pet.id = :petId")
	public List<DetailsAdoption> findDetailsAdoptionByPetId(@Param("petId") Integer petId);

	@Query("SELECT COUNT(da) FROM DetailsAdoption da WHERE da.adoption.pet.id = :petId")
	public Integer findNumberOfSuitorsByPetId(@Param("petId") Integer petId);

	@Query("SELECT da FROM DetailsAdoption da WHERE da.suitorToAdopt = :owner")
	public DetailsAdoption findDetailAdoptionsByOwner(@Param("owner") Owner owner);

	@Query("SELECT da FROM DetailsAdoption da WHERE da.adoption.id = :adoptionId AND da.suitorToAdopt.id = :suitorId")
	public DetailsAdoption findDetailAdoptionsByAdoptionAndSuitor(
			@Param("adoptionId") Integer adoptionId, @Param("suitorId") Integer suitorId);

	@Modifying
	@Query("DELETE from DetailsAdoption da WHERE da.adoption.id = :adoptionId")
	public void deleteDetailsByAdoption(@Param("adoptionId") Integer adoptionId);

}
