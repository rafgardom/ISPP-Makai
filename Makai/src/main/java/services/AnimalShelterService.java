
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

import repositories.AnimalShelterRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import utilities.UserNamePasswordValidator;
import domain.Actor;
import domain.AnimalShelter;
import forms.AnimalShelterForm;

@Service
@Transactional
public class AnimalShelterService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private AnimalShelterRepository	animalShelterRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService			actorService;

	@Autowired
	private Validator				validator;


	// Constructors------------------------------------------------------------
	public AnimalShelterService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public AnimalShelter findOne(final int animalShelterId) {
		AnimalShelter result;

		result = this.animalShelterRepository.findOne(animalShelterId);
		Assert.notNull(result);

		return result;
	}

	public Collection<AnimalShelter> findAll() {
		Collection<AnimalShelter> result;

		result = this.animalShelterRepository.findAll();

		return result;
	}

	public AnimalShelter create() {
		final UserAccount userAccount = new UserAccount();

		final Collection<Authority> authorities = new ArrayList<Authority>();
		final Authority a = new Authority();
		a.setAuthority(Authority.ANIMALSHELTER);
		authorities.add(a);
		userAccount.setAuthorities(authorities);

		final AnimalShelter result = new AnimalShelter();

		result.setUserAccount(userAccount);

		return result;
	}

	public AnimalShelterForm createForm() {
		AnimalShelterForm result;
		result = new AnimalShelterForm();

		return result;
	}

	public AnimalShelter save(final AnimalShelter animalShelter) {
		Assert.notNull(animalShelter);
		AnimalShelter result;

		result = this.animalShelterRepository.save(animalShelter);
		return result;
	}

	// Other business methods -------------------------------------------------

	public AnimalShelter findByPrincipal() {
		AnimalShelter result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccountId(userAccount.getId());
		Assert.notNull(result);

		return result;
	}

	public AnimalShelter findByUserAccountId(final int userAccountId) {
		Assert.isTrue(userAccountId != 0);

		AnimalShelter result;

		result = this.animalShelterRepository.findByUserAccountId(userAccountId);

		return result;
	}

	public AnimalShelter reconstruct(final AnimalShelterForm animalShelterForm, final BindingResult binding) throws IOException {
		Assert.notNull(animalShelterForm);
		AnimalShelter result;
		String password;
		final UserNamePasswordValidator accountValidator = new UserNamePasswordValidator();
		boolean userNameValidator = true;
		boolean passwordValidator = false;

		if (animalShelterForm.getId() == 0) {
			result = this.create();

			if (accountValidator.passwordValidate(animalShelterForm.getPassword()))
				passwordValidator = true;

			if (accountValidator.userNameValidate(animalShelterForm.getUsername())) {
				userNameValidator = false;
				FieldError fieldError;
				final String[] codes = {
					"animalShelter.userName.error"
				};
				fieldError = new FieldError("animalShelterForm", "username", result.getUserAccount().getUsername(), false, codes, null, "");
				binding.addError(fieldError);
			}
			if (animalShelterForm.getPassword().equals(animalShelterForm.getRepeatPassword()) && animalShelterForm.getPassword() != null && !animalShelterForm.getPassword().isEmpty() && !animalShelterForm.getPassword().contains(" ") && passwordValidator) {
				password = this.actorService.hashPassword(animalShelterForm.getPassword());
				result.getUserAccount().setPassword(password);
			} else {
				FieldError fieldError;
				final String[] codes = {
					"animalShelter.password.error"
				};
				fieldError = new FieldError("animalShelterForm", "password", result.getUserAccount().getPassword(), false, codes, null, "");
				binding.addError(fieldError);

			}

			if (!animalShelterForm.getUsername().contains(" ") && userNameValidator && !animalShelterForm.getUsername().isEmpty())
				result.getUserAccount().setUsername(animalShelterForm.getUsername());

			if (animalShelterForm.getUsername().contains(" ")) {
				FieldError fieldError;
				final String[] codes = {
					"animalShelter.username.error"
				};
				fieldError = new FieldError("animalShelterForm", "username", result.getUserAccount().getUsername(), false, codes, null, "");
				binding.addError(fieldError);
			}

			final Actor actor = this.actorService.findByusername(animalShelterForm.getUsername());

			if (actor != null) {
				FieldError fieldError;
				final String[] codes = {
					"animalShelter.username.unique.error"
				};
				fieldError = new FieldError("animalShelterForm", "username", result.getUserAccount().getUsername(), false, codes, null, "");
				binding.addError(fieldError);
			}

			result.getUserAccount().setEnabled(true);

		} else
			result = this.findOne(animalShelterForm.getId());

		result.setCoordinates(animalShelterForm.getCoordinates());
		result.setEmail(animalShelterForm.getEmail());
		result.setName(animalShelterForm.getName());
		result.setPhone(animalShelterForm.getPhone());
		result.setPicture(animalShelterForm.getPicture());

		final MultipartFile userImage = animalShelterForm.getUserImage();
		result.setPicture(userImage.getBytes());

		if (result.getPicture().length == 0) {
			FieldError fieldError;
			final String[] codes = {
				"customer.register.picture.empty.error"
			};
			fieldError = new FieldError("animalShelterForm", "userImage", animalShelterForm.getUserImage(), false, codes, null, "");
			binding.addError(fieldError);
		}

		this.validator.validate(result, binding);

		return result;
	}

	public AnimalShelterForm toObjectForm(final AnimalShelter animalShelter) {
		Assert.notNull(animalShelter);
		final AnimalShelterForm result = new AnimalShelterForm();

		result.setCoordinates(animalShelter.getCoordinates());
		result.setEmail(animalShelter.getEmail());
		result.setId(animalShelter.getId());
		result.setName(animalShelter.getName());
		result.setPhone(animalShelter.getPhone());
		result.setPicture(animalShelter.getPicture());

		return result;
	}
}
