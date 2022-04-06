package org.springframework.samples.petclinic.detailsadoption;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.adoption.Adoption;
import org.springframework.samples.petclinic.adoption.AdoptionRepository;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.pethotel.PetHotel;
import org.springframework.samples.petclinic.pethotel.PetHotelRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DetailsAdoptionService {

	private DetailsAdoptionRepository detailsAdoptionRepository;

	@Autowired
	public DetailsAdoptionService(DetailsAdoptionRepository detailsAdoptionRepository) {
		this.detailsAdoptionRepository = detailsAdoptionRepository;
	}

	@Transactional
	public void saveDetailsAdoption(DetailsAdoption detailsAdoption) throws DataAccessException {
		detailsAdoptionRepository.save(detailsAdoption);
	}

	@Transactional(readOnly = true)
	public List<DetailsAdoption> findDetailsAdoptionByPetId(Integer petId) {
		return detailsAdoptionRepository.findDetailsAdoptionByPetId(petId);
	}

	@Transactional(readOnly = true)
	public Integer findNumberOfSuitorsByPetId(Integer petId) {
		return detailsAdoptionRepository.findNumberOfSuitorsByPetId(petId);
	}

	@Transactional(readOnly = true)
	public DetailsAdoption findDetailAdoptionsByOwner(Owner owner) {
		return detailsAdoptionRepository.findDetailAdoptionsByOwner(owner);
	}

	@Transactional(readOnly = true)
	public DetailsAdoption findDetailAdoptionsByAdoptionAndSuitor(Integer adoptionId, Integer suitorId) {
		return detailsAdoptionRepository.findDetailAdoptionsByAdoptionAndSuitor(adoptionId, suitorId);
	}

	@Transactional(readOnly = true)
	public void deleteDetailsByAdoption(Integer adoptionId) {
		detailsAdoptionRepository.deleteDetailsByAdoption(adoptionId);
	}

}
