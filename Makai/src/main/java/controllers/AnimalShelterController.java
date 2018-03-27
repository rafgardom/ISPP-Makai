
package controllers;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AnimalShelterService;
import domain.AnimalShelter;
import forms.AnimalShelterForm;

@Controller
@RequestMapping("/animalShelter")
public class AnimalShelterController extends AbstractController {

	//Related services
	@Autowired
	private AnimalShelterService	animalShelterService;


	//Constructor
	public AnimalShelterController() {
		super();
	}

	//Register
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {
		ModelAndView result;
		final AnimalShelterForm animalShelterForm = new AnimalShelterForm();
		result = this.createModelAndView(animalShelterForm);

		return result;
	}

	//Save register
	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView saveRegister(@Valid final AnimalShelterForm animalShelterForm, final BindingResult binding) throws IOException {
		ModelAndView result;
		AnimalShelter animalShelter;
		boolean pictureTooLong = false;
		animalShelter = this.animalShelterService.reconstruct(animalShelterForm, binding);
		if (binding.hasErrors()) {
			result = this.createModelAndView(animalShelterForm, "animalShelter.register.error");
			if (animalShelterForm.getUserImage().getSize() == 0)
				result.addObject("imageError", "animalShelter.register.picture.empty.error");
		} else
			try {

				if (animalShelterForm.getUserImage().getSize() > 5242880 || !animalShelterForm.getUserImage().getContentType().contains("image")) {
					pictureTooLong = true;
					throw new IllegalArgumentException();
				}
				this.animalShelterService.save(animalShelter);
				result = new ModelAndView("redirect:/security/login.do");

			} catch (final Throwable oops) {
				if (pictureTooLong == false)
					result = this.createModelAndView(animalShelterForm, "animalShelter.register.error");
				else
					result = this.createModelAndView(animalShelterForm, "animalShelter.register.picture.error");

			}
		return result;
	}

	// Ancillary methods

	protected ModelAndView createModelAndView(final AnimalShelterForm animalShelterForm) {
		final ModelAndView result = this.createModelAndView(animalShelterForm, null);
		return result;
	}

	protected ModelAndView createModelAndView(final AnimalShelterForm animalShelterForm, final String message) {
		final ModelAndView result = new ModelAndView("animalShelter/register");
		result.addObject("animalShelterForm", animalShelterForm);
		result.addObject("RequestURI", "animalShelter/register.do");
		result.addObject("errorMessage", message);

		return result;
	}
}
