package org.springframework.samples.petclinic.detailsadoption;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.petclinic.adoption.Adoption;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.owner.Owner;

@Entity
public class DetailsAdoption extends BaseEntity {

	@ManyToOne
	private Adoption adoption;

	@ManyToOne
	private Owner suitorToAdopt;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate requestDate;

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

	public LocalDate getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(LocalDate requestDate) {
		this.requestDate = requestDate;
	}

	public String getCareDescription() {
		return careDescription;
	}

	public void setCareDescription(String careDescription) {
		this.careDescription = careDescription;
	}

}
