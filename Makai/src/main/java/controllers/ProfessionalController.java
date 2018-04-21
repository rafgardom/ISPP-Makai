
package controllers;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ProfessionalService;
import domain.Professional;
import forms.ProfessionalForm;

@Controller
@RequestMapping("/professional")
public class ProfessionalController extends AbstractController {

	//Related services
	@Autowired
	private ProfessionalService	professionalService;


	//Constructor
	public ProfessionalController() {
		super();
	}

	//Register
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {
		ModelAndView result;
		final ProfessionalForm professionalForm = new ProfessionalForm();
		result = this.createModelAndView(professionalForm);

		return result;
	}

	//Save register
	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView saveRegister(@Valid final ProfessionalForm professionalForm, final BindingResult binding) throws IOException {
		ModelAndView result;
		Professional professional;
		boolean pictureTooLong = false;
		professional = this.professionalService.reconstruct(professionalForm, binding);
		if (binding.hasErrors()) {
			result = this.createModelAndView(professionalForm, "professional.register.error");
			if (professionalForm.getUserImage().getSize() == 0)
				result.addObject("imageError", "professional.register.picture.empty.error");
		} else
			try {
				//				final MultipartFile userImage = customerForm.getUserImage();

				if (professionalForm.getUserImage().getSize() > 2097152 || !professionalForm.getUserImage().getContentType().contains("image")) {
					pictureTooLong = true;
					throw new IllegalArgumentException();
				}
				//				customer.setPicture(userImage.getBytes());
				this.professionalService.save(professional);
				result = new ModelAndView("redirect:/security/login.do");

			} catch (final Throwable oops) {
				if (pictureTooLong == false)
					result = this.createModelAndView(professionalForm, "professional.register.error");
				else
					result = this.createModelAndView(professionalForm, "professional.register.picture.error");

			}
		return result;
	}

	// Ancillary methods

	protected ModelAndView createModelAndView(final ProfessionalForm professionalForm) {
		final ModelAndView result = this.createModelAndView(professionalForm, null);
		return result;
	}

	protected ModelAndView createModelAndView(final ProfessionalForm professionalForm, final String message) {
		final ModelAndView result = new ModelAndView("professional/register");
		result.addObject("professionalForm", professionalForm);
		result.addObject("RequestURI", "professional/register.do");
		result.addObject("errorMessage", message);

		return result;
	}
}
