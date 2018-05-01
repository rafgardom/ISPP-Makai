
package controllers.customer;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CustomerService;
import services.NotificationService;
import services.RatingService;
import services.RequestService;
import services.TravelService;
import controllers.AbstractController;
import domain.Customer;
import domain.Rating;
import domain.Request;
import domain.Travel;

@Controller
@RequestMapping("/rating/customer")
public class RatingCustomerController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private RatingService		ratingService;

	@Autowired
	private RequestService		requestService;

	@Autowired
	private TravelService		travelService;

	@Autowired
	private NotificationService	notificationService;

	@Autowired
	private CustomerService		customerService;


	// Constructors -----------------------------------------------------------

	public RatingCustomerController() {
		super();
	}

	// Create  ---------------------------------------------------------------

	@RequestMapping(value = "/createRequest", method = RequestMethod.GET)
	public ModelAndView createRequest(@RequestParam final int requestId) {
		ModelAndView result;
		Rating rating;
		Request request;
		try {
			Assert.isTrue(!this.ratingService.getFindByRequestId(requestId));
			request = this.requestService.findOne(requestId);
			rating = this.ratingService.createToRequest(request);

			result = this.createModelAndView(rating);
		} catch (final Throwable oops) {
			result = new ModelAndView("error");
		}
		return result;
	}

	@RequestMapping(value = "/createRequest", method = RequestMethod.POST, params = "save")
	public ModelAndView saveRequest(@Valid final Rating rating, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createModelAndView(rating);
		else
			try {
				Assert.isTrue(!this.ratingService.getFindByRequestId(rating.getRequest().getId()));
				this.ratingService.save(rating);
				result = new ModelAndView("redirect:../../offer/customer/list.do?requestId=" + rating.getRequest().getId());

			} catch (final Throwable oops) {
				if (oops.getMessage().equals("only whiteSpaces"))
					result = this.createModelAndView(rating, "rating.whiteSpaces.error");
				else
					result = this.createModelAndView(rating, "rating.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/createTravel", method = RequestMethod.GET)
	public ModelAndView createTravel(@RequestParam final int travelId) {
		ModelAndView result;
		final Rating rating;
		Travel travel;
		Customer customer;
		try {
			customer = this.customerService.findByPrincipal();
			//Assert.isNull(this.ratingService.findRatingByCustomerFromTravel(travelId, customer.getId()));
			travel = this.travelService.findOne(travelId);
			rating = this.ratingService.createToTravel(travel);

			result = this.createModelAndView(rating);
		} catch (final Throwable oops) {
			result = new ModelAndView("error");
		}
		return result;
	}

	@RequestMapping(value = "/createTravel", method = RequestMethod.POST, params = "save")
	public ModelAndView saveTravel(@Valid Rating rating, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createModelAndView(rating);
		else
			try {

				rating = this.ratingService.save(rating);

				result = new ModelAndView("redirect:/travel/myPastList.do");

			} catch (final Throwable oops) {
				if (oops.getMessage().equals("only whiteSpaces"))
					result = this.createModelAndView(rating, "rating.whiteSpaces.error");
				else
					result = this.createModelAndView(rating, "rating.commit.error");
			}
		return result;
	}
	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createModelAndView(final Rating rating) {
		ModelAndView result;

		result = this.createModelAndView(rating, null);

		return result;
	}

	protected ModelAndView createModelAndView(final Rating rating, final String message) {
		ModelAndView result;
		final Integer numberNoti;
		numberNoti = this.notificationService.findNotificationWithoutRead();

		if (rating.getTravel() == null) {
			result = new ModelAndView("rating/createRequest");
			result.addObject("RequestURI", "rating/customer/createRequest.do?requestId=" + rating.getRequest().getId());

		} else {
			result = new ModelAndView("rating/createTravel");
			result.addObject("RequestURI", "rating/customer/createTravel.do?travelId=" + rating.getTravel().getId());
		}

		result.addObject("numberNoti", numberNoti);
		result.addObject("rating", rating);
		result.addObject("message", message);

		return result;
	}
}
