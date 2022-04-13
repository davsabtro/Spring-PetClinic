package org.springframework.samples.petclinic.pethotel;

import java.util.Collection;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.pet.Pet;
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
