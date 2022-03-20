package org.springframework.samples.petclinic.pethotel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.core.convert.ConversionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerService;
import org.springframework.samples.petclinic.pet.Pet;
import org.springframework.samples.petclinic.pet.PetService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/pethotels")
public class PetHotelController {

	private static final String VIEWS_PETHOTEL_CREATE_FORM = "pethotels/createPethotelForm";

	private PetHotelService petHotelService;
	private PetService petService;
	private OwnerService ownerService;
	private ConversionService conversionService;

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@Autowired
	public PetHotelController(PetHotelService petHotelService, PetService petService, OwnerService ownerService,
			ConversionService conversionService) {
		this.petHotelService = petHotelService;
		this.petService = petService;
		this.ownerService = ownerService;
		this.conversionService = conversionService;
	}

	@InitBinder
	public void bindingPreparation(WebDataBinder binder) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		CustomDateEditor orderDateEditor = new CustomDateEditor(dateFormat, true);
		binder.registerCustomEditor(Date.class, orderDateEditor);
	}

	@GetMapping(value = "/new")
	public String initCreationForm(Map<String, Object> model) {
		PetHotel pethotel = new PetHotel();
		model.put("pethotel", pethotel);
		User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = currentUser.getUsername();
		Owner owner = ownerService.findOwnerUserName(userName);
		Collection<Pet> pets = petService.findPetsByOwner(owner);
		model.put("petsCollection", pets);
		String today = LocalDate.now().toString();
		model.put("today", today);
		String tomorrow = LocalDate.now().plusDays(1).toString();
		model.put("tomorrow", tomorrow);
		Collection<PetHotel> petHotelDataAboutThisOwner = petHotelService.findPetHotelDataByOwner(owner);
		if (!petHotelDataAboutThisOwner.isEmpty()) {
			model.put("petHotelDataAboutThisOwner", petHotelDataAboutThisOwner);
		}
		return VIEWS_PETHOTEL_CREATE_FORM;
	}

	@PostMapping(value = "/new")
	public String processCreationForm(
			@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date startDate,
			@RequestParam("finishDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date finishDate,
			@RequestParam("pet") Pet pet, @Valid PetHotel pethotel, BindingResult result,
			RedirectAttributes redirectAttributes, ModelMap model) {

		User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = currentUser.getUsername();
		Owner owner = ownerService.findOwnerUserName(userName);
		String redirect = "";
		if (result.hasErrors()) {
			return VIEWS_PETHOTEL_CREATE_FORM;
		} 
		//validación de fecha
		else if (startDate.after(finishDate)) {
			redirect = "redirect:/pethotels/new";
			String message = "Fecha incorrecta";
			redirectAttributes.addFlashAttribute("message", message);
		} 
		
		//si todo lo anterior bien
		else {
		Collection<PetHotel> petHotelDataAboutThisPet = petHotelService.findPetHotelDataByPet(pet);
		// si hay al menos una reserva, antes de reservar, obtenemos los datos de ésta
		if (!petHotelDataAboutThisPet.isEmpty()) {
			for (PetHotel phData : petHotelDataAboutThisPet) {
				Date bookedStartDate = phData.getStartDate();
				Date bookedFinishedDate = phData.getFinishDate();
				Boolean petHasRoomForThisDate = this.petHotelService.checkPetHasRoomForThisDate(startDate, finishDate,
						bookedStartDate, bookedFinishedDate);
				// comprobamos si ya tenemos una reserva en las fechas elegidas y configuramos
				// mensaje de error
				if (petHasRoomForThisDate) {
					Integer numberOfHotelReservationsForThisPet = petHotelService.findNumberOfReservationsByPet(pet);
					if (numberOfHotelReservationsForThisPet.equals(1)) {
						String mssg = String.format(
								"Ya tienes una reserva desde %s hasta %s para tu mascota %s. ¡Elige otra fecha!",
								conversionService.convert(bookedStartDate, String.class),
								conversionService.convert(bookedFinishedDate, String.class), pet);
						redirectAttributes.addFlashAttribute("message", mssg);
					} else if (numberOfHotelReservationsForThisPet > 1) {
						String mssg = String.format(
								"Ya tienes %d reservas para tu mascota %s en estas fechas. ¡Elige otra fecha!",
								numberOfHotelReservationsForThisPet, pet);
						redirectAttributes.addFlashAttribute("message", mssg);
					}
					redirect = "redirect:/pethotels/new";
				}
				// si no hay ninguna reserva en las fechas elegidas, reservamos el hotel.
				else {
					redirect = "redirect:/";
					String message = String.format(
							"Has reservado una habitacion para %s desde %s hasta %s. ¡Recibirás un correo de confirmación!",
							pet, conversionService.convert(startDate, String.class),
							conversionService.convert(finishDate, String.class));
					redirectAttributes.addFlashAttribute("message", message);
					pethotel.setFinishDate(finishDate);
					pethotel.setStartDate(startDate);
					pethotel.setPet(pet);
					pethotel.setOwner(owner);
					this.petHotelService.savePetHotel(pethotel);
				}
			}
		}
		// si no había ninguna reserva anteriormente, reservamos directamente
		else {
			redirect = "redirect:/";
			String message = String.format(
					"Has reservado una habitacion para %s desde %s hasta %s. ¡Recibirás un correo de confirmación!",
					pet, conversionService.convert(startDate, String.class),
					conversionService.convert(finishDate, String.class));
			redirectAttributes.addFlashAttribute("message", message);
			pethotel.setFinishDate(finishDate);
			pethotel.setStartDate(startDate);
			pethotel.setPet(pet);
			pethotel.setOwner(owner);
			this.petHotelService.savePetHotel(pethotel);
		}
		

	}
		return redirect;
}
}
