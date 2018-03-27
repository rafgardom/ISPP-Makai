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

import java.io.IOException;

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
	public ModelAndView saveRegister(@Valid final CustomerForm customerForm, final BindingResult binding) throws IOException {
		ModelAndView result;
		Customer customer;
		boolean pictureTooLong = false;
		customer = this.customerService.reconstruct(customerForm, binding);
		if (binding.hasErrors()) {
			result = this.createModelAndView(customerForm, "customer.register.error");
			if (customerForm.getUserImage().getSize() == 0)
				result.addObject("imageError", "customer.register.picture.empty.error");
		} else
			try {
				//				final MultipartFile userImage = customerForm.getUserImage();

				if (customerForm.getUserImage().getSize() > 5242880 || !customerForm.getUserImage().getContentType().contains("image")) {
					pictureTooLong = true;
					throw new IllegalArgumentException();
				}
				//				customer.setPicture(userImage.getBytes());
				this.customerService.save(customer);
				result = new ModelAndView("redirect:/security/login.do");

			} catch (final Throwable oops) {
				if (pictureTooLong == false)
					result = this.createModelAndView(customerForm, "customer.register.error");
				else
					result = this.createModelAndView(customerForm, "customer.register.picture.error");

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
