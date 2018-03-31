
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
		Integer numberNoti;

		actor = this.actorService.findByPrincipal();
		notifications = this.notificationService.findByActorId(actor.getId());
		numberNoti = this.notificationService.findNotificationWithoutRead();

		result = new ModelAndView("notification/list");
		result.addObject("notifications", notifications);
		result.addObject("numberNoti", numberNoti);
		result.addObject("requestURI", "notification/actor/list.do");

		return result;
	}

	// Display ----------------------------------------------------------------		

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int notificationId) {
		ModelAndView result;
		Notification notification;
		final Integer numberNoti;

		try {
			notification = this.notificationService.findOne(notificationId);
			numberNoti = this.notificationService.findNotificationWithoutRead();

			this.notificationService.notificationViewed(notification);

			result = new ModelAndView("notification/display");
			result.addObject("notification", notification);
			result.addObject("numberNoti", numberNoti);
			result.addObject("requestURI", "notification/actor/display.do");
		} catch (final Throwable oops) {
			//			result = new ModelAndView("redirect:list.do");
			result = new ModelAndView("error");
		}

		return result;
	}

	// Delete -----------------------------------------------------------------		

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int notificationId) {
		ModelAndView result;
		Notification notification;

		try {
			notification = this.notificationService.findOne(notificationId);
			this.notificationService.delete(notification);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:list.do");
		}

		return result;
	}

	//Borrar todas las notificaciones de un actor
	@RequestMapping(value = "/deleteAll", method = RequestMethod.GET)
	public ModelAndView deleteAll(@RequestParam final int notificationId) {
		ModelAndView result;

		try {
			this.notificationService.deleteAllMyNotifications();
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:list.do");
		}

		return result;
	}

	// Reload
	@RequestMapping(value = "/reload", method = RequestMethod.GET)
	public Integer reload() {
		Integer result;

		result = this.notificationService.findNotificationWithoutRead();

		return result;
	}

}
