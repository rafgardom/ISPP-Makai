
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ProfessionalRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Professional;

@Service
@Transactional
public class ProfessionalService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private ProfessionalRepository	professionalRepository;


	// Supporting services ----------------------------------------------------

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
}
