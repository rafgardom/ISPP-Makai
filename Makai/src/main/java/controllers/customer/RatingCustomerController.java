
package controllers.customer;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.NotificationService;
import services.RatingService;
import services.RequestService;
import controllers.AbstractController;
import domain.Rating;
import domain.Request;

@Controller
@RequestMapping("/rating/customer")
public class RatingCustomerController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private RatingService		ratingService;

	@Autowired
	private RequestService		requestService;

	@Autowired
	private NotificationService	notificationService;


	// Constructors -----------------------------------------------------------

	public RatingCustomerController() {
		super();
	}

	// Create  ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int requestId) {
		ModelAndView result;
		Rating rating;
		Request request;
		try {
			request = this.requestService.findOne(requestId);
			rating = this.ratingService.createToRequest(request);

			result = this.createModelAndView(rating);
		} catch (final Throwable oops) {
			result = new ModelAndView("error");
		}
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Rating rating, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createModelAndView(rating);
		else
			try {
				this.ratingService.save(rating);
				result = new ModelAndView("");

			} catch (final Throwable oops) {
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

		result = new ModelAndView("rating/create");
		result.addObject("RequestURI", "rating/customer/create.do");
		result.addObject("numberNoti", numberNoti);
		result.addObject("rating", rating);
		result.addObject("message", message);

		return result;
	}
}
