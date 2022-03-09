package org.springframework.samples.petclinic.pethotel;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.pet.PetRepository;
import org.springframework.samples.petclinic.pet.Visit;
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
	
	public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
	    return dateToConvert.toInstant()
	      .atZone(ZoneId.systemDefault())
	      .toLocalDate();
	}

}
