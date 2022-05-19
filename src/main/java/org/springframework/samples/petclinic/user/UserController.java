/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.user;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.clinicowner.ClinicOwner;
import org.springframework.samples.petclinic.clinicowner.ClinicOwnerService;
import org.springframework.samples.petclinic.clinicowner.PlanType;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Controller
public class UserController {

	private static final String VIEWS_OWNER_CREATE_FORM = "users/signUpForm";

	private static final String VIEWS_CLINIC_OWNER_CREATE_FORM = "users/signUpForm";

	private final OwnerService ownerService;
	private final ClinicOwnerService clinicOwnerService;

	@Autowired
	public UserController(OwnerService ownerService, ClinicOwnerService clinicOwnerService) {
		this.ownerService = ownerService;
		this.clinicOwnerService = clinicOwnerService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/users/new")
	public String initCreationForm(Map<String, Object> model) {
		Owner owner = new Owner();
		model.put("owner", owner);
		return VIEWS_OWNER_CREATE_FORM;
	}

	@PostMapping(value = "/users/new")
	public String processCreationForm(@Valid Owner owner, BindingResult result, RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			return VIEWS_OWNER_CREATE_FORM;
		} else {
			// creating owner, user, and authority
			this.ownerService.saveOwner(owner);
			redirectAttributes.addFlashAttribute("message",
					"¡Usuario " + owner.getUser().getUsername() + " registrado con éxito!");
			return "redirect:/";
		}
	}

	@GetMapping(value = "/clinics/new")
	public String initCreationClinicForm(Map<String, Object> model) {
		ClinicOwner clinicOwner = new ClinicOwner();
		model.put("clinicOwner", clinicOwner);
		return VIEWS_CLINIC_OWNER_CREATE_FORM;
	}

	@PostMapping(value = "/clinics/new")
	public String processCreationClinicForm(@Valid ClinicOwner clinicOwner, BindingResult result,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			return VIEWS_CLINIC_OWNER_CREATE_FORM;
		} else {
			clinicOwner.setPlan(PlanType.BASIC);
			this.clinicOwnerService.saveClinicOwner(clinicOwner, "basicClinicOwner");
			redirectAttributes.addFlashAttribute("message",
					"¡Clinica " + clinicOwner.getClinicName() + " registrada con éxito!");
			return "redirect:/";
		}
	}

}
