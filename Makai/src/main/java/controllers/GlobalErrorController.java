
package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class GlobalErrorController extends AbstractController {

	//Show error page
	@RequestMapping(value = "/error", method = RequestMethod.GET)
	public ModelAndView showError() {
		ModelAndView result;

		result = new ModelAndView("error");
		return result;
	}
}
