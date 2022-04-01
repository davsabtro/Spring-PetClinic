package org.springframework.samples.petclinic.adoption;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.owner.Owner;
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

	@Transactional(readOnly = true)
	public Collection<Adoption> findPetsForAdoption() throws DataAccessException {
		return adoptionRepository.findPetsForAdoption();
	}
	
	@Transactional(readOnly = true)
	public Integer findNumberOfPetsForAdoption() throws DataAccessException {
		return adoptionRepository.findNumberOfPetsForAdoption();
	}

	@Transactional(readOnly = true)
	public Adoption findPetsForAdoptionByAdoptionId(Integer adoptionId) throws DataAccessException {
		return adoptionRepository.findPetsForAdoptionByAdoptionId(adoptionId);
	}

	@Transactional(readOnly = true)
	public List<Adoption> findAdoptionDataByPetId(Integer petId) throws DataAccessException {
		return adoptionRepository.findAdoptionDataByPetId(petId);
	}

}
