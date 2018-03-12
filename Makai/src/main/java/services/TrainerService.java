
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.TrainerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Offer;
import domain.Receipt;
import domain.Trainer;

@Service
@Transactional
public class TrainerService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private TrainerRepository	trainerRepository;


	// Supporting services ----------------------------------------------------

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
}
