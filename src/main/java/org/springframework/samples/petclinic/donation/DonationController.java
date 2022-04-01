package org.springframework.samples.petclinic.donation;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.cause.Cause;
import org.springframework.samples.petclinic.cause.CauseService;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
			@PathVariable(name = "causeId") int causeId, Donation donation, BindingResult result) {
		if (result.hasErrors()) {
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