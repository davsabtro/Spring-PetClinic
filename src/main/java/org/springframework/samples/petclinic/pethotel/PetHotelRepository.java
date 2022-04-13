package org.springframework.samples.petclinic.pethotel;

import java.util.Collection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.pet.Pet;

public interface PetHotelRepository extends CrudRepository<PetHotel, Integer> {

	@Query("SELECT pt FROM PetHotel pt WHERE pt.pet = :pet")
	public Collection<PetHotel> findPetHotelDataByPet(@Param("pet") Pet pet);

	@Query("SELECT COUNT(pt) FROM PetHotel pt WHERE pt.pet = :pet")
	public Integer findNumberOfReservationsByPet(@Param("pet") Pet pet);

	@Query("SELECT pt FROM PetHotel pt WHERE pt.owner = :owner")
	public Collection<PetHotel> findPetHotelDataByOwner(@Param("owner") Owner owner);
}
