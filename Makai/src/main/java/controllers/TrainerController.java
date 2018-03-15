
package controllers;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.TrainerService;
import domain.Trainer;
import forms.TrainerForm;

@Controller
@RequestMapping("/trainer")
public class TrainerController extends AbstractController {

	//Related services
	@Autowired
	private TrainerService	trainerService;


	//Constructor
	public TrainerController() {
		super();
	}

	//Register
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {
		ModelAndView result;
		final TrainerForm trainerForm = new TrainerForm();
		result = this.createModelAndView(trainerForm);

		return result;
	}

	//Save register
	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView saveRegister(@Valid final TrainerForm trainerForm, final BindingResult binding) throws IOException {
		ModelAndView result;
		Trainer trainer;
		boolean pictureTooLong = false;
		trainer = this.trainerService.reconstruct(trainerForm, binding);
		if (binding.hasErrors())
			result = this.createModelAndView(trainerForm, "trainer.register.error");
		else
			try {
				//				final MultipartFile userImage = customerForm.getUserImage();

				if (trainerForm.getUserImage().getSize() > 5242880 || !trainerForm.getUserImage().getContentType().contains("image")) {
					pictureTooLong = true;
					throw new IllegalArgumentException();
				}
				//				customer.setPicture(userImage.getBytes());
				this.trainerService.save(trainer);
				result = new ModelAndView("redirect:/security/login.do");

			} catch (final Throwable oops) {
				if (pictureTooLong == false)
					result = this.createModelAndView(trainerForm, "trainer.register.error");
				else
					result = this.createModelAndView(trainerForm, "trainer.register.picture.error");

			}
		return result;
	}

	// Ancillary methods

	protected ModelAndView createModelAndView(final TrainerForm trainerForm) {
		final ModelAndView result = this.createModelAndView(trainerForm, null);
		return result;
	}

	protected ModelAndView createModelAndView(final TrainerForm trainerForm, final String message) {
		final ModelAndView result = new ModelAndView("trainer/register");
		result.addObject("trainerForm", trainerForm);
		result.addObject("RequestURI", "trainer/register.do");
		result.addObject("errorMessage", message);

		return result;
	}
}
