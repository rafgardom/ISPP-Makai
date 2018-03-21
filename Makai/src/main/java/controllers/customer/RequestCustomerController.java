
package controllers.customer;

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
import services.CustomerService;
import services.RequestService;
import controllers.AbstractController;
import domain.Animal;
import domain.Category;
import domain.Customer;
import domain.Request;
import forms.RequestForm;

@Controller
@RequestMapping("/request/customer")
public class RequestCustomerController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private RequestService	requestService;

	@Autowired
	private CustomerService	customerService;

	@Autowired
	private AnimalService	animalService;


	// Constructors -----------------------------------------------------------

	public RequestCustomerController() {
		super();
	}

	// List ---------------------------------------------------------------

	@RequestMapping(value = "/myList", method = RequestMethod.GET)
	public ModelAndView listByCustomer() {
		ModelAndView result;
		Collection<Request> requests;
		Customer customer;

		customer = this.customerService.findByPrincipal();

		requests = this.requestService.findRequestByCustomer(customer);

		result = new ModelAndView("request/myList");
		result.addObject("requestURI", "request/myList.do");
		result.addObject("requests", requests);

		return result;
	}

	// Create  ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		RequestForm requestForm;

		requestForm = new RequestForm();

		result = this.createEditModelAndView(requestForm);

		return result;
	}

	// Edit ---------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int requestId) {
		ModelAndView result;
		RequestForm requestForm;
		Request request;

		request = this.requestService.findOne(requestId);
		requestForm = this.requestService.requestToFormObject(request);
		result = this.createEditModelAndView(requestForm);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(@Valid final RequestForm requestForm, final BindingResult binding) {

		ModelAndView result;
		Request request;

		if (binding.hasErrors())
			result = this.createEditModelAndView(requestForm);
		else
			try {
				request = this.requestService.reconstruct(requestForm, binding);
				this.requestService.save(request);
				result = new ModelAndView("redirect:myList.do");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(requestForm, "request.commit.error");
			}
		return result;
	}

	// Delete ---------------------------------------------------------------

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int requestId) {
		ModelAndView result;
		Request request;

		request = this.requestService.findOne(requestId);
		this.requestService.delete(request);

		result = new ModelAndView("redirect:myList.do");

		return result;

	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final RequestForm requestForm) {
		ModelAndView result;

		result = this.createEditModelAndView(requestForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final RequestForm requestForm, final String message) {
		ModelAndView result;
		Customer principal;
		Collection<Animal> animals;
		Category[] categories;

		principal = this.customerService.findByPrincipal();
		animals = this.animalService.findByActorId(principal.getId());

		categories = Category.values();

		if (requestForm.getId() == 0) {
			result = new ModelAndView("request/create");
			result.addObject("RequestURI", "request/customer/create.do");
		} else {
			result = new ModelAndView("request/edit");
			result.addObject("RequestURI", "request/customer/edit.do");
		}
		result.addObject("requestForm", requestForm);
		result.addObject("animals", animals);
		result.addObject("categoriesList", categories);
		result.addObject("message", message);

		return result;
	}
}
