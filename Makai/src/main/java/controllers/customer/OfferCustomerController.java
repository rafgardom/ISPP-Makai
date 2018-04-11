
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
import services.RequestService;

import com.paypal.api.payments.Payment;

import controllers.AbstractController;
import domain.Offer;
import domain.Request;

@Controller
@RequestMapping("/offer/customer")
public class OfferCustomerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private OfferService	offerService;

	@Autowired
	private RequestService	requestService;


	// Constructors -----------------------------------------------------------

	public OfferCustomerController() {
		super();
	}

	// List ---------------------------------------------------------------

	@RequestMapping(value = "/accept", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int offerId, final HttpServletRequest req, final HttpServletResponse resp) {
		ModelAndView result;
		Offer offer;
		Request request;

		try {
			offer = this.offerService.findOne(offerId);
			request = offer.getRequest();
			Assert.isTrue(!this.requestService.tieneOfferAceptadaUnRequest(request));
			final PaypalEnvironment paypalEnvironment = new PaypalEnvironment();
			final Payment payment = paypalEnvironment.createPayment(req, resp, offer.getPrice() * 0.1, offer.getId(), "offerId", "/offer/customer");

			result = new ModelAndView("redirect:" + req.getAttribute("redirectURL"));
			//			this.offerService.acceptedOffer(offer);
		} catch (final Throwable oops) {
			result = new ModelAndView("error");
			System.out.println(oops);
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
