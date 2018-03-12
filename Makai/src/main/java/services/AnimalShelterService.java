
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AnimalShelterRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.AnimalShelter;

@Service
@Transactional
public class AnimalShelterService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private AnimalShelterRepository	animalShelterRepository;


	// Supporting services ----------------------------------------------------

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
}
