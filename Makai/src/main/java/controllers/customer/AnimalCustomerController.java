
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

		result = new ModelAndView("redirect:listByCustomer.do");

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
			result = new ModelAndView("animal/customer/register");
		else
			result = new ModelAndView("animal/customer/edit");
		result.addObject("animal", animal);
		result.addObject("message", message);

		return result;
	}

}
