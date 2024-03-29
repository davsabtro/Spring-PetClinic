/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.springframework.samples.petclinic.donation;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


public class DonationValidator implements Validator {

	@Override
	public void validate(Object obj, Errors errors) {
		var donation = (Donation) obj;

		String amount = "amount";

		if (donation.getAmount() == null) {
			errors.rejectValue(amount, "La donación no puede ser nula",
					"La donación no puede ser nula");
		}

		if (donation.getAmount() <= 0) {
			errors.rejectValue(amount, "El importe de donacion debe ser mayor que cero",
					"El importe de donacion debe ser mayor que cero");
		}

		String[] splitter = donation.getAmount().toString().split("\\.");
		if (splitter[1].length() > 2) {
			errors.rejectValue(amount, "El importe no debe de tener más de dos números decimales",
					"El importe no debe de tener más de dos números decimales");
		}
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Donation.class.isAssignableFrom(clazz);
	}

}
