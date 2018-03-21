
package controllers.actor;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import controllers.AbstractController;
import domain.Actor;
import forms.ProfileForm;

@Controller
@RequestMapping("/profile")
public class ProfileActorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ActorService	actorService;


	// Constructors -----------------------------------------------------------

	public ProfileActorController() {
		super();
	}

	// Display ----------------------------------------------------------------	

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;
		Actor actor;
		byte[] base64;
		StringBuilder imageString;
		String image;

		actor = this.actorService.findByPrincipal();

		base64 = Base64.encode(actor.getPicture());
		imageString = new StringBuilder();
		imageString.append("data:image/png;base64,");
		imageString.append(new String(base64));
		image = imageString.toString();

		result = new ModelAndView("profile/display");
		result.addObject("actor", actor);
		result.addObject("pictureImage", image);
		result.addObject("requestURI", "profile/display.do");

		return result;
	}

	// Edit -------------------------------------------------------------------		

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		ProfileForm profileForm;

		profileForm = this.actorService.principalToFormObject();

		result = new ModelAndView("profile/edit");
		result.addObject("profileForm", profileForm);
		result.addObject("requestURI", "profile/edit.do");

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final ProfileForm profileForm, final BindingResult binding) throws IOException {
		ModelAndView result;
		Actor actor;
		byte[] savedFile;

		actor = this.actorService.reconstructEdit(profileForm, binding);

		if (binding.hasErrors()) {
			System.out.println(binding.toString());
			result = this.createModelAndView(profileForm, "profile.register.error");
		} else
			try {

				if (profileForm.getUserImage().getSize() > 0 && profileForm.getUserImage().getSize() <= 5242880) {

					savedFile = profileForm.getUserImage().getBytes();
					actor.setPicture(savedFile);

				}

				this.actorService.save(actor);
				result = new ModelAndView("redirect:display.do");

			} catch (final Throwable oops) {
				System.out.println(oops.toString());
				result = this.createModelAndView(profileForm, "profile.register.error");

			}
		return result;
	}

	// Ancillary methods

	protected ModelAndView createModelAndView(final ProfileForm profileForm) {
		final ModelAndView result = this.createModelAndView(profileForm, null);
		return result;
	}

	protected ModelAndView createModelAndView(final ProfileForm profileForm, final String message) {
		final ModelAndView result = new ModelAndView("profile/edit");
		result.addObject("profileForm", profileForm);
		result.addObject("RequestURI", "profile/edit.do");
		result.addObject("errorMessage", message);

		return result;
	}

}
