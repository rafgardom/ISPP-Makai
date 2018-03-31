
package controllers.actor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.NotificationService;
import services.RequestService;
import controllers.AbstractController;
import domain.Actor;
import domain.Request;

@Controller
@RequestMapping("/request/actor")
public class RequestController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private RequestService		requestService;

	@Autowired
	private NotificationService	notificationService;

	@Autowired
	private ActorService		actorService;


	// Constructors -----------------------------------------------------------

	public RequestController() {
		super();
	}

	// Display -------------------------------------------------------		
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int requestId) {
		ModelAndView result;
		Request request;
		final Integer numberNoti;
		Actor principal;
		Boolean intruso = false;

		try {
			request = this.requestService.findOne(requestId);
			numberNoti = this.notificationService.findNotificationWithoutRead();

			principal = this.actorService.findByPrincipal();
			if (this.actorService.checkAuthority(principal, "CUSTOMER"))
				if (request.getCustomer().getId() != principal.getId())
					intruso = true;

			if (intruso == true)
				result = new ModelAndView("redirect:../customer/myList.do");
			else {
				result = new ModelAndView("request/display");
				result.addObject("request", request);
				result.addObject("numberNoti", numberNoti);
				result.addObject("requestURI", "request/trainer/display.do?requestId=" + request.getId());
			}
		} catch (final Throwable oops) {
			result = new ModelAndView("error");
		}
		return result;
	}

}
