
package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BannerService;
import controllers.AbstractController;
import domain.Banner;

@Controller
@RequestMapping("/banner/admin")
public class BannerAdministratorController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private BannerService	bannerService;


	// Constructors -----------------------------------------------------------

	public BannerAdministratorController() {
		super();
	}

	// Listing -----------------------------------------------------------------		
	@RequestMapping(value = "/validate", method = RequestMethod.GET)
	public ModelAndView validate(@RequestParam final int bannerId) {
		ModelAndView result;
		try {
			final Banner banner = this.bannerService.findOne(bannerId);

			this.bannerService.validate(banner);
			result = new ModelAndView("redirect:../actor/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("error");
		}
		return result;
	}
}
