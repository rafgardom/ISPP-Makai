
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AnimalService;
import services.BreedService;
import services.CustomerService;
import services.SpecieService;

import com.google.gson.Gson;

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
	private AnimalService	animalService;

	@Autowired
	private CustomerService	customerService;

	@Autowired
	private SpecieService	specieService;

	@Autowired
	private BreedService	breedService;


	// Constructors -----------------------------------------------------------

	public AnimalController() {
		super();
	}

	// ListingByCustomer -------------------------------------------------------		
	//	@RequestMapping(value = "/listByCustomer", method = RequestMethod.GET)
	//	public ModelAndView listByCustomer() {
	//		ModelAndView result;
	//		Collection<Animal> animals;
	//		Customer customer;
	//
	//		customer = this.customerService.findByPrincipal();
	//
	//		animals = this.animalService.findByCustomerId(customer.getId());
	//
	//		result = new ModelAndView("animal/list");
	//		result.addObject("requestURI", "animal/listByCustomer.do");
	//		result.addObject("animals", animals);
	//
	//		return result;
	//	}

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

		animal = this.animalService.findOne(animalId);
		animalForm = this.animalService.animalToFormObject(animal);

		result = this.createEditModelAndView(animalForm);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final AnimalForm animalForm, final BindingResult binding) {

		ModelAndView result;
		Animal animal;
		byte[] savedFile;

		if (binding.hasErrors()) {
			System.out.println(binding.toString());
			result = this.createEditModelAndView(animalForm);

		} else
			try {

				if (animalForm.getAnimalImage().getSize() > 0) {

					savedFile = animalForm.getAnimalImage().getBytes();
					animalForm.setPicture(savedFile);

				} else if (animalForm.getAnimalImage().getSize() > 5242880) {
					//					pictureTooLong = true;
					System.out.println("La imagen es demasiado larga");
					throw new IllegalArgumentException();
				} else
					animalForm.setPicture(null);

				animal = this.animalService.reconstruct(animalForm, binding);

				animal = this.animalService.save(animal);
				result = new ModelAndView("master.page");

			} catch (final Throwable oops) {
				System.out.println(oops);

				result = this.createEditModelAndView(animalForm, "animal.commit.error");

			}
		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Animal animal, final BindingResult binding) {

		ModelAndView result;

		this.animalService.delete(animal);

		result = new ModelAndView("redirect:listByCustomer.do");

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

		sexs = Sex.values();
		species = this.specieService.findAll();
		breeds = this.breedService.findAll();
		json = new Gson().toJson(breeds);

		if (animalForm.getId() == 0)
			result = new ModelAndView("animal/register");
		else
			result = new ModelAndView("animal/edit");
		result.addObject("animal", animalForm);
		result.addObject("sexs", sexs);
		result.addObject("species", species);
		result.addObject("breeds", breeds);
		result.addObject("jsonBreeds", json);
		result.addObject("message", message);

		return result;
	}
}
