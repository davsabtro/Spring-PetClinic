/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.springframework.samples.petclinic.pet;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.pet.exceptions.DuplicatedPetNameException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder for @Transactional
 * and @Cacheable annotations
 *
 * @author Michael Isvy
 */
@Service
public class PetService {

	private PetRepository petRepository;

	private VisitRepository visitRepository;

	@Autowired
	private VisitService visitService;

	@Autowired
	public PetService(PetRepository petRepository, VisitRepository visitRepository) {
		this.petRepository = petRepository;
		this.visitRepository = visitRepository;
	}

	@Transactional(readOnly = true)
	public Collection<PetType> findPetTypes() throws DataAccessException {
		return petRepository.findPetTypes();
	}

	@Transactional
	public void saveVisit(Visit visit) throws DataAccessException {
		visitRepository.save(visit);
	}

	@Transactional(readOnly = true)
	public Pet findPetById(int id) throws DataAccessException {
		return petRepository.findById(id);
	}

	@Transactional(rollbackFor = DuplicatedPetNameException.class)
	public void savePet(Pet pet) throws DataAccessException, DuplicatedPetNameException {
		if (pet.getOwner() != null) {
			Pet otherPet = pet.getOwner().getPetwithIdDifferent(pet.getName(), pet.getId());
			if (StringUtils.hasLength(pet.getName())
					&& (otherPet != null && otherPet.getId() != pet.getId())) {
				throw new DuplicatedPetNameException();
			} else
				petRepository.save(pet);
		} else
			petRepository.save(pet);
	}


	public Collection<Visit> findVisitsByPetId(int petId) {
		return visitRepository.findByPetId(petId);
	}

	@Transactional(readOnly = true)
	public Collection<Pet> findPetsByOwner(Owner user) throws DataAccessException {
		return petRepository.findPetsByOwner(user);
	}

	@Transactional
	public void deletePet(Pet pet) {
		Integer petId = pet.getId();
		visitService.deleteVisits(pet);
		petRepository.deleteById(petId);
	}

	@Transactional(readOnly = true)
	public Integer findNumberOfPetsByOwner(Owner user) throws DataAccessException {
		return petRepository.findNumberOfPetsByOwner(user);
	}

}
