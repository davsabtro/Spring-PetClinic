package org.springframework.samples.petclinic.pethotel;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.pet.Pet;

@Entity
@Table(name = "hotels")
public class PetHotel extends BaseEntity {

	@ManyToOne
	private Owner owner;

	@OneToOne
	@NotNull
	private Pet pet;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date startDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date finishDate;

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public Pet getPet() {
		return pet;
	}

	public void setPet(Pet pet) {
		this.pet = pet;
	}
}
