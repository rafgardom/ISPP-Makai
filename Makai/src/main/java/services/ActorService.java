
package services;

import java.io.IOException;
import java.util.Calendar;
import java.util.Collection;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import utilities.UserNamePasswordValidator;
import domain.Actor;
import domain.Administrator;
import domain.Customer;
import domain.Trainer;
import domain.Training;
import domain.Travel;
import forms.ProfileForm;

@Service
@Transactional
public class ActorService {

	// Managed repository ----------------------------------------------------
	@Autowired
	private ActorRepository			actorRepository;

	// Supporting services ---------------------------------------------------

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private TrainerService			trainerService;

	@Autowired
	private TrainingService			trainingService;

	@Autowired
	private TravelService			travelService;

	@Autowired
	private Validator				validator;


	// Constructors------------------------------------------------------------
	public ActorService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Actor findOne(final int actorId) {
		Actor result;

		result = this.actorRepository.findOne(actorId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Actor> findAll() {
		Collection<Actor> result;

		result = this.actorRepository.findAll();

		return result;
	}

	public Collection<Actor> findAllActorsWithoutAdmin() {
		Collection<Actor> result;
		Administrator admin;
		admin = this.administratorService.findByPrincipal();

		result = this.actorRepository.findAll();
		result.remove(admin);

		return result;
	}

	public Actor save(Actor actor) {
		Assert.notNull(actor);

		Actor aux;
		Md5PasswordEncoder encoder;
		String hash;
		UserAccount userAccount;

		userAccount = actor.getUserAccount();
		Assert.notNull(userAccount.getUsername());
		Assert.notNull(userAccount.getPassword());

		aux = this.findOne(actor.getId());

		if (!(aux.getUserAccount().getPassword().equals(userAccount.getPassword()))) {
			encoder = new Md5PasswordEncoder();
			hash = encoder.encodePassword(actor.getUserAccount().getPassword(), null);

			actor.getUserAccount().setPassword(hash);
		}

		actor = this.actorRepository.save(actor);

		return actor;
	}

	// Other business methods -----------------------------------------------

	public Actor findByPrincipal() {
		Actor result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccountId(userAccount.getId());
		Assert.notNull(result);

		return result;
	}

	public Actor findByUserAccountId(final int userAccountId) {
		Assert.isTrue(userAccountId != 0);

		Actor result;

		result = this.actorRepository.findByUserAccountId(userAccountId);

		return result;
	}

	public Collection<Actor> findAllNotAdmin() {
		Collection<Actor> result;

		result = this.actorRepository.findAllNotAdmin();

		return result;
	}

	public Boolean checkAuthority(final Actor actor, final String authority) {
		Boolean result;
		Collection<Authority> authorities;

		authorities = actor.getUserAccount().getAuthorities();

		result = false;
		for (final Authority a : authorities)
			result = result || (a.getAuthority().equals(authority));

		return result;
	}

	public String hashPassword(final String password) {
		String result;
		ShaPasswordEncoder encoder;

		encoder = new ShaPasswordEncoder(256);
		result = encoder.encodePassword(password, null);

		return result;
	}

	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver getCommonsMultipartResolver() {
		final CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(20971520);   // 20MB
		multipartResolver.setMaxInMemorySize(10485760);  // 1MB
		return multipartResolver;
	}

	public Actor reconstructEdit(final ProfileForm profileForm, final BindingResult binding) throws IOException {
		Assert.notNull(profileForm);
		final Actor principal;
		Actor result = null;
		final UserNamePasswordValidator accountValidator = new UserNamePasswordValidator();
		boolean passwordValidator = false;

		principal = this.findByPrincipal();

		if (profileForm.getUserImage().getSize() > 2097152) {
			FieldError fieldError;
			final String[] codes = {
				"profile.register.picture.tooLong.error"
			};
			fieldError = new FieldError("profileForm", "userImage", profileForm.getUserImage(), false, codes, null, "");
			binding.addError(fieldError);
		}
		if (profileForm.getUserImage().getSize() > 0 && !profileForm.getUserImage().getContentType().contains("image")) {
			FieldError fieldError;
			final String[] codes = {
				"profile.picture.extension.error"
			};
			fieldError = new FieldError("profileForm", "userImage", profileForm.getUserImage(), false, codes, null, "");
			binding.addError(fieldError);
		}

		final Pattern coordinatePattern = Pattern.compile("^[a-zñÑá-úÁ-ÚA-Z]+(?:[\\s-][a-zñÑá-úÁ-ÚA-Z]+)*$");
		if (!profileForm.getCoordinates().getState().isEmpty())
			if (!coordinatePattern.matcher(profileForm.getCoordinates().getState()).matches()) {
				FieldError fieldError;
				final String[] codes = {
					"general.coordinates.state.error"
				};
				fieldError = new FieldError("profileForm", "coordinates.state", profileForm.getCoordinates().getState(), false, codes, null, "");
				binding.addError(fieldError);
			}
		if (!profileForm.getCoordinates().getProvince().isEmpty())
			if (!coordinatePattern.matcher(profileForm.getCoordinates().getProvince()).matches()) {
				FieldError fieldError;
				final String[] codes = {
					"general.coordinates.province.error"
				};
				fieldError = new FieldError("profileForm", "coordinates.province", profileForm.getCoordinates().getProvince(), false, codes, null, "");
				binding.addError(fieldError);
			}
		// comprobamos que no hayan espacios en el name
		if (profileForm.getName().replace(" ", "").isEmpty()) {
			FieldError fieldError;
			final String[] codes = {
				"org.hibernate.validator.constraints.NotBlank.message"
			};
			fieldError = new FieldError("profileForm", "name", profileForm.getName(), false, codes, null, "");
			binding.addError(fieldError);
		}
		Assert.isTrue(!profileForm.getName().replace(" ", "").isEmpty());

		if (this.checkAuthority(principal, "CUSTOMER")) {
			Customer customer;

			customer = this.customerService.findByPrincipal();
			customer.setSurname(profileForm.getSurname());
			result = customer;

		} else if (this.checkAuthority(principal, "ADMIN")) {
			Administrator administrator;

			administrator = this.administratorService.findByPrincipal();
			administrator.setSurname(profileForm.getSurname());
			administrator.setNid(profileForm.getNid());
			result = administrator;

		} else if (this.checkAuthority(principal, "TRAINER")) {
			Trainer trainer;

			trainer = this.trainerService.findByPrincipal();
			trainer.setSurname(profileForm.getSurname());
			trainer.setNid(profileForm.getNid());
			result = trainer;
		} else
			result = this.findByPrincipal();

		result.setCoordinates(profileForm.getCoordinates());
		result.setEmail(profileForm.getEmail());
		result.setName(profileForm.getName());
		result.setPhone(profileForm.getPhone());

		if (profileForm.getPicture() != null)
			result.setPicture(profileForm.getPicture());

		if (profileForm.getPassword() != null && !profileForm.getPassword().isEmpty()) {
			if (accountValidator.passwordValidate(profileForm.getPassword()))
				passwordValidator = true;

			if (!profileForm.getPassword().equals(profileForm.getRepeatPassword())) {

				FieldError fieldError;
				final String[] codes = {
					"profile.repeatPasword.error"
				};
				fieldError = new FieldError("profileForm", "repeatPassword", profileForm.getPassword(), false, codes, null, "");
				binding.addError(fieldError);
			} else if (profileForm.getPassword().contains(" ") || !passwordValidator) {
				FieldError fieldError;
				final String[] codes = {
					"profile.password.error"
				};
				fieldError = new FieldError("profileForm", "password", profileForm.getPassword(), false, codes, null, "");
				binding.addError(fieldError);
			} else
				result.getUserAccount().setPassword(this.hashPassword(profileForm.getPassword()));
		}
		this.validator.validate(result, binding);

		return result;

	}
	public ProfileForm principalToFormObject() {
		Actor actor;
		final ProfileForm result = new ProfileForm();

		actor = this.findByPrincipal();
		Assert.notNull(actor);

		if (this.checkAuthority(actor, "CUSTOMER")) {
			Customer customer;

			customer = this.customerService.findByPrincipal();
			result.setSurname(customer.getSurname());

		} else if (this.checkAuthority(actor, "ADMIN")) {
			Administrator administrator;

			administrator = this.administratorService.findByPrincipal();
			result.setSurname(administrator.getSurname());
			result.setNid(administrator.getNid());

		} else if (this.checkAuthority(actor, "TRAINER")) {
			Trainer trainer;

			trainer = this.trainerService.findByPrincipal();
			result.setSurname(trainer.getSurname());
			result.setNid(trainer.getNid());
		}

		result.setCoordinates(actor.getCoordinates());
		result.setEmail(actor.getEmail());
		result.setName(actor.getName());
		result.setPhone(actor.getPhone());
		result.setPicture(actor.getPicture());
		result.setUserImage(null);

		return result;
	}

	public Actor ban(Actor actor) {
		Assert.notNull(actor);

		Calendar today;
		today = Calendar.getInstance();

		final Administrator administrator;

		administrator = this.administratorService.findByPrincipal();
		Assert.notNull(administrator);
		Assert.isTrue(!actor.equals(administrator));

		if (this.checkAuthority(actor, "TRAINER")) {
			Collection<Training> trainings;
			trainings = this.trainingService.findByTrainerId(actor.getId());
			if (trainings != null)
				for (final Training tr : trainings)
					this.trainingService.delete(tr);
		} else if (this.checkAuthority(actor, "CUSTOMER") || this.checkAuthority(actor, "PROFESSIONAL")) {
			Collection<Travel> travels;
			travels = this.travelService.findTravelByTransporterId(actor.getId());
			if (travels != null)
				for (final Travel tr : travels)
					if (!today.getTime().after(tr.getStartMoment()))
						this.travelService.delete(tr);
		}

		actor.getUserAccount().setEnabled(false);

		actor = this.actorRepository.save(actor);

		return actor;
	}

	public Actor unban(Actor actor) {
		Assert.notNull(actor);

		Administrator administrator;

		administrator = this.administratorService.findByPrincipal();
		Assert.notNull(administrator);
		Assert.isTrue(!actor.equals(administrator));

		actor.getUserAccount().setEnabled(true);

		actor = this.actorRepository.save(actor);

		return actor;
	}

	public Actor findByusername(final String username) {
		return this.actorRepository.findByusername(username);
	}

	public Actor findAdministrator() {
		Actor[] actors;
		Collection<Actor> administrators;

		administrators = this.actorRepository.findAdministrator();
		actors = administrators.toArray(new Actor[administrators.size()]);

		return actors[0];
	}
}
