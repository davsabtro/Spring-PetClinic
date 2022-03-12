package org.springframework.samples.petclinic.pethotel;

import org.springframework.data.repository.CrudRepository;

public interface PetHotelRepository extends CrudRepository<PetHotel, Integer> {
}
