
package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AnimalService;
import services.BannerService;
import services.BreedService;
import services.NotificationService;
import services.SpecieService;
import utilities.Utilities;

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

	@Autowired
	private BannerService		bannerService;


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
		try {
			actor = this.actorService.findByPrincipal();
			numberNoti = this.notificationService.findNotificationWithoutRead();

			animals = new HashSet<AnimalForm>();

			for (final Animal a : this.animalService.findByActorIdNotHidden(actor.getId())) {
				AnimalForm animalForm;

				animalForm = this.animalService.animalToFormObject(a);

				animals.add(animalForm);
			}

			final ArrayList<String> imagesLeft = this.bannerService.getBannerByZone("izquierda");
			final ArrayList<String> imagesBottom = this.bannerService.getBannerByZone("abajo");
			final ArrayList<String> imagesRight = this.bannerService.getBannerByZone("derecha");

			result = new ModelAndView("animal/list");
			result.addObject("requestURI", "animal/list.do");
			result.addObject("numberNoti", numberNoti);
			result.addObject("animals", animals);

			result.addObject("imagesLeft", imagesLeft);
			result.addObject("imagesBottom", imagesBottom);
			result.addObject("imagesRight", imagesRight);
		} catch (final Throwable e) {
			result = new ModelAndView("error");
		}
		return result;
	}
	// ListingAdopted -------------------------------------------------------		
	@RequestMapping(value = "/listAdopted", method = RequestMethod.GET)
	public ModelAndView listAdopted() {
		ModelAndView result;
		Collection<AnimalForm> animals;
		Actor actor;
		final Integer numberNoti;
		try {
			actor = this.actorService.findByPrincipal();
			numberNoti = this.notificationService.findNotificationWithoutRead();

			animals = new HashSet<AnimalForm>();

			for (final Animal a : this.animalService.findByActorIdAndAdopted(actor.getId())) {
				AnimalForm animalForm;

				animalForm = this.animalService.animalToFormObject(a);

				animals.add(animalForm);
			}

			final ArrayList<String> imagesLeft = this.bannerService.getBannerByZone("izquierda");
			final ArrayList<String> imagesBottom = this.bannerService.getBannerByZone("abajo");
			final ArrayList<String> imagesRight = this.bannerService.getBannerByZone("derecha");

			result = new ModelAndView("animal/listAdopted");
			result.addObject("requestURI", "animal/list.do");
			result.addObject("numberNoti", numberNoti);
			result.addObject("animals", animals);

			result.addObject("imagesLeft", imagesLeft);
			result.addObject("imagesBottom", imagesBottom);
			result.addObject("imagesRight", imagesRight);
		} catch (final Throwable e) {
			result = new ModelAndView("error");
		}
		return result;
	}

	// ListingByCustomer -------------------------------------------------------		
	@RequestMapping(value = "/listNotAdopted", method = RequestMethod.GET)
	public ModelAndView listNotAdopted() {
		ModelAndView result;
		Collection<AnimalForm> animals;
		Actor actor;
		final Integer numberNoti;
		try {
			actor = this.actorService.findByPrincipal();
			numberNoti = this.notificationService.findNotificationWithoutRead();

			animals = new HashSet<AnimalForm>();

			for (final Animal a : this.animalService.findByActorIdAndNotAdopted(actor.getId())) {
				AnimalForm animalForm;

				animalForm = this.animalService.animalToFormObject(a);

				animals.add(animalForm);
			}

			final ArrayList<String> imagesLeft = this.bannerService.getBannerByZone("izquierda");
			final ArrayList<String> imagesBottom = this.bannerService.getBannerByZone("abajo");
			final ArrayList<String> imagesRight = this.bannerService.getBannerByZone("derecha");

			result = new ModelAndView("animal/listNotAdopted");
			result.addObject("requestURI", "animal/list.do");
			result.addObject("numberNoti", numberNoti);
			result.addObject("animals", animals);
			result.addObject("imagesLeft", imagesLeft);
			result.addObject("imagesBottom", imagesBottom);
			result.addObject("imagesRight", imagesRight);
		} catch (final Throwable e) {
			result = new ModelAndView("error");
		}
		return result;
	}

	// ListingByCustomer -------------------------------------------------------		
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int animalId) {
		ModelAndView result;
		Actor actor;
		Animal animal;
		Breed[] breeds;
		String specie;
		String image;
		//		AnimalForm animalForm;
		final Integer numberNoti;
		try {
			actor = this.actorService.findByPrincipal();
			animal = this.animalService.findOne(animalId);
			Assert.isTrue(animal.getIsHidden() == false);
			//			animalForm = this.animalService.animalToFormObject(animal);
			numberNoti = this.notificationService.findNotificationWithoutRead();
			breeds = animal.getBreeds().toArray(new Breed[animal.getBreeds().size()]);
			specie = breeds[0].getSpecie().getType();
			image = Utilities.showImage(animal.getPicture());
			result = new ModelAndView("animal/display");
			//			result.addObject("animal", animalForm);

			final ArrayList<String> imagesLeft = this.bannerService.getBannerByZone("izquierda");
			final ArrayList<String> imagesBottom = this.bannerService.getBannerByZone("abajo");
			final ArrayList<String> imagesRight = this.bannerService.getBannerByZone("derecha");

			result.addObject("principal", actor);
			result.addObject("animal", animal);
			result.addObject("specie", specie);
			result.addObject("animalImage", image);
			result.addObject("numberNoti", numberNoti);
			result.addObject("requestURI", "animal/display.do");
			result.addObject("imagesLeft", imagesLeft);
			result.addObject("imagesBottom", imagesBottom);
			result.addObject("imagesRight", imagesRight);
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
			System.out.println(oops.toString());
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

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView saveRegister(@Valid final AnimalForm animalForm, final BindingResult binding) throws IOException {

		ModelAndView result;
		Animal animal;
		byte[] savedFile;
		animal = this.animalService.reconstruct(animalForm, binding);

		if (binding.hasErrors()) {
			System.out.println(binding.toString());
			result = this.createEditModelAndView(animalForm);

		} else
			try {

				if (animalForm.getAnimalImage().getSize() > 0 && animalForm.getAnimalImage().getSize() <= 2097152 && animalForm.getAnimalImage().getContentType().contains("image")) {

					savedFile = animalForm.getAnimalImage().getBytes();
					animal.setPicture(savedFile);

				}

				animal = this.animalService.save(animal);
				result = new ModelAndView("redirect:list.do");

			} catch (final Throwable oops) {
				System.out.println(oops);

				if (oops.getCause() != null)
					if (oops.getCause().getCause().getMessage().contains("Duplicate")) {
						FieldError fieldError;
						final String[] codes = {
							"animal.chipNumber.extension.error"
						};
						fieldError = new FieldError("animalForm", "chipNumber", animalForm.getChipNumber(), false, codes, null, "");
						binding.addError(fieldError);
					}

				result = this.createEditModelAndView(animalForm, "animal.commit.error");

			}
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
			Assert.isTrue(animal.getIsHidden() == false);
			Assert.isTrue(animalId != 0);
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

				if (animalForm.getAnimalImage().getSize() > 0 && animalForm.getAnimalImage().getSize() <= 2097152 && animalForm.getAnimalImage().getContentType().contains("image")) {

					savedFile = animalForm.getAnimalImage().getBytes();
					animal.setPicture(savedFile);

				}

				animal = this.animalService.save(animal);
				result = new ModelAndView("redirect:list.do");

			} catch (final Throwable oops) {
				System.out.println(oops);

				if (oops.getCause() != null)
					if (oops.getCause().getCause().getMessage().contains("Duplicate")) {
						FieldError fieldError;
						final String[] codes = {
							"animal.chipNumber.extension.error"
						};
						fieldError = new FieldError("animalForm", "chipNumber", animalForm.getChipNumber(), false, codes, null, "");
						binding.addError(fieldError);
					}

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

	@RequestMapping(value = "/menu", method = RequestMethod.GET)
	public ModelAndView menu() {
		ModelAndView result;

		final Integer numberNoti;
		try {
			numberNoti = this.notificationService.findNotificationWithoutRead();

			final ArrayList<String> imagesLeft = this.bannerService.getBannerByZone("izquierda");
			final ArrayList<String> imagesBottom = this.bannerService.getBannerByZone("abajo");
			final ArrayList<String> imagesRight = this.bannerService.getBannerByZone("derecha");

			result = new ModelAndView("animal/menu");

			result.addObject("numberNoti", numberNoti);
			result.addObject("imagesLeft", imagesLeft);
			result.addObject("imagesBottom", imagesBottom);
			result.addObject("imagesRight", imagesRight);
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
		final ModelAndView result;
		Sex[] sexs;
		final Collection<Specie> species;
		final Collection<Breed> breeds;
		String json;
		final Integer numberNoti;

		sexs = Sex.values();
		species = this.specieService.findAll();
		breeds = this.breedService.findAll();
		json = new Gson().toJson(breeds);
		numberNoti = this.notificationService.findNotificationWithoutRead();

		result = new ModelAndView("animal/register");
		if (animalForm.getId() == 0)
			result.addObject("requestURI", "animal/register.do");
		else
			result.addObject("requestURI", "animal/edit.do?animalId=" + animalForm.getId());
		result.addObject("animalForm", animalForm);
		result.addObject("sexs", sexs);
		result.addObject("species", species);
		result.addObject("breeds", breeds);
		result.addObject("jsonBreeds", json);
		result.addObject("numberNoti", numberNoti);
		result.addObject("RequestURI", "animal/edit.do");
		result.addObject("errorMessage", message);

		return result;
	}
}
