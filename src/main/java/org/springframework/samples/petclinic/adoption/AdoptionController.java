package org.springframework.samples.petclinic.adoption;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerService;
import org.springframework.samples.petclinic.pet.Pet;
import org.springframework.samples.petclinic.pet.PetService;
import org.springframework.samples.petclinic.pet.exceptions.DuplicatedPetNameException;
import org.springframework.samples.petclinic.pethotel.PetHotelController;
import org.springframework.samples.petclinic.pethotel.PetHotelService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/adoption")
public class AdoptionController {

	private PetHotelService petHotelService;
	private AdoptionService adoptionService;
	private PetService petService;
	private OwnerService ownerService;
	private ConversionService conversionService;

	private static final String VIEWS_PETS_LIST = "adoptions/petList";
	private static final String VIEWS_PETS_IN_ADOPTION_LIST = "adoptions/petInAdoptionList";
	private static final String VIEWS_PET_IS_REQUESTED_YET_VIEW = "adoptions/petIsRequestedYet";
	private static final String VIEWS_SUITORS_LIST = "adoptions/adoptionSuitorsList";

	private static final Logger log = LoggerFactory.getLogger(AdoptionController.class);

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@Autowired
	public AdoptionController(PetHotelService petHotelService, AdoptionService adoptionService, PetService petService,
			OwnerService ownerService) {
		this.petHotelService = petHotelService;
		this.adoptionService = adoptionService;
		this.petService = petService;
		this.ownerService = ownerService;
	}

	@GetMapping(value = "/petsList")
	public String listMyOwnPets(ModelMap model) {
		User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = currentUser.getUsername();
		Owner owner = ownerService.findOwnerUserName(userName);
		Collection<Pet> pets = petService.findPetsByOwner(owner);
		model.put("petsCollection", pets);
		log.info("La lista de pets: " + pets);
		Integer numOfPets = petService.findNumberOfPetsByOwner(owner);
		model.put("numOfPets", numOfPets);
		return VIEWS_PETS_LIST;
	}

	@GetMapping(value = "/{petId}/givePetInAdoption")
	public String giveInAdoptionAction(@PathVariable("petId") int petId, RedirectAttributes redirectAttributes)
			throws DataAccessException, DuplicatedPetNameException {
		Adoption adoption = new Adoption();
		Pet pet = this.petService.findPetById(petId);
		User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = currentUser.getUsername();
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
		User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = currentUser.getUsername();
		model.put("currentUserName", userName);
		Owner owner = ownerService.findOwnerUserName(userName);
		model.put("owner", owner);
		for (Adoption adoption : petsForAdoption) {
			if (adoption.getSuitorsToAdopt().contains(owner)) {
				model.put("petData", adoption);
				return VIEWS_PET_IS_REQUESTED_YET_VIEW;
			}
		}
		return VIEWS_PETS_IN_ADOPTION_LIST;
	}

	@GetMapping(value = "/{adoptionId}/{petId}/{adopterId}/requestForAdoption")
	public String requestForAdoption(@PathVariable("adoptionId") int adoptionId, @PathVariable("petId") int petId,
			@PathVariable("adopterId") int adopterId, RedirectAttributes redirectAttributes) {
		System.out.println("Hemos entrado aqui");
		Adoption adoption = adoptionService.findPetsForAdoptionByAdoptionId(adoptionId);
		Pet pet = this.petService.findPetById(petId);
		User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = currentUser.getUsername();
		Owner owner = ownerService.findOwnerUserName(userName);
		redirectAttributes.addFlashAttribute("owner", owner);
		System.out.println("owner es: " + owner);
		List<Owner> suitorsToAdoptList = adoption.getSuitorsToAdopt();
		suitorsToAdoptList.add(owner);
		adoptionService.saveAdoption(adoption);
		redirectAttributes.addFlashAttribute("message",
				String.format("Has solicitado la adopción de %s", pet.getName()));
		return "redirect:/";
	}

	@GetMapping(value = "/{petId}/suitorsList")
	public String listSuitors(@PathVariable("petId") int petId, ModelMap model) {
		List<Owner> suitors = adoptionService.findSuitorsByPetId(petId);
		Integer numOfSuitors = suitors.size();
		model.put("suitors", suitors);
		model.put("numOfSuitors", numOfSuitors);
		return VIEWS_SUITORS_LIST;
	}

}
