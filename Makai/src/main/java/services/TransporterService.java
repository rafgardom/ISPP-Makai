
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.TransporterRepository;
import security.LoginService;
import security.UserAccount;
import domain.Transporter;

@Service
@Transactional
public class TransporterService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private TransporterRepository	transporterRepository;


	// Supporting services ----------------------------------------------------

	// Constructors------------------------------------------------------------
	public TransporterService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Transporter findOne(final int transporterId) {
		Transporter result;

		result = this.transporterRepository.findOne(transporterId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Transporter> findAll() {
		Collection<Transporter> result;

		result = this.transporterRepository.findAll();

		return result;
	}

	// Other business methods -------------------------------------------------

	public Transporter findByPrincipal() {
		Transporter result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccountId(userAccount.getId());
		Assert.notNull(result);

		return result;
	}

	public Transporter findByUserAccountId(final int userAccountId) {
		Assert.isTrue(userAccountId != 0);

		Transporter result;

		result = this.transporterRepository.findByUserAccountId(userAccountId);

		return result;
	}
	public Collection<Transporter> findPassengersByTravel(final int travelId) {
		return this.transporterRepository.findPassengersByTravel(travelId);
	}
}
