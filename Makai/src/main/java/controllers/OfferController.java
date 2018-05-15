
package controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

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
import services.PriceService;
import services.RatingService;
import services.RequestService;
import domain.Banner;
import domain.Offer;
import domain.Price;
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

	@Autowired
	private RatingService		ratingService;

	@Autowired
	private PriceService		priceService;


	//Listing request's offers
	@RequestMapping(value = "/customer/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int requestId) {
		ModelAndView result;
		final Integer numberNoti;

		try {
			final Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, -1);
			final Date today = new Date(calendar.getTimeInMillis());

			final Request request = this.requestService.findOne(requestId);
			Assert.notNull(request);
			final Collection<Offer> offers = this.offerService.findOfferByRequest(request);
			numberNoti = this.notificationService.findNotificationWithoutRead();

			final Boolean tieneRating[] = new Boolean[offers.size()];
			int cont = 0;
			for (final Offer aux : offers) {
				tieneRating[cont] = (this.ratingService.getFindByRequestId(aux.getRequest().getId()));
				cont++;
			}

			//			final ArrayList<String> imagesLeft = this.bannerService.getBannerByZone("izquierda");
			final ArrayList<Banner> imagesBottom = this.bannerService.getBannerByZone("abajo");
			//			final ArrayList<String> imagesRight = this.bannerService.getBannerByZone("derecha");

			final Price trainingCommission = this.priceService.findOne();

			result = new ModelAndView("offer/customer/list");
			result.addObject("offers", offers);
			result.addObject("numberNoti", numberNoti);
			result.addObject("tieneRating", tieneRating);
			result.addObject("RequestURI", "offer/customer/list.do");
			result.addObject("trainingPrice", trainingCommission.getAdoptionFee());

			//			result.addObject("imagesLeft", imagesLeft);
			result.addObject("imagesBottom", imagesBottom);
			//			result.addObject("imagesRight", imagesRight);
			result.addObject("today", today);

		} catch (final Throwable oops) {
			result = new ModelAndView("error");
		}
		return result;
	}
}
