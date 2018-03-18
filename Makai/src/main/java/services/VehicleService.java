
package services;

import java.io.IOException;
import java.util.Calendar;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

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

	@Autowired
	private Validator			validator;


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

	public Vehicle reconstruct(final VehicleForm vehicleForm, final BindingResult binding) throws IOException {
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
		result.setSeats(vehicleForm.getSeats());
		result.setYear(vehicleForm.getYear());

		final MultipartFile userImage = vehicleForm.getUserImage();
		result.setPicture(userImage.getBytes());

		if (result.getPicture().length == 0) {
			FieldError fieldError;
			final String[] codes = {
				"customer.register.picture.empty.error"
			};
			fieldError = new FieldError("vehicleForm", "userImage", vehicleForm.getUserImage(), false, codes, null, "");
			binding.addError(fieldError);
		}

		this.validator.validate(result, binding);

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
