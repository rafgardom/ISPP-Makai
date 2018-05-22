
package controllers.actor;

import java.io.IOException;
import java.util.ArrayList;
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
import services.BannerService;
import services.NotificationService;
import services.RatingService;
import utilities.Utilities;
import controllers.AbstractController;
import domain.Actor;
import domain.Banner;
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

	@Autowired
	private BannerService		bannerService;




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

		try {
			ratings = null;
			actor = this.actorService.findByPrincipal();
			image = Utilities.showImage(actor.getPicture(),"user");
			numberNoti = this.notificationService.findNotificationWithoutRead();

			if (this.actorService.checkAuthority(actor, "TRAINER"))
				ratings = this.ratingService.findByTrainerId(actor.getId());
			else if (this.actorService.checkAuthority(actor, "PROFESSIONAL") || this.actorService.checkAuthority(actor, "CUSTOMER"))
				ratings = this.ratingService.findByTransporterId(actor.getId());

			//			final ArrayList<String> imagesLeft = this.bannerService.getBannerByZone("izquierda");
			final ArrayList<Banner> imagesBottom = this.bannerService.getBannerByZone("abajo");
			//			final ArrayList<String> imagesRight = this.bannerService.getBannerByZone("derecha");

			result = new ModelAndView("profile/display");
			result.addObject("actor", actor);
			result.addObject("pictureImage", image);
			result.addObject("numberNoti", numberNoti);
			result.addObject("ratings", ratings);
			result.addObject("requestURI", "profile/display.do");
			//			result.addObject("imagesLeft", imagesLeft);
			result.addObject("imagesBottom", imagesBottom);
			//			result.addObject("imagesRight", imagesRight);
		} catch (final Throwable e) {
			result = new ModelAndView("error");
		}
		return result;
	}

	@RequestMapping(value = "/displayProfile", method = RequestMethod.GET)
	public ModelAndView displayProfile(@RequestParam final int actorId) {
		ModelAndView result;
		Actor actor;
		String image;
		final Integer numberNoti;
		Collection<Rating> ratings;

		try {
			ratings = null;
			actor = this.actorService.findOne(actorId);
			image = Utilities.showImage(actor.getPicture(),"user");
			numberNoti = this.notificationService.findNotificationWithoutRead();

			if (this.actorService.checkAuthority(actor, "TRAINER"))
				ratings = this.ratingService.findByTrainerId(actor.getId());
			else if (this.actorService.checkAuthority(actor, "PROFESSIONAL") || this.actorService.checkAuthority(actor, "CUSTOMER"))
				ratings = this.ratingService.findByTransporterId(actor.getId());

			//			final ArrayList<String> imagesLeft = this.bannerService.getBannerByZone("izquierda");
			final ArrayList<Banner> imagesBottom = this.bannerService.getBannerByZone("abajo");
			//			final ArrayList<String> imagesRight = this.bannerService.getBannerByZone("derecha");

			result = new ModelAndView("profile/display");
			result.addObject("actor", actor);
			result.addObject("pictureImage", image);
			result.addObject("numberNoti", numberNoti);
			result.addObject("ratings", ratings);
			result.addObject("requestURI", "profile/display.do");
			//			result.addObject("imagesLeft", imagesLeft);
			result.addObject("imagesBottom", imagesBottom);
			//			result.addObject("imagesRight", imagesRight);
		} catch (final Throwable e) {
			result = new ModelAndView("error");
		}
		return result;
	}

	// Edit -------------------------------------------------------------------		

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		ProfileForm profileForm;
		final Integer numberNoti;
		try {
			profileForm = this.actorService.principalToFormObject();
			numberNoti = this.notificationService.findNotificationWithoutRead();

			result = new ModelAndView("profile/edit");
			result.addObject("profileForm", profileForm);
			result.addObject("numberNoti", numberNoti);
			result.addObject("requestURI", "profile/edit.do");
		} catch (final Throwable e) {
			result = new ModelAndView("error");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final ProfileForm profileForm, final BindingResult binding) throws IOException {
		ModelAndView result;
		Actor actor;
		byte[] savedFile;

		try {
			actor = this.actorService.reconstructEdit(profileForm, binding);

			if (binding.hasErrors()) {
				System.out.println(binding.toString());
				result = this.createModelAndView(profileForm, "profile.register.error");
			} else {

				if (profileForm.getUserImage().getSize() > 0 && profileForm.getUserImage().getSize() <= 2097152) {

					savedFile = profileForm.getUserImage().getBytes();
					actor.setPicture(savedFile);

				}

				this.actorService.save(actor);
				result = new ModelAndView("redirect:display.do");
			}
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
