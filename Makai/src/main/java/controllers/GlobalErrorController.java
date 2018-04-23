
package controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.BannerService;
import domain.Banner;

@Controller
public class GlobalErrorController extends AbstractController {

	@Autowired
	private BannerService	bannerService;


	//Show error page
	@RequestMapping(value = "/error", method = RequestMethod.GET)
	public ModelAndView showError() {
		ModelAndView result;

		final ArrayList<Banner> imagesLeft = this.bannerService.getBannerByZone("izquierda");
		final ArrayList<Banner> imagesBottom = this.bannerService.getBannerByZone("abajo");
		final ArrayList<Banner> imagesRight = this.bannerService.getBannerByZone("derecha");

		result = new ModelAndView("error");

		result.addObject("imagesLeft", imagesLeft);
		result.addObject("imagesBottom", imagesBottom);
		result.addObject("imagesRight", imagesRight);
		return result;
	}
}
