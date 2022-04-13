package org.springframework.samples.petclinic.donation;

import org.springframework.data.repository.CrudRepository;

public interface DonationRepository extends CrudRepository<Donation, Integer> {

}
