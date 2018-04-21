
package controllers.administrator;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.NotificationService;
import controllers.AbstractController;
import domain.Actor;
import domain.Notification;
import domain.NotificationType;

@Controller
@RequestMapping("/notification/admin")
public class NotificationAdministratorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private NotificationService	notificationService;

	@Autowired
	private ActorService		actorService;


	// Constructors -----------------------------------------------------------

	public NotificationAdministratorController() {
		super();
	}

	// Create -----------------------------------------------------------------		

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Notification notification;

		try {
			notification = this.notificationService.create();

			result = this.createEditModelAndView(notification);
		} catch (final Throwable e) {
			result = new ModelAndView("error");
		}
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Notification notification, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println(binding.toString());
			result = this.createEditModelAndView(notification);
		} else
			try {
				this.notificationService.saveForAll(notification);
				result = new ModelAndView("redirect:../actor/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(notification, "notification.commit.error");
			}
		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Notification notification) {
		ModelAndView result;

		result = this.createEditModelAndView(notification, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Notification notification, final String message) {
		ModelAndView result;
		Collection<Actor> actors;
		NotificationType[] notificationTypes;
		final Integer numberNoti;

		actors = this.actorService.findAll();
		notificationTypes = NotificationType.values();
		numberNoti = this.notificationService.findNotificationWithoutRead();

		result = new ModelAndView("notification/create");
		result.addObject("notification", notification);
		result.addObject("numberNoti", numberNoti);
		result.addObject("actors", actors);
		result.addObject("notificationTypes", notificationTypes);
		result.addObject("RequestURI", "notification/admin/create.do");
		result.addObject("errorMessage", message);

		return result;
	}

}
