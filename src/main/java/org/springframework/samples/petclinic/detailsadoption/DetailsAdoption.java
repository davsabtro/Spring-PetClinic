package org.springframework.samples.petclinic.detailsadoption;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.petclinic.adoption.Adoption;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.pet.Pet;

@Entity
public class DetailsAdoption extends BaseEntity {

	@ManyToOne
	private Adoption adoption;

	@ManyToOne
	private Owner suitorToAdopt;

	@Column(name = "request_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate request_date;

	@NotBlank
	private String careDescription;

	public Adoption getAdoption() {
		return adoption;
	}

	public void setAdoption(Adoption adoption) {
		this.adoption = adoption;
	}

	public Owner getSuitorToAdopt() {
		return suitorToAdopt;
	}

	public void setSuitorToAdopt(Owner suitorToAdopt) {
		this.suitorToAdopt = suitorToAdopt;
	}

	public LocalDate getRequest_date() {
		return request_date;
	}

	public void setRequest_date(LocalDate request_date) {
		this.request_date = request_date;
	}

	public String getCareDescription() {
		return careDescription;
	}

	public void setCareDescription(String careDescription) {
		this.careDescription = careDescription;
	}

}
