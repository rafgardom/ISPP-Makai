
package controllers.actor;

import java.io.IOException;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.NotificationService;
import services.RatingService;
import utilities.Utilities;
import controllers.AbstractController;
import domain.Actor;
import domain.Rating;
import forms.ProfileForm;

@Controller
@RequestMapping("/profile")
public class ProfileActorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ActorService		actorService;

	@Autowired
	private NotificationService	notificationService;

	@Autowired
	private RatingService		ratingService;


	// Constructors -----------------------------------------------------------

	public ProfileActorController() {
		super();
	}

	// Display ----------------------------------------------------------------	

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;
		Actor actor;
		String image;
		final Integer numberNoti;
		Collection<Rating> ratings;

		ratings = null;
		actor = this.actorService.findByPrincipal();
		image = Utilities.showImage(actor.getPicture());
		numberNoti = this.notificationService.findNotificationWithoutRead();

		if (this.actorService.checkAuthority(actor, "TRAINER"))
			ratings = this.ratingService.findByTrainerId(actor.getId());
		else if (this.actorService.checkAuthority(actor, "PROFESSIONAL") || this.actorService.checkAuthority(actor, "CUSTOMER"))
			ratings = this.ratingService.findByTransporterId(actor.getId());

		result = new ModelAndView("profile/display");
		result.addObject("actor", actor);
		result.addObject("pictureImage", image);
		result.addObject("numberNoti", numberNoti);
		result.addObject("ratings", ratings);
		result.addObject("requestURI", "profile/display.do");

		return result;
	}

	@RequestMapping(value = "/displayProfile", method = RequestMethod.GET)
	public ModelAndView displayProfile(@RequestParam final int actorId) {
		ModelAndView result;
		Actor actor;
		String image;
		final Integer numberNoti;
		Collection<Rating> ratings;

		ratings = null;
		actor = this.actorService.findOne(actorId);
		image = Utilities.showImage(actor.getPicture());
		numberNoti = this.notificationService.findNotificationWithoutRead();

		if (this.actorService.checkAuthority(actor, "TRAINER"))
			ratings = this.ratingService.findByTrainerId(actor.getId());
		else if (this.actorService.checkAuthority(actor, "PROFESSIONAL") || this.actorService.checkAuthority(actor, "CUSTOMER"))
			ratings = this.ratingService.findByTransporterId(actor.getId());

		result = new ModelAndView("profile/display");
		result.addObject("actor", actor);
		result.addObject("pictureImage", image);
		result.addObject("numberNoti", numberNoti);
		result.addObject("ratings", ratings);
		result.addObject("requestURI", "profile/display.do");

		return result;
	}

	// Edit -------------------------------------------------------------------		

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		ProfileForm profileForm;
		final Integer numberNoti;

		profileForm = this.actorService.principalToFormObject();
		numberNoti = this.notificationService.findNotificationWithoutRead();

		result = new ModelAndView("profile/edit");
		result.addObject("profileForm", profileForm);
		result.addObject("numberNoti", numberNoti);
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
		final Integer numberNoti;
		numberNoti = this.notificationService.findNotificationWithoutRead();
		result.addObject("numberNoti", numberNoti);
		result.addObject("profileForm", profileForm);
		result.addObject("RequestURI", "profile/edit.do");
		result.addObject("errorMessage", message);

		return result;
	}

}
