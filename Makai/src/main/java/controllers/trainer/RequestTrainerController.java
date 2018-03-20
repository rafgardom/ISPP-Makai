
package controllers.trainer;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.RequestService;
import controllers.AbstractController;
import domain.Request;

@Controller
@RequestMapping("/request/trainer")
public class RequestTrainerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private RequestService	requestService;


	// Constructors -----------------------------------------------------------
	public RequestTrainerController() {
		super();
	}

	// List -------------------------------------------------------------------		

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Request> requests;

		requests = this.requestService.findRequestsNotAccepted();

		result = new ModelAndView("request/list");
		result.addObject("requestURI", "request/list.do");
		result.addObject("requests", requests);

		return result;
	}

}
