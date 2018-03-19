
package controllers.customer;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.RequestService;
import controllers.AbstractController;
import domain.Request;
import forms.RequestForm;

@Controller
@RequestMapping("/request/customer")
public class RequestCustomerController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private RequestService	requestService;


	// Constructors -----------------------------------------------------------

	public RequestCustomerController() {
		super();
	}

	// Creation ---------------------------------------------------------------		
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Request request;
		RequestForm requestForm;

		request = this.requestService.create();
		requestForm = this.requestService.requestToFormObject(request);

		result = this.createEditModelAndView(requestForm);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final RequestForm requestForm, final BindingResult binding) {

		ModelAndView result;
		Request request;

		if (binding.hasErrors())
			result = this.createEditModelAndView(requestForm);
		else
			try {
				request = this.requestService.reconstruct(requestForm, binding);

				request = this.requestService.save(request);
				result = new ModelAndView("master.page");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(requestForm, "animal.commit.error");

			}
		return result;

	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Request request, final BindingResult binding) {

		ModelAndView result;

		this.requestService.delete(request);

		result = new ModelAndView("redirect:list.do");

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

		result = new ModelAndView("request/customer/register");

		result.addObject("request", requestForm);
		result.addObject("message", message);

		return result;
	}

}
