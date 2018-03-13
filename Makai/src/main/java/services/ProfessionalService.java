
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import repositories.ProfessionalRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Professional;
import forms.ProfessionalForm;

@Service
@Transactional
public class ProfessionalService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private ProfessionalRepository	professionalRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService			actorService;


	//	@Autowired
	//	private Validator			validator;

	// Constructors------------------------------------------------------------
	public ProfessionalService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Professional findOne(final int professionalId) {
		Professional result;

		result = this.professionalRepository.findOne(professionalId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Professional> findAll() {
		Collection<Professional> result;

		result = this.professionalRepository.findAll();

		return result;
	}

	public Professional create() {
		final UserAccount userAccount = new UserAccount();

		final Collection<Authority> authorities = new ArrayList<Authority>();
		final Authority a = new Authority();
		a.setAuthority(Authority.PROFESSIONAL);
		authorities.add(a);
		userAccount.setAuthorities(authorities);

		final Professional result = new Professional();

		result.setUserAccount(userAccount);
		result.setAvgRating(0.0);

		return result;
	}

	public ProfessionalForm createForm() {
		ProfessionalForm result;
		result = new ProfessionalForm();

		return result;
	}

	public Professional save(final Professional professional) {
		Assert.notNull(professional);
		Professional result;

		result = this.professionalRepository.save(professional);
		return result;
	}

	// Other business methods -------------------------------------------------

	public Professional findByPrincipal() {
		Professional result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccountId(userAccount.getId());
		Assert.notNull(result);

		return result;
	}

	public Professional findByUserAccountId(final int userAccountId) {
		Assert.isTrue(userAccountId != 0);

		Professional result;

		result = this.professionalRepository.findByUserAccountId(userAccountId);

		return result;
	}

	public Professional reconstruct(final ProfessionalForm professionalForm, final BindingResult binding) {
		Assert.notNull(professionalForm);
		Professional result;
		String password;

		if (professionalForm.getId() == 0) {
			result = this.create();

			if (professionalForm.getPassword() == professionalForm.getRepeatPassword() && professionalForm.getPassword() != null && !professionalForm.getPassword().isEmpty()) {
				password = this.actorService.hashPassword(professionalForm.getPassword());
				result.getUserAccount().setPassword(password);
			}
		} else
			result = this.findOne(professionalForm.getId());

		result.setCoordinates(professionalForm.getCoordinates());
		result.setEmail(professionalForm.getEmail());
		result.setName(professionalForm.getName());
		result.setPhone(professionalForm.getPhone());
		result.setPicture(professionalForm.getPicture());

		//		this.validator.validate(result, binding);

		return result;
	}

	public ProfessionalForm toFormObject(final Professional professional) {
		Assert.notNull(professional);
		final ProfessionalForm result = new ProfessionalForm();

		result.setCoordinates(professional.getCoordinates());
		result.setEmail(professional.getEmail());
		result.setId(professional.getId());
		result.setName(professional.getName());
		result.setPhone(professional.getPhone());
		result.setPicture(professional.getPicture());

		return result;
	}
}
