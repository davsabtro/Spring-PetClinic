package org.springframework.samples.petclinic.vet;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;


@Component
public class SpecialtyFormatter implements Formatter<Specialty> {
    
	private final VetService vetService;

	@Autowired
	public SpecialtyFormatter(VetService vetService) {
		this.vetService = vetService;
	}

	public String print(Specialty specialty, Locale locale) {
		return specialty.getName();
	}

	public Specialty parse(String text, Locale locale) throws ParseException {
		Collection<Specialty> findSpecialties = this.vetService.findSpecialties();
		for (Specialty specialty : findSpecialties) {
			if (specialty.getName().equals(text)) {
				return specialty;
			}
		}
		throw new ParseException("specialty not found: " + text, 0);
	}
}
