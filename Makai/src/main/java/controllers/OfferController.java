
package controllers;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BannerService;
import services.NotificationService;
import services.OfferService;
import services.RequestService;
import domain.Banner;
import domain.Offer;
import domain.Request;

@Controller
@RequestMapping("/offer")
public class OfferController extends AbstractController {

	//Related services

	@Autowired
	private OfferService		offerService;

	@Autowired
	private RequestService		requestService;

	@Autowired
	private NotificationService	notificationService;

	@Autowired
	private BannerService		bannerService;


	//Listing request's offers
	@RequestMapping(value = "/customer/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int requestId) {
		ModelAndView result;
		final Integer numberNoti;

		try {
			final Request request = this.requestService.findOne(requestId);
			Assert.notNull(request);
			final Collection<Offer> offers = this.offerService.findOfferByRequest(request);
			numberNoti = this.notificationService.findNotificationWithoutRead();

			final ArrayList<Banner> imagesLeft = this.bannerService.getBannerByZone("izquierda");
			final ArrayList<Banner> imagesBottom = this.bannerService.getBannerByZone("abajo");
			final ArrayList<Banner> imagesRight = this.bannerService.getBannerByZone("derecha");

			result = new ModelAndView("offer/customer/list");
			result.addObject("offers", offers);
			result.addObject("numberNoti", numberNoti);
			result.addObject("RequestURI", "offer/customer/list.do");

			result.addObject("imagesLeft", imagesLeft);
			result.addObject("imagesBottom", imagesBottom);
			result.addObject("imagesRight", imagesRight);

		} catch (final Throwable oops) {
			result = new ModelAndView("error");
		}
		return result;
	}
}
