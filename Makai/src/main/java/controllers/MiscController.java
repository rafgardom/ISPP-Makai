
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.NotificationService;
import domain.Actor;

@Controller
@RequestMapping("/misc")
public class MiscController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public MiscController() {
		super();
	}


	// Services ---------------------------------------------------------------

	@Autowired
	private ActorService		actorService;

	@Autowired
	private NotificationService	notificationService;


	// Privacy policy ----------------------------------------------------------

	@RequestMapping(value = "/privacyPolicy", method = RequestMethod.GET)
	public ModelAndView privacyPolicy(@RequestParam final boolean sc) {
		ModelAndView result;
		Integer numberNoti;
		Actor actor;
		boolean userLogueado = false;
		try {
			try {
				actor = this.actorService.findByPrincipal();
				userLogueado = true;

			} catch (final Throwable e) {

			}
			result = new ModelAndView("privacyPolicy");
			if (userLogueado) {
				numberNoti = this.notificationService.findNotificationWithoutRead();
				result.addObject("numberNoti", numberNoti);
			}
			result.addObject("requestURI", "misc/privacyPolicy.do");
			result.addObject("sc", sc);
		} catch (final Throwable e) {
			result = new ModelAndView("error");
		}
		return result;
	}

	// Cookies policy ----------------------------------------------------------

	@RequestMapping(value = "/cookiesPolicy", method = RequestMethod.GET)
	public ModelAndView cookiesPolicy() {
		ModelAndView result;
		Integer numberNoti = null;
		Boolean userLogueado = false;
		Actor actor;

		try {
			actor = this.actorService.findByPrincipal();
			userLogueado = true;
			numberNoti = this.notificationService.findNotificationWithoutRead();
		} catch (final Throwable oops) {

		}

		result = new ModelAndView("cookiesPolicy");
		if (userLogueado)
			result.addObject("numberNoti", numberNoti);
		result.addObject("requestURI", "misc/cookiesPolicy.do");

		return result;
	}

	// FAQ ----------------------------------------------------------

	@RequestMapping(value = "/faq", method = RequestMethod.GET)
	public ModelAndView faq() {
		ModelAndView result;
		Boolean userLogueado = false;
		Actor actor;
		Integer numberNoti = null;

		try {
			actor = this.actorService.findByPrincipal();
			userLogueado = true;
			numberNoti = this.notificationService.findNotificationWithoutRead();
		} catch (final Throwable oops) {

		}

		result = new ModelAndView("faq");
		result.addObject("requestURI", "misc/faq.do");
		if (userLogueado)
			result.addObject("numberNoti", numberNoti);

		return result;
	}

	// Conditions ----------------------------------------------------------

	@RequestMapping(value = "/conditions", method = RequestMethod.GET)
	public ModelAndView conditions() {
		ModelAndView result;

		result = new ModelAndView("conditions");
		result.addObject("requestURI", "misc/conditions.do");

		return result;
	}

}
