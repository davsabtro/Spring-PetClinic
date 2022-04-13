package org.springframework.samples.petclinic.cause;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.donation.Donation;

public interface CauseRepository extends CrudRepository<Cause, Integer> {

	@Query("SELECT DISTINCT c FROM Cause c WHERE c.budgetTarget > c.donated")
	public Collection<Cause> findAll();

	Cause findById(int id) throws DataAccessException;

	@Query("SELECT d FROM Donation d WHERE d.cause.id = ?1")
	Collection<Donation> findDonationListOfCauseById(int id) throws DataAccessException;

}
