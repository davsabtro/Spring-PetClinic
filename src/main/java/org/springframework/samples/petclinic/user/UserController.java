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
package org.springframework.samples.petclinic.user;

import java.util.Map;
import java.util.Objects;
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
	private static final String VIEWS_OWNER_CHANGE_PASSWORD_FORM = "users/changePasswordForm";
	private static final String VIEWS_CLINIC_OWNER_CREATE_FORM = "users/signUpForm";

	private final OwnerService ownerService;
	private final UserService userService;
	private final ClinicOwnerService clinicOwnerService;

	@Autowired
	public UserController(OwnerService ownerService, UserService userService,
			ClinicOwnerService clinicOwnerService) {
		this.ownerService = ownerService;
		this.userService = userService;
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
	public String processCreationForm(@Valid Owner owner, BindingResult result,
			RedirectAttributes redirectAttributes) {
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

	@GetMapping(value = "/users/changePassword")
	public String initChangePasswordForm(Map<String, Object> model) {
		PasswordDTO values = new PasswordDTO();
		String username = userService.getCurrentUserName();
		User user = userService.findUser(username).orElse(null);
		if (user != null) {
			values.setUserPassword(user.getPassword());
		}
		model.put("values", values);
		return VIEWS_OWNER_CHANGE_PASSWORD_FORM;
	}

	@PostMapping(value = "/users/changePassword")
	public String processChangePasswordForm(PasswordDTO values, BindingResult result,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			return VIEWS_OWNER_CHANGE_PASSWORD_FORM;
		} else {
			// creating owner, user, and authority
			var currentPassword = values.getCurrentPassword();
			var newPasword = values.getNewPassword();
			var confirmPassword = values.getConfirmPassword();

			String username = userService.getCurrentUserName();
			User user = userService.findUser(username).orElse(null);

			if (Objects.isNull(user)) {
				redirectAttributes.addFlashAttribute("message", "Usuario no encontrado");
				return VIEWS_OWNER_CHANGE_PASSWORD_FORM;
			}

			if (Objects.isNull(currentPassword) || Objects.isNull(newPasword)
					|| Objects.isNull(confirmPassword)) {
				redirectAttributes.addFlashAttribute("message",
						"Todos los campos son obligatorios");
				return VIEWS_OWNER_CHANGE_PASSWORD_FORM;
			}
			if (!currentPassword.equals(user.getPassword())) {
				redirectAttributes.addFlashAttribute("message",
						"La contraseña no coincide con la actual");
				return VIEWS_OWNER_CHANGE_PASSWORD_FORM;
			}

			if (!newPasword.equals(confirmPassword)) {
				redirectAttributes.addFlashAttribute("message",
						"La contraseña nueva no coincide con la confirmación");
				return VIEWS_OWNER_CHANGE_PASSWORD_FORM;
			}

			user.setPassword(newPasword);
			this.userService.saveUser(user);
			return "redirect:/";
		}
	}

}
