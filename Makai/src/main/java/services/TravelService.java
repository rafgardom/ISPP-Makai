
package services;

import java.util.Calendar;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.TravelRepository;
import domain.Transporter;
import domain.Travel;
import domain.Vehicle;

@Service
@Transactional
public class TravelService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private TravelRepository	travelRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private TransporterService	transporterService;


	// Constructors------------------------------------------------------------
	public TravelService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Travel findOne(final int travelId) {
		Travel result;

		result = this.travelRepository.findOne(travelId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Travel> findAll() {
		Collection<Travel> result;

		result = this.travelRepository.findAll();

		return result;
	}

	public Travel create() {
		Travel result;
		Transporter principal;
		final Vehicle vehicle = null;

		principal = this.transporterService.findByPrincipal();
		Assert.notNull(principal);

		result = new Travel();
		result.setTransporterOwner(principal);
		result.setVehicle(vehicle);

		return result;
	}

	public Travel save(final Travel travel) {
		Assert.notNull(travel);
		Travel result;
		Transporter principal;
		Calendar today;

		principal = this.transporterService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(travel.getTransporterOwner().getId() == principal.getId());

		today = Calendar.getInstance();
		Assert.isTrue(today.getTime().before(travel.getStartMoment()));

		result = this.travelRepository.save(travel);

		return result;
	}

	public void delete(final Travel travel) {
		Transporter principal;
		Calendar today;

		Assert.notNull(travel);
		Assert.isTrue(travel.getId() != 0);

		principal = this.transporterService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(travel.getTransporterOwner().getId() == principal.getId());

		today = Calendar.getInstance();
		Assert.isTrue(today.getTime().after(travel.getStartMoment()));

		this.travelRepository.delete(travel);
	}
}
