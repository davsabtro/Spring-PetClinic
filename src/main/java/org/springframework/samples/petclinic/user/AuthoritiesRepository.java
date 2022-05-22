package org.springframework.samples.petclinic.user;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AuthoritiesRepository extends CrudRepository<Authorities, String> {

	@Query("SELECT a FROM Authorities a WHERE a.user.username = :username")
	public Collection<Authorities> findAuthoritiesByUserName(@Param("username") String username);

}
