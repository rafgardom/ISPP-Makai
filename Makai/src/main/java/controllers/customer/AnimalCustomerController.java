
package controllers.customer;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AnimalService;
import services.CustomerService;
import controllers.AbstractController;
import domain.Animal;
import domain.Sex;
import forms.AnimalForm;

@Controller
@RequestMapping("/animal/customer")
public class AnimalCustomerController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private AnimalService	animalService;

	@Autowired
	private CustomerService	customerService;


	// Constructors -----------------------------------------------------------

	public AnimalCustomerController() {
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
		sexs = Sex.values();

		if (animalForm.getId() == 0)
			result = new ModelAndView("animal/customer/register");
		else
			result = new ModelAndView("animal/customer/edit");
		result.addObject("animal", animalForm);
		result.addObject("sexs", sexs);
		result.addObject("message", message);

		return result;
	}

}
