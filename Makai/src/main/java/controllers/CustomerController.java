/*
 * CustomerController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CustomerService;
import domain.Customer;
import forms.CustomerForm;

@Controller
@RequestMapping("/customer")
public class CustomerController extends AbstractController {

	//Related services
	@Autowired
	private CustomerService	customerService;


	// Constructors -----------------------------------------------------------

	public CustomerController() {
		super();
	}

	//Register
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {
		ModelAndView result;
		final CustomerForm customerForm = new CustomerForm();
		result = this.createModelAndView(customerForm);

		return result;
	}

	//Save register
	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView saveRegister(@Valid final CustomerForm customerForm, final BindingResult binding) {
		ModelAndView result;
		Customer customer;

		customer = this.customerService.reconstruct(customerForm, binding);
		if (binding.hasErrors())
			result = this.createModelAndView(customerForm, "customer.register.error");
		else
			try {
				this.customerService.save(customer);
				result = new ModelAndView("redirect:/security/login.do");

			} catch (final Throwable oops) {
				result = this.createModelAndView(customerForm, "customer.register.error");
			}
		return result;
	}

	// Ancillary methods

	protected ModelAndView createModelAndView(final CustomerForm customerForm) {
		final ModelAndView result = this.createModelAndView(customerForm, null);
		return result;
	}

	protected ModelAndView createModelAndView(final CustomerForm customerForm, final String message) {
		final ModelAndView result = new ModelAndView("customer/register");
		result.addObject("customerForm", customerForm);
		result.addObject("RequestURI", "customer/register.do");
		result.addObject("errorMessage", message);

		return result;
	}
}
