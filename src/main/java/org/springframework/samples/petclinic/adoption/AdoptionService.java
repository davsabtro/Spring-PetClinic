package org.springframework.samples.petclinic.adoption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.pethotel.PetHotel;
import org.springframework.samples.petclinic.pethotel.PetHotelRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdoptionService {
	
	private AdoptionRepository adoptionRepository;

	

	@Autowired
	public AdoptionService(AdoptionRepository adoptionRepository) {
		this.adoptionRepository = adoptionRepository;
	}

	@Transactional
	public void saveAdoption(Adoption adoption) throws DataAccessException {
		adoptionRepository.save(adoption);
	}



	

}
