
package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.BannerService;
import services.CustomerService;
import services.MilestoneService;
import services.NotificationService;
import services.OfferService;
import services.TrainerService;
import domain.Actor;
import domain.Banner;
import domain.Customer;
import domain.Milestone;
import domain.Offer;
import domain.Trainer;
import forms.MilestoneForm;

@Controller
@RequestMapping("/milestone")
public class MilestoneController extends AbstractController {

	//Related services
	@Autowired
	private MilestoneService	milestoneService;

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private TrainerService		trainerService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private OfferService		offerService;

	@Autowired
	private BannerService		bannerService;

	@Autowired
	private NotificationService	notificationService;


	//Listing milestones
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int offerId) {
		ModelAndView result;
		try {
			Trainer trainer;
			Customer customer;
			final Actor principal = this.actorService.findByPrincipal();
			final Offer offer = this.offerService.findOne(offerId);

			Assert.notNull(offer);
			if (principal instanceof Customer) {
				customer = this.customerService.findByPrincipal();
				Assert.isTrue(offer.getRequest().getCustomer().equals(customer));
			} else if (principal instanceof Trainer) {
				trainer = this.trainerService.findByPrincipal();
				Assert.isTrue(offer.getTrainer().equals(trainer));
			} else
				throw new IllegalArgumentException();

			final ArrayList<Banner> imagesBottom = this.bannerService.getBannerByZone("abajo");
			final Integer numberNoti = this.notificationService.findNotificationWithoutRead();

			final Collection<Milestone> milestones = this.milestoneService.findAllByOffer(offerId);
			result = new ModelAndView("milestone/list");
			result.addObject("milestones", milestones);
			result.addObject("RequestURI", "milestone/list.do");
			result.addObject("numberNoti", numberNoti);
			result.addObject("imagesBottom", imagesBottom);
			result.addObject("offerId", offerId);

		} catch (final Throwable e) {
			result = new ModelAndView("error");
		}
		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int milestoneId) {
		ModelAndView result;
		MilestoneForm milestoneForm;

		try {
			milestoneForm = this.milestoneService.display(milestoneId);
			final ArrayList<Banner> imagesBottom = this.bannerService.getBannerByZone("abajo");
			final Integer numberNoti = this.notificationService.findNotificationWithoutRead();

			result = new ModelAndView("milestone/display");
			result.addObject("milestoneForm", milestoneForm);
			result.addObject("RequestURI", "milestone/display.do");
			result.addObject("offerId", milestoneForm.getOfferId());
			result.addObject("numberNoti", numberNoti);
			result.addObject("imagesBottom", imagesBottom);
		} catch (final Throwable e) {
			result = new ModelAndView("error");
		}
		return result;
	}

	//Mark as complete
	@RequestMapping(value = "/complete", method = RequestMethod.GET)
	public ModelAndView setComplete(@RequestParam final int milestoneId) {
		ModelAndView result;
		Milestone milestone;

		try {
			milestone = this.milestoneService.findOne(milestoneId);
			this.milestoneService.setComplete(milestone);

			result = new ModelAndView("redirect:/milestone/list.do?offerId=" + milestone.getOffer().getId());
		} catch (final Throwable e) {
			result = new ModelAndView("error");
		}
		return result;
	}

	//Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int offerId) {
		ModelAndView result;
		try {
			final MilestoneForm milestoneForm = this.milestoneService.create(offerId);
			result = this.createEditModelAndView(milestoneForm);
		} catch (final Throwable e) {
			result = new ModelAndView("error");
		}
		return result;

	}

	//Save
	@RequestMapping(value = "/save", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final MilestoneForm milestoneForm, final BindingResult binding) {
		ModelAndView result;
		Milestone milestone;

		try {
			final Offer offer = this.offerService.findOne(milestoneForm.getOfferId());
			final Date date = milestoneForm.getTargetDate();
			final Date start = offer.getStartMoment();
			final Date finish = Offer.getFinishMoment(offer);
			if (date.after(finish) || date.before(start)) {
				FieldError fieldError;
				final String[] codes = {
					"milestone.targetDate.error"
				};
				fieldError = new FieldError("milestoneForm", "targetDate", milestoneForm.getTargetDate(), false, codes, null, "");
				binding.addError(fieldError);
			}
			if (binding.hasErrors())
				result = this.createEditModelAndView(milestoneForm, "milestone.validation.error");
			else {
				milestone = this.milestoneService.reconstruct(milestoneForm, binding);
				if (binding.hasErrors())
					result = this.createEditModelAndView(milestoneForm, "milestone.validation.error");
				else
					try {
						final Trainer trainer = this.trainerService.findByPrincipal();

						Assert.isTrue(offer.getTrainer().equals(trainer));

						milestone.setOffer(offer);
						this.milestoneService.save(milestone);
						result = new ModelAndView("redirect:/milestone/list.do?offerId=" + milestoneForm.getOfferId());

					} catch (final Throwable e) {
						result = this.createEditModelAndView(milestoneForm, "milestone.save.error");
					}
			}
		} catch (final Throwable e) {
			result = this.createEditModelAndView(milestoneForm, "milestone.validation.error");
		}
		return result;
	}

	//Edit
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int milestoneId) {
		ModelAndView result;

		try {
			final Trainer trainer = this.trainerService.findByPrincipal();
			final Milestone milestone = this.milestoneService.findOne(milestoneId);

			Assert.isTrue(milestone.getOffer().getTrainer().equals(trainer));

			final MilestoneForm milestoneForm = this.milestoneService.toObjectForm(milestone);
			result = this.createEditModelAndView(milestoneForm);
		} catch (final Throwable e) {
			result = new ModelAndView("error");
		}
		return result;
	}

	//Delete

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int milestoneId) {
		ModelAndView result;

		try {
			final Trainer trainer = this.trainerService.findByPrincipal();
			final Milestone milestone = this.milestoneService.findOne(milestoneId);
			final int offerId = milestone.getOffer().getId();

			Assert.isTrue(milestone.getOffer().getTrainer().equals(trainer));

			Assert.isTrue(milestone.getOffer().getIsAccepted() == false);

			this.milestoneService.delete(milestone);

			result = new ModelAndView("redirect:/milestone/list.do?offerId=" + offerId);
		} catch (final Throwable e) {
			result = new ModelAndView("error");
		}
		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final MilestoneForm milestoneForm) {
		final ModelAndView result = this.createEditModelAndView(milestoneForm, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final MilestoneForm milestoneForm, final String message) {
		ModelAndView result;

		final Integer numberNoti = this.notificationService.findNotificationWithoutRead();

		result = new ModelAndView("milestone/save");
		result.addObject("RequestURI", "milestone/save.do");
		result.addObject("errorMessage", message);
		result.addObject("milestoneForm", milestoneForm);
		result.addObject("numberNoti", numberNoti);
		result.addObject("offerId", milestoneForm.getOfferId());
		if (milestoneForm.getId() == 0)
			result.addObject("create", true);

		return result;
	}
}
