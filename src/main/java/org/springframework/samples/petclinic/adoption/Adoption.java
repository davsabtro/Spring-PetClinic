package org.springframework.samples.petclinic.adoption;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.pet.Pet;

@Entity
public class Adoption extends BaseEntity {

	@ManyToOne
	private Owner owner;

	@ManyToOne
	private Pet pet;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate requestDate;

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

	public LocalDate getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(LocalDate requestDate) {
		this.requestDate = requestDate;
	}
}
