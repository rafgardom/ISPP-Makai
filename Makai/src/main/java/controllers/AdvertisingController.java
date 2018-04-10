
package controllers;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdvertisingService;
import domain.Advertising;
import forms.AdvertisingForm;

@Controller
@RequestMapping("/advertising")
public class AdvertisingController extends AbstractController {

	//Related services
	@Autowired
	private AdvertisingService	advertisingService;


	//Constructor
	public AdvertisingController() {
		super();
	}

	//Register
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {
		ModelAndView result;
		final AdvertisingForm advertisingForm = new AdvertisingForm();
		result = this.createModelAndView(advertisingForm);

		return result;
	}

	//Save register
	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView saveRegister(@Valid final AdvertisingForm advertisingForm, final BindingResult binding) throws IOException {
		ModelAndView result;
		Advertising advertising;
		boolean pictureTooLong = false;
		advertising = this.advertisingService.reconstruct(advertisingForm, binding);
		if (binding.hasErrors()) {
			result = this.createModelAndView(advertisingForm, "advertising.register.error");
			if (advertisingForm.getUserImage().getSize() == 0)
				result.addObject("imageError", "advertising.register.picture.empty.error");
		} else
			try {

				if (advertisingForm.getUserImage().getSize() > 5242880 || !advertisingForm.getUserImage().getContentType().contains("image")) {
					pictureTooLong = true;
					throw new IllegalArgumentException();
				}
				this.advertisingService.save(advertising);
				result = new ModelAndView("redirect:/security/login.do");

			} catch (final Throwable oops) {
				if (pictureTooLong == false)
					result = this.createModelAndView(advertisingForm, "advertising.register.error");
				else
					result = this.createModelAndView(advertisingForm, "advertising.register.picture.error");

			}
		return result;
	}

	// Ancillary methods

	protected ModelAndView createModelAndView(final AdvertisingForm advertisingForm) {
		final ModelAndView result = this.createModelAndView(advertisingForm, null);
		return result;
	}

	protected ModelAndView createModelAndView(final AdvertisingForm advertisingForm, final String message) {
		final ModelAndView result = new ModelAndView("advertising/register");
		result.addObject("advertisingForm", advertisingForm);
		result.addObject("RequestURI", "advertising/register.do");
		result.addObject("errorMessage", message);

		return result;
	}
}
