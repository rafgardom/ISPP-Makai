
package controllers.trainer;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.OfferService;
import services.TrainerService;
import controllers.AbstractController;
import domain.Offer;
import domain.Trainer;

@Controller
@RequestMapping("/offer/trainer")
public class OfferTrainerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private TrainerService	trainerService;

	@Autowired
	private OfferService	offerService;


	// Constructors -----------------------------------------------------------
	public OfferTrainerController() {
		super();
	}

	// List -------------------------------------------------------------------		

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Trainer trainer;
		Collection<Offer> offers;

		trainer = this.trainerService.findByPrincipal();
		offers = this.offerService.findOffersByTrainer(trainer);

		result = new ModelAndView("offer/list");
		result.addObject("offers", offers);
		result.addObject("requestURI", "offer/trainer/list.do");

		return result;
	}

	// Delete -----------------------------------------------------------------		

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int offerId) {
		ModelAndView result;
		Offer offer;

		offer = this.offerService.findOne(offerId);
		try {
			this.offerService.delete(offer);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:list.do");
		}

		return result;
	}

}
