
package services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import repositories.AdvertisingRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import utilities.UserNamePasswordValidator;
import domain.Actor;
import domain.Advertising;
import forms.AdvertisingForm;

@Service
@Transactional
public class AdvertisingService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private AdvertisingRepository	advertisingRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService			actorService;

	@Autowired
	private Validator				validator;


	// Constructors------------------------------------------------------------
	public AdvertisingService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Advertising findOne(final int advertisingId) {
		Advertising result;

		result = this.advertisingRepository.findOne(advertisingId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Advertising> findAll() {
		Collection<Advertising> result;

		result = this.advertisingRepository.findAll();

		return result;
	}

	public Advertising create() {
		final UserAccount userAccount = new UserAccount();

		final Collection<Authority> authorities = new ArrayList<Authority>();
		final Authority a = new Authority();
		a.setAuthority(Authority.ADVERTISING);
		authorities.add(a);
		userAccount.setAuthorities(authorities);

		final Advertising result = new Advertising();

		result.setUserAccount(userAccount);

		return result;
	}

	public AdvertisingForm createForm() {
		AdvertisingForm result;
		result = new AdvertisingForm();

		return result;
	}

	public Advertising save(final Advertising advertising) {
		Assert.notNull(advertising);
		Advertising result;

		result = this.advertisingRepository.save(advertising);
		return result;
	}

	// Other business methods -------------------------------------------------

	public Advertising findByPrincipal() {
		Advertising result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccountId(userAccount.getId());
		Assert.notNull(result);

		return result;
	}

	public Advertising findByUserAccountId(final int userAccountId) {
		Assert.isTrue(userAccountId != 0);

		Advertising result;

		result = this.advertisingRepository.findByUserAccountId(userAccountId);

		return result;
	}

	public Advertising reconstruct(final AdvertisingForm advertisingForm, final BindingResult binding) throws IOException {
		Assert.notNull(advertisingForm);
		Advertising result;
		String password;
		final UserNamePasswordValidator accountValidator = new UserNamePasswordValidator();
		boolean userNameValidator = true;
		boolean passwordValidator = false;

		if (advertisingForm.getId() == 0) {
			result = this.create();

			if (accountValidator.passwordValidate(advertisingForm.getPassword()))
				passwordValidator = true;

			if (accountValidator.userNameValidate(advertisingForm.getUsername())) {
				userNameValidator = false;
				FieldError fieldError;
				final String[] codes = {
					"advertising.userName.error"
				};
				fieldError = new FieldError("advertisingForm", "username", result.getUserAccount().getUsername(), false, codes, null, "");
				binding.addError(fieldError);
			}
			if (advertisingForm.getPassword().equals(advertisingForm.getRepeatPassword()) && advertisingForm.getPassword() != null && !advertisingForm.getPassword().isEmpty() && !advertisingForm.getPassword().contains(" ") && passwordValidator) {
				password = this.actorService.hashPassword(advertisingForm.getPassword());
				result.getUserAccount().setPassword(password);
			} else {
				FieldError fieldError;
				final String[] codes = {
					"advertising.password.error"
				};
				fieldError = new FieldError("advertisingForm", "password", result.getUserAccount().getPassword(), false, codes, null, "");
				binding.addError(fieldError);

			}

			if (!advertisingForm.getUsername().contains(" ") && userNameValidator && !advertisingForm.getUsername().isEmpty())
				result.getUserAccount().setUsername(advertisingForm.getUsername());

			if (advertisingForm.getUsername().contains(" ")) {
				FieldError fieldError;
				final String[] codes = {
					"advertising.username.error"
				};
				fieldError = new FieldError("advertisingForm", "username", result.getUserAccount().getUsername(), false, codes, null, "");
				binding.addError(fieldError);
			}

			final Actor actor = this.actorService.findByusername(advertisingForm.getUsername());

			if (actor != null) {
				FieldError fieldError;
				final String[] codes = {
					"advertising.username.unique.error"
				};
				fieldError = new FieldError("advertisingForm", "username", result.getUserAccount().getUsername(), false, codes, null, "");
				binding.addError(fieldError);
			}

			result.getUserAccount().setEnabled(true);

		} else
			result = this.findOne(advertisingForm.getId());

		result.setCoordinates(advertisingForm.getCoordinates());

		final Pattern coordinatePattern = Pattern.compile("^[a-zñÑA-Z]+(?:[\\s-][a-zñÑA-Z]+)*$");
		if (!result.getCoordinates().getState().isEmpty())
			if (!coordinatePattern.matcher(result.getCoordinates().getState()).matches()) {
				FieldError fieldError;
				final String[] codes = {
					"general.coordinates.state.error"
				};
				fieldError = new FieldError("advertisingForm", "coordinates.state", result.getCoordinates().getState(), false, codes, null, "");
				binding.addError(fieldError);
			}
		if (!result.getCoordinates().getProvince().isEmpty())
			if (!coordinatePattern.matcher(result.getCoordinates().getProvince()).matches()) {
				FieldError fieldError;
				final String[] codes = {
					"general.coordinates.province.error"
				};
				fieldError = new FieldError("animalShelterForm", "coordinates.province", result.getCoordinates().getProvince(), false, codes, null, "");
				binding.addError(fieldError);
			}

		result.setEmail(advertisingForm.getEmail());
		result.setName(advertisingForm.getName());
		result.setPhone(advertisingForm.getPhone());
		result.setPicture(advertisingForm.getPicture());

		final MultipartFile userImage = advertisingForm.getUserImage();
		result.setPicture(userImage.getBytes());

		if (result.getPicture().length == 0) {
			FieldError fieldError;
			final String[] codes = {
				"customer.register.picture.empty.error"
			};
			fieldError = new FieldError("advertisingForm", "userImage", advertisingForm.getUserImage(), false, codes, null, "");
			binding.addError(fieldError);
		}

		this.validator.validate(result, binding);

		return result;
	}

	public AdvertisingForm toObjectForm(final Advertising advertising) {
		Assert.notNull(advertising);
		final AdvertisingForm result = new AdvertisingForm();

		result.setCoordinates(advertising.getCoordinates());
		result.setEmail(advertising.getEmail());
		result.setId(advertising.getId());
		result.setName(advertising.getName());
		result.setPhone(advertising.getPhone());
		result.setPicture(advertising.getPicture());

		return result;
	}
}
