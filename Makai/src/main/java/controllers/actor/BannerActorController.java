
package controllers.actor;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;

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
import controllers.AbstractController;
import domain.Actor;
import domain.Banner;
import forms.BannerForm;

@Controller
@RequestMapping("/banner/actor")
public class BannerActorController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private BannerService		bannerService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private NotificationService	notificationService;


	// Constructors -----------------------------------------------------------

	public BannerActorController() {
		super();
	}

	// Listing -----------------------------------------------------------------		
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listByAdvertising() {
		ModelAndView result;
		Collection<BannerForm> bannerForms;
		Actor actor;
		Integer numberNoti;

		actor = this.actorService.findByPrincipal();
		numberNoti = this.notificationService.findNotificationWithoutRead();

		bannerForms = new HashSet<BannerForm>();

		for (final Banner b : this.bannerService.findByActorId(actor.getId()))
			bannerForms.add(this.bannerService.bannerToFormObject(b));

		result = new ModelAndView("banner/list");
		result.addObject("requestURI", "banner/actor/list.do");
		result.addObject("numberNoti", numberNoti);
		result.addObject("bannerForms", bannerForms);

		return result;
	}

	// Display -----------------------------------------------------------------		
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int bannerId) {
		ModelAndView result;
		final Integer numberNoti;
		Banner banner;

		banner = this.bannerService.findOne(bannerId);
		numberNoti = this.notificationService.findNotificationWithoutRead();

		result = new ModelAndView("banner/display");
		result.addObject("requestURI", "banner/actor/display.do");
		result.addObject("numberNoti", numberNoti);
		result.addObject("banner", banner);

		return result;
	}

	// Delete --------------------------------------------------------------------		

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView ban(@RequestParam final int bannerId) {
		ModelAndView result;
		Banner banner;
		try {
			banner = this.bannerService.findOne(bannerId);

			this.bannerService.delete(banner);

			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			System.out.println(oops.toString());
			result = new ModelAndView("error");
		}
		return result;
	}

	// Creation ---------------------------------------------------------------		
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView register() {
		ModelAndView result;
		Banner banner;
		BannerForm bannerForm;

		banner = this.bannerService.create();
		bannerForm = this.bannerService.bannerToFormObject(banner);
		bannerForm.setPrice(0.0);

		result = this.createEditModelAndView(bannerForm);

		return result;
	}

	// Edition ----------------------------------------------------------------		

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int bannerId) {
		ModelAndView result;
		Banner banner;
		BannerForm bannerForm;

		banner = this.bannerService.findOne(bannerId);
		bannerForm = this.bannerService.bannerToFormObject(banner);

		result = this.createEditModelAndView(bannerForm);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final BannerForm bannerForm, final BindingResult binding) throws IOException {

		ModelAndView result;
		Banner banner;
		byte[] savedFile;

		banner = this.bannerService.reconstruct(bannerForm, binding);

		if (binding.hasErrors()) {
			System.out.println(binding.toString());
			result = this.createEditModelAndView(bannerForm);

		} else
			try {

				if (bannerForm.getBannerImage().getSize() > 0) {

					savedFile = bannerForm.getBannerImage().getBytes();
					banner.setPicture(savedFile);

				}

				this.bannerService.save(banner);
				result = new ModelAndView("redirect:list.do");

			} catch (final Throwable oops) {
				System.out.println(oops);

				result = this.createEditModelAndView(bannerForm, "banner.commit.error");

			}
		return result;

	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final BannerForm bannerForm) {
		ModelAndView result;

		result = this.createEditModelAndView(bannerForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final BannerForm bannerForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("banner/create");
		result.addObject("bannerForm", bannerForm);
		result.addObject("requestURI", "banner/actor/create.do");
		result.addObject("errorMessage", message);

		return result;
	}
}
