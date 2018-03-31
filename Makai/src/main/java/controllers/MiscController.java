
package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/misc")
public class MiscController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public MiscController() {
		super();
	}

	// Privacy policy ----------------------------------------------------------

	@RequestMapping(value = "/privacyPolicy", method = RequestMethod.GET)
	public ModelAndView privacyPolicy(@RequestParam final boolean sc) {
		ModelAndView result;
		try {
			result = new ModelAndView("privacyPolicy");
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

		result = new ModelAndView("cookiesPolicy");
		result.addObject("requestURI", "misc/cookiesPolicy.do");

		return result;
	}
}
