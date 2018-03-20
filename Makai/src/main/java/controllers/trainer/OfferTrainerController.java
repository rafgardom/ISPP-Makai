
package controllers.trainer;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AnimalService;
import services.OfferService;
import services.RequestService;
import services.TrainerService;
import controllers.AbstractController;
import domain.Actor;
import domain.Animal;
import domain.Offer;
import domain.Request;
import domain.Trainer;
import forms.OfferForm;

@Controller
@RequestMapping("/offer/trainer")
public class OfferTrainerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private TrainerService	trainerService;

	@Autowired
	private OfferService	offerService;

	@Autowired
	private ActorService	actorService;

	@Autowired
	private RequestService	requestService;

	@Autowired
	private AnimalService	animalService;


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

	// List -------------------------------------------------------------------	
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int offerId) {
		ModelAndView result;
		Actor actor;
		Offer offer;

		actor = this.actorService.findByPrincipal();
		offer = this.offerService.findOne(offerId);

		result = new ModelAndView("offer/display");
		result.addObject("offer", offer);
		result.addObject("principal", actor);
		result.addObject("requestURI", "offer/trainer/display.do?" + offer.getId());

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

	// Creation ---------------------------------------------------------------		

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int requestId) {
		ModelAndView result;
		Offer offer;
		Request request;
		OfferForm offerForm;

		request = this.requestService.findOne(requestId);

		offer = this.offerService.createWithAnimal(request);
		offerForm = this.offerService.offerToFormObject(offer);

		result = this.createEditModelAndView(offerForm);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final OfferForm offerForm, final BindingResult binding) {

		ModelAndView result;
		Offer offer;

		if (binding.hasErrors())
			result = this.createEditModelAndView(offerForm);
		else
			try {
				offer = this.offerService.reconstruct(offerForm, binding);

				offer = this.offerService.save(offer);
				result = new ModelAndView("redirect:list.do");

			} catch (final Throwable oops) {
				System.out.println(oops);
				result = this.createEditModelAndView(offerForm, "offer.commit.error");

			}
		return result;

	}

	// Editar ---------------------------------------------------------------		

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int offerId) {
		ModelAndView result;
		Offer offer;
		Request request;
		OfferForm offerForm;

		offer = this.offerService.findOne(offerId);

		request = offer.getRequest();

		offerForm = this.offerService.offerToFormObject(offer);

		result = this.createEditModelAndView(offerForm);

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final OfferForm offerForm) {
		ModelAndView result;

		result = this.createEditModelAndView(offerForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final OfferForm offerForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("offer/create");

		final Collection<Animal> animals = this.animalService.findAnimalFromAnimalShelter();

		result.addObject("RequestURI", "offer/trainer/create.do");
		result.addObject("animals", animals);
		result.addObject("offerForm", offerForm);
		result.addObject("message", message);

		return result;
	}

}