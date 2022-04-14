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
package org.springframework.samples.petclinic.cause;

import java.util.Collection;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.donation.Donation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Controller
public class CauseController {

	private static final String VIEWS_CAUSE_CREATE_OR_UPDATE_FORM =
			"causes/createOrUpdateCauseForm";
	private static final String VIEWS_CAUSE_LIST = "causes/causesList";
	private static final String VIEWS_CAUSE_DETAILS = "causes/causeDetails";
	private static final String VIEWS_CAUSES_CREATE_REDIRECT = "redirect:/causes/new";


	private final CauseService causeService;


	@Autowired
	public CauseController(CauseService causeService) {
		this.causeService = causeService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@InitBinder("cause")
	public void initCauseBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new CauseValidator());
	}

	@GetMapping(value = "/causes/new")
	public String initCreationForm(Map<String, Object> model) {
		Cause cause = new Cause();
		model.put("cause", cause);
		return VIEWS_CAUSE_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/causes/new")
	public String processCreationForm(@Valid Cause cause, BindingResult result, ModelMap model,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors() || cause.getName().isEmpty() || cause.getDescription().isEmpty()
				|| cause.getOrganization().isEmpty()) {
			final String allErrors = result.getAllErrors().stream().map(ObjectError::toString)
					.reduce((e1, e2) -> e1 + ", " + e2).orElse("");
			if (allErrors != null && allErrors.contains("java.lang.NumberFormatException")) {
				model.clear();
				model.put("cause", cause);
				result = new BeanPropertyBindingResult(cause, "cause");
				result.rejectValue("budgetTarget",
						"No puedes introducir texto en el importe objetivo de la causa",
						"No puedes introducir texto en el importe objetivo de la causa");
				model.put("org.springframework.validation.BindingResult.donation", result);
			}
			redirectAttributes.addFlashAttribute("message", "Todos los campos son obligatorios");
			return VIEWS_CAUSES_CREATE_REDIRECT;
		} else {
			this.causeService.saveCause(cause);

			return "redirect:/causes/";
		}
	}

	@GetMapping(value = {"/causes"})
	public String showCauseList(Map<String, Object> model) {
		Collection<Cause> causes = causeService.findAll();
		model.put("causes", causes);
		return VIEWS_CAUSE_LIST;
	}

	@GetMapping(value = {"/causes/{id}/edit"})
	public String editCauseProfile(@PathVariable("id") int causeId, Model model) {
		Cause cause = causeService.findCauseById(causeId);
		model.addAttribute(cause);
		return VIEWS_CAUSE_CREATE_OR_UPDATE_FORM;
	}

	@GetMapping(value = {"/causes/{id}"})
	public String showCauseInfo(@PathVariable("id") int causeId, Model model) {
		Cause cause = causeService.findCauseById(causeId);
		Collection<Donation> donationList = causeService.findDonationListOfCauseById(causeId);
		model.addAttribute(cause);
		model.addAttribute(donationList);

		return VIEWS_CAUSE_DETAILS;
	}

	@PostMapping(value = "/causes/{id}/edit")
	public String processEditForm(Cause cause, BindingResult result,
			@PathVariable("id") int causeId) {
		if (result.hasErrors()) {
			return VIEWS_CAUSE_CREATE_OR_UPDATE_FORM;
		} else {
			cause.setId(causeId);
			causeService.saveCause(cause);
			return "redirect:/causes";
		}
	}


	@GetMapping("causes/{id}/delete")
	public String deleteCause(@PathVariable("id") int id) {
		Cause cause = causeService.findCauseById(id);
		causeService.deleteCause(cause);
		return "redirect:/causes";

	}


}
