package org.springframework.samples.petclinic.pethotel;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.pet.Pet;

public interface PetHotelRepository extends Repository<PetHotel, Integer> {

	void save(PetHotel petHotel) throws DataAccessException;
}
