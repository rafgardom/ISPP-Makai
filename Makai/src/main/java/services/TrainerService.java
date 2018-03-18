
package services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import repositories.TrainerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Offer;
import domain.Receipt;
import domain.Trainer;
import forms.TrainerForm;

@Service
@Transactional
public class TrainerService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private TrainerRepository	trainerRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService		actorService;

	@Autowired
	private Validator			validator;


	// Constructors------------------------------------------------------------
	public TrainerService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Trainer findOne(final int trainerId) {
		Trainer result;

		result = this.trainerRepository.findOne(trainerId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Trainer> findAll() {
		Collection<Trainer> result;

		result = this.trainerRepository.findAll();

		return result;
	}

	public Trainer create() {
		final UserAccount userAccount = new UserAccount();

		final Collection<Authority> authorities = new ArrayList<Authority>();
		final Authority a = new Authority();
		a.setAuthority(Authority.TRAINER);
		authorities.add(a);
		userAccount.setAuthorities(authorities);

		final Trainer result = new Trainer();

		result.setUserAccount(userAccount);

		final Collection<Offer> offers = new ArrayList<Offer>();
		result.setOffers(offers);

		final Collection<Receipt> receipts = new ArrayList<Receipt>();
		result.setReceipts(receipts);

		result.setAvgRating(0.0);

		return result;
	}

	public TrainerForm createForm() {
		final TrainerForm result = new TrainerForm();

		return result;
	}

	public Trainer save(final Trainer trainer) {
		Assert.notNull(trainer);
		Trainer result;

		result = this.trainerRepository.save(trainer);
		return result;
	}

	// Other business methods -------------------------------------------------

	public Trainer findByPrincipal() {
		Trainer result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccountId(userAccount.getId());
		Assert.notNull(result);

		return result;
	}

	public Trainer findByUserAccountId(final int userAccountId) {
		Assert.isTrue(userAccountId != 0);

		Trainer result;

		result = this.trainerRepository.findByUserAccountId(userAccountId);

		return result;
	}

	public Trainer reconstruct(final TrainerForm trainerForm, final BindingResult binding) throws IOException {
		Assert.notNull(trainerForm);
		Trainer result;
		String password;

		if (trainerForm.getId() == 0) {
			result = this.create();

			if (trainerForm.getPassword().equals(trainerForm.getRepeatPassword()) && trainerForm.getPassword() != null && !trainerForm.getPassword().isEmpty()) {
				password = this.actorService.hashPassword(trainerForm.getPassword());
				result.getUserAccount().setPassword(password);
				result.getUserAccount().setUsername(trainerForm.getUserName());
			} else {
				FieldError fieldError;
				final String[] codes = {
					"trainer.password.error"
				};
				fieldError = new FieldError("trainerForm", "password", result.getUserAccount().getPassword(), false, codes, null, "");
				binding.addError(fieldError);
			}

			result.getUserAccount().setEnabled(true);

		} else
			result = this.findOne(trainerForm.getId());

		result.setCoordinates(trainerForm.getCoordinates());
		result.setEmail(trainerForm.getEmail());
		result.setName(trainerForm.getName());
		result.setNid(trainerForm.getNid());
		result.setPhone(trainerForm.getPhone());
		result.setPicture(trainerForm.getPicture());
		result.setSurname(trainerForm.getSurname());

		final MultipartFile userImage = trainerForm.getUserImage();
		result.setPicture(userImage.getBytes());

		if (result.getPicture().length == 0) {
			FieldError fieldError;
			final String[] codes = {
				"trainer.register.picture.empty.error"
			};
			fieldError = new FieldError("trainerForm", "userImage", trainerForm.getUserImage(), false, codes, null, "");
			binding.addError(fieldError);
		}

		this.validator.validate(result, binding);

		return result;
	}

	public TrainerForm toFormObject(final Trainer trainer) {
		Assert.notNull(trainer);
		final TrainerForm result = new TrainerForm();

		result.setCoordinates(trainer.getCoordinates());
		result.setEmail(trainer.getEmail());
		result.setId(trainer.getId());
		result.setName(trainer.getName());
		result.setNid(trainer.getNid());
		result.setPhone(trainer.getPhone());
		result.setPicture(trainer.getPicture());
		result.setSurname(trainer.getSurname());
		result.setReceipts(trainer.getReceipts());
		result.setOffers(result.getOffers());
		result.setAvgRating(trainer.getAvgRating());

		return result;
	}
}
