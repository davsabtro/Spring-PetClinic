package org.springframework.samples.petclinic.adoption;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.pet.Pet;

@Entity
public class Adoption extends BaseEntity {

	@ManyToOne
	private Owner owner;

	@OneToMany
	private List<Owner> SuitorsToAdopt;

	@ManyToOne
	private Pet pet;

	@Column(name = "request_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate request_date;

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public Pet getPet() {
		return pet;
	}

	public void setPet(Pet pet) {
		this.pet = pet;
	}

	public LocalDate getRequest_date() {
		return request_date;
	}

	public void setRequest_date(LocalDate request_date) {
		this.request_date = request_date;
	}

	public List<Owner> getSuitorsToAdopt() {
		return SuitorsToAdopt;
	}

	public void setSuitorsToAdopt(List<Owner> suitorsToAdopt) {
		SuitorsToAdopt = suitorsToAdopt;
	}

}
