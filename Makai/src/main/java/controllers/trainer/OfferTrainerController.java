
package controllers.trainer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AnimalService;
import services.BannerService;
import services.NotificationService;
import services.OfferService;
import services.RequestService;
import services.TrainerService;
import utilities.Utilities;
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
	private TrainerService		trainerService;

	@Autowired
	private OfferService		offerService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private RequestService		requestService;

	@Autowired
	private AnimalService		animalService;

	@Autowired
	private NotificationService	notificationService;

	@Autowired
	private BannerService		bannerService;


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
		final Integer numberNoti;
		try {
			trainer = this.trainerService.findByPrincipal();
			offers = this.offerService.findOffersByTrainer(trainer);
			numberNoti = this.notificationService.findNotificationWithoutRead();

			//			final ArrayList<String> imagesLeft = this.bannerService.getBannerByZone("izquierda");
			final ArrayList<String> imagesBottom = this.bannerService.getBannerByZone("abajo");
			//			final ArrayList<String> imagesRight = this.bannerService.getBannerByZone("derecha");

			result = new ModelAndView("offer/list");
			result.addObject("offers", offers);
			result.addObject("numberNoti", numberNoti);
			result.addObject("requestURI", "offer/trainer/list.do");
			//			result.addObject("imagesLeft", imagesLeft);
			result.addObject("imagesBottom", imagesBottom);
			//			result.addObject("imagesRight", imagesRight);
		} catch (final Throwable e) {
			result = new ModelAndView("error");
		}
		return result;
	}

	@RequestMapping(value = "/listPassed", method = RequestMethod.GET)
	public ModelAndView listPassed() {
		ModelAndView result;
		Trainer trainer;
		Collection<Offer> offers;
		final Integer numberNoti;
		try {
			trainer = this.trainerService.findByPrincipal();
			offers = this.offerService.findOffersByTrainerPassed(trainer);
			numberNoti = this.notificationService.findNotificationWithoutRead();

			//			final ArrayList<String> imagesLeft = this.bannerService.getBannerByZone("izquierda");
			final ArrayList<String> imagesBottom = this.bannerService.getBannerByZone("abajo");
			//			final ArrayList<String> imagesRight = this.bannerService.getBannerByZone("derecha");

			result = new ModelAndView("offer/listPast");
			result.addObject("offers", offers);
			result.addObject("numberNoti", numberNoti);
			result.addObject("requestURI", "offer/trainer/listPassed.do");
			//			result.addObject("imagesLeft", imagesLeft);
			result.addObject("imagesBottom", imagesBottom);
			//			result.addObject("imagesRight", imagesRight);
		} catch (final Throwable e) {
			result = new ModelAndView("error");
		}
		return result;
	}

	// Display -------------------------------------------------------------------	
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int offerId) {
		ModelAndView result;
		Actor actor;
		Offer offer;
		final String image;
		Boolean intruso = false;
		final Integer numberNoti;

		try {
			actor = this.actorService.findByPrincipal();
			offer = this.offerService.findOne(offerId);
			numberNoti = this.notificationService.findNotificationWithoutRead();

			image = Utilities.showImage(offer.getAnimal().getPicture());

			//			final ArrayList<String> imagesLeft = this.bannerService.getBannerByZone("izquierda");
			final ArrayList<String> imagesBottom = this.bannerService.getBannerByZone("abajo");
			//			final ArrayList<String> imagesRight = this.bannerService.getBannerByZone("derecha");

			if (this.actorService.checkAuthority(actor, "CUSTOMER"))
				if (offer.getRequest().getCustomer().getId() != actor.getId())
					intruso = true;
			if (this.actorService.checkAuthority(actor, "TRAINER"))
				if (offer.getTrainer().getId() != actor.getId())
					intruso = true;

			if (intruso == true)
				result = new ModelAndView("redirect:../../");
			else {

				result = new ModelAndView("offer/display");
				result.addObject("offer", offer);
				result.addObject("principal", actor);
				result.addObject("animalImage", image);
				result.addObject("numberNoti", numberNoti);
				result.addObject("requestURI", "offer/trainer/display.do?" + offer.getId());
				//				result.addObject("imagesLeft", imagesLeft);
				result.addObject("imagesBottom", imagesBottom);
				//				result.addObject("imagesRight", imagesRight);
			}
		} catch (final Throwable oops) {
			result = new ModelAndView("error");
			result.addObject("errorOffer", "offer.commit.error.no.exist.offer");
		}
		return result;
	}

	// Delete -----------------------------------------------------------------		

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int offerId) {
		ModelAndView result;
		Offer offer;

		try {
			offer = this.offerService.findOne(offerId);
			this.offerService.delete(offer);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("error");
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

		try {
			request = this.requestService.findOne(requestId);

			if (request.getAnimal() != null)
				offer = this.offerService.createWithAnimal(request);
			else
				offer = this.offerService.createWithoutAnimal(request);

			offerForm = this.offerService.offerToFormObject(offer);

			result = this.createEditModelAndView(offerForm);
		} catch (final Throwable oops) {
			Trainer trainer;
			Collection<Offer> offers;
			final Integer numberNoti;

			trainer = this.trainerService.findByPrincipal();
			offers = this.offerService.findOffersByTrainer(trainer);
			numberNoti = this.notificationService.findNotificationWithoutRead();

			result = new ModelAndView("offer/list");
			result.addObject("offers", offers);
			result.addObject("numberNoti", numberNoti);
			result.addObject("requestURI", "offer/trainer/list.do");
			result.addObject("message", "offer.commit.error.deleted.accept");
		}

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final OfferForm offerForm, final BindingResult binding) throws IOException {

		ModelAndView result;
		Offer offer = null;

		if (binding.hasErrors())
			if (offerForm.getRequest() == null)
				result = this.createEditModelAndView(offerForm, "offer.commit.error.no.request");
			else
				result = this.createEditModelAndView(offerForm);
		else
			try {
				offer = this.offerService.reconstruct(offerForm, binding);
				offer = this.offerService.save(offer);
				result = new ModelAndView("redirect:list.do");

			} catch (final Throwable oops) {
				System.out.println(oops);
				if (offer != null)
					if (offer.getIsAccepted() == true)
						result = this.createEditModelAndView(offerForm, "offer.commit.error.is.accepted");
				if (this.requestService.tieneOfferAceptadaUnRequest(offerForm.getRequest()))
					result = this.createEditModelAndView(offerForm, "offer.commit.error.request.accept");
				if (!this.offerService.elAnimalNoHaSidoAdoptado(offerForm.getAnimal()))
					result = this.createEditModelAndView(offerForm, "offer.commit.error.animal.adoptado");
				else
					result = this.createEditModelAndView(offerForm, "offer.commit.error.no.exist.offer");
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
		Trainer principal;

		try {
			offer = this.offerService.findOne(offerId);

			principal = this.trainerService.findByPrincipal();

			Assert.isTrue(offer.getTrainer().getId() == principal.getId());

			request = offer.getRequest();

			offerForm = this.offerService.offerToFormObject(offer);

			result = this.createEditModelAndView(offerForm);

		} catch (final Throwable oops) {
			result = new ModelAndView("error");
			result.addObject("errorOffer", "offer.commit.error.no.exist.offer");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(@Valid final OfferForm offerForm, final BindingResult binding) throws IOException {

		ModelAndView result;
		Offer offer = null;

		if (binding.hasErrors())
			if (offerForm.getRequest() == null)
				result = this.createEditModelAndView(offerForm, "offer.commit.error.no.request");
			else
				result = this.createEditModelAndView(offerForm);
		else
			try {
				offer = this.offerService.reconstruct(offerForm, binding);
				offer = this.offerService.save(offer);
				result = new ModelAndView("redirect:list.do");

			} catch (final Throwable oops) {
				System.out.println(oops);
				if (offer != null)
					if (offer.getIsAccepted() == true)
						result = this.createEditModelAndView(offerForm, "offer.commit.error.is.accepted");
				if (this.requestService.tieneOfferAceptadaUnRequest(offerForm.getRequest()))
					result = this.createEditModelAndView(offerForm, "offer.commit.error.request.accept");
				if (!this.offerService.elAnimalNoHaSidoAdoptado(offerForm.getAnimal()))
					result = this.createEditModelAndView(offerForm, "offer.commit.error.animal.adoptado");
				else
					result = this.createEditModelAndView(offerForm, "offer.commit.error.no.exist.offer");
			}
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
		final Integer numberNoti;

		if (offerForm.getId() == 0) {
			result = new ModelAndView("offer/create");
			result.addObject("RequestURI", "offer/trainer/create.do?requestId=" + offerForm.getRequest().getId());
		} else {
			result = new ModelAndView("offer/edit");
			result.addObject("RequestURI", "offer/trainer/edit.do?offerId=" + offerForm.getId());
		}

		//final Collection<Animal> animals = this.animalService.findAnimalWithoutAdopted();
		final Collection<Animal> animals = this.animalService.findAnimalsWithoutDeletedAndCustomer();
		numberNoti = this.notificationService.findNotificationWithoutRead();

		result.addObject("animals", animals);
		result.addObject("offerForm", offerForm);
		result.addObject("numberNoti", numberNoti);
		result.addObject("message", message);

		return result;
	}

}
