package org.springframework.samples.petclinic.donation;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.petclinic.cause.Cause;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.user.User;

@Entity
@Table(name = "donations")
public class Donation extends BaseEntity {

	@NotNull
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate donationDate;

	@Positive
	private double amount;

	@NotNull
	@ManyToOne
	private Cause cause;

	@NotNull
	@ManyToOne
	private User client;

	public void setDonationDate(LocalDate donationDate) {
		this.donationDate = donationDate;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public void setCause(Cause cause) {
		this.cause = cause;
	}

	public void setClient(User client) {
		this.client = client;
	}

	public LocalDate getDonationDate() {
		return donationDate;
	}

	public Double getAmount() {
		return this.amount;
	}

	public Cause getCause() {
		return this.cause;
	}

	public User getClient() {
		return this.client;
	}
}
