
package services;

import java.util.Calendar;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.VehicleRepository;
import domain.Transporter;
import domain.Vehicle;
import forms.VehicleForm;

@Service
@Transactional
public class VehicleService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private VehicleRepository	vehicleRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private TransporterService	transporterService;


	// Constructors------------------------------------------------------------
	public VehicleService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Vehicle findOne(final int vehicleId) {
		Vehicle result;

		result = this.vehicleRepository.findOne(vehicleId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Vehicle> findAll() {
		Collection<Vehicle> result;

		result = this.vehicleRepository.findAll();

		return result;
	}

	public VehicleForm createForm() {
		final VehicleForm result = new VehicleForm();

		return result;
	}

	public Vehicle create() {
		Vehicle result;
		Transporter principal;

		principal = this.transporterService.findByPrincipal();
		Assert.notNull(principal);

		result = new Vehicle();
		result.setTransporter(principal);

		return result;
	}

	@SuppressWarnings("deprecation")
	public Vehicle save(final Vehicle vehicle) {
		Assert.notNull(vehicle);
		Vehicle result;
		Transporter principal;
		Calendar today;

		principal = this.transporterService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(vehicle.getTransporter().getId() == principal.getId());

		// Hacer comprobacion del año
		today = Calendar.getInstance();
		Assert.isTrue(today.getTime().getYear() >= vehicle.getYear());

		result = this.vehicleRepository.save(vehicle);

		return result;
	}

	public void delete(final Vehicle vehicle) {
		Assert.notNull(vehicle);
		Transporter principal;

		principal = this.transporterService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(vehicle.getTransporter().getId() == principal.getId());
		Assert.isTrue(!vehicle.getIsActived());

		this.vehicleRepository.delete(vehicle);
	}

	public Vehicle reconstruct(final VehicleForm vehicleForm) {
		Assert.notNull(vehicleForm);
		Vehicle result;

		if (vehicleForm.getId() == 0)
			result = this.create();
		else
			result = this.findOne(vehicleForm.getId());

		result.setAccommodation(vehicleForm.getAccommodation());
		result.setBrand(vehicleForm.getBrand());
		result.setCarType(vehicleForm.getCarType());
		result.setColor(vehicleForm.getColor());
		result.setDescription(vehicleForm.getDescription());
		result.setId(vehicleForm.getId());
		result.setLicense(vehicleForm.getLicense());
		result.setPicture(vehicleForm.getPicture());
		result.setSeats(vehicleForm.getSeats());
		result.setYear(vehicleForm.getYear());

		return result;
	}

	public VehicleForm toFormObject(final Vehicle vehicle) {
		Assert.notNull(vehicle);
		final VehicleForm result = new VehicleForm();

		result.setAccommodation(vehicle.getAccommodation());
		result.setBrand(vehicle.getBrand());
		result.setCarType(vehicle.getCarType());
		result.setColor(vehicle.getColor());
		result.setDescription(vehicle.getDescription());
		result.setId(vehicle.getId());
		result.setLicense(vehicle.getLicense());
		result.setPicture(vehicle.getPicture());
		result.setSeats(vehicle.getSeats());
		result.setYear(vehicle.getYear());

		return result;
	}

}
