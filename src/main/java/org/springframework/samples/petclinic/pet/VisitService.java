package org.springframework.samples.petclinic.pet;

import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VisitService {
    private VisitRepository visitRepository;
    @Autowired
	public VisitService(VisitRepository visitRepository) {
		this.visitRepository = visitRepository;
		
	}

    @Transactional
	public void deleteVisits(Pet pet){
      for(Visit visit : pet.getVisits()){
        visitRepository.deleteById(visit.getId());
        }
	}
    @Transactional
	public void deleteVisitById(Integer visitId,Pet pet) {
      pet.setVisitsInternal(new HashSet<>());
      visitRepository.deleteById(visitId);
	}
    
}
