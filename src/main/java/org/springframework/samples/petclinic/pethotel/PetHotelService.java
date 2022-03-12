package org.springframework.samples.petclinic.pethotel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PetHotelService {

	private PetHotelRepository petHotelRepository;

	@Autowired
	public PetHotelService(PetHotelRepository petHotelRepository) {
		this.petHotelRepository = petHotelRepository;
	}

	@Transactional
	public void savePetHotel(PetHotel petHotel) throws DataAccessException {
		petHotelRepository.save(petHotel);
	}
}
