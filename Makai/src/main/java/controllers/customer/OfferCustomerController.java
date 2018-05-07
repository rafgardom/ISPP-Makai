
package controllers.customer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import paypal.PaypalEnvironment;
import services.BannerService;
import services.NotificationService;
import services.OfferService;
import services.PriceService;
import services.RequestService;
import services.TrainerService;

import com.paypal.api.payments.Payment;

import controllers.AbstractController;
import domain.Banner;
import domain.Offer;
import domain.Price;
import domain.Rating;
import domain.Request;
import domain.Trainer;

@Controller
@RequestMapping("/offer/customer")
public class OfferCustomerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private OfferService		offerService;

	@Autowired
	private RequestService		requestService;

	@Autowired
	private PriceService		priceService;

	@Autowired
	private TrainerService		trainerService;

	@Autowired
	private NotificationService	notificationService;

	@Autowired
	private BannerService		bannerService;


	// Constructors -----------------------------------------------------------

	public OfferCustomerController() {
		super();
	}

	// List ---------------------------------------------------------------

	@RequestMapping(value = "/accept", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int offerId, final HttpServletRequest req, final HttpServletResponse resp) {
		ModelAndView result;
		Offer offer = null;
		Request request;
		final Price priceObject = this.priceService.findOne();

		try {
			final Date today = new Date();

			offer = this.offerService.findOne(offerId);

			if (offer.getStartMoment().before(today))
				throw new IllegalArgumentException();

			request = offer.getRequest();
			Assert.isTrue(!this.requestService.tieneOfferAceptadaUnRequest(request));
			final PaypalEnvironment paypalEnvironment = new PaypalEnvironment();
			Double price = offer.getPrice() * (priceObject.getAdoptionFee() / 100);
			if (price > 2500.0)
				price = 2500.0;

			final Payment payment = paypalEnvironment.createPayment(req, resp, price, offer.getId(), "offerId", "/offer/customer");

			result = new ModelAndView("redirect:" + req.getAttribute("redirectURL"));
			//			this.offerService.acceptedOffer(offer);
		} catch (final Throwable oops) {
			result = new ModelAndView("error");
			if (offer != null) {
				if (offer.getIsAccepted() == true)
					result.addObject("errorOffer", "offer.commit.error.is.accepted");
				else if (this.requestService.tieneOfferAceptadaUnRequest(offer.getRequest()))
					result.addObject("errorOffer", "offer.commit.error.request.accept");
				else if (!this.offerService.elAnimalNoHaSidoAdoptado(offer.getAnimal()))
					result.addObject("errorOffer", "offer.commit.error.animal.adoptado");
			} else
				result.addObject("errorOffer", "offer.commit.error.no.exist.offer");

		}

		//		result = new ModelAndView("redirect:../../request/customer/myList.do");

		return result;
	}
	@RequestMapping(value = "/payment/done", method = RequestMethod.GET)
	public ModelAndView paymentFirstStep(@RequestParam(required = false) final int offerId) {
		ModelAndView result;
		result = new ModelAndView();
		try {
			final Offer offer = this.offerService.findOne(offerId);
			this.offerService.acceptedOffer(offer);
			result = new ModelAndView("offer/customer/payment/successful");
			result.addObject("requestId", offer.getRequest().getId());
			result.addObject("offerId", true);
			result.addObject("requestId", offer.getRequest().getId());

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/");
			result.addObject("errorMessage", "offer.pay.error");
		}
		return result;
	}

	@RequestMapping(value = "/payment/successful", method = RequestMethod.GET)
	public ModelAndView paymentSecondStep() {
		ModelAndView result;
		result = new ModelAndView();
		try {
			result = new ModelAndView("redirect:/offer/customer/payment/done.do");

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/");
			result.addObject("errorMessage", "offer.pay.error");
		}
		return result;
	}

	//	@RequestMapping(value = "/pilotPlan", method = RequestMethod.GET)
	//	public ModelAndView paymentSimulation(@RequestParam final int offerId) {
	//		ModelAndView result;
	//		result = new ModelAndView();
	//		try {
	//			final Offer offer = this.offerService.findOne(offerId);
	//			this.offerService.acceptedOffer(offer);
	//			result = new ModelAndView("redirect:/offer/customer/pilotPlan/successful.do");
	//
	//		} catch (final Throwable oops) {
	//			result = new ModelAndView("redirect:/");
	//			result.addObject("errorMessage", "offer.pay.error");
	//		}
	//		return result;
	//	}
	//
	//	@RequestMapping(value = "/pilotPlan/successful", method = RequestMethod.GET)
	//	public ModelAndView paymentSimulation() {
	//		ModelAndView result;
	//		result = new ModelAndView();
	//		try {
	//			result = new ModelAndView("offer/customer/pilotPlan/successful");
	//
	//		} catch (final Throwable oops) {
	//			result = new ModelAndView("redirect:/");
	//			result.addObject("errorMessage", "offer.pay.error");
	//		}
	//		return result;
	//	}

	// List -------------------------------------------------------------------		

	@RequestMapping(value = "/listTrainer", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int trainerId) {
		ModelAndView result;
		Trainer trainer;
		Collection<Offer> offers;
		final Collection<Rating> ratings;

		final Integer numberNoti;
		try {
			trainer = this.trainerService.findOne(trainerId);
			offers = this.offerService.findOffersAcceptByTrainer(trainer);
			numberNoti = this.notificationService.findNotificationWithoutRead();

			final ArrayList<Banner> imagesBottom = this.bannerService.getBannerByZone("abajo");

			result = new ModelAndView("offer/customer/listTrainer");
			result.addObject("offers", offers);
			result.addObject("numberNoti", numberNoti);
			result.addObject("requestURI", "offer/customer/listTrainer.do");
			result.addObject("imagesBottom", imagesBottom);
		} catch (final Throwable e) {
			result = new ModelAndView("error");
		}
		return result;
	}

}
