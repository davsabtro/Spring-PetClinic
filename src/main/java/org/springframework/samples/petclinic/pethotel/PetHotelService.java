package org.springframework.samples.petclinic.pethotel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.pet.Pet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;

@Service
public class PetHotelService {

	private PetHotelRepository petHotelRepository;

	@Autowired
	private ConversionService conversionService;

	@Autowired
	public PetHotelService(PetHotelRepository petHotelRepository) {
		this.petHotelRepository = petHotelRepository;
	}

	@Transactional
	public void savePetHotel(PetHotel petHotel) throws DataAccessException {
		petHotelRepository.save(petHotel);
	}

	@Transactional(readOnly = true)
	public Collection<PetHotel> findPetHotelDataByPet(Pet pet) {
		return petHotelRepository.findPetHotelDataByPet(pet);
	}

	@Transactional(readOnly = true)
	public Integer findNumberOfReservationsByPet(Pet pet) {
		return petHotelRepository.findNumberOfReservationsByPet(pet);
	}

	@Transactional(readOnly = true)
	public Collection<PetHotel> findPetHotelDataByOwner(Owner owner) {
		return petHotelRepository.findPetHotelDataByOwner(owner);
	}

	public boolean checkPetHasRoomForThisDate(Date startDate, Date finishDate, Date bookedStartDate,
			Date bookedFinishedDate) {
		return ((startDate.after(bookedStartDate) && finishDate.before(bookedFinishedDate))
				|| (startDate.before(bookedStartDate) && finishDate.after(bookedStartDate))
				|| (startDate.before(bookedFinishedDate) && finishDate.after(bookedFinishedDate))
				|| startDate.equals(bookedStartDate) || finishDate.equals(bookedFinishedDate));

	}

}
