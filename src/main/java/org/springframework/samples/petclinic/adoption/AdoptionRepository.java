package org.springframework.samples.petclinic.adoption;

import org.springframework.data.repository.CrudRepository;


public interface AdoptionRepository extends CrudRepository<Adoption, Integer>{

}
