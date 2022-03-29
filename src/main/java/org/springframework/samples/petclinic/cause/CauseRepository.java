package org.springframework.samples.petclinic.cause;

import java.util.Collection;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface CauseRepository extends CrudRepository<Cause,Integer>{
	
	@Query("SELECT DISTINCT c FROM Cause c WHERE c.budgetTarget > c.donated")
	public Collection<Cause> findAll();	

	Cause findById(int id) throws DataAccessException;
   
}
