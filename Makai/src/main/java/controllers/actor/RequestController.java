
package controllers.actor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.RequestService;
import controllers.AbstractController;
import domain.Request;

@Controller
@RequestMapping("/request/actor")
public class RequestController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private RequestService	requestService;


	// Constructors -----------------------------------------------------------

	public RequestController() {
		super();
	}

	// Display -------------------------------------------------------		
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int requestId) {
		ModelAndView result;
		Request request;

		request = this.requestService.findOne(requestId);

		result = new ModelAndView("request/display");
		result.addObject("request", request);
		result.addObject("requestURI", "request/trainer/display.do?requestId=" + request.getId());

		return result;
	}

}
