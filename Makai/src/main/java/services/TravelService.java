
package services;

import java.util.Calendar;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.TravelRepository;
import domain.Coordinates;
import domain.Transporter;
import domain.Travel;
import domain.Vehicle;
import forms.TravelForm;

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

	public TravelForm createForm() {
		final TravelForm result = new TravelForm();

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

	public Travel reconstruct(final TravelForm travelForm) {
		Assert.notNull(travelForm);
		Travel result;

		if (travelForm.getId() == 0)
			result = this.create();
		else
			result = this.findOne(travelForm.getId());
		;

		result.setOrigin(new Coordinates(travelForm.getCountryOrigin(), travelForm.getCityOrigin(), travelForm.getStateOrigin(), travelForm.getProvinceOrigin(), travelForm.getZip_codeOrigin()));
		result.setDestination(new Coordinates(travelForm.getCountryDestination(), travelForm.getCityDestination(), travelForm.getStateDestination(), travelForm.getProvinceDestination(), travelForm.getZip_codeDestination()));
		result.setStartMoment(travelForm.getStartMoment());
		result.setEndMoment(travelForm.getEndMoment());
		result.setHumanSeats(travelForm.getHumanSeats());
		result.setAnimalSeats(travelForm.getAnimalSeats());
		result.setVehicle(travelForm.getVehicle());

		return result;
	}

	public TravelForm toFormObject(final Travel travel) {
		Assert.notNull(travel);
		final TravelForm result = new TravelForm();

		result.setCountryOrigin(travel.getOrigin().getCountry());
		result.setCountryDestination(travel.getDestination().getCountry());
		result.setStateOrigin(travel.getOrigin().getState());
		result.setStateDestination(travel.getDestination().getState());
		result.setProvinceOrigin(travel.getOrigin().getProvince());
		result.setProvinceDestination(travel.getDestination().getProvince());
		result.setCityOrigin(travel.getOrigin().getCity());
		result.setCityDestination(travel.getDestination().getCity());
		result.setZip_codeOrigin(travel.getOrigin().getZip_code());
		result.setZip_codeDestination(travel.getDestination().getZip_code());
		result.setStartMoment(travel.getStartMoment());
		result.setAnimalSeats(travel.getAnimalSeats());
		result.setHumanSeats(travel.getHumanSeats());
		result.setVehicle(travel.getVehicle());

		return result;
	}
}
