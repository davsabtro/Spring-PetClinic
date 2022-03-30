package org.springframework.samples.petclinic.adoption;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.pet.Pet;

@Entity
public class Adoption extends BaseEntity{
	
	@ManyToOne
	private Owner owner;
	
	@ManyToOne
	private Pet pet;
	
	@Column(name = "request_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date request_date;

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

	public Date getRequest_date() {
		return request_date;
	}

	public void setRequest_date(Date request_date) {
		this.request_date = request_date;
	}
	
	


}
