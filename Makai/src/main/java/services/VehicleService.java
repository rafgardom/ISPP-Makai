
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

}
