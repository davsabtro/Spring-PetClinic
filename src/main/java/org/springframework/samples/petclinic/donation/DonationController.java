package org.springframework.samples.petclinic.donation;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.cause.Cause;
import org.springframework.samples.petclinic.cause.CauseService;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/causes/{causeId}/donate")
public class DonationController {

	private static final String VIEWS_DONATION_CREATE_FORM = "donations/createDonationForm";

	private final DonationService donationService;
	private final CauseService causeService;
	private final UserService userService;

	@Autowired
	public DonationController(DonationService donationService, CauseService causeService,
			UserService userService) {
		this.donationService = donationService;
		this.causeService = causeService;
		this.userService = userService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@InitBinder("donation")
	public void initDonationBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new DonationValidator());
	}

	private User getLoggedUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return userService.findUser(auth.getName()).orElseThrow(NoSuchElementException::new);
	}

	@GetMapping("/new")
	public String initCreationForm(@PathVariable(name = "causeId") int causeId, Model model) {
		model.addAttribute("cause", causeService.findCauseById(causeId));
		model.addAttribute("donation", new Donation());
		return VIEWS_DONATION_CREATE_FORM;
	}

	@PostMapping("/new")
	public String processCreationForm(HttpSession session,
			@PathVariable(name = "causeId") int causeId, @Valid Donation donation, BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			if (result.getAllErrors().get(0).getDefaultMessage().contains("java.lang.NumberFormatException")) {	
				System.out.println(model.values());
				model.clear();
				model.put("donation", donation);
                result = new BeanPropertyBindingResult(donation, "donation");
				result.rejectValue("amount", "No puedes introducir texto en el importe de la donación", "No puedes introducir texto en el importe de la donación");
				model.put("org.springframework.validation.BindingResult.donation", result);
            }
			return VIEWS_DONATION_CREATE_FORM;

		} else {
			Cause cause = causeService.findCauseById(causeId);
			donation.setCause(cause);
			donation.setDonationDate(LocalDate.now());
			donation.setClient(getLoggedUser());
			try {
				donationService.saveDonation(donation);
				session.setAttribute("message",
						String.format("Hemos recibido su donación de %.02f EUR, muchas gracias.",
								donation.getAmount()));
			} catch (IllegalArgumentException e) {
				session.setAttribute("message",
						"Ha ocurrido un problema realizando la donación. Vuelva a intentarlo más tarde.");
			}
			return "redirect:/causes/" + cause.getId();
		}
	}
}
