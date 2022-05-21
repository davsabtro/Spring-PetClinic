package org.springframework.samples.petclinic.clinicowner;

import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/clinicowner")
public class ClinicOwnerController {
	private static final String VIEWS_CLINICOWNER_PREMIUM_PRICES = "clinicOwner/premium";

	private final ClinicOwnerService clinicOwnerService;
	private final UserService userService;

	@Autowired
	public ClinicOwnerController(ClinicOwnerService clinicOwnerService, UserService userService) {
		this.clinicOwnerService = clinicOwnerService;
		this.userService = userService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/premium")
	public String premiumSelectForm(Map<String, Object> model) {
		String currentUserName = this.userService.getCurrentUserName();
		ClinicOwner currentClinicOwner = this.clinicOwnerService.findClinicOwnerByUserName(currentUserName);
		model.put("currentClinicOwner", currentClinicOwner);
		return VIEWS_CLINICOWNER_PREMIUM_PRICES;
	}

	@GetMapping(value = "changePlan/{plan}")
	public String listSuitors(@PathVariable("plan") String plan, RedirectAttributes redirectAttributes) {
		String currentUserName = this.userService.getCurrentUserName();
		ClinicOwner currentClinicOwner = this.clinicOwnerService.findClinicOwnerByUserName(currentUserName);
		currentClinicOwner.setPlan(PlanType.valueOf(plan));

		if (plan.equals(PlanType.BASIC.toString())) {
			this.clinicOwnerService.saveClinicOwner(currentClinicOwner, "basicClinicOwner");
			redirectAttributes.addFlashAttribute("message", "Tu cuenta ha pasado a plan " + plan.charAt(0)
					+ plan.toLowerCase().substring(1, plan.length())
					+ ". Esto significa que ya no puedes disfrutar de todas las ventajas de PetClinic Premium. Esperemos no sea por mucho tiempo ;)"
					+ " ¡Recuerda que para visualizar tus funcionalidades debes volver a iniciar sesión!");

		} else if (plan.equals(PlanType.ADVANCED.toString())) {
			this.clinicOwnerService.saveClinicOwner(currentClinicOwner, "advancedClinicOwner");
			redirectAttributes.addFlashAttribute("message", "¡Buena opción! Ya puedes disfrutar de las ventajas de "
					+ plan.charAt(0) + plan.toLowerCase().substring(1, plan.length()) + " en Petclinic."
					+ " ¡Recuerda que para visualizar tus funcionalidades debes volver a iniciar sesión!");
		} else if (plan.equals(PlanType.PRO.toString())) {
			this.clinicOwnerService.saveClinicOwner(currentClinicOwner, "proClinicOwner");
			redirectAttributes.addFlashAttribute("message",
					"¡Genial! Ya eres " + plan.charAt(0) + plan.toLowerCase().substring(1, plan.length())
							+ " en Petclinic. ¡Puedes disfrutar de todos los servicios!"
							+ " ¡Recuerda que para visualizar tus funcionalidades debes volver a iniciar sesión!");
		}

		return "redirect:/";
	}

}
