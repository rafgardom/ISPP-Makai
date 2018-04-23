
package controllers;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.BannerService;

@Controller
public class GlobalErrorController extends AbstractController {

	@Autowired
	private BannerService	bannerService;


	//Show error page
	@RequestMapping(value = "/error", method = RequestMethod.GET)
	public ModelAndView showError() throws UnsupportedEncodingException {
		ModelAndView result;

		final ArrayList<String> imagesLeft = this.bannerService.getBannerByZone("izquierda");
		final ArrayList<String> imagesBottom = this.bannerService.getBannerByZone("abajo");
		final ArrayList<String> imagesRight = this.bannerService.getBannerByZone("derecha");

		result = new ModelAndView("error");

		result.addObject("imagesLeft", imagesLeft);
		result.addObject("imagesBottom", imagesBottom);
		result.addObject("imagesRight", imagesRight);
		return result;
	}
}
