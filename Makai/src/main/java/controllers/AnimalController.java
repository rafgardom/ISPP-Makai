
package controllers;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AnimalService;
import services.BreedService;
import services.NotificationService;
import services.SpecieService;

import com.google.gson.Gson;

import domain.Actor;
import domain.Animal;
import domain.Breed;
import domain.Sex;
import domain.Specie;
import forms.AnimalForm;

@Controller
@RequestMapping("/animal")
public class AnimalController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private AnimalService		animalService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private SpecieService		specieService;

	@Autowired
	private BreedService		breedService;

	@Autowired
	private NotificationService	notificationService;


	// Constructors -----------------------------------------------------------

	public AnimalController() {
		super();
	}

	// ListingByCustomer -------------------------------------------------------		
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listByCustomer() {
		ModelAndView result;
		Collection<AnimalForm> animals;
		Actor actor;
		final Integer numberNoti;

		actor = this.actorService.findByPrincipal();
		numberNoti = this.notificationService.findNotificationWithoutRead();

		animals = new HashSet<AnimalForm>();

		for (final Animal a : this.animalService.findByActorIdNotHidden(actor.getId())) {
			AnimalForm animalForm;

			animalForm = this.animalService.animalToFormObject(a);

			animals.add(animalForm);
		}

		result = new ModelAndView("animal/list");
		result.addObject("requestURI", "animal/list.do");
		result.addObject("numberNoti", numberNoti);
		result.addObject("animals", animals);

		return result;
	}

	// ListingByCustomer -------------------------------------------------------		
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int animalId) {
		ModelAndView result;
		Animal animal;
		//		AnimalForm animalForm;
		final Integer numberNoti;
		try {
			animal = this.animalService.findOne(animalId);
			//			animalForm = this.animalService.animalToFormObject(animal);
			numberNoti = this.notificationService.findNotificationWithoutRead();

			result = new ModelAndView("animal/display");
			//			result.addObject("animal", animalForm);
			result.addObject("animal", animal);
			result.addObject("numberNoti", numberNoti);
			result.addObject("requestURI", "animal/display.do");
		} catch (final Throwable e) {
			result = new ModelAndView("error");
		}
		return result;
	}

	// Delete --------------------------------------------------------------------		

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView ban(@RequestParam final int animalId) {
		ModelAndView result;
		Animal animal;
		try {
			animal = this.animalService.findOne(animalId);

			this.animalService.delete(animal);

			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("error");
		}
		return result;
	}

	// Creation ---------------------------------------------------------------		
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {
		ModelAndView result;
		Animal animal;
		AnimalForm animalForm;

		animal = this.animalService.create();
		animalForm = this.animalService.animalToFormObject(animal);

		result = this.createEditModelAndView(animalForm);

		return result;
	}

	// Edition ----------------------------------------------------------------		
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int animalId) {
		ModelAndView result;
		Animal animal;
		AnimalForm animalForm;
		try {
			animal = this.animalService.findOne(animalId);
			animalForm = this.animalService.animalToFormObject(animal);

			result = this.createEditModelAndView(animalForm);
		} catch (final Throwable e) {
			result = new ModelAndView("error");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final AnimalForm animalForm, final BindingResult binding) throws IOException {

		ModelAndView result;
		Animal animal;
		byte[] savedFile;

		animal = this.animalService.reconstruct(animalForm, binding);

		if (binding.hasErrors()) {
			System.out.println(binding.toString());
			result = this.createEditModelAndView(animalForm);

		} else
			try {

				if (animalForm.getAnimalImage().getSize() > 0) {

					savedFile = animalForm.getAnimalImage().getBytes();
					animal.setPicture(savedFile);

				}

				animal = this.animalService.save(animal);
				result = new ModelAndView("redirect:list.do");

			} catch (final Throwable oops) {
				System.out.println(oops);

				result = this.createEditModelAndView(animalForm, "animal.commit.error");

			}
		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Animal animal, final BindingResult binding) {

		ModelAndView result;
		try {
			this.animalService.delete(animal);

			result = new ModelAndView("redirect:listByCustomer.do");
		} catch (final Throwable e) {
			result = new ModelAndView("error");
		}
		return result;

	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final AnimalForm animalForm) {
		ModelAndView result;

		result = this.createEditModelAndView(animalForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final AnimalForm animalForm, final String message) {
		ModelAndView result;
		Sex[] sexs;
		final Collection<Specie> species;
		final Collection<Breed> breeds;
		String json;
		final String selectedBreeds;
		final Integer numberNoti;

		sexs = Sex.values();
		species = this.specieService.findAll();
		breeds = this.breedService.findAll();
		json = new Gson().toJson(breeds);
		selectedBreeds = new Gson().toJson(animalForm.getBreeds());
		numberNoti = this.notificationService.findNotificationWithoutRead();

		if (animalForm.getId() == 0)
			result = new ModelAndView("animal/register");
		else
			result = new ModelAndView("animal/edit");
		result.addObject("animalForm", animalForm);
		result.addObject("sexs", sexs);
		result.addObject("species", species);
		result.addObject("breeds", breeds);
		result.addObject("jsonBreeds", json);
		result.addObject("selectedBreeds", selectedBreeds);
		result.addObject("numberNoti", numberNoti);
		result.addObject("RequestURI", "animal/edit.do");
		result.addObject("errorMessage", message);

		return result;
	}
}
