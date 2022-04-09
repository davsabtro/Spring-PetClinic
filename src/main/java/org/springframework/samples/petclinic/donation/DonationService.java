package org.springframework.samples.petclinic.donation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.cause.Cause;
import org.springframework.samples.petclinic.cause.CauseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class DonationService {

	private DonationRepository donationRepository;

	@Autowired
	private CauseService causeService;

	@Autowired
	public DonationService(DonationRepository donationRepository) {
		this.donationRepository = donationRepository;
	}

	@Transactional
	public void saveDonation(Donation donation) {
		Cause cause = donation.getCause();
		if (cause.isClosed())
			throw new IllegalArgumentException();
		double leftToFulfill = cause.getBudgetTarget() - cause.getDonated();
		donation.setAmount(Math.min(donation.getAmount(), leftToFulfill));
		cause.setDonated(cause.getDonated() + donation.getAmount());
		causeService.saveCause(cause);
		donationRepository.save(donation);
	}

}
