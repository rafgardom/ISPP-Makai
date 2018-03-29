
package controllers.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CustomerService;
import services.OfferService;
import controllers.AbstractController;
import domain.Offer;

@Controller
@RequestMapping("/offer/customer")
public class OfferCustomerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private CustomerService	customerService;

	@Autowired
	private OfferService	offerService;


	// Constructors -----------------------------------------------------------

	public OfferCustomerController() {
		super();
	}

	// List ---------------------------------------------------------------

	@RequestMapping(value = "/accept", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int offerId) {
		ModelAndView result;
		Offer offer;

		try {
			offer = this.offerService.findOne(offerId);
			this.offerService.acceptedOffer(offer);
		} catch (final Throwable oops) {
			System.out.println(oops);
		}

		result = new ModelAndView("redirect:../../request/customer/myList.do");

		return result;
	}
}
