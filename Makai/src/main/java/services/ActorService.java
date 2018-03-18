
package services;

import java.io.IOException;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
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
import domain.Actor;
import domain.Administrator;
import domain.Customer;
import domain.Trainer;
import forms.ProfileForm;

@Service
@Transactional
public class ActorService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private ActorRepository			actorRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private TrainerService			trainerService;

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

	// Other business methods -------------------------------------------------

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
		Md5PasswordEncoder encoder;

		encoder = new Md5PasswordEncoder();
		result = encoder.encodePassword(password, null);

		return result;
	}

	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver getCommonsMultipartResolver() {
		final CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(20971520);   // 20MB
		multipartResolver.setMaxInMemorySize(1048576);  // 1MB
		return multipartResolver;
	}

	public Actor reconstructEdit(final ProfileForm profileForm, final BindingResult binding) throws IOException {
		Assert.notNull(profileForm);
		final Actor principal;
		Actor result = null;

		principal = this.findByPrincipal();

		if (this.checkAuthority(principal, "CUSTOMER")) {
			Customer customer;

			customer = this.customerService.findByPrincipal();
			customer.setSurname(profileForm.getSurname());
			customer.setNid(profileForm.getNid());
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

		System.out.println(profileForm.getPicture());

		if (profileForm.getPicture() != null)
			result.setPicture(profileForm.getPicture());

		if (profileForm.getPassword() != null && profileForm.getPassword() != profileForm.getRepeatPassword()) {
			FieldError fieldError;
			final String[] codes = {
				"profile.repeatPasword.error"
			};
			fieldError = new FieldError("profileForm", "repeatPassword", profileForm.getPassword(), false, codes, null, "");
			binding.addError(fieldError);
		} else if (profileForm.getPassword() != null)
			result.getUserAccount().setPassword(profileForm.getPassword());

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
			result.setNid(customer.getNid());

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

}
