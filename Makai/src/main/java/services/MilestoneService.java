
package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;

import repositories.MilestoneRepository;
import domain.Actor;
import domain.Customer;
import domain.Milestone;
import domain.Offer;
import domain.Trainer;
import forms.MilestoneForm;

@Service
@Transactional
public class MilestoneService {

	//Managed repository
	@Autowired
	private MilestoneRepository	milestoneRepository;

	//Supported services

	@Autowired
	private TrainerService		trainerService;

	@Autowired
	private OfferService		offerService;

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private Validator			validator;


	//Constructor
	public MilestoneService() {
		super();
	}

	//Simple CRUD methods

	public Milestone findOne(final int milestoneId) {
		return this.milestoneRepository.findOne(milestoneId);
	}

	public Collection<Milestone> findAll() {
		return this.milestoneRepository.findAll();
	}

	public Collection<Milestone> findAllByOffer(final int offerId) {
		return this.milestoneRepository.findAllByOffer(offerId);
	}

	public MilestoneForm create(final int offerId) {
		final MilestoneForm result = new MilestoneForm();
		final Trainer trainer = this.trainerService.findByPrincipal();
		Assert.notNull(trainer);

		result.setOfferId(offerId);
		return result;
	}

	public Milestone save(final Milestone milestone) {
		Milestone result;

		Assert.notNull(milestone.getOffer());
		final Trainer trainer = this.offerService.findTrainerByOfferId(milestone.getOffer().getId());
		final Trainer principal = this.trainerService.findByPrincipal();
		Assert.isTrue(trainer.equals(principal));

		result = this.milestoneRepository.save(milestone);
		return result;
	}

	public void delete(final Milestone milestone) {
		Assert.notNull(milestone);
		this.milestoneRepository.delete(milestone);
	}

	public void deleteAll(final Collection<Milestone> milestones) {
		Assert.notNull(milestones);
		this.milestoneRepository.delete(milestones);
	}

	//Other business services

	public void setComplete(final Milestone milestone) {
		Assert.notNull(milestone);
		final Trainer trainer = this.trainerService.findByPrincipal();
		Assert.isTrue(milestone.getRealMoment() == null);
		Assert.isTrue(trainer.equals(milestone.getOffer().getTrainer()));
		Assert.isTrue(milestone.getOffer().getIsAccepted() == true);

		milestone.setRealMoment(new Date());
		this.milestoneRepository.save(milestone);
	}

	public Milestone reconstruct(final MilestoneForm milestoneForm, final BindingResult binding) {
		Milestone result = new Milestone();
		Offer offer;

		if (milestoneForm.getId() == 0) {
			result.setTitle(milestoneForm.getTitle());
			result.setDescription(milestoneForm.getDescription());
			result.setImportance(milestoneForm.getImportance());
		} else
			result = this.findOne(milestoneForm.getId());

		offer = this.offerService.findOne(milestoneForm.getOfferId());
		try {
			final Date now = new Date();
			Assert.notNull(offer);
			final Calendar calendar = Calendar.getInstance();
			if (offer.getDuration().getDay() != null)
				calendar.add(Calendar.DATE, offer.getDuration().getDay());
			if (offer.getDuration().getMonth() != null)
				calendar.add(Calendar.MONTH, offer.getDuration().getMonth());
			if (offer.getDuration().getYear() != null)
				calendar.add(Calendar.MONTH, offer.getDuration().getYear());
			final Date endTraining = calendar.getTime();

			Assert.isTrue(milestoneForm.getTargetDate().after(now));
			Assert.isTrue(milestoneForm.getTargetDate().before(endTraining));

			result.setTargetDate(milestoneForm.getTargetDate());
		} catch (final Throwable e) {
			result.setTargetDate(new Date());
			FieldError fieldError;
			final String[] codes = {
				"milestone.targetDate.error"
			};
			fieldError = new FieldError("milestoneForm", "targetDate", result.getTargetDate(), false, codes, null, "");
			binding.addError(fieldError);
		}

		if (milestoneForm.getComment().length() > 0 && milestoneForm.getComment().trim().length() == 0) {
			result.setTargetDate(new Date());
			FieldError fieldError;
			final String[] codes = {
				"milestone.whiteSpace.error"
			};
			fieldError = new FieldError("milestoneForm", "comment", result.getComment(), false, codes, null, "");
			binding.addError(fieldError);
		}

		result.setComment(milestoneForm.getComment());

		if (milestoneForm.getProblem().length() > 0 && milestoneForm.getProblem().trim().length() == 0) {
			result.setTargetDate(new Date());
			FieldError fieldError;
			final String[] codes = {
				"milestone.whiteSpace.error"
			};
			fieldError = new FieldError("milestoneForm", "problem", result.getProblem(), false, codes, null, "");
			binding.addError(fieldError);
		}
		result.setProblem(milestoneForm.getProblem());

		this.validator.validate(result, binding);

		return result;
	}
	public MilestoneForm toObjectForm(final Milestone milestone) {
		final MilestoneForm result = new MilestoneForm();

		result.setId(milestone.getId());
		result.setTitle(milestone.getTitle());
		result.setTargetDate(milestone.getTargetDate());
		result.setImportance(milestone.getImportance());
		result.setRealMoment(milestone.getRealMoment());
		result.setComment(milestone.getComment());
		result.setProblem(milestone.getProblem());
		result.setOfferId(milestone.getOffer().getId());
		result.setDescription(milestone.getDescription());
		result.setOfferId(milestone.getOffer().getId());

		return result;
	}

	public MilestoneForm display(final int milestoneId) {
		final Actor actor = this.actorService.findByPrincipal();
		MilestoneForm result = null;

		if (actor instanceof Customer) {
			final Milestone milestone = this.findOne(milestoneId);
			final Customer customer = this.customerService.findByPrincipal();
			Assert.isTrue(milestone.getOffer().getRequest().getCustomer().equals(customer));
			result = this.toObjectForm(milestone);

		} else if (actor instanceof Trainer) {
			final Milestone milestone = this.findOne(milestoneId);
			final Trainer trainer = this.trainerService.findByPrincipal();
			Assert.isTrue(milestone.getOffer().getTrainer().equals(trainer));
			result = this.toObjectForm(milestone);
		}
		return result;
	}

}
