
package controllers.actor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.NotificationService;
import controllers.AbstractController;
import domain.Actor;
import domain.Notification;

@Controller
@RequestMapping("/notification/actor")
public class NotificationActorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private NotificationService	notificationService;

	@Autowired
	private ActorService		actorService;


	// Constructors -----------------------------------------------------------

	public NotificationActorController() {
		super();
	}

	// List -------------------------------------------------------------------		

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Actor actor;
		Collection<Notification> notifications;

		actor = this.actorService.findByPrincipal();
		notifications = this.notificationService.findByActorId(actor.getId());

		result = new ModelAndView("notification/list");
		result.addObject("notifications", notifications);
		result.addObject("requestURI", "notification/actor/list.do");

		return result;
	}

	// Display ----------------------------------------------------------------		

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int notificationId) {
		ModelAndView result;
		Notification notification;

		notification = this.notificationService.findOne(notificationId);

		this.notificationService.notificationViewed(notification);

		result = new ModelAndView("notification/display");
		result.addObject("notification", notification);
		result.addObject("requestURI", "notification/actor/display.do");

		return result;
	}

	// Delete -----------------------------------------------------------------		

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int notificationId) {
		ModelAndView result;
		Notification notification;

		notification = this.notificationService.findOne(notificationId);
		try {
			this.notificationService.delete(notification);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:list.do");
		}

		return result;
	}

}
