package org.springframework.samples.petclinic.pethotel;

import java.time.ZoneId;
import java.util.Date;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PetHotelDateToStringConverter implements Converter<Date, String> {

	@Override
	public String convert(Date source) {
		return source.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toString();
	}
}
