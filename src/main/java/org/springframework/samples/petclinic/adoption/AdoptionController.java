package org.springframework.samples.petclinic.adoption;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.detailsadoption.DetailsAdoption;
import org.springframework.samples.petclinic.detailsadoption.DetailsAdoptionService;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerService;
import org.springframework.samples.petclinic.pet.Pet;
import org.springframework.samples.petclinic.pet.PetService;
import org.springframework.samples.petclinic.pet.exceptions.DuplicatedPetNameException;
import org.springframework.samples.petclinic.pethotel.PetHotelController;
import org.springframework.samples.petclinic.pethotel.PetHotelService;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.samples.petclinic.vet.Vet;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/adoption")
public class AdoptionController {

	private AdoptionService adoptionService;
	private PetService petService;
	private OwnerService ownerService;
	private DetailsAdoptionService detailsAdoptionService;
	private UserService userService;

	private static final String VIEWS_PETS_LIST = "adoptions/petListForAdoption";
	private static final String VIEWS_PETS_IN_ADOPTION_LIST = "adoptions/petInAdoptionList";
	private static final String VIEWS_PET_IS_REQUESTED_YET_VIEW = "adoptions/petIsRequestedYet";
	private static final String VIEWS_SUITORS_LIST = "adoptions/adoptionSuitorsList";
	private static final String VIEWS_ADOPTION_REQUEST_FORM = "adoptions/petAdoptionRequestForm";

	private static final Logger log = LoggerFactory.getLogger(AdoptionController.class);

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@Autowired
	public AdoptionController(AdoptionService adoptionService, PetService petService, OwnerService ownerService,
			DetailsAdoptionService detailsAdoptionService, UserService userService) {
		this.adoptionService = adoptionService;
		this.petService = petService;
		this.ownerService = ownerService;
		this.detailsAdoptionService = detailsAdoptionService;
		this.userService = userService;
	}

	@GetMapping(value = "/petsList")
	public String listMyOwnPetsForGiveItsInAdoption(ModelMap model) {
		String userName = userService.getCurrentUserName();
		Owner owner = ownerService.findOwnerUserName(userName);
		model.put("owner", owner);
		Collection<Pet> pets = petService.findPetsByOwner(owner);
		model.put("petsCollection", pets);
		log.info("La lista de pets: " + pets);
		Integer numOfPets = petService.findNumberOfPetsByOwner(owner);
		model.put("numOfPets", numOfPets);
		List<Pet> myPetsCollection = ownerService.findOwnersPets(userName);
		model.put("myPetsCollection", myPetsCollection);
		return VIEWS_PETS_LIST;
	}

	@GetMapping(value = "/{petId}/givePetInAdoption")
	public String giveInAdoptionAction(@PathVariable("petId") int petId, RedirectAttributes redirectAttributes)
			throws DataAccessException, DuplicatedPetNameException {
		Adoption adoption = new Adoption();
		Pet pet = this.petService.findPetById(petId);
		String userName = userService.getCurrentUserName();
		Owner owner = ownerService.findOwnerUserName(userName);
		adoption.setOwner(owner);
		adoption.setPet(pet);
		LocalDate now = LocalDate.now();
		adoption.setRequest_date(now);
		adoptionService.saveAdoption(adoption);
		pet.setIsGivenForAdoption(true);
		petService.savePet(pet);
		redirectAttributes.addFlashAttribute("message",
				String.format("Has agregado la mascota %s en la lista de adopción", pet.getName()));
		return "redirect:/adoption/petsList";
	}

	@GetMapping(value = "/petsOnAdoptionList")
	public String listPetsOnAdoption(ModelMap model) {
		Collection<Adoption> petsForAdoption = adoptionService.findPetsForAdoption();
		model.put("petsCollection", petsForAdoption);
		Integer numOfPets = adoptionService.findNumberOfPetsForAdoption();
		model.put("numOfPets", numOfPets);
		String userName = userService.getCurrentUserName();
		model.put("currentUserName", userName);
		Owner owner = ownerService.findOwnerUserName(userName);
		model.put("owner", owner);
		DetailsAdoption detailsAdoption = detailsAdoptionService.findDetailAdoptionsByOwner(owner);
		if (!Objects.isNull(detailsAdoption)) {
			model.put("detailsAdoption", detailsAdoption);
			return VIEWS_PET_IS_REQUESTED_YET_VIEW;
		}
		return VIEWS_PETS_IN_ADOPTION_LIST;
	}

	@GetMapping(value = "/{adoptionId}/manageAdoptionRequest")
	public String manageAdoptionRequest(@PathVariable("adoptionId") int adoptionId, ModelMap model) {
		Adoption adoption = adoptionService.findPetsForAdoptionByAdoptionId(adoptionId);
		model.put("adoption", adoption);
		return VIEWS_ADOPTION_REQUEST_FORM;
	}

	@PostMapping(value = "/{adoptionId}/manageAdoptionRequest")
	public String adoptionRequest(@Valid Adoption adopt, @RequestParam("adoptionId") int adoptionId,
			@RequestParam("petId") int petId, @RequestParam("adopterId") int adopterId,
			@RequestParam("careDescription") String careDescription, BindingResult result,
			RedirectAttributes redirectAttributes, ModelMap model) {
		if (result.hasErrors()) {
			model.put("message", "Se han producido errores el enviar la solicitud. Por favor, vuelva a intentarlo.");
			return VIEWS_ADOPTION_REQUEST_FORM;
		} else {
			Adoption adoption = adoptionService.findPetsForAdoptionByAdoptionId(adoptionId);
			DetailsAdoption detailsAdoption = new DetailsAdoption();
			Pet pet = this.petService.findPetById(petId);
			String userName = userService.getCurrentUserName();
			Owner owner = ownerService.findOwnerUserName(userName);
			redirectAttributes.addFlashAttribute("owner", owner);
			adoptionService.saveAdoption(adoption);
			detailsAdoption.setAdoption(adoption);
			detailsAdoption.setCareDescription(careDescription);
			detailsAdoption.setRequest_date(LocalDate.now());
			detailsAdoption.setSuitorToAdopt(owner);
			this.detailsAdoptionService.saveDetailsAdoption(detailsAdoption);
			redirectAttributes.addFlashAttribute("message",
					String.format("Has solicitado la adopción de %s", pet.getName()));
			return "redirect:/";
		}
	}

	@GetMapping(value = "/{petId}/suitorsList")
	public String listSuitors(@PathVariable("petId") int petId, ModelMap model) {
		List<DetailsAdoption> detailsAdoption = detailsAdoptionService.findDetailsAdoptionByPetId(petId);
		Integer numOfSuitors = detailsAdoptionService.findNumberOfSuitorsByPetId(petId);
		model.put("detailsAdoption", detailsAdoption);
		model.put("numOfSuitors", numOfSuitors);
		return VIEWS_SUITORS_LIST;
	}

}
