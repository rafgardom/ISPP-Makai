
package controllers.customer;

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
import services.OfferService;
import services.PriceService;
import services.RequestService;

import com.paypal.api.payments.Payment;

import controllers.AbstractController;
import domain.Offer;
import domain.Price;
import domain.Request;

@Controller
@RequestMapping("/offer/customer")
public class OfferCustomerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private OfferService	offerService;

	@Autowired
	private RequestService	requestService;

	@Autowired
	private PriceService	priceService;


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
			offer = this.offerService.findOne(offerId);
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

}
