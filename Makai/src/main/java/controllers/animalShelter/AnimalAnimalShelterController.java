
package controllers.animalShelter;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AnimalService;
import services.AnimalShelterService;
import controllers.AbstractController;
import domain.Animal;

@Controller
@RequestMapping("/animal/animalShelter")
public class AnimalAnimalShelterController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private AnimalService			animalService;

	@Autowired
	private AnimalShelterService	animalShelterService;


	// Constructors -----------------------------------------------------------

	public AnimalAnimalShelterController() {
		super();
	}

	// ListingByAnimalShelter -------------------------------------------------------		
	//	@RequestMapping(value = "/listByAnimalShelter", method = RequestMethod.GET)
	//	public ModelAndView listByAnimalShelter() {
	//		ModelAndView result;
	//		Collection<Animal> animals;
	//		AnimalShelter animalShelter;
	//
	//		animalShelter = this.animalShelterService.findByPrincipal();
	//
	//		animals = this.animalService.findByAnimalShelterId(animalShelter.getId());
	//
	//		result = new ModelAndView("animal/list");
	//		result.addObject("requestURI", "animal/listByAnimalShelter.do");
	//		result.addObject("animals", animals);
	//
	//		return result;
	//	}

	// Creation ---------------------------------------------------------------		
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView register() {
		ModelAndView result;
		Animal animal;

		animal = this.animalService.create();

		result = this.createEditModelAndView(animal);

		return result;
	}

	// Edition ----------------------------------------------------------------		
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int animalId) {
		ModelAndView result;
		Animal animal;

		animal = this.animalService.findOne(animalId);

		result = this.createEditModelAndView(animal);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Animal animal, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println(binding.toString());
			result = this.createEditModelAndView(animal);

		} else
			try {
				animal = this.animalService.save(animal);
				result = new ModelAndView("master.page");

			} catch (final Throwable oops) {
				System.out.println(oops);

				result = this.createEditModelAndView(animal, "animal.commit.error");

			}
		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Animal animal, final BindingResult binding) {

		ModelAndView result;

		this.animalService.delete(animal);

		result = new ModelAndView("redirect:listByAnimalShelter.do");

		return result;

	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Animal animal) {
		ModelAndView result;

		result = this.createEditModelAndView(animal, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Animal animal, final String message) {
		ModelAndView result;

		if (animal.getId() == 0)
			result = new ModelAndView("animal/animalShelter/register");
		else
			result = new ModelAndView("animal/animalShelter/edit");
		result.addObject("animal", animal);
		result.addObject("message", message);

		return result;
	}

}
