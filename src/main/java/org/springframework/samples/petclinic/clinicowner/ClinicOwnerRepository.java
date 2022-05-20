package org.springframework.samples.petclinic.clinicowner;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.donation.Donation;

public interface ClinicOwnerRepository extends CrudRepository<ClinicOwner, Integer> {

	@Query("SELECT c FROM ClinicOwner c WHERE c.user.username = ?1")
	ClinicOwner findClinicOwnerByUserName(String username) throws DataAccessException;
	
	

}
